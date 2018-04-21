/*  $Id: quintus.pl,v 1.38 2004/09/30 10:41:35 jan Exp $

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

:- module(quintus, 
	[ unix/1,
%	  file_exists/1,

	  abs/2,
	  sin/2,
	  cos/2,
	  tan/2,
	  log/2,
	  log10/2,
	  pow/3,
	  ceiling/2,
	  floor/2,
	  round/2,
	  acos/2,
	  asin/2,
	  atan/2,
	  atan2/3,
	  sign/2,
	  sqrt/2,
	  random/3,

	  genarg/3,

	  (mode)/1,
	  (public)/1,
	  (meta_predicate)/1,
	  no_style_check/1,
	  otherwise/0,
	  subsumes_chk/2,		% ?General, ?Specific
	  simple/1,
%	  statistics/2,			% Please access as quintus:statistics/2
	  prolog_flag/2,

	  date/1,			% -date(Year, Month, Day)

	  current_stream/3,		% ?File, ?Mode, ?Stream
	  stream_position/3,		% +Stream, -Old, +New
	  skip_line/0,
	  skip_line/1,			% +Stream

	  compile/1,			% +File(s)

	  atom_char/2,
	  midstring/3,			% ABC, B, AC
	  midstring/4,			% ABC, B, AC, LenA
	  midstring/5,			% ABC, B, AC, LenA, LenB
	  midstring/6,			% ABC, B, AC, LenA, LenB, LenC

	  raise_exception/1,		% +Exception
	  on_exception/3		% +Ball, :Goal, :Recover
	]).

		/********************************
		*      SYSTEM INTERACTION       *
		*********************************/

%	unix(+Action)
%	interface to  Unix.

unix(system(Command)) :-
        shell(Command).
unix(shell(Command)) :-
        shell(Command).
unix(shell) :-
        shell.
unix(access(File, 0)) :-
        access_file(File, read).
unix(cd) :-
	expand_file_name(~, [Home]),
	working_directory(_, Home).
unix(cd(Dir)) :-
	working_directory(_, Dir).
unix(args(L)) :-
	current_prolog_flag(argv, L).
unix(argv(L)) :-
	current_prolog_flag(argv, S),
	maplist(to_prolog, S, L).

to_prolog(S, A) :-
	name(S, L),
	name(A, L).


%	file_exists(+File)
%	Succeeds if `File' exists as a file or directory in the Unix file
%	system.

file_exists(File) :-
	exists_file(File).


		/********************************
		*        META PREDICATES        *
		*********************************/

%	otherwise/0
%	For (A -> B ; otherwise -> C)

otherwise.


		/********************************
		*          ARITHMETIC           *
		*********************************/

%	abs(+Number, -Absolute)
%	Unify `Absolute' with the absolute value of `Number'.

abs(Number, Absolute) :-
	Absolute is abs(Number).

%	Math library predicates

sin(A, V) :-	  V is sin(A).
cos(A, V) :-	  V is cos(A).
tan(A, V) :-	  V is tan(A).
log(A, V) :-	  V is log(A).
log10(X, V) :-	  V is log10(X).
pow(X,Y,V) :-	  V is X**Y.
ceiling(X, V) :-  V is ceil(X).
floor(X, V) :-	  V is floor(X).
round(X, V) :-	  V is round(X).
sqrt(X, V) :-	  V is sqrt(X).
acos(X, V) :-	  V is acos(X).
asin(X, V) :-	  V is asin(X).
atan(X, V) :-	  V is atan(X).
atan2(Y, X, V) :- V is atan(Y, X).
sign(X, V) :-	  V is sign(X).

%	random(+Min, +Max, -Value)
%
%	Value is a random integer with Min <= Value < Max.  Note that
%	Quintus random/3 also deals with floats.  This one doesn't.

random(Min, Max, Value) :-
	Value is Min + random(Max-Min).

		 /*******************************
		 *	TERM MANIPULATION	*
		 *******************************/


genarg(N, T, A) :-			% SWI-Prolog arg/3 is generic
	arg(N, T, A).


		 /*******************************
		 *	      FLAGS		*
		 *******************************/

%	prolog_flag(?Flag, ?Value)
%
%	Should map relevant Quintus flag identifiers.

prolog_flag(version, Version) :- !,
	current_prolog_flag(version, N),
	current_prolog_flag(arch, Arch),
	current_prolog_flag(compiled_at, Compiled),
	Major is N // 10000,
	Minor is N // 100 mod 100,
	Patch is N mod 100,
	concat_atom(['SWI-Prolog ',
		     Major, '.', Minor, '.', Patch,
		     ' (', Arch, '): ', Compiled], Version).
prolog_flag(Flag, Value) :-
	current_prolog_flag(Flag, Value).


		 /*******************************
		 *	    STATISTICS		*
		 *******************************/

%	Here used to be a definition of Quintus statistics/2 in traditional
%	SWI-Prolog statistics/2.  The current built-in emulates Quintus 
%	almost completely.


		 /*******************************
		 *	     DATE/TIME		*
		 *******************************/

%	date(-Date)
%
%	Get current date.

