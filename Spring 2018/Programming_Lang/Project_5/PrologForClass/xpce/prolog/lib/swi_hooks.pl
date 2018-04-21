/*  $Id: swi_hooks.pl,v 1.5 2003/03/17 10:27:55 jan Exp $

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

:- module(pce_swi_hooks, []).

:- multifile
	prolog:debug_control_hook/1,
	prolog:help_hook/1,
	prolog:show_profile_hook/2.

       		 /*******************************
		 *	    DEBUG HOOKS		*
		 *******************************/

prolog:debug_control_hook(spy(Method)) :-
	call(spypce(Method)).
prolog:debug_control_hook(nospy(Method)) :-
	call(nospypce(Method)).


		 /*******************************
		 *	     HELP HOOK		*
		 *******************************/

prolog:help_hook(help) :- !,
	call(prolog_help).
prolog:help_hook(apropos(What)) :- !,
	call(prolog_apropos(What)).
prolog:help_hook(help(What)) :- !,
	call((   pce_to_method(What, Method)
	     ->  manpce(Method)
	     ;   prolog_help(What)
	     )).


		 /*******************************
		 *	     PROFILING		*
		 *******************************/

prolog:show_profile_hook(_Style, _Top) :-
	call(pce_show_profile).
