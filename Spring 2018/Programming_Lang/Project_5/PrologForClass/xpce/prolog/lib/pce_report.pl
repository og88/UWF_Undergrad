/*  $Id: pce_report.pl,v 1.7 2003/10/01 13:11:45 jan Exp $

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


:- module(pce_reporter, []).

:- pce_begin_class(reporter, label,
		   "Label for reporting").

variable(hor_stretch,	int := 100,	get, "Stretch-ability").
variable(hor_shrink,	int := 100,	get, "Shrink-ability").
variable(delay_next_to,	date*,		get, "Delay errors and warnings").

initialise(R) :->
	send(R, send_super, initialise, reporter, ''),
	send(R, elevation, -1),
	send(R, border, 2),
	send(R, reference, point(0, R?height)).

report(R, Status:name, Fmt:[char_array], Args:any ...) :->
	(   get(R, delay_next_to, DelayTo),
	    DelayTo \== @nil,
	    get(DelayTo, difference, new(date), ToGo),
	    ToGo > 0,
	    (   vital(Status)
	    ->	send(timer(ToGo), delay),
		fail
	    ;	true
	    )
	->  true
	;   Msg =.. [report, Status, Fmt | Args],
	    colour(Status, Colour),
	    send(R, colour, Colour),
	    delay(Status, Date),
	    send(R, slot, delay_next_to, Date),
	    send_super(R, Msg)
	).
	
colour(error, red) :- !.
colour(_, @default).

delay(warning, Date) :- !,
	new(Date, date),
	send(Date, advance, 2).
delay(error, Date) :- !,
	new(Date, date),
	send(Date, advance, 5).
delay(_, @nil).

vital(warning).
vital(error).
vital(inform).

:- pce_end_class.


:- pce_begin_class(report_dialog, dialog,
		   "Dialog window holding reporter").

initialise(D) :->
	send_super(D, initialise),
	send(D, gap, size(0, 0)),
	send(D, resize_message, message(D, layout, @arg2)),
	send(D, append, new(reporter)).

:- pce_end_class.
