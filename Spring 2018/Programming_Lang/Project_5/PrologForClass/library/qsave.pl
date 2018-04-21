/*  $Id: qsave.pl,v 1.35 2004/09/30 10:41:35 jan Exp $

    Part of SWI-Prolog

    Author:        Jan Wielemaker
    E-mail:        jan@swi.psy.uva.nl
    WWW:           http://www.swi-prolog.org
    Copyright (C): 1985-2002, University of Amsterdam

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    As a special exception, if you link this library with other files,
    compiled with a Free Software compiler, to produce an executable, this
    library does not by itself cause the resulting executable to be covered
    by the GNU General Public License. This exception does not however
    invalidate any other reasons why the executable file might be covered by
    the GNU General Public License.
*/

:- module(qsave,
	  [ qsave_program/1
	  , qsave_program/2
	  ]).
:- use_module(library(lists)).

:- module_transparent
	qsave_program/1,
	qsave_program/2.

:- system_mode(on).

:- dynamic verbose/1.

%	qsave_program(+File, +[Options ...])
%
%	Make a saved state in file `File'.

qsave_program(File) :-
	qsave_program(File, []).

qsave_program(FileSpec, Options0) :-
	check_options(Options0),
	strip_module(FileSpec, Module, FileBase),
	exe_file(FileBase, File),
	option(Options0, autoload/true,	   Autoload,  Options1),
	option(Options1, map/[],	   Map,	      Options2),
	option(Options2, goal/[],	   GoalTerm,  Options3),
	option(Options3, op/save,	   SaveOps,   Options4),
	option(Options4, class/runtime,	   SaveClass, Options5),
	option(Options5, initfile/DefInit, InitFile,  Options6),
	default_init_file(SaveClass, DefInit),
	(   GoalTerm == []
	->  Options = Options6,
	    flag('$banner_goal', BannerGoal, BannerGoal),
	    define_predicate(user:BannerGoal)
	;   term_to_atom(Module:GoalTerm, GoalAtom),
	    term_to_atom(GT, GoalAtom),
	    define_predicate(user:GT),
	    Options = [goal=GoalAtom|Options6]
	),
	(   Autoload == true
	->  save_autoload
	;   true
	),
	open_map(Map),
	set_prolog_flag(saved_program, true),
	set_prolog_flag(saved_program_class, SaveClass),
	(   exists_file(File)
	->  delete_file(File)
	;   true
	),
	$rc_open_archive(File, RC),
	make_header(RC, SaveClass, Options),
	save_options(RC, [ class(SaveClass),
			   init_file(InitFile)
			 | Options
			 ]),
	save_resources(RC, SaveClass),
	$rc_open(RC, $state, $prolog, write, StateFd),
	$open_wic(StateFd),
	system_mode(on),		% generate system modules too
	save_modules(SaveClass),
	save_records,
	save_flags,
	save_imports,
	save_prolog_flags,
	save_operators(SaveOps),
	save_format_predicates,
	save_functions,
%	save_foreign_libraries,
	system_mode(off),
	$close_wic,
	close(StateFd),
	$rc_close_archive(RC),
	$mark_executable(File),
	close_map.

exe_file(Base, Exe) :-
	current_prolog_flag(windows, true),
	file_name_extension(_, '', Base), !,
	file_name_extension(Base, exe, Exe).
exe_file(Exe, Exe).

default_init_file(runtime, none) :- !.
default_init_file(_,       InitFile) :-
	$option(init_file, InitFile, InitFile).


		 /*******************************
		 *	     HEADER		*
		 *******************************/

make_header(RC, _, Options) :-
	option(Options, emulator/(-), OptVal, _),
	OptVal \== -, !,
	absolute_file_name(OptVal, [access(read)], Emulator),
	$rc_append_file(RC, $header, $rc, none, Emulator).
make_header(RC, _, Options) :-
	(   current_prolog_flag(windows, true)
	->  DefStandAlone = true
	;   DefStandAlone = false
	),
	option(Options, stand_alone/DefStandAlone, OptVal, _),
	OptVal == true, !,
	current_prolog_flag(executable, Executable),
	$rc_append_file(RC, $header, $rc, none, Executable).
make_header(RC, SaveClass, _Options) :-
	current_prolog_flag(unix, true),
	current_prolog_flag(executable, Executable),
	$rc_open(RC, $header, $rc, write, Fd),
	format(Fd, '#!/bin/sh~n', []),
	format(Fd, '# SWI-Prolog saved state~n', []),
	(   SaveClass == runtime
	->  ArgSep = ' -- '
	;   ArgSep = ' '
	),
	format(Fd, 'exec ${SWIPL-~w} -x "$0"~w"$@"~n~n', [Executable, ArgSep]),
	close(Fd).
make_header(_, _, _).


		 /*******************************
		 *	     OPTIONS		*
		 *******************************/

min_stack(local,    32).
min_stack(global,   16).
min_stack(trail,    16).
min_stack(argument, 16).

convert_option(Stack, Val, NewVal) :-	% stack-sizes are in K-bytes
	min_stack(Stack, Min), !,
	(   Val == 0
	->  NewVal = Val
	;   NewVal is max(Min, Val*1024)
	).
convert_option(_, Val, Val).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Save the options in the $options resource.   The home directory is saved
for development saves, so it keeps refering to the development home.

The script-file (-s script) is not saved at all. I think this is fine to
avoid a save-script loading itself.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

save_options(RC, Options) :-
	$rc_open(RC, $options, $prolog, write, Fd),
	(   $option(OptionName, OptionVal0, _),
	        (   OptionName == home	% save home if not runtime
		->  \+ memberchk(class(runtime), Options)
		;   true
		),
	        \+ OptionName == script_file,
	        option(Options, OptionName/_, OptionVal1, _),
	        (   var(OptionVal1)	% used the default
		->  OptionVal = OptionVal0
		;   convert_option(OptionName, OptionVal1, OptionVal)
		),
	        format(Fd, '~w=~w~n', [OptionName, OptionVal]),
	    fail
	;   true
	),
	close(Fd).

		 /*******************************
		 *	     RESOURCES		*
		 *******************************/

save_resources(_RC, development) :- !.
save_resources(RC, _SaveClass) :-
	feedback('~nRESOURCES~n~n', []),
	copy_resources(RC),
	(   current_predicate(_, M:resource(_,_,_)),
	    forall(M:resource(Name, Class, FileSpec),
		   (   mkrcname(M, Name, RcName),
		       save_resource(RC, RcName, Class, FileSpec)
		   )),
	    fail
	;   true
	).

mkrcname(user, Name, Name) :- !.
mkrcname(M, Name, RcName) :-
	concat_atom([M, :, Name], RcName).

save_resource(RC, Name, Class, FileSpec) :-
	absolute_file_name(FileSpec,
			   [ access(read),
			     file_errors(fail)
			   ], File), !,
	feedback('~t~8|~w~t~32|~w~t~48|~w~n',
		 [Name, Class, File]),
	$rc_append_file(RC, Name, Class, none, File).
save_resource(RC, Name, Class, _) :-
	$rc_handle(SystemRC),
	copy_resource(SystemRC, RC, Name, Class), !.
save_resource(_, Name, Class, FileSpec) :-
	print_message(warning,
		      error(existence_error(resource,
					    resource(Name, Class, FileSpec)),
			    _)).

copy_resources(ToRC) :-
	$rc_handle(FromRC),
	$rc_members(FromRC, List),
	(   member(rc(Name, Class), List),
	    \+ user:resource(Name, Class, _),
	    \+ reserved_resource(Name, Class),
	    copy_resource(FromRC, ToRC, Name, Class),
	    fail
	;   true
	).

reserved_resource($header,	$rc).
reserved_resource($state,	$prolog).
reserved_resource($options,	$prolog).

copy_resource(FromRC, ToRC, Name, Class) :-
	$rc_open(FromRC, Name, Class, read, FdIn),
	$rc_open(ToRC,	 Name, Class, write, FdOut),
	feedback('~t~8|~w~t~24|~w~t~40|~w~n',
		 [Name, Class, '<Copied from running state>']),
	copy_stream_data(FdIn, FdOut),
	close(FdOut),
	close(FdIn).


		 /*******************************
		 *	      MODULES		*
		 *******************************/

save_modules(SaveClass) :-
	forall(special_module(X),
	       save_module(X, SaveClass)),
	forall((current_module(X), \+ special_module(X)),
	       save_module(X, SaveClass)).

special_module(system).
special_module(user).

define_predicate(Head) :-
	'$define_predicate'(Head), !.	% autoloader
define_predicate(Head) :-
	strip_module(Head, _, Term),
	functor(Term, Name, Arity),
	throw(error(existence_error(procedure, Name/Arity), _)).


		 /*******************************
		 *	      AUTOLOAD		*
		 *******************************/

save_autoload :-
	autoload.

		 /*******************************
		 *	       MODULES		*
		 *******************************/

%	save_module(+Module, +SaveClass)
%
%	Saves a module

save_module(M, SaveClass) :-
	$qlf_start_module(M),
	feedback('~n~nMODULE ~w~n', [M]),
	save_unknown(M),
	(   P = (M:H),
	    current_predicate(_, P),
	    \+ predicate_property(P, imported_from(_)),
	    \+ predicate_property(P, foreign),
	    functor(H, F, A),
	    feedback('~nsaving ~w/~d ', [F, A]),
	    (	H = resource(_,_,_),
		SaveClass \== development
	    ->	save_attribute(P, (dynamic)),
		(   M == user
		->  save_attribute(P, (multifile))
		),
		feedback('(Skipped clauses)', []),
		fail
	    ;	true
	    ),
	    save_attributes(P),
	    \+ predicate_property(P, (volatile)),
	    nth_clause(P, _, Ref),
	    feedback('.', []),
	    $qlf_assert_clause(Ref, SaveClass),
	    fail
	;   $qlf_end_part,
	    feedback('~n', [])
	).
	
pred_attrib(dynamic,       P, $set_predicate_attribute(P, dynamic,       1)).
pred_attrib(volatile,      P, $set_predicate_attribute(P, volatile,      1)).
pred_attrib(thread_local,  P, $set_predicate_attribute(P, thread_local,  1)).
pred_attrib(multifile,     P, $set_predicate_attribute(P, multifile,     1)).
pred_attrib(transparent,   P, $set_predicate_attribute(P, transparent,   1)).
pred_attrib(discontiguous, P, $set_predicate_attribute(P, discontiguous, 1)).
pred_attrib(notrace,       P, $set_predicate_attribute(P, trace,         0)).
pred_attrib(show_childs,   P, $set_predicate_attribute(P, hide_childs,   0)).
pred_attrib(indexed(Term), P, M:index(Term)) :-
	strip_module(P, M, _).

predicate_attribute(P, Attribute) :-
	pred_attrib(Attribute, P, _),
	predicate_property(P, Attribute).

save_attribute(P, Attribute) :-
	pred_attrib(Attribute, P, D),
	(   Attribute = indexed(Term)
	->  \+(( arg(1, Term, 1),
	         functor(Term, _, Arity),
		 forall(between(2, Arity, N), arg(N, Term, 0))))
	;   true
	),
	$add_directive_wic(D),
	feedback('(~w) ', [Attribute]).

save_attributes(P) :-
	(   predicate_attribute(P, Attribute),
	    save_attribute(P, Attribute),
	    fail
	;   true
	).
	    
%	Save status of the unknown flag

save_unknown(M) :-
	current_prolog_flag(M:unknown, Unknown),
	(   Unknown == error
	->  true
	;   $add_directive_wic(set_prolog_flag(M:unknown, Unknown))
	).

		 /*******************************
		 *	      RECORDS		*
		 *******************************/

save_records :-
	feedback('~nRECORDS~n', []),
	(   current_key(X),
	    feedback('~n~t~8|~w ', [X, V]),
	    recorded(X, V, _),
	    feedback('.', []),
	    $add_directive_wic(recordz(X, V, _)),
	    fail
	;   true
	).


		 /*******************************
		 *	      FLAGS		*
		 *******************************/

save_flags :-
	feedback('~nFLAGS~n~n', []),
	(   current_flag(X),
	    flag(X, V, V),
	    feedback('~t~8|~w = ~w~n', [X, V]),
	    $add_directive_wic(flag(X, _, V)),
	    fail
	;   true
	).

		 /*******************************
		 *	     IMPORTS		*
		 *******************************/

default_import(system, _, _) :- !, fail.
default_import(To, Head, _) :-
	$get_predicate_attribute(To:Head, (dynamic), 1), !,
	fail.
default_import(user, Head, _) :- !,
	$default_predicate(user:Head, system:Head).
default_import(To, Head, _From) :-
	$default_predicate(To:Head, user:Head).
default_import(To, Head, _From) :-
	$default_predicate(To:Head, system:Head).

save_imports :-
	feedback('~nIMPORTS~n~n', []),
	(   predicate_property(M:H, imported_from(I)),
	    \+ default_import(M, H, I),
	    functor(H, F, A),
	    feedback('~t~8|~w:~w/~d <-- ~w~n', [M, F, A, I]),
	    $add_directive_wic(M:import(I:H)),
	    fail
	;   true
	).	    

		 /*******************************
		 *	      FEATURES		*
		 *******************************/

save_prolog_flags :-
	feedback('~nPROLOG FLAGS~n~n', []),
	$current_prolog_flag(Feature, Value, global, write, _Type),
	\+ no_save_flag(Feature),
	feedback('~t~8|~w: ~w~n', [Feature, Value]),
	$add_directive_wic(qsave:restore_prolog_flag(Feature, Value)),
	fail.
save_prolog_flags.

no_save_flag(argv).
no_save_flag(associated_file).
no_save_flag(hwnd).			% should be read-only, but comes
					% from user-code

%	Deal with possibly protected flags (debug_on_error and
%	report_error are protected flags for the runtime kernel).

restore_prolog_flag(Flag, Value) :-
	catch(set_prolog_flag(Flag, Value), _, true).


		 /*******************************
		 *	     OPERATORS		*
		 *******************************/

save_operators(save) :- !,
	feedback('~nOPERATORS~n', []),
	findall(op(P, T, N), current_op(P, T, N), Ops),
	findall(op(P, T, N), '$builtin_op'(P, T, N), SystemOps),
	make_operators(Ops, SystemOps, Set),
	deleted_operators(SystemOps, Ops, Deleted),
	append(Set, Deleted, Modify),
	forall(member(O, Modify),
	       (   feedback('~n~t~8|~w ', [O]),
		   $add_directive_wic(O),
		   O)).
save_operators(_).

make_operators([], _, []).
make_operators([Op|L0], SystemOps, [Op|L]) :-
	\+ memberchk(Op, SystemOps), !,
	make_operators(L0, SystemOps, L).
make_operators([_|T], SystemOps, L) :-
	make_operators(T, SystemOps, L).

deleted_operators([], _, []).
deleted_operators([Op|L0], CurrentOps, [op(0, T, N)|L]) :-
	Op = op(_, T, N),
	\+ (  member(op(_, OT, N), CurrentOps),
	      same_op_type(T, OT)
	   ), !,
	deleted_operators(L0, CurrentOps, L).
deleted_operators([_|L0], CurrentOps, L) :-
	deleted_operators(L0, CurrentOps, L).

same_op_type(T, OT) :-
	op_type(T, Type),
	op_type(OT, Type).

op_type(fx,  prefix).
op_type(fy,  prefix).
op_type(xfx, infix).
op_type(xfy, infix).
op_type(yfx, infix).
op_type(yfy, infix).
op_type(xf,  postfix).
op_type(yf,  postfix).

		 /*******************************
		 *       FORMAT PREDICATES	*
		 *******************************/

save_format_predicates :-
	feedback('~nFORMAT PREDICATES~n', []),
	current_format_predicate(Code, Head),
	qualify_head(Head, QHead),
	D = format_predicate(Code, QHead),
	feedback('~n~t~8|~w ', [D]),
	$add_directive_wic(D),
	fail.
save_format_predicates.

qualify_head(T, T) :-
	functor(T, :, 2), !.
qualify_head(T, user:T).


		 /*******************************
		 *	     FUNCTIONS		*
		 *******************************/

save_functions :-
	feedback('~nFUNCTIONS~n', []),
	'$prolog_arithmetic_function'(M:Head, Index),
	functor(Head, Name, Arity),
	PredArity is Arity + 1,
	functor(PredHead, Name, PredArity),
	D = '$arithmetic_function'(M:PredHead, Index),
	feedback('~n~t~8|~w ', [D]),
	$add_directive_wic(D),
	fail.
save_functions.


		 /*******************************
		 *       FOREIGN LIBRARIES	*
		 *******************************/

save_foreign_libraries :-
	$c_current_predicate(_, shlib:reload_foreign_libraries), !,
	feedback('~nFOREIGN LIBRARY HOOK~n', []),
	$add_directive_wic(shlib:reload_foreign_libraries).
save_foreign_libraries.


		 /*******************************
		 *	       UTIL		*
		 *******************************/

open_map([]) :- !,
	retractall(verbose(_)).
open_map(File) :-
	open(File, write, Fd),
	asserta(verbose(Fd)).

close_map :-
	retract(verbose(Fd)),
	close(Fd), !.
close_map.

feedback(Fmt, Args) :-
	verbose(Fd), !,
	format(Fd, Fmt, Args).
%	flush_output(Fd).		% Real debugging only
feedback(_, _).


option(List, Name/_Default, Value, Rest) :- % goal = Goal
	select(Name=Value, List, Rest), !.
option(List, Name/_Default, Value, Rest) :- % goal(Goal)
	Term =.. [Name, Value],
	select(Term, List, Rest), !.
option(List, _Name/Default, Default, List).
	
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Option checking and exception generation.  This should be in a library!
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

option_type(Name,	 integer) :- min_stack(Name, _MinValue).
option_type(class,	 atom([runtime,kernel,development])).
option_type(autoload,	 bool).
option_type(map,	 atom).
option_type(op,		 atom([save, standard])).
option_type(stand_alone, bool).
option_type(goal, 	 callable).
option_type(toplevel, 	 callable).
option_type(initfile, 	 atom).
option_type(emulator, 	 ground).

check_options([]) :- !.
check_options([Var|_]) :-
	var(Var), !,
	throw(error(domain_error(save_options, Var), _)).
check_options([Name=Value|T]) :- !,
	(   option_type(Name, Type)
	->  (   check_type(Type, Value)
	    ->  check_options(T)
	    ;	throw(error(domain_error(Type, Value), _))
	    )
	;   throw(error(domain_error(save_option, Name), _))
	).
check_options([Term|T]) :-
	Term =.. [Name,Arg], !,
	check_options([Name=Arg|T]).
check_options([Var|_]) :-
	throw(error(domain_error(save_options, Var), _)).
check_options(Opt) :-
	throw(error(domain_error(list, Opt), _)).

check_type(integer, V) :-
	integer(V).
check_type(atom(List), V) :-
	atom(V),
	memberchk(V, List), !.
check_type(atom, V) :-
	atom(V).
check_type(callable, V) :-
	atom(V).
check_type(callable, V) :-
	compound(V).
check_type(ground, V) :-
	ground(V).
check_type(bool, true).
check_type(bool, false).
	
		 /*******************************
		 *	      MESSAGES		*
		 *******************************/

:- multifile prolog:message/3.

message(no_resource(Name, Class, File)) -->
	[ 'Could not find resource ~w/~w on ~w or system resources'-
	  [Name, Class, File] ].