date(date(Year,Month,Day)) :-
	get_time(T),
	convert_time(T, Year, Month, Day, _, _, _, _).


		/********************************
		*          STYLE CHECK          *
		*********************************/

q_style_option(single_var, singleton) :- !.
q_style_option(Option, Option).

no_style_check(QOption) :-
	q_style_option(QOption, SWIOption), 
	style_check(-SWIOption).


		/********************************
		*         DIRECTIVES            *
		*********************************/

% :- op(1150, fx, [(mode), (public)]).

mode(_).
public(_).


		 /*******************************
		 *	TERM MANIPULATION	*
		 *******************************/

%	temporary hack for subsumes_chk/2 in ordinary Prolog world.
%	This comes from the SWI-Prolog port of ALE.
%	If all works fine we might move this to the kernel.

subsumes_chk(X,Y) :-
  \+ \+ (copy_term(Y,Y2),
         term_variables(Y,YFVs),
         term_variables(Y2,Y2FVs),
         X = Y2,
         numbervars(YFVs,0,_),   % don't use '$VAR' in a_ atoms!
         YFVs = Y2FVs).


		 /*******************************
		 *	      TYPES		*
		 *******************************/

simple(X) :-
	(   atomic(X)
	->  true
	;   var(X)
	).

		/********************************
		*            MODULES            *
		*********************************/

:- initialization op(1150, fx, user:(meta_predicate)).

:- module_transparent
	(meta_predicate)/1.

meta_predicate((Head, More)) :- !, 
	meta_predicate(Head), 
	meta_predicate(More).
meta_predicate(Spec) :-
	strip_module(Spec, M, Head),
	meta_predicate(M, Head).

meta_predicate(M, Head) :-
	Head =.. [Name|Arguments], 
	member(Arg, Arguments), 
	module_expansion_argument(Arg), !, 
	functor(Head, Name, Arity), 
	module_transparent(M:Name/Arity).
meta_predicate(_, _).		% just a mode declaration

module_expansion_argument(:).
module_expansion_argument(N) :- integer(N).


		 /*******************************
		 *	      STREAMS		*
		 *******************************/

%	current_stream(?Object, ?Mode, ?Stream)
%
%	SICStus/Quintus and backward compatible predicate.  New code should
%	be using the ISO compatible stream_property/2.

current_stream(Object, Mode, Stream) :-
	stream_property(Stream, mode(FullMode)),
	stream_mode(FullMode, Mode),
	(   stream_property(Stream, file_name(Object))
	->  true
	;   stream_property(Stream, file_no(Object))
	->  true
	;   Object = []
	).

stream_mode(read,   read).
stream_mode(write,  write).
stream_mode(append, write).
stream_mode(update, write).

%	stream_position(+Stream, -Old, +New)

stream_position(Stream, Old, New) :-
	stream_property(Stream, position(Old)),
	set_stream_position(Stream, New).


%	skip_line(Stream)

skip_line :-
	skip(10).
skip_line(Stream) :-
	skip(Stream, 10).


		 /*******************************
		 *	   COMPILATION		*
		 *******************************/

:- meta_predicate
	compile(:).

compile(Files) :-
	consult(Files).

		 /*******************************
		 *	   ATOM-HANDLING	*
		 *******************************/

%	atom_char/2

atom_char(Char, Code) :-
	char_code(Char, Code).

%	midstring(?ABC, ?B, ?AC, LenA, LenB, LenC)
%
%	Too difficult to explain.  See the Quintus docs.  As far as I
%	understand them the code below emulates this function just fine.

midstring(ABC, B, AC) :-
	midstring(ABC, B, AC, _, _, _).
midstring(ABC, B, AC, LenA) :-
	midstring(ABC, B, AC, LenA, _, _).
midstring(ABC, B, AC, LenA, LenB) :-
	midstring(ABC, B, AC, LenA, LenB, _).
midstring(ABC, B, AC, LenA, LenB, LenC) :-	% -ABC, +B, +AC
	var(ABC), !,
	atom_length(AC, LenAC),
	(   nonvar(LenA) ; nonvar(LenC)
	->  plus(LenA, LenC, LenAC)
	;   true
	),
	sub_atom(AC, 0, LenA, _, A),
	LenC is LenAC - LenA,
	sub_atom(AC, _, LenC, 0, C),
	atom_length(B, LenB),
	concat_atom([A,B,C], ABC).
midstring(ABC, B, AC, LenA, LenB, LenC) :-
	sub_atom(ABC, LenA, LenB, LenC, B),
	sub_atom(ABC, 0, LenA, _, A),
	sub_atom(ABC, _, LenC, 0, C),
	atom_concat(A, C, AC).


		 /*******************************
		 *	     EXCEPTIONS		*
		 *******************************/

%	raise_exception(+Term)
%
%	Quintus compatible exception handling

raise_exception(Term) :-
	throw(Term).

%	on_exception(+Template, :Goal, :Recover)

:- meta_predicate
	on_exception(+, :, :).

on_exception(Except, Goal, Recover) :-
	catch(Goal, Except, Recover).
