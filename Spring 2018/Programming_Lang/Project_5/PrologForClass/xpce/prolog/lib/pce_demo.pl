/*  $Id: pce_demo.pl,v 1.3 1994/12/02 16:26:12 jan Exp $

    Part of XPCE
    Designed and implemented by Anjo Anjewierden and Jan Wielemaker
    E-mail: jan@swi.psy.uva.nl

    Copyright (C) 1992 University of Amsterdam. All rights reserved.
*/

:- module(pce_demo,
	  [ pcedemo/0
	  ]).


:- use_module(library(pce)).
:- use_module(library('contrib/contrib')).
:- require([ emacs/1
	   , forall/2
	   , member/2
	   ]).


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
This file defines a demo-starting tool.  The  demo's themselves should
be  in the library  files 'demo/<demo-file>.pl'.  At   the end of this
file is a list of available demo's.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

pcedemo :-
	new(B, browser('PCE Demo''s', size(60,10))),
	send(B, tab_stops, vector(150)),
	fill_browser(B),

	new(D, dialog),
	send(D, below, B),
	send(D, append, new(O, button(open, message(@prolog, open_demo, B)))),
	send(D, append, button(source, message(@prolog, view_source, B))),
	send(D, append, button(quit, message(D?frame, free))),
	send(D, default_button, open),

	send(B, open_message, message(O, execute)),
	send(B, style, title, style(font := font(helvetica, bold, 14))),
	
	send(B, open).


fill_browser(B) :-
	forall(demo(Name, Summary, _File, _Predicate),
	       send(B, append, dict_item(Name,
					 string('%s	%s', Name, Summary)))),
	send(B, append,
	     dict_item('======Contributions====================',
		       style := title)),
	forall(contribution(Name, Summary, _Author, _File, _Predicate),
	       send(B, append, dict_item(Name,
					 string('%s	%s', Name, Summary)))).


open_demo(Browser) :-
	get(Browser, selection, DictItem),
	(   (	DictItem == @nil
	    ;	get(DictItem, style, title)
	    )
	->  send(@display, inform, 'First select a demo')
	;   get(DictItem, key, Name),
	    (	(   demo(Name, _Summary, File, Predicate)
		;   contribution(Name, _Summary, _Author, File, Predicate)
		)
	    ->  (   use_module(File)
		->  (   Predicate
		    ->  true
		    ;   send(@pce, inform, 'Failed to start %s demo', Name)
		    )
		;   send(@pce, inform, 'Can''t find demo sourcefile')
		)
	    ;   send(Browser, report, error, 'No such demo: %s', Name)
	    )
	).

view_source(Browser) :-
	get(Browser, selection, DictItem),
	(   DictItem == @nil
	->  send(@display, inform, 'First select a demo')
	;   get(DictItem, key, Name),
	    (	demo(Name, _Summary, File, _Predicate)
	    ;	contribution(Name, _Summary, _Author, File, _Predicate)
	    ),
	    locate_file(File, Path),
	    emacs(Path)
	).

locate_file(Base, File) :-
	atom(Base),
	member(Ext, ['.pl', '']),
	new(S, string('%s%s', Base, Ext)),
	send(file(S), exists), !,
	get(S, value, File).
locate_file(library(Base), Path) :-
	user:library_directory(Dir),
	member(Ext, ['.pl', '']),
	new(S, string('%s/%s%s', Dir, Base, Ext)),
	send(file(S), exists), !,
	get(S, value, Path).
locate_file(Base, _) :-
	format('Cannot find source-file ~w~n', Base),
	fail.

		/********************************
		*             DEMO'S		*
		********************************/

demo('PceDraw',				% Name              
     'Drawing tool',			% Summary           
     library(pcedraw),			% Sources           
     pcedraw).				% Toplevel predicate

demo('Ispell',
     'Graphical interface to ispell (requires ispell 3)',
     library('demo/ispell'),
     ispell) :-
	send(@pce, has_feature, process).

demo('Emacs',
     'Emacs (Epoch) look-alike editor',
     library(pce_emacs),
     emacs).

demo('FontViewer',
     'Examine PCE predefined fonts',
     library('demo/fontviewer'),
     fontviewer).

demo('ImageViewer',
     'Examine image files in a directory',
     library('demo/imageviewer'),
     image_viewer).

demo('Cursors',
     'Displays browser of available cursors',
     library('demo/cursor'),
     cursor_demo).

demo('Events',
     'Display hierarchy of event-types',
     library('demo/event_hierarchy'),
     event_hierarchy).

demo('GraphViewer',
     'Visualise a graph represented as Prolog facts',
     library('demo/graph'),
     graph_viewer).

demo('FtpLog',
     'Examine /usr/adm/xferlog (ftp log file)',
     library('demo/ftplog'),
     ftplog('/usr/adm/xferlog')) :-
	send(@pce, has_feature, process).


demo('ChessTool',
     'Simple frontend for /usr/games/chess',
     library('demo/chess'),
     chess) :-
	send(@pce, has_feature, process).

demo('Example Dialog',
     'Dialog showing all dialog components',
     library('demo/dialog'),
     demo_dialog).

demo('Constraints',
     'Using constraints and relations',
     library('demo/constraint'),
     constraint_demo).

demo('Kangaroo',
     'Jumping kangaroos demo',
     library('demo/kangaroo'),
     kangaroo).

demo('Juggler',					  
     'Annimation of a juggling creature',	  
     library('demo/juggler'),			  
     juggle_demo).				  


demo('Biff',
     'Notify incoming mail',
     library('demo/biff'),
     biff).
