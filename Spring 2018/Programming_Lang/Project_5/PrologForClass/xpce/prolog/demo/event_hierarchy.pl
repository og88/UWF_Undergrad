/*  $Id: event_hierarchy.pl,v 1.4 2002/02/01 15:04:49 jan Exp $

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


:- module(pce_event_hierarchy,
	  [ event_hierarchy/0
	  ]).

:- use_module(library(pce)).
:- require([ chain_list/2
	   , forall/2
	   , member/2
	   ]).

event_hierarchy :-
	new(P, picture('PCE Event Hierarchy')),
	new(D, dialog),
	send(D, below, P),
	send(D, append, button(quit, message(P, destroy))),
	get(@event_tree, root, Root),
	new(T, tree(new(RootNode, node(text(Root?value, left, normal))))),
	fill_event_hierarchy(Root, RootNode),
	send(P, display, T),
	send(P, open).


fill_event_hierarchy(Node, TreeNode) :-
	get(Node, sons, Sons),
	Sons \== @nil, !,
	chain_list(Sons, List),
	forall(member(S, List),
	       (send(TreeNode, son, new(N, node(text(S?value, left, normal)))),
	        fill_event_hierarchy(S, N))).
fill_event_hierarchy(_, _).
