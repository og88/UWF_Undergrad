/*  $Id: pce_util.pl,v 1.12 2003/03/05 14:12:01 jan Exp $

    Part of XPCE --- The SWI-Prolog GUI toolkit

    Author:        Jan Wielemaker and Anjo Anjewierden
    E-mail:        jan@swi.psy.uva.nl
    WWW:           http://www.swi.psy.uva.nl/projects/xpce/
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

:- module(pce_util,
	  [ get_object/3, get_object/4, get_object/5
	  , get_object/6, get_object/7, get_object/8
	  , get_object/9, get_object/10, get_object/11
	  , get_object/12, get_object/13

	  , send_list/2, send_list/3
	  , get_chain/3

	  , chain_list/2

	  , default/3
	  ]).


:- meta_predicate
	get_object(+, :, -),
	get_object(+, :, +, -),
	get_object(+, :, +, +, -),
	get_object(+, :, +, +, +, -),
	get_object(+, :, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, +, +, +, +, -),
	get_object(+, :, +, +, +, +, +, +, +, +, +, +, -),

	send_list(:, +),
	send_list(:, +, +),
	send_list1(:, +),
	send_list1(:, +, +),

	get_chain(+, :, -).


:- use_module(library(pce)).
:- require([ pce_error/1
	   ]).

%   get_object(+@Object, +Selector, ...+Argument, ..., -Output)
%
%   Succeeds once if Output is the value returned by invoking get method
%   called Selector on Object.  Output is an object description, except for the
%   special objects @nil, @default, @on and @off all of which are both
%   object descriptions and object names.

get_object(Obj, Sel, Out) :-
	get(Obj, Sel, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, Out) :-
	get(Obj, Sel, A1, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, Out) :-
	get(Obj, Sel, A1, A2, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, Out) :-
	get(Obj, Sel, A1, A2, A3, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, A6, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, A6, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, A9, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, A9, R),
	get_to_object(R, Out).
get_object(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, Out) :-
	get(Obj, Sel, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, R),
	get_to_object(R, Out).

get_to_object(Ref, Object) :-
	(   atomic(Ref)
	->  Object = Ref
	;   object(Ref, Object)
	).


%	send_list(+ListOfObjs, +ListOfSels)
%
%	Send a messages to the carthesian product of ListOfObjs and
%	ListOfSels.

send_list(X, _) :-
	var(X),
	throw(error(instantiation_error, _)).
send_list(_, X) :-
	var(X),
	throw(error(instantiation_error, _)).
pce_ifhostproperty(prolog(quintus), [],
(   send_list([], _) :- !)).
send_list(_, []) :- !.
pce_ifhostproperty(prolog(quintus), [],
(   send_list([Object|Objects], Selectors) :- !, 
        send_list(Object, Selectors), 
        send_list(Objects, Selectors))).
send_list(Object, [Selector|Selectors]) :- !, 
        send_list(Object, Selector), 
        send_list(Object, Selectors).
send_list(Object, Selector) :-
        send_list1(Object, Selector).

send_list1(Module:Obj, Selector) :-
        atom(Module), !,
        send_list_module(Obj, Selector, Module).
send_list1(Object, Selector) :-
        send(Object, Selector).

send_list_module([], _, _) :- !.
send_list_module(_, [], _) :- !.
send_list_module([Object|Objects], Selectors, Module) :- !, 
        send_list_module(Object, Selectors, Module), 
        send_list_module(Objects, Selectors, Module).
send_list_module(Object, [Selector|Selectors], Module) :- !, 
        send_list_module(Object, Selector, Module), 
        send_list_module(Object, Selectors, Module).
send_list_module(Object, Selector, Module) :-
        send(Object, Module:Selector).


%       send_list(+ListOfObjs, +ListOfSels, +ListOfArgs)
%
%       Send a messages to the carthesian product of ListOfObjs and
%       ListOfSels.

send_list(X, _, _) :-
	var(X),
	throw(error(instantiation_error, _)).
send_list(_, X, _) :-
	var(X),
	throw(error(instantiation_error, _)).
send_list(_, _, X) :-
	var(X),
	throw(error(instantiation_error, _)).
pce_ifhostproperty(prolog(quintus), [],
(   send_list([], _,  _) :- !)).
send_list(_, [], _) :- !.
send_list(_, _, []) :- !.
pce_ifhostproperty(prolog(quintus), [],
(   send_list([Object|Objects], Selectors, Arguments) :- !, 
        send_list(Object, Selectors, Arguments), 
        send_list(Objects, Selectors, Arguments))).
send_list(Objects, [Selector|Selectors], Arguments) :- !, 
        send_list(Objects, Selector, Arguments), 
        send_list(Objects, Selectors, Arguments).
send_list(Object, Selector, [Argument|Arguments]) :- !, 
        send_list(Object, Selector, Argument), 
        send_list(Object, Selector, Arguments).
send_list(Object, Selector, Argument) :-
        send_list1(Object, Selector, Argument).

send_list1(Module:Obj, Selector, Arg) :-
        atom(Module), !,
        send_list_module(Obj, Selector, Arg, Module).
send_list1(Obj, Selector, Arg) :-
        send(Obj, Selector, Arg).

send_list_module([], _, _, _) :- !.
send_list_module(_, [], _, _) :- !.
send_list_module(_, _, [], _) :- !.
send_list_module([Object|Objects], Selectors, Arguments, Module) :- !, 
	send_list_module(Object, Selectors, Arguments, Module), 
	send_list_module(Objects, Selectors, Arguments, Module).
send_list_module(Objects, [Selector|Selectors], Arguments, Module) :- !, 
	send_list_module(Objects, Selector, Arguments, Module), 
	send_list_module(Objects, Selectors, Arguments, Module).
send_list_module(Object, Selector, [Argument|Arguments], Module) :- !, 
	send_list_module(Object, Selector, Argument, Module), 
	send_list_module(Object, Selector, Arguments, Module).
send_list_module(Object, Selector, Argument, Module) :-
	send(Object, Module:Selector, Argument).


%   get_chain(+@Object, +Selector, -List)
%
%   List is a Prolog list constructed from the PCE chain returned by <-Selector
%   on Object.  get_chain/3 returns a list of object names, 

get_chain(Object, Selector, List) :-
	get(Object, Selector, Chain), 
	chain_list(Chain, List).


%   chain_list(@+Chain, -List)
%   chain_list_object(@+Chain, -List)
%
%   List is a Prolog list of all objects in Chain.  chain_list/2 returns object
%   names, chain_list_object/2 object descriptions.

chain_list(Chain, List) :-
	nonvar(Chain), !,
	(   Chain == @nil
	->  List = []
	;   to_object(Chain, ChainObject),
	    send(ChainObject, instance_of, chain),
	    (   send(ChainObject, current_no, 1)
	    ->  chain_to_list_(ChainObject, List)
	    ;   List = []
	    )
	).
chain_list(Chain, List) :-
	new(Chain, chain),
	send_list(Chain, append, List).

chain_to_list_(Chain, [El|Rest]) :-
	get(Chain, next, El), !, 
	chain_to_list_(Chain, Rest).
chain_to_list_(Chain, []) :-
	\+ get(Chain, current, _).

to_object(Ref, Ref) :-
	object(Ref), !.
to_object(Term, Obj) :-
	new(Obj, Term).


		/********************************
		*             DEFAULTS		*
		********************************/

%	default(+Argument, +Default, -Value)
%	default(+Argument, class_variable(+Object, +Name), -Value)
%
%	Get the default value for an argument.

default(@default, Default, Value) :- !,
	(   var(Default)
	->  Value = Default
	;   (   Default = class_variable(Obj, Name)
	    ;	Default = resource(Obj, Name)
	    )
	->  (   get(Obj, class_variable_value, Name, Value)
	    ->	true
	    ;	pce_error(get_class_variable_failed(Name, Obj))
	    )
	;   Value = Default
	).
default(Value, _Default, Value).
