/*  $Id: syspred.pl,v 1.66 2004/09/30 10:41:35 jan Exp $

    Part of SWI-Prolog

    Author:        Jan Wielemaker
    E-mail:        jan@swi.psy.uva.nl
    WWW:           http://www.swi-prolog.org
    Copyright (C): 1985-2004, University of Amsterdam

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

:- module($syspreds,
	[ leash/1
	, visible/1
	, style_check/1
	, (spy)/1
	, (nospy)/1
	, trace/1
	, trace/2
	, nospyall/0
	, debugging/0
	, concat_atom/2
	, term_to_atom/2
	, int_to_atom/2
	, dwim_match/2
	, source_file/1
	, prolog_load_context/2
	, current_predicate/2
	, $defined_predicate/1
	, predicate_property/2
	, $predicate_property/2
	, clause_property/2
	, recorda/2
	, recordz/2
	, recorded/2
	, current_module/1
	, current_module/2
	, module/1
	, statistics/0
	, shell/2
	, shell/1
	, shell/0
	, on_signal/3
	, current_signal/3
	, open_shared_object/2
	, open_shared_object/3
	, format/1
	, sformat/2
	, sformat/3
	, garbage_collect/0
	, arithmetic_function/1
        , default_module/2
	, absolute_file_name/2
	, require/1
	, call_with_depth_limit/3
	, length/2
	, numbervars/3
	, nb_setval/2				% +Var, +Value
	]).	

		/********************************
		*           DEBUGGER            *
		*********************************/

$map_bits(_, [], Bits, Bits) :- !.
$map_bits(Pred, [H|T], Old, New) :-
	$map_bits(Pred, H, Old, New0),
	$map_bits(Pred, T, New0, New).
$map_bits(Pred, +Name, Old, New) :- !, 		% set a bit
	call(Pred, Name, Bits), !,
	New is Old \/ Bits.
$map_bits(Pred, -Name, Old, New) :- !, 		% clear a bit
	call(Pred, Name, Bits), !,
	New is Old /\ (\Bits).
$map_bits(Pred, ?Name, Old, Old) :-		% ask a bit
	call(Pred, Name, Bits),
	Old /\ Bits > 0.

