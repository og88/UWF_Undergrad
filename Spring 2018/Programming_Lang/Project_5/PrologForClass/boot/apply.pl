/*  $Id: apply.pl,v 1.8 2004/09/21 14:22:23 jan Exp $

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

:- module($apply,
	  [ maplist/2,			% :Goal, +List
	    maplist/3,			% :Goal, ?List, ?List
	    maplist/4,			% :Goal, ?List, ?List, ?List
	    sublist/3,			% :Goal, +List, -List
	    forall/2			% :Goal, :Goal
	  ]).

:- module_transparent
	maplist/2, 
	maplist2/2, 
	maplist/3, 
	maplist2/3, 
	maplist/4, 
	maplist2/4, 
	sublist/3, 
	forall/2.

%	maplist(:Goal, +List)
%
%	True if Goal can succesfully be applied on all elements of List.
%	Arguments are reordered to gain performance as well as to make
%	the predicate deterministic under normal circumstances.

maplist(Goal, List) :-
	maplist2(List, Goal).

maplist2([], _).
maplist2([Elem|Tail], Goal) :-
	call(Goal, Elem), 
	maplist2(Tail, Goal).

%	maplist(:Goal, ?List1, ?List2)
%
%	True if Goal can succesfully be applied to all succesive pairs
%	of elements of List1 and List2.

maplist(Goal, List1, List2) :-
	maplist2(List1, List2, Goal).

maplist2([], [], _).
maplist2([Elem1|Tail1], [Elem2|Tail2], Goal) :-
	call(Goal, Elem1, Elem2), 
	maplist2(Tail1, Tail2, Goal).

%	maplist(:Goal, ?List1, ?List2, ?List)
%
%	True if Goal can succesfully be applied to all succesive triples
%	of elements of List1 and List2.

maplist(Goal, List1, List2, List3) :-
	maplist2(List1, List2, List3, Goal).

maplist2([], [], [], _).
maplist2([Elem1|Tail1], [Elem2|Tail2], [Elem3|Tail3], Goal) :-
	call(Goal, Elem1, Elem2, Elem3), 
	maplist2(Tail1, Tail2, Tail3, Goal).

%	sublist(:Goal, +List1, ?List2)
%	
%	Succeeds if List2 unifies with a list holding those terms for wich
%	apply(Goal, Elem) succeeds.

sublist(_, [], []) :- !.
sublist(Goal, [H|T], Sub) :-
	call(Goal, H), !, 
	Sub = [H|R], 
	sublist(Goal, T, R).
sublist(Goal, [_|T], R) :-
	sublist(Goal, T, R).

%	forall(+Condition, +Action)
%	
%	True if Action if true for all variable bindings for which Condition
%	if true.

:- noprofile(forall/2).

forall(Cond, Action) :-
	\+ (Cond, \+ Action).
