/*  $Id: toc_filesystem.pl,v 1.2 2002/02/01 15:04:49 jan Exp $

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

:- module(toc_filesystem, []).
:- use_module(library(pce)).
:- use_module(library(pce_toc)).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
This  library  refines   library(pce_toc)    with   filesystem  browsing
capabilities. It again is designed for  further subclassing to deal with
domain specific subclasses as demonstrated in the Prolog Navigator.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

:- pce_begin_class(toc_directory, toc_folder,
		   "Represent a directory").

variable(name,	name,	get, "Base name of the folder").

initialise(F, Dir:directory, Show:[name]) :->
	default(Show, name, Selector),
	get(Dir, Selector, Label),
	send_super(F, initialise, Label, Dir),
	get(Dir, name, BaseName),
	send(F, slot, name, BaseName).

show(F, Show:name) :->
	"Determine selector visualised"::
	get(F, identifier, Dir),
	get(Dir, Show, Label),
	send(F, label, Label).

expand(F) :->
	"Expand this directory"::
	send(F, update).

refresh(F) :->
	"Update for possible changes"::
	get(F, identifier, Dir),
	(   send(Dir, modified)
	->  send(F, update)
	;   true
	),
	send(F?sons, for_all,
	     if(message(@arg1, has_send_method, refresh),
		message(@arg1, refresh))).

update(F) :->
	"Really update"::
	get(F, identifier, Dir),
	(   send(Dir, exists)
	->  get(F?tree, window, FB),
	    (   send(FB, has_get_method, file_pattern)
	    ->  get(FB, file_pattern, Regex)
	    ;   Regex = @default
	    ),
	    new(SubDirNames, chain),
	    new(SubFileNames, chain),
	    send(Dir, scan, SubFileNames, SubDirNames, Regex),
    
	    get(F?sons, map, @arg1?name, Labels), % delete removed ones
	    send(Labels, subtract, SubFileNames),
	    send(Labels, subtract, SubDirNames),
	    send(F?sons, for_all,
		 if(message(Labels, member, @arg1?name),
		    message(@arg1, delete_tree))),
		 
	    (   send(SubDirNames, empty),
		send(SubFileNames, empty)
	    ->  true
	    ;   send(SubDirNames, for_all, message(F, ensure_dir, @arg1)),
		send(SubFileNames, for_all, message(F, ensure_file, @arg1)),
		send(F, sort_sons)
	    )
	;   send(F, delete_tree)
	).

		
ensure_dir(F, SubDir:name) :->
	"Ensure we have a subdirectory with this name"::
	(   get(F?sons, find, @arg1?name == SubDir, Node)
	->  (   send(Node, instance_of, toc_directory)
	    ->	true
	    ;	send(Node, delete_tree),
		send(F, make_dir, SubDir)
	    )
	;   send(F, make_dir, SubDir)
	).

make_dir(F, Name:name) :->
	"Add a subdirectory"::
	get(F, identifier, Dir),
	get(Dir, directory, Name, SubDir),
	get(F?tree, window, FB),
	send(FB, son, F, toc_directory(SubDir)).

ensure_file(F, File:name) :->
	"Ensure file is displayed"::
	(   get(F?sons, find, @arg1?name == File, Node)
	->  (   send(Node, instance_of, toc_directory)
	    ->	send(Node, delete_tree),
		send(F, make_file, File)
	    ;	true
	    )
	;   send(F, make_file, File)
	).

make_file(F, Name:name) :->
	"Add a subdirectory"::
	get(F, identifier, Dir),
	get(Dir, file, Name, File),
	get(F?tree, window, FB),
	get(FB, make_file_node, File, Node),
	send(FB, son, F, Node).

sort_sons(F) :->
	"Sort the sons"::
	send_super(F, sort_sons, ?(F, compare_sons, @arg1, @arg2)).

compare_sons(_, S1:node, S2:node, Diff:{smaller,equal,larger}) :<-
	"Directories above files, both in alpabetical order"::
	(   send(S1, instance_of, toc_directory)
	->  (   send(S2, instance_of, toc_directory)
	    ->	get(S1?name, compare, S2?name, Diff)
	    ;	Diff = smaller
	    )
	;   (   send(S2, instance_of, toc_directory)
	    ->	Diff = larger
	    ;	get(S1?label, compare, S2?label, Diff)
	    )
	).

:- pce_end_class(toc_directory).


:- pce_begin_class(toc_filesystem, toc_window,
		   "Table-of-content based on directories").

class_variable(auto_refresh, int*, 10,
	       "Check directories for modifications after this interval").

variable(refresh_timer,	timer*, get, "Timer for automatic refresh").

initialise(FB, Root:[directory]) :->
	"Create from initial dierctory"::
	(   Root == @default
	->  absolute_file_name('.', Dir),
	    new(R, directory(Dir))
	;   get(Root, path, Path0),
	    absolute_file_name(Path0, Dir),
	    new(R, directory(Dir))
	),
	send_super(FB, initialise),
	send(FB, root, toc_directory(R, path)),
	send(FB, expand_root),
	(   get(FB, auto_refresh, Time),
	    Time \== @nil
	->  send(FB, auto_refresh, Time)
	;   true
	).

unlink(FB) :->
	send(FB, kill_timer),
	send_super(FB, unlink).

expand_node(FB, Id:any) :->
	"Expand a directory"::
	get(FB, node, Id, Node),
	send(Node, expand).

up(FB) :->
	"Provide the parent directory"::
	get(FB, root, RootNode),
	get(RootNode, identifier, RootDir),
	get(RootDir, parent, Parent),
	send(FB, root,
	     new(R, toc_directory(Parent, path)), @on),
	send(RootNode, show, name),
	send(R, update).

:- pce_group(virtual).

make_file_node(_FB, File:file, Node:toc_node) :<-
	"Virtual: create a node for a file"::
	get(File, base_name, Name),
	new(Node, toc_file(Name, File)),
	send(Node, name, Name).

:- pce_group(expand).

dir_node(FB, Dir:directory, Create:[bool], Node:toc_node) :<-
	"Get node for directory, possibly add it to tree"::
	get(FB?tree, nodes, NodeTable),
	(   get(NodeTable, find_key,
		and(message(@arg1, instance_of, directory),
		    message(@arg1, same, Dir)),
		NodeDir)
	->  get(NodeTable, member, NodeDir, Node)
	;   Create == @on
	->  get(Dir, parent, Parent),
	    get(FB, dir_node, Parent, Create, ParentNode),
	    send(ParentNode, collapsed, @off),
	    get(ParentNode?sons, find,
		and(message(@arg1?identifier, instance_of, directory),
		    message(@arg1?identifier, same, Dir)),
		Node)
	).


:- pce_group(refresh).

refresh(FB) :->
	"->refresh the root"::
	get(FB, root, RootNode),
	send(RootNode, refresh).

auto_refresh(FB, Interval:int*) :->
	"Associate an auto-refresh timer"::
	send(FB, kill_timer),
	(   Interval == @nil
	->  true
	;   send(FB, slot, refresh_timer,
		 new(T, timer(Interval, message(FB, refresh)))),
	    send(T, status, repeat)
	).
	
kill_timer(FB) :->
	"Kill timer if we have one"::
	(   get(FB, slot, refresh_timer, Timer),
	    Timer \== @nil
	->  send(Timer, status, idle),
	    free(Timer),
	    send(FB, slot, refresh_timer, @nil)
	;   true
	).

:- pce_end_class(toc_filesystem).