$port_bit(      call, 2'000000001).
$port_bit(      exit, 2'000000010).
$port_bit(      fail, 2'000000100).
$port_bit(      redo, 2'000001000).
$port_bit(     unify, 2'000010000).
$port_bit(     break, 2'000100000).
$port_bit(  cut_call, 2'001000000).
$port_bit(  cut_exit, 2'010000000).
$port_bit( exception, 2'100000000).
$port_bit(       cut, 2'011000000).
$port_bit(       all, 2'000111111).
$port_bit(      full, 2'000101111).
$port_bit(      half, 2'000101101).

leash(Ports) :-
	$leash(Old, Old),
	$map_bits($port_bit, Ports, Old, New),
	$leash(_, New).

visible(Ports) :-
	$visible(Old, Old),
	$map_bits($port_bit, Ports, Old, New),
	$visible(_, New).

$map_style_check(atom,     	    2'0000001).
$map_style_check(singleton, 	    2'0000010).
$map_style_check(dollar,   	    2'0000100).
$map_style_check((discontiguous),   2'0001000).
$map_style_check(dynamic,	    2'0010000).
$map_style_check(charset,	    2'0100000).

style_check(+string) :- !,
	set_prolog_flag(double_quotes, string).
style_check(-string) :- !,
	set_prolog_flag(double_quotes, codes).
style_check(?string) :- !,
	current_prolog_flag(double_quotes, string).
style_check(Spec) :-
	$style_check(Old, Old),
	$map_bits($map_style_check, Spec, Old, New),
	$style_check(_, New).

:- module_transparent
	trace/1,
	trace/2,
	$trace/2,
	spy/1,
	nospy/1.

%	prolog:debug_control_hook(+Action)
%
%	Allow user-hooks in the Prolog debugger interaction.  See the calls
%	below for the provided hooks.  We use a single predicate with action
%	argument to avoid an uncontrolled poliferation of hooks.
%
%	TBD: What hooks to provide for trace/[1,2]

:- multifile
	prolog:debug_control_hook/1.	% +Action
:- module_transparent
	prolog:debug_control_hook/1.

trace(Preds) :-
	trace(Preds, +all).

trace([], _) :- !.
trace([H|T], Ps) :- !,
	trace(H, Ps),
	trace(T, Ps).
trace(Pred, Ports) :-
	set_prolog_flag(debug, true),
	$find_predicate(Pred, Preds),
	Preds \== [],
	(   $member(Head, Preds),
	        (   Head = _:_
		->  QHead0 = Head
		;   QHead0 = user:Head
		),
		$define_predicate(QHead0),
	        (   predicate_property(QHead0, imported_from(M))
		->  QHead0 = _:Plain,
		    QHead = M:Plain
		;   QHead = QHead0
		),
	        $trace(Ports, QHead),
	        trace_ports(QHead, Tracing),
	        print_message(informational, trace(QHead, Tracing)),
	    fail
	;   true
	).

trace_alias(all,  [trace_call, trace_redo, trace_exit, trace_fail]).
trace_alias(call, [trace_call]).
trace_alias(redo, [trace_redo]).
trace_alias(exit, [trace_exit]).
trace_alias(fail, [trace_fail]).

$trace([], _) :- !.
$trace([H|T], Head) :- !,
	$trace(H, Head),
	$trace(T, Head).
$trace(+H, Head) :-
	trace_alias(H, A0), !,
	tag_list(A0, +, A1),
	$trace(A1, Head).
$trace(+H, Head) :- !,
	trace_alias(_, [H]),
	$set_predicate_attribute(Head, H, 1).
$trace(-H, Head) :-
	trace_alias(H, A0), !,
	tag_list(A0, -, A1),
	$trace(A1, Head).
$trace(-H, Head) :- !,
	trace_alias(_, [H]),
	$set_predicate_attribute(Head, H, 0).
$trace(H, Head) :-
	atom(H),
	$trace(+H, Head).

tag_list([], _, []).
tag_list([H0|T0], F, [H1|T1]) :-
	H1 =.. [F, H0],
	tag_list(T0, F, T1).

spy([]) :- !.
spy([H|T]) :- !,
	spy(H),
	spy(T).
spy(Spec) :-
	prolog:debug_control_hook(spy(Spec)), !.
spy(Spec) :-
	$find_predicate(Spec, Preds),
	$member(Head, Preds),
	    $define_predicate(Head),
	    $spy(Head),
	fail.
spy(_).

nospy([]) :- !.
nospy([H|T]) :- !,
	nospy(H),
	nospy(T).
nospy(Spec) :-
	prolog:debug_control_hook(nospy(Spec)), !.
nospy(Spec) :-
	$find_predicate(Spec, Preds),
	$member(Head, Preds),
	    $nospy(Head),
	fail.
nospy(_).

nospyall :-
	prolog:debug_control_hook(nospyall),
	fail.
nospyall :-
	spy_point(Head),
	    $nospy(Head),
	fail.
nospyall.

debugging :-
	prolog:debug_control_hook(debugging), !.
debugging :-
	current_prolog_flag(debug, true), !,
	print_message(informational, debugging(on)),
	findall(H, spy_point(H), SpyPoints),
	print_message(informational, spying(SpyPoints)),
	findall(trace(H,P), trace_point(H,P), TracePoints),
	print_message(informational, tracing(TracePoints)).
debugging :-
	print_message(informational, debugging(off)).

spy_point(Module:Head) :-
	current_predicate(_, Module:Head),
	$get_predicate_attribute(Module:Head, spy, 1),
	\+ predicate_property(Module:Head, imported_from(_)).

trace_point(Module:Head, Ports) :-
	current_predicate(_, Module:Head),
	    $get_predicate_attribute(Module:Head, trace_any, 1),
	    \+ predicate_property(Module:Head, imported_from(_)),
	    trace_ports(Module:Head, Ports).

trace_ports(Head, Ports) :-
	findall(Port,
		(trace_alias(Port, [AttName]),
		 $get_predicate_attribute(Head, AttName, 1)),
		Ports).


		/********************************
		*             ATOMS             *
		*********************************/

concat_atom([A, B], C) :- !,
	atom_concat(A, B, C).
concat_atom(L, Atom) :-
	$concat_atom(L, Atom).

term_to_atom(Term, Atom) :-
	atom_to_term(Atom, Term, 0).

int_to_atom(Int, Atom) :-
	int_to_atom(Int, 10, Atom).

dwim_match(A1, A2) :-
	dwim_match(A1, A2, _).


		/********************************
		*             SOURCE            *
		*********************************/

source_file(File) :-
	$time_source_file(File, _).
source_file(File) :-
	atom(File),
	absolute_file_name(File, Abs),	% canonise
	$time_source_file(Abs, _).

%	prolog_load_context(+Key, -Value)
%
%	Provides context information for term_expansion and directives.
%	Note that only the line-number info is valid for the
%	'$stream_position'.  Largely Quintus compatible.

:- module_transparent
	prolog_load_context/2.

prolog_load_context(module, Module) :-
	$set_source_module(Module, Module).
prolog_load_context(file, F) :-
	source_location(F, _).
prolog_load_context(stream, S) :-
	source_location(F, _),
	(   system:$load_input(F, S0)
	->  S = S0
	).
prolog_load_context(directory, D) :-
	source_location(F, _),
	file_directory_name(F, D).
prolog_load_context(term_position, '$stream_position'(0,L,0,0,0)) :-
	source_location(_, L).

		 /*******************************
		 *	      CONTROL		*
		 *******************************/

%	call_with_depth_limit(+Goal, +DepthLimit, -Result)
%
%	Try to proof Goal, but fail on any branch exceeding the indicated
%	depth-limit.  Unify Result with the maximum-reached limit on success,
%	depth_limit_exceeded if the limit was exceeded and fails otherwise.

:- module_transparent call_with_depth_limit/3.

call_with_depth_limit(G, Limit, Result) :-
	$depth_limit(Limit, OLimit, OReached),
	(   catch(G, E, $depth_limit_except(OLimit, OReached, E)),
	    $depth_limit_true(Limit, OLimit, OReached, Result, Det),
	    ( Det == ! -> ! ; true )
	;   $depth_limit_false(OLimit, OReached, Result)
	).


		/********************************
		*           DATA BASE           *
		*********************************/

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
The predicate current_predicate/2 is   a  difficult subject since  the
introduction  of defaulting     modules   and   dynamic     libraries.
current_predicate/2 is normally  called with instantiated arguments to
verify some  predicate can   be called without trapping   an undefined
predicate.  In this case we must  perform the search algorithm used by
the prolog system itself.

If the pattern is not fully specified, we only generate the predicates
actually available in this  module.   This seems the best for listing,
etc.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */


:- module_transparent
	current_predicate/2,
	$defined_predicate/1.

current_predicate(Name, Head) :-
	var(Head), !,
	context_module(Module),
	generate_current_predicate(Name, Module, Head).
current_predicate(Name, Module:Head) :-
	(var(Module) ; var(Head)), !,
	generate_current_predicate(Name, Module, Head).
current_predicate(Name, Term) :-
	$c_current_predicate(Name, Term),
	$defined_predicate(Term), !.
current_predicate(Name, Term) :-
	strip_module(Term, Module, Head),
	default_module(Module, DefModule),
	$c_current_predicate(Name, DefModule:Head),
	$defined_predicate(DefModule:Head), !.
current_predicate(Name, Term) :-
	current_prolog_flag(autoload, true),
	strip_module(Term, Module, Head),
	functor(Head, Name, Arity),
	$find_library(Module, Name, Arity, _LoadModule, _Library), !.

generate_current_predicate(Name, Module, Head) :-
	current_module(Module),
	$c_current_predicate(Name, Module:Head),
	$defined_predicate(Module:Head).

$defined_predicate(Head) :-
	$get_predicate_attribute(Head, defined, 1), !.

:- module_transparent
	predicate_property/2,
	$predicate_property/2.

predicate_property(Pred, Property) :-
	Property == undefined, !,
	(   Pred = Module:Head,
	    var(Module)
	;   strip_module(Pred, Module, Head)
	), !,
	current_module(Module),
	Term = Module:Head,
	$c_current_predicate(_, Term),
	\+ $defined_predicate(Term),		% Speed up a bit
	\+ current_predicate(_, Term).
predicate_property(Pred, Property) :-
	current_predicate(_, Pred),
	$predicate_property(Property, Pred).

$predicate_property(interpreted, Pred) :-
	$get_predicate_attribute(Pred, foreign, 0).
$predicate_property(built_in, Pred) :-
	$get_predicate_attribute(Pred, system, 1).
$predicate_property(exported, Pred) :-
	$get_predicate_attribute(Pred, exported, 1).
$predicate_property(foreign, Pred) :-
	$get_predicate_attribute(Pred, foreign, 1).
$predicate_property((dynamic), Pred) :-
	$get_predicate_attribute(Pred, (dynamic), 1).
$predicate_property((volatile), Pred) :-
	$get_predicate_attribute(Pred, (volatile), 1).
$predicate_property((thread_local), Pred) :-
	$get_predicate_attribute(Pred, (thread_local), 1).
$predicate_property((multifile), Pred) :-
	$get_predicate_attribute(Pred, (multifile), 1).
$predicate_property(imported_from(Module), Pred) :-
	$get_predicate_attribute(Pred, imported, Module).
$predicate_property(transparent, Pred) :-
	$get_predicate_attribute(Pred, transparent, 1).
$predicate_property(indexed(Pattern), Pred) :-
	$get_predicate_attribute(Pred, indexed, Pattern).
$predicate_property(file(File), Pred) :-
	source_file(Pred, File).
$predicate_property(line_count(LineNumber), Pred) :-
	$get_predicate_attribute(Pred, line_count, LineNumber).
$predicate_property(notrace, Pred) :-
	$get_predicate_attribute(Pred, trace, 0).
$predicate_property(nodebug, Pred) :-
	$get_predicate_attribute(Pred, hide_childs, 1).
$predicate_property(spying, Pred) :-
	$get_predicate_attribute(Pred, spy, 1).
$predicate_property(hashed(N), Pred) :-
	$get_predicate_attribute(Pred, hashed, N),
	N > 0.
$predicate_property(references(N), Pred) :-
	$get_predicate_attribute(Pred, references, N),
	N \== 0.			% show negative for debugging!
$predicate_property(number_of_clauses(N), Pred) :-
	$get_predicate_attribute(Pred, number_of_clauses, N),
	N \== 0.
$predicate_property(noprofile, Pred) :-
	$get_predicate_attribute(Pred, noprofile, 1).

:- index(clause_property(0, 1)).

clause_property(Clause, line_count(LineNumber)) :-
	$get_clause_attribute(Clause, line_count, LineNumber).
clause_property(Clause, file(File)) :-
	$get_clause_attribute(Clause, file, File).
clause_property(Clause, fact) :-
	$get_clause_attribute(Clause, fact, true).
clause_property(Clause, erased) :-
	$get_clause_attribute(Clause, erased, true).


recorda(Key, Value) :-
	recorda(Key, Value, _).
recordz(Key, Value) :-
	recordz(Key, Value, _).
recorded(Key, Value) :-
	recorded(Key, Value, _).

		 /*******************************
		 *	       REQUIRE		*
		 *******************************/

:- module_transparent
	require/1.

require([]).
require([N/A|T]) :- !,
	functor(Head, N, A),
	$require(Head),
	require(T).
require([H|_T]) :-
	throw(error(type_error(predicate_indicator, H), _)).


		/********************************
		*            MODULES            *
		*********************************/

current_module(Module) :-
	$current_module(Module, _).

current_module(Module, File) :-
	$current_module(Module, File),
	File \== [].

module(Module) :-
	atom(Module),
	current_module(Module), !,
	$module(_, Module).
module(Module) :-
	$module(_, Module),
	print_message(warning, no_current_module(Module)).

		/********************************
		*          STATISTICS           *
		*********************************/

statistics :-
	statistics(trail, Trail),
	statistics(trailused, TrailUsed),
	statistics(local, Local),
	statistics(localused, LocalUsed),
	statistics(global, Global),
	statistics(globalused, GlobalUsed),
	statistics(cputime, Cputime),
	statistics(inferences, Inferences),
	statistics(heapused, Heapused),
	statistics(atoms, Atoms),
	statistics(functors, Functors),
	statistics(predicates, Predicates),
	statistics(modules, Modules),
	statistics(codes, Codes),
	statistics(locallimit, LocalLimit),
	statistics(globallimit, GlobalLimit),
	statistics(traillimit, TrailLimit),

	format('~2f seconds cpu time for ~D inferences~n',
				    [Cputime, Inferences]),
	format('~D atoms, ~D functors, ~D predicates, ~D modules, ~D VM-codes~n~n',
				    [Atoms, Functors, Predicates, Modules, Codes]),
	format('                       Limit    Allocated       In use~n'),
	(   statistics(heap, Heap),
	    statistics(heaplimit, HeapLimit)
	->  format('Heap         :~t~D~28| ~t~D~41| ~t~D~54| Bytes~n',
		   [HeapLimit, Heap, Heapused])
	;   format('Heap         :                  ~t~D~54| Bytes~n',
		   [Heapused])
	),
	format('Local  stack :~t~D~28| ~t~D~41| ~t~D~54| Bytes~n',
	       [LocalLimit, Local, LocalUsed]),
	format('Global stack :~t~D~28| ~t~D~41| ~t~D~54| Bytes~n',
	       [GlobalLimit, Global, GlobalUsed]),
	format('Trail  stack :~t~D~28| ~t~D~41| ~t~D~54| Bytes~n~n',
	       [TrailLimit, Trail, TrailUsed]),

	gc_statistics,
	agc_statistics,
	shift_statistics,
	thread_statistics.

gc_statistics :-
	statistics(collections, Collections),
	Collections > 0, !,
	statistics(collected, Collected),
	statistics(gctime, GcTime),

	format('~D garbage collections gained ~D bytes in ~2f seconds.~n',
	       [Collections, Collected, GcTime]).
gc_statistics.

agc_statistics :-
	catch(statistics(agc, Agc), _, fail),
	Agc > 0, !,
	statistics(agc_gained, Gained),
	statistics(agc_time, Time),
	format('~D atom garbage collections gained ~D atoms in ~2f seconds.~n',
	       [Agc, Gained, Time]).
agc_statistics.

shift_statistics :-
	statistics(local_shifts, LS),
	statistics(global_shifts, GS),
	statistics(trail_shifts, TS),
	(   LS > 0
	;   GS > 0
	;   TS > 0
	), !,
	format('Stack shifts: ~D local, ~D global, ~D trail.~n',
	       [LS, GS, TS]).
shift_statistics.

thread_statistics :-
	current_prolog_flag(threads, true), !,
	statistics(threads, Active),
	statistics(threads_created, Created),
	statistics(thread_cputime, CpuTime),
	Finished is Created - Active,
	format('~D threads, ~D finished threads used ~2f seconds.~n',
	       [Active, Finished, CpuTime]).
thread_statistics.


		/********************************
		*      SYSTEM INTERACTION       *
		*********************************/

shell(Command, Status) :-
	$shell(Command, Status).

shell(Command) :-
	shell(Command, 0).

shell :-
	getenv('SHELL', Shell), !,
	shell(Shell).
shell :-
	shell('/bin/sh').

		 /*******************************
		 *	      SIGNALS		*
		 *******************************/

:- module_transparent
	on_signal/3.

on_signal(Signal, Old, New) :-
	atom(Signal), !,
	$on_signal(_Num, Signal, Old, New).
on_signal(Signal, Old, New) :-
	integer(Signal), !,
	$on_signal(Signal, _Name, Old, New).
on_signal(Signal, _Old, _New) :-
	throw(error(type_error(signal, Signal), on_signal/3)).

current_signal(Name, Id, Handler) :-
	between(1, 32, Id),
	$on_signal(Id, Name, Handler, Handler).


		 /*******************************
		 *	      DLOPEN		*
		 *******************************/

:- module_transparent
	open_shared_object/2,
	open_shared_object/3.

dlopen_flag(now,	2'01).		% see pl-load.c for these constants
dlopen_flag(global,	2'10).		% Solaris only

map_dlflags([], 0).
map_dlflags([F|T], M) :-
	map_dlflags(T, M0),
	dlopen_flag(F, I),
	M is M0 \/ I.

open_shared_object(File, Flags, Handle) :- % compatibility
	is_list(Flags), \+ is_list(Handle), !,
	open_shared_object(File, Handle, Flags).
open_shared_object(File, Handle, Flags) :-
	map_dlflags(Flags, Mask),
	$open_shared_object(File, Handle, Mask).

open_shared_object(File, Handle) :-
	open_shared_object(File, [], Handle). % use pl-load.c defaults


		/********************************
		*              I/O              *
		*********************************/

format(Fmt) :-
	format(Fmt, []).

sformat(String, Format, Arguments) :-
	$write_on_string(format(Format, Arguments), String).
sformat(String, Format) :-
	$write_on_string(format(Format), String).

		 /*******************************
		 *	      FILES		*
		 *******************************/

%	absolute_file_name(+Term, -AbsoluteFile)

absolute_file_name(Name, Abs) :-
	atomic(Name), !,
	$absolute_file_name(Name, Abs).
absolute_file_name(Term, Abs) :-
	$chk_file(Term, [''], [access(read)], File), !,
	$absolute_file_name(File, Abs).
absolute_file_name(Term, Abs) :-
	$chk_file(Term, [''], [], File), !,
	$absolute_file_name(File, Abs).


		/********************************
		*         MISCELLENEOUS         *
		*********************************/

%	Invoke the garbage collector.  The argument is the debugging level
%	to use during garbage collection.  This only works if the system
%	is compiled with the -DODEBUG cpp flag.  Only to simplify maintenance.

garbage_collect :-
	$garbage_collect(0).

%	arithmetic_function(Spec)
%	Register a predicate as an arithmetic function.  Takes Name/Arity
%	and a term as argument.

:- module_transparent
	arithmetic_function/1.

arithmetic_function(Spec) :-
	strip_module(Spec, Module, Term),
	(   Term = Name/Arity
	;   functor(Term, Name, Arity)
	), !,
	PredArity is Arity + 1,
	functor(Head, Name, PredArity),
	$arithmetic_function(Module:Head, 0).

%	default_module(+Me, -Super)
%	Is true if `Super' is `Me' or a super (auto import) module of `Me'.

default_module(Me, Me).
default_module(Me, Super) :-
	import_module(Me, S),
	default_module(S, Super).


		 /*******************************
		 *	 LIST MANIPULATION	*
		 *******************************/

%	length(?List, ?N)
%	Is true when N is the length of List.

length(List, Length) :-
	$length(List, Length), !.		% written in C
length(List, Length) :-
	var(Length),
	length2(List, Length).

length2([], 0).
length2([_|List], N) :-
	length2(List, M), 
	succ(M, N).


		 /*******************************
		 *	       TERM		*
		 *******************************/

%	numbervars(+Term, +StartIndex, -EndIndex)
%	
%	Number all unbound variables in Term   using  $VAR(N), where the
%	first N is StartIndex and EndIndex is  unified to the index that
%	will be given to the next variable.

numbervars(Term, From, To) :-
	numbervars(Term, From, To, []).


		 /*******************************
		 *	       GVAR		*
		 *******************************/

%	nb_setval(+Name, +Value)
%	
%	Bind the non-backtrackable variable Name with a copy of Value

nb_setval(Name, Value) :-
	duplicate_term(Value, Copy),
	nb_linkval(Name, Copy).
