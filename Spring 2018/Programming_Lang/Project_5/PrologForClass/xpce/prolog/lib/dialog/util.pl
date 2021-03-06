/*  $Id: util.pl,v 1.4 2002/02/28 19:14:58 jan Exp $

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

:- module(dia_util, []).
:- use_module(library(pce)).

:- pce_begin_class(dia_transient_hyper, hyper,
		   "Hyper-link to a transient window").

unlink_from(H) :->
	get(H, to, Transient),
	send_super(H, unlink_from),
	send(Transient, destroy).

:- pce_end_class.


:- pce_begin_class(dia_client_hyper, dia_transient_hyper,
		   "Clear dialog when destroyed").

unlink_to(H) :->
	get(H, to, To),
	send(To, clear),
	send_super(H, unlink_to).

:- pce_end_class.
