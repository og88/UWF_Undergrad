/*  $Id: prolog_mode.pl,v 1.70 2004/09/21 14:22:25 jan Exp $

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

:- module(emacs_prolog_mode, []).
:- use_module(library(pce)).
:- use_module(library(emacs_extend)).
:- use_module(library(prolog_predicate)).
:- use_module(library(pce_prolog_xref)).
:- require([ make/0
	   , absolute_file_name/3
	   , auto_call/1
	   , chain_list/2
	   , concat_atom/2
	   , default/3
	   , forall/2
	   , ignore/1
	   , list_to_set/2
	   , member/2
	   , memberchk/2
	   , seek/4
	   , strip_module/3
	   ]).
pce_ifhostproperty(prolog(quintus),
		   (:- use_module(library(strings), [concat_chars/2]))).

resource(mode_pl_icon, image, image('32x32/doc_pl.xpm')).
resource(breakpoint,   image, image('16x16/stop.xpm')).

:- emacs_begin_mode(prolog, language,
		    "Mode for editing XPCE/Prolog sources",
					% BINDINGS
	[ insert_if_then_else	       = key('(') + key(';') + key('>'),

					% delete some things
	  manual_entry		       = -button(help),
	  find_tag		       = -button(browse),
	  compile		       = -button(compile),

					% extend the menus
	  prolog_manual		       = button(help),
	  (spy)			       = button(prolog),
	  trace			       = button(prolog),
	  break_at		       = key('\\C-cb') + button(prolog),
	  delete_breakpoint	       = button(prolog),
	  edit_breakpoints	       = button(prolog),
	  -			       = button(prolog),
	  check_clause		       = key('\\C-c\\C-s') + button(prolog),
	  insert_full_stop	       = key(.),
	  find_definition	       = key('\\e.') + button(browse),
	  -			       = button(prolog),
	  make			       = key('\\C-c\\C-m') + button(compile),
	  compile_buffer	       = key('\\C-c\\C-b') + button(compile),
	  consult_selection	       = button(compile) + button(compile),
	  source_file		       = button(browse,
						@prolog?source_file_chain),

	  forward_clause	       = key('\\ee'),
	  backward_clause	       = key('\\ea'),
	  backward_predicate	       = key('\\e['),
	  forward_predicate	       = key('\\e]'),

	  editpce		       = key('\\C-ce') + button(pce),
	  tracepce		       = key('\\C-ct') + button(pce),
	  spypce		       = button(pce),
	  -			       = button(pce),
	  what_class		       = key('\\C-cw') + button(pce),
	  -			       = button(pce),
	  pce_insert_require_directive = key('\\C-c\\C-r') + button(pce),
	  pce_check_require_directives = button(pce),
	  -			       = button(pce),
	  pce_define_class	       = button(pce),
	  -			       = button(browse),
	  prolog_navigator	       = button(browse) + key('\\C-c\\C-n')
	],
					% SYNTAX TABLE
	[ $    = symbol,
	  @    = symbol,
%	  '"'  = string_quote('"'),
%	  '''' = string_quote(''''),
	  '%'  = comment_start,
	  '\\n' + comment_end,
	  '/'  + comment_start('*'),
	  '*'  + comment_end('/'),

	  paragraph_end([ '\\s *$',		% empty line
			  '^/\\*',		% comment start
			  '.*\\*/\\s *$', 	% comment end
			  '^%',			% line comment
			  '^	',		% indented line
			  '.*:<?->?\\s *$'	% clause head
			])
	]).
		 
class_variable(varmark_style, style*,
	       style(background := honeydew,
		     underline := @on)).

variable(varmark_style, style*,       get, "How to mark variables").
variable(has_var_marks, bool := @off, get, "Optimise a bit").

icon(_, I:image) :<-
	"Return icon for mode"::
	catch(new(I, image(resource(mode_pl_icon))), _, fail).

setup_mode(M) :->
	"Attach styles for errors, warnings, etc."::
	send_super(M, setup_mode),
	send(M, style, singleton,  style(bold := @on)),
	send(M,	style, breakpoint, style(icon := resource(breakpoint))),
	send(M,	style, error,	   style(background := red)),
	send(M,	style, warning,	   style(background := orange)),
	send(M,	style, info,	   style(background := grey80)),
	(   get(M, varmark_style, Style),
	    Style \== @nil
	->  send(M, style, varmark, Style)
	;   true
	),
	send(M, setup_styles).


:- send(@class, attribute, outline_regex_list,
	chain(regex('\\(^\\w+.*:<?->?\\)\\([^.]+\\.\\(\\s *\n\\)*\\)\\s '))).

source_file_chain(Ch) :-
	new(Ch, chain),
	forall(user_source_file(X), send(Ch, append, X)),
	send(Ch, sort).

user_source_file(F) :-
	source_file(F),
	\+ (lib_dir(D), atom_concat(D, _, F)).

ignore_paths_from(library).
ignore_paths_from(pce_boot).

lib_dir(D) :-
	ignore_paths_from(Category),
	user:file_search_path(Category, X),
	expand_path(X, D0),
	absolute_file_name(D0, D).	% canonise

expand_path(X, X) :-
	atomic(X), !.
expand_path(Term, D) :-
	Term =.. [New, Sub],
	user:file_search_path(New, D0),
	expand_path(D0, D1),
	concat_atom([D1, /, Sub], D).


:- pce_group(indent).

:- pce_global(@prolog_neck_regex,
	      new(regex(':-\\|:->\\|:<-\\|-->'))).
:- pce_global(@prolog_full_stop,
	      new(regex('[^-#$&*+./:<=>?@\\^`~]\\.\\($\\|\\s \\)'))).

indent_line(E) :->
	"Indent current line (Prolog indentation)"::
	send(E, beginning_of_text_on_line),
	get(E, caret, Caret),
	get(E, beginning_of_clause, Caret, Base),
	(   send(E, indent_comment_line)
	;   send(E, indent_close_bracket_line, ')}]', Base)
	;   send(E, indent_if_then_else)
	;   send(E, indent_expression_line, ')}]', Base)
	;   send(E, indent_clause_line)
	;   send(E, align_line, 8)
	).
	

beginning_of_clause(E, Start:int, BOP:int) :<-
	"Find start of predicate"::
	new(Here, number(Start)),
	get(E, text_buffer, TB),
	repeat,
	    (	(   send(Here, less_equal, 0)
		;   \+ send(@prolog_full_stop, search, TB, Here, 0)
		)
	    ->	!,
		(   get(TB, character, 0, 0'#) 	% Deal with #! first-line
		->  get(TB, scan, 0, line, 1, start, BOP0)
		;   BOP0 = 0
		),
		get(TB, skip_comment, BOP0, BOP)
	    ;   get(@prolog_full_stop, register_start, SReg),
		get(@prolog_full_stop, register_end, P0),
		send(Here, value, SReg),
		get(TB, skip_comment, P0, BOP),
		BOP =< Start,
		get(TB, scan_syntax, 0, BOP, tuple(code,_))
	    ).
beginning_of_clause(E) :->
	"Goto start of clause"::
	get(E, caret, Caret),
	get(E, beginning_of_clause, Caret, BOC),
	send(E, caret, BOC).

beginning_of_if_then_else(E, Pos:int) :<-
	"Beginning of if-then-else construct"::
	get(E, caret, Caret),
	get(E, text_buffer, TB),
	pce_catch_error(mismatched_bracket,
			get(TB, matching_bracket, Caret, ')', Pos)),
	get(TB, character, Pos-1, Before),
	\+ send(E?syntax, has_syntax, Before, word),
	Before \== 0'?,				% '?(' for xpce
	get(E, beginning_of_clause, Caret, BegOfPred),
	BegOfPred < Pos,
	get(TB, scan_syntax, BegOfPred, Pos, tuple(code,_)).


indent_if_then_else(E) :->
	"Indent subclause in an (if->then;else)"::
	get(E, beginning_of_if_then_else, OpenPos),
	get(E, caret, Caret),
	get(E, text_buffer, TB),
	get(E, skip_comment, Caret-1, OpenPos, EndOfPreviousTerm),
	(   send(regex('\\,'), match, TB, EndOfPreviousTerm)
	->  get(TB, scan, Caret, line, -1, start, StartOfPrevLine),
	    get(regex('\\s *\\(->\\|;\\)\\s *'), match, TB, StartOfPrevLine, L),
	    get(E, column, L+StartOfPrevLine, PrevExprCol),
	    send(E, align_line, PrevExprCol)
	;   get(E, column, OpenPos, OpenCol),
	    send(E, align_line, OpenCol)
	).
	

indent_clause_line(E) :->
	"Indent current line according to clause"::
	get(E, caret, Caret),
	get(E, text_buffer, TB),
	get(E, skip_comment, Caret-1, 0, Glue),
	(   send(regex('\\.'), match, TB, Glue)		% new clause
	->  send(E, align_line, 0)
	;   send(regex('\\,'), match, TB, Glue)	  	% Next subclause
	->  get(E, alignment_of_previous_line, N),
	    (	N == 0					% head :- !,
	    ->	send(E, align_line, 8)
	    ;	send(E, align_line, N)
	    )
	;   send(@prolog_neck_regex, match, TB, Glue+1, 0) % First subclause
	->  send(E, align_line, 8)			% (seach backward)
	;   send(E, align_with_previous_line)
	).


insert_if_then_else(E, Times:[int], Char:char) :->
	"Indent after typing (, > or ;"::
	send(E, insert_self, Times, Char),
	get(E, caret, Caret),
	get(E, text_buffer, TB),
	get(TB, scan, Caret, line, 0, start, SOL),
	(   get(regex('\\s *\\((\\|->\\|;\\)'), match, TB, SOL, L),
	    Caret =:= SOL + L,
	    get(E, beginning_of_if_then_else, OpenPos)
	->  get(E, text_buffer, TB),
	    get(TB, scan, Caret, line, 0, start, SOL),
	    (   (   send(regex('\\s *\\((\\|->\\|;\\)$'), match,
			 TB, SOL, Caret)
		;   Caret =:= 1 + OpenPos
		)
	    ->  get(E, column, OpenPos, Col),
		send(E, align, Col+4)
	    ;   true
	    )
	;   true
	).


indent_clause(E) :->
	"Indent current clause"::
	get(E, text_buffer, TB),
	get(E, beginning_of_clause, E?caret, Start),
	send(E, caret, Start),
	between(0, 1000, _),		% avoid loops on errors
	    send(E, indent_line),
	    get(E, caret, Caret),
	    (	get(regex('.*\\S.\\.'), match, TB, Caret, Size),
		End is Caret + Size,
		get(TB, scan_syntax, Start, End, tuple(code,_))
	    ->	!
	    ;	get(TB, size, Caret)
	    ->	!
	    ;   send(E, next_line),
		fail
	    ),
 	send(E, forward_char, Size),
	send(E, electric_caret, Start).


fill_paragraph(M, Justify:[int]) :->
	"Fill paragraph or indent clause"::
	get(M, caret, Caret),
	(   get(M, beginning_of_clause, Caret, BOC),
	    get(M, forward_clause, BOC, EOC),
	    between(BOC, EOC, Caret)
	->  send(M, indent_clause)
	;   send_super(M, fill_paragraph, Justify)
	).


		 /*******************************
		 *          COMPILATION		*
		 *******************************/

make(E) :->				% SWI-Prolog specific
	"Run `make/0' in the Prolog window"::
	send(@emacs, save_some_buffers),
	make,
	send(E, report, status, 'Make done').

compile_buffer(E) :->
	"Save current buffer and (re)consult its file"::
	get(E?text_buffer, file, File),
	(   send(File, instance_of, file)
	->  send(E, save_if_modified),
	    get(File, name, Path),
	    print_message(silent, emacs(consult(user:Path))),
	    consult(user:Path),
	    print_message(silent, emacs(consulted(user:Path))),
	    send(E, report, status, '%s compiled', Path)
	;   send(E, report, error,
		 'Buffer is not connected to a file')
	).


		/********************************
		*       FINDING PREDICATES	*
		********************************/

default(M, For:type, Default:unchecked) :<-
	"Provide default for prompter"::
	(   send(For, includes, prolog_predicate)
	->  get(M, caret, Caret),
	    get(M, name_and_arity, Caret, tuple(Name, Arity)),
	    concat_atom([Name, /, Arity], Default)
	;   get_super(M, default, For, Default)
	).


find_definition(M, For:prolog_predicate, NewWindow:[bool]) :->
	"Find definition of predicate [in new window]"::
	get(M, text_buffer, TB),
	get(For, head, @off, Head),
	(   (   xref_defined(TB, Head, local(Line))		% local
	    ;	xref_defined(TB, Head, constraint(Line))
	    )
	->  (   NewWindow == @on
	    ->	get(M, text_buffer, TB),
		new(W2, emacs_frame(TB)),
		send(W2?editor, goto_line, Line)
	    ;	send(M, goto_line, Line)
	    )
	;   xref_defined(TB, Head, imported(File))	% imported
	->  new(B, emacs_buffer(File)),
	    get(B, open, NewWindow, EmacsFrame),
	    get(EmacsFrame, mode, Mode),
	    send(Mode, instance_of, emacs_prolog_mode),
	    send(Mode, find_local_definition, For)
	;   get(For, source, SourceLocation) 		% From Prolog DB
	->  send(@emacs, goto_source_location, SourceLocation, NewWindow)
	;   send(For, has_property, foreign)
	->  send(M, report, warning,
		 'Predicate is defined in a foreign language')
	;   send(M, report, warning,
		 'Cannot find source')
	).


find_local_definition(M, For:prolog_predicate) :->
	"Find Prolog predicate in local buffer"::
	get(M, text_buffer, TB),
	get(For, head, @off, Head),
	(   (   xref_defined(TB, Head, local(Line))
	    ->  true
	    ;   send(M, xref_buffer),
		xref_defined(TB, Head, local(Line))
	    )
	->  send(M, goto_line, Line)
	;   send(M, report, warning, 'Cannot find %N', For)
	).


		 /*******************************
		 *	    PCE CLASSES		*
		 *******************************/

class_regex(':-\\s *pce_begin_class(\\(\\w+\\)',
	    ':-\\s *pce_end_class\\s *.',
	    A-[A]).
class_regex(':-\\s *emacs_begin_mode(\\(\\w+\\)',
	    ':-\\s *emacs_end_mode\\s *.',
	    A-[emacs_, A, '_mode']).

what_class(E, ClassName:name) :<-
	"Find current XPCE class"::
	get(E, caret, Caret),
	get(E, text_buffer, TB),
	class_regex(Begin, End, Raw-Parts),
	new(BG, regex(Begin)),
	get(BG, search, TB, Caret, 0, BeginClass),
	(   get(regex(End), search, TB, Caret, 0, EndClass)
	->  EndClass < BeginClass
	;   true
	), !,
	get(BG, register_value, TB, 1, name, Raw),
	concat_atom(Parts, ClassName).

what_class(E) :->
	"Display current class"::
	(   get(E, what_class, ClassName)
	->  send(E, report, inform, 'Caret is in XPCE class "%s"', ClassName)
	;   send(E, report, inform,
		 'Not between :- pce_begin_class and :- pce_end_class')
	).


source_file(E, F:file) :->
	"Switch to named source_file"::
	send(E, find_file, F).


prolog_module(M, Module:name) :<-
	"Return module defined in this class"::
	get(M, prolog_term, 0, ModuleTerm),
	ModuleTerm = (:- module(Module, _)).


what_module(M) :->
	"Report the Prolog module defined in this file"::
	(   get(M, prolog_module, Module)
	->  send(M, report, status,
		 'File defines Prolog module "%s"', Module)
	;   send(M, report, status,
		 'Not a module file')
	).

pce_define_class(M, Name:name,
		 SuperClass:super='class|name', Comment:comment=string) :->
	"Insert XPCE class definition"::
	(   atom(SuperClass)
	->  Super = SuperClass
	;   get(SuperClass, name, Super)
	),
	sformat(QName, '~q', Name),
	sformat(QSuper, '~q', Super),
	send(Comment, strip),
	get(Comment, value, C),
	(   C == ''
	->  send(M, format,
		 ':- pce_begin_class(%s, %s).\n\n\
		  :- pce_end_class(%s).\n',
		 QName, QSuper, QName)
	;   atom_codes(C, Codes),
	    string_to_list(S, Codes),
	    sformat(QComment, '~q', S),
	    send(M, format,
		 ':- pce_begin_class(%s, %s, %s).\n\n\
		  :- pce_end_class(%s).\n',
		 QName, QSuper, QComment, QName)
	),
	send(M, previous_line, 2).


		 /*******************************
		 *	   BROWSE STUFF		*
		 *******************************/

prolog_navigator(M) :->
	"Open source-file browser"::
	get(M, file, File), File \== @nil,
	get(File, absolute_path, Path),
	get(M, line_number, Line),
	prolog_ide(open_navigator(source_location(Path, Line))).


edit_breakpoints(_M) :->
	"Open Prolog debug settings window"::
	prolog_ide(open_debug_status).


		 /*******************************
		 *	   COMPILATION		*
		 *******************************/

consult_region(M, From:[int], To:[int]) :->
	"Consult region between indices"::
	default(From, M?mark, F),
	default(To, M?caret, T),
	get(T-F, value, S),
	(   S >= 0
	->  Start = F, Size = S
	;   Start = T, Size is -S
	),
	new(File, file),		% temporary file
	send(File, open, write),
	send(File, append, ?(M, contents, Start, Size)),
	send(File, newline),		% make sure it ends with a newline
	send(File, close),
	get(File, name, TmpNam),
	consult(user:TmpNam),
	send(M, report, status, 'Region consulted'),
	send(File, remove).
	

consult_selection(M) :->
	"Consult selected text"::
	get(M, selection, point(From, To)),
	send(M, consult_region, From, To).


		 /*******************************
		 *	       PCE		*
		 *******************************/

pce_insert_require_directive(M) :->
	"Insert :-require/1 directive"::
	send(M, save_if_modified),
	get(M, file, File),
	get(File, name, Name),
	auto_call(pce_require(Name, Directive, Message)),
	send(M, insert, Directive),
	(   Message \== ''
	->  send(M, report, status, Message)
	;   true
	).


pce_check_require_directives(M, Dir:directory) :->
	"Mark :- require's that are out-of-date"::
	get(Dir, files, '.*\\.pl$', PlFiles),
	send(PlFiles, for_some,
	     message(M, pce_check_require, ?(Dir, file, @arg1))),
	get(Dir, directories, SubDirs),
	send(SubDirs, for_some,
	     message(M, pce_check_require_directives,
		     ?(Dir, directory, @arg1))).


no_check(library(pce)).
no_check(library('xref/common')).
no_check(library('xref/mkcommon')).
no_check(library('xref/quintus')).
no_check(library('xref/sicstus')).

do_not_check(File) :-
	no_check(Spec),
	absolute_file_name(Spec, [access(read), extensions([pl])], Expanded),
	send(File, same, Expanded).

pce_check_require(M, File:file) :->
	"Open of there is no :- require"::
	(   do_not_check(File)
	->  true
	;   get(File, name, Name),
	    send(M, report, status, 'Checking %s', Name),
	    send(M, synchronise),
	    auto_call(pce_require(Name, _Directive, Message)),
	    (   send(Message, sub, 'up-to-date')
	    ->  true
	    ;   new(B, emacs_buffer(File)),
		(   get(regex('^:-\\s *require('), search, B, Index)
		->  true
		;   Index = 0
		),
		send(@emacs_mark_list, append_hit, B, Index)
	    ),
	    send(M, report, done)
	).

spy(M) :->
	"Set spy-point on implementation"::
	get(M, prolog_term, Term),
	(   do_spy(Term, M, Feedback)
	->  term_to_atom(Feedback, Atom),
	    send(M, report, status,
		 'Placed spy-point on "%s"', Atom)
	;   send(M, report, warning, 
		 'Can''t find anything to spy from caret location')
	).

do_spy((Head :-> _Body), M, (Class->Name)) :- !,
	get(M, what_class, Class),
	functor(Head, Name, _Arity),
	spypce((Class->Name)).
do_spy((Head :<- _Body), M, <-(Class, Name)) :- !,
	get(M, what_class, Class),
	functor(Head, Name, _Arity),
	spypce(<-(Class, Name)).
do_spy(variable(Name, _Type, _Access), M, (Class-Name)) :-
	get(M, what_class, Class),
	spypce((Class-Name)).
do_spy(variable(Name, _Type, _Access, _Doc), M, (Class-Name)) :-
	get(M, what_class, Class),
	spypce((Class-Name)).
do_spy((Head :- _Body), M, Spec) :-
	prolog_debug_spec(M, Head, Spec),
	user:spy(Spec).
do_spy(Head, M, Spec) :-
	prolog_debug_spec(M, Head, Spec),
	user:spy(Spec).


trace(M) :->
	"Set trace-point on implementation"::
	get(M, prolog_term, Term),
	(   do_trace(Term, M, Feedback)
	->  term_to_atom(Feedback, Atom),
	    send(M, report, status,
		 'Placed trace-point on "%s"', Atom)
	;   send(M, report, warning, 
		 'Can''t find anything to trace from caret location')
	).

do_trace((Head :-> _Body), M, (Class->Name)) :- !,
	get(M, what_class, Class),
	functor(Head, Name, _Arity),
	tracepce((Class->Name)).
do_trace((Head :<- _Body), M, <-(Class, Name)) :- !,
	get(M, what_class, Class),
	functor(Head, Name, _Arity),
	tracepce(<-(Class, Name)).
do_trace(variable(Name, _Type, _Access), M, (Class-Name)) :-
	get(M, what_class, Class),
	tracepce((Class-Name)).
do_trace(variable(Name, _Type, _Access, _Doc), M, (Class-Name)) :-
	get(M, what_class, Class),
	tracepce((Class-Name)).
do_trace((Head :- _Body), M, Spec) :-
	prolog_debug_spec(M, Head, Spec),
	user:trace(Spec).
do_trace(Head, M, Spec) :-
	prolog_debug_spec(M, Head, Spec),
	user:trace(Spec).

prolog_debug_spec(M, Head, Spec) :-
	catch(functor(Head, Name, Arity), _, fail),
	(   get(M, prolog_module, Module)
	->  Spec = (Module:Name/Arity)
	;   Spec = Name/Arity
	).
	    
		 /*******************************
		 *	       DROP		*
		 *******************************/
	
preview_drop(M, Obj:object*) :->
	"Preview the upcomming drop action"::
	(   Obj == @nil
	->  send(M, report, status, '')
	;   get(Obj, get_method, prolog_source, tuple(_, Method))
	->  (	get(Method, summary, Summary), Summary \== @nil
	    ->	send(M, report, status, 'Drop to include %s', Summary)
	    ;   send(M, report, status,
		     'Please drop to include source at caret')
	    )
	;   send(M, send_super, preview_drop, Obj)
	).

drop(M, Obj:object) :->
	"Import source-code from object"::
	(   send(Obj, has_get_method, prolog_source)
	->  send(M, insert, Obj?prolog_source),
	    send(M, mark_undo),
	    send(M, report, status, 'Source included')
	;   send(M, send_super, drop, Obj)
	).

		 /*******************************
		 *	  SYNTAX CHECKING	*
		 *******************************/

error_at_location(M, Caret:int) :->
	"Goto error at location"::
	send(M, caret, Caret),
	send(M, check_clause).


symbol_chars("-#$&*+./:<=>?@\\^`~").


symbol_char(C) :-
	symbol_chars(Symbols),
	memberchk(C, Symbols).


insert_full_stop(M, Arg:[int]) :->
	"Check clause after typing '.'"::
	send(M, insert_self, Arg, 0'.),
	get(M, text_buffer, TB),
	get(TB, size, Len),
	get(M, caret, Here),
	(   Here == Len
        ->  send(M, open_line)
	;   true
	),
	(   Arg == @default,
	    get(M, caret, Caret),
	    get(M, character, Caret-2, Prev),
	    \+ symbol_char(Prev),
	    get(M, scan_syntax, 0, Caret, tuple(code,_))
	->  get(M, check_clause, repair := @off, _End)
	;   true
	).

:- multifile
	alternate_syntax/3.

alternate_syntax(prolog,    true,
			    true).
alternate_syntax(pce_class, pce_expansion:push_compile_operators(SM),
			    pce_expansion:pop_compile_operators) :-
	'$set_source_module'(SM, SM).

:- dynamic
	syntax_error/1.

check_clause(M, From:from=[int], Repair:repair=[bool], End:int) :<-
	"Check clause, returning the end of it"::
        (   From == @default
	->  get(M, caret, C),
	    get(M, beginning_of_clause, C, Start),
	    ignore(send(M, electric_caret, Start)),
	    Verbose = true
	;   Start = From,
	    Verbose = fail
	),
	get(M, text_buffer, TB),
	pce_open(TB, read, Fd),
	read_term_from_stream(Fd, Start, T, Error, S, P),
	close(Fd),
	(   Error == none
	->  (	send(M, has_send_method, colourise_term)
	    ->	send(M, colourise_term, T, P)
	    ;	unmark_singletons(M, P)
	    ),
	    (   S == []
	    ->  (   Verbose
		->  send(M, report, status, 'Clause checked')
		;   true
		)
	    ;   send(M, mark_singletons, T, S, P),
		(   Repair \== @off
		->  replace_singletons(M, P)
		;   true
		)
	    ),
	    arg(2, P, E0),
	    get(TB, find, E0, '.', 1, end, End)
	;   Error = EPos:Msg,
	    (	Repair \== @off
	    ->  send(M, caret, EPos)
	    ;	true
	    ),
	    send(M, report, warning, 'Syntax error: %s', Msg),
	    fail
	).

read_term_from_stream(Fd, Start, T, Error, S, P) :-
	retractall(syntax_error(_)),
	alternate_syntax(_Name, Setup, Restore),
	Setup,
	seek(Fd, Start, bof, _),
	read_with_errors(Fd, Start, T, Error, S, P),
	Restore,
	(   Error == none
	->  true
	;   assert(syntax_error(Error)),
	    fail
	), !.
read_term_from_stream(_, _, _, Error, _, _) :-
	setof(E, retract(syntax_error(E)), Es),
	last(Es, Error).

pce_ifhostproperty(prolog(swi),
(read_with_errors(Fd, _Start, T, Error, Singletons, TermPos) :-
	catch(read_term(Fd, T, [ singletons(Singletons),
				 subterm_positions(TermPos)
			       ]),
	      Error0,
	      true),
	pl_error_message(Error0, Error))).
pce_ifhostproperty(prolog(quintus),
(read_with_errors(Fd, Start, T, Error, Singletons, TermPos) :-
	on_exception(syntax_error(_G, _Pos,
				  Message,
				  Pre, Post, _),
		     read_term(Fd, [ syntax_errors(error),
				     singletons(Singletons),
				     subterm_positions(TermPos)
				   ], T),
		     qp_error_message(Message, Start, Pre, Post, Error)),
	(var(Error) -> Error = none ; true))).

pce_ifhostproperty(prolog(swi),
[(
pl_error_message(X, none) :-
	var(X), !
 ),
 (
pl_error_message(error(syntax_error(Id),
		       stream(_S, _Line, _LinePos, CharNo)),
		 CharNo:Msg) :-
	message_to_string(error(syntax_error(Id), _), Msg)
 )
]).

pce_ifhostproperty(prolog(quintus),
(qp_error_message(Msg, Start, Pre, Post, EP:TheMsg) :-
	length(Pre, EP0),
	(   EP0 > 10
	->  length(PreM, 10),
	    append(_, PreM, Pre)
	;   PreM = Pre
	),
	length(Post, PL),
	(   PL > 10
	->  length(PosM, 10),
	    append(PosM, _, Post)
	;   PosM = Post
	),
	(   Msg == ''
	->  MsgChars0 = ''
	;   atom_codes(Msg, MsgChars0)
	),
	concat_chars([ MsgChars0,
		       "between `..", PreM, "' and `", PosM, "..'"
		     ],
		     MsgChars),
	atom_codes(TheMsg, MsgChars),
	EP is EP0 + Start)).


check_clause(M, From:[int]) :->
	"Check syntax of clause"::
	get(M, check_clause, From, _).


unmark_singletons(M, P) :-
	arg(1, P, Start),
	arg(2, P, End),
	new(Pt, point(Start, End)),
	send(M, for_all_fragments,
	     if(and(@arg1?style == singleton,
		    message(@arg1, overlap, Pt)),
		message(@arg1, free))).

%	->mark_singletons: Term, Singletons, Pos
%	
%	Mark singleton variables in Term, where Singletons is a list of
%	singleton variables returned from read_term/3 and Pos is the
%	subterm-position returned.

mark_singletons(M, Term:prolog, Singletons:prolog, Pos:prolog) :->
	"Mark singleton variables using info from read_term/3"::
	(   Singletons == []
	->  true
	;   mark_singletons(M, Term, Singletons, Pos)
	).

mark_singletons(M, T, S, A-Z) :-
	var(T),
	member_var(T, S), !,
	get(M, text_buffer, TB),
	new(_, emacs_colour_fragment(TB, A, Z-A, singleton)).
mark_singletons(_, _, _, _-_) :- !.
mark_singletons(_, _, _, list_position(_, _, [], none)) :- !.
mark_singletons(M, T, S, list_position(_, _, [], Tail)) :- !,
	mark_singletons(M, T, S, Tail).
mark_singletons(M, [H|T], S, list_position(A, Z, [E|ET], Tail)) :- !,
	mark_singletons(M, H, S, E),
	mark_singletons(M, T, S, list_position(A, Z, ET, Tail)).
mark_singletons(_, _, _, string_position(_,_)) :- !.
mark_singletons(M, {T}, S, brace_term_position(_, _, P)) :- !,
	mark_singletons(M, T, S, P).
mark_singletons(M, T, S, term_position(_,_,_,_,Args)) :-
	mark_arg_singletons(M, T, S, 1, Args).

mark_arg_singletons(_, _, _, _, []) :- !.
mark_arg_singletons(M, T, S, N, [H|L]) :-
	arg(N, T, A),
	mark_singletons(M, A, S, H),
	NN is N + 1,
	mark_arg_singletons(M, T, S, NN, L).

member_var(V, [_=V2|_]) :-
	V == V2, !.
member_var(V, [_|T]) :-
	member_var(V, T).

replace_singletons(M, P) :-
	arg(1, P, Start),
	arg(2, P, End),
	new(Pt, point(Start, End)),
	get(M, find_all_fragments,
	    and(message(@arg1, overlap, Pt),
		@arg1?style == singleton),
	    Frags),
	send(M, attribute, singletons, Frags),
	get(M, caret, C),
	send(M, internal_mark, C),
	send(M, focus_function, '_replace_singletons'),
	prepare_replace_singletons(M).

'_replace_singletons'(M, Id:event_id) :->
	get(M, attribute, singletons, Frags),
	get(Frags, delete_head, Frag),
	(   (   Id == 0'y
	    ->  send(Frag, insert, 0, '_'),
	        send(Frag, free)
	    ;   Id == 0'_
	    ->  send(Frag, string, '_'),
		send(Frag, free)
	    ;   Id == 0'n
	    ->  true
	    )
	->  (   send(Frags, empty)
	    ->  send(M, caret, M?internal_mark),
		cancel_replace_singletons(M)
	    ;   prepare_replace_singletons(M)
	    )
	;   cancel_replace_singletons(M),
	    Id == 27			% ESC: succeed
	).

cancel_replace_singletons(M) :-
	send(M, focus_function, @nil),
	send(M, mark_status, inactive),
	send(M, delete_attribute, singletons),
	send(M, report, status, '').

prepare_replace_singletons(M) :-
	get(M, attribute, singletons, Frags),
	get(Frags, head, F0),
	get(F0, start, S),
	get(F0, end, E),
	send(M, selection, S, E, highlight),
	send(M, report, status,
	     'Replace singleton? (''y'' --> _Name, ''_'' --> _, ''n'')').


		 /*******************************
		 *        TERM-READING		*
		 *******************************/

%	<-prolog_term
%	
%	Read a Prolog term from the buffer. If From is specified, this
%	is taken to be the start of the clause rather than using
%	<-beginning_of_clause from <-caret. If Silent is @off, error
%	messages are not printed. If a variable is passed into TermPos,
%	it is unified with the subterm-position specification a
%	specified in read_term/3.

prolog_term(M, From:[int], Silent:[bool], TermPos:[prolog], Clause:prolog) :<-
	  "Read clause start at <From> or around caret"::
	  (   From == @default
	  ->  get(M, caret, Caret),
	      get(M, beginning_of_clause, Caret, Start)
	  ;   Start = From
	  ),
	  get(M, text_buffer, TB),
	  pce_open(TB, read, Fd),
	  read_term_from_stream(Fd, Start, Clause, Error, _S, P),
	  close(Fd),
	  ignore(P = TermPos),
	  (   Error == none
	  ->  true
	  ;   (   Silent \== @on
	      ->  Error = EPos:Msg,
		  send(M, caret, EPos),
		  send(M, report, warning, 'Syntax error: %s', Msg)
	      ),
	      fail
	  ).


		 /*******************************
		 *	   MARK VARIABLE	*
		 *******************************/

typed(M, Id:'event|event_id', Editor:editor) :->
	"Extend variable marks"::
	send_super(M, typed, Id, Editor),
	(   object(M)			% Control-x k destroys the mode
	->  get(M, caret, Caret),
	    get(M, text_buffer, TB),
	    (   send(regex('[_A-Z][a-zA-Z_0-9]*.?'), match, TB, Caret, 0)
	    ->  send(M, mark_variable)
	    ;   true
	    )
	;   true
	).
	
new_caret_position(M, NewCaret:int) :->
	"Mark variables around caret"::
	send_super(M, new_caret_position, NewCaret),
	(   get(M, varmark_style, Style),
	    Style \== @nil
	->  send(M, mark_variable)
	;   true
	).

varmark_style(M, Style:style*) :->
	"Set the style for marking variables"::
	send(M, slot, varmark_style, Style),
	send(M, style, varmark, Style).

mark_variable(M) :->
	"Mark variable around caret"::
	send(M, unmark_variables),
	get(M, caret, Caret),
	(   get(M, prolog_term, @default, @on, Pos, Clause),
	    find_variable(Pos, Clause, Caret, Var)
	->  get(M, text_buffer, TB),
	    send(M, slot, has_var_marks, @on),
	    (   subterm_position(Var, Clause, Pos, F-T),
		Len is T - F,
		new(_, emacs_colour_fragment(TB, F, Len, varmark)),
		fail
	    ;   true
	    )
	;   true
	).

unmark_variables(M) :->
	"Remove all variable-mark fragments"::
	(   get(M, has_var_marks, @on)
	->  send(M, remove_syntax_fragments, style := varmark),
	    send(M, slot, has_var_marks, @off)
	;   true
	).

%	find_variable(+TermPos, +Clause, +Caret, -Var)
%	
%	Find the variable around the caret and return it in Var. If the
%	caret is not on a variable, fail.

find_variable(F-T, Var, Caret, Var) :-
	between(F, T, Caret), !,
	var(Var).
find_variable(term_position(_,_,_,_,ArgPos), Compound, Caret, Var) :-
	nth1(N, ArgPos, P),
	arg(N, Compound, Arg),
	find_variable(P, Arg, Caret, Var).
find_variable(list_position(_,_,EP,TP), List, Caret, Var) :-
	list_pos(EP, TP, List, P, E),
	find_variable(P, E, Caret, Var).

list_pos([], P, T, P, T) :-
	P \== none, !.
list_pos([PH|_],  _, [EH|_], PH, EH).
list_pos([_|PT], TP, [_|ET],  P,  E) :-
	list_pos(PT, TP, ET, P, E).

%	subterm_position(+Term, +Clause, +TermPos, -Pos)
%	
%	Find all positions at which Term appears in Clause.

subterm_position(Search, Term, Pos, Pos) :-
	Search == Term.
subterm_position(Search, Term, term_position(_,_,_,_,ArgPos), Pos) :- !,
	nth1(N, ArgPos, P2),
	arg(N, Term, A),
	subterm_position(Search, A, P2, Pos).
subterm_position(Search, List, list_position(_,_,EP,TP), Pos) :-
	list_pos(EP, TP, List, P, E),
	subterm_position(Search, E, P, Pos).


		 /*******************************
		 *       SOURCE DEBUGGER	*
		 *******************************/

break_at(M) :->
	"Set a Prolog break-point at this location"::
	send(M, save_buffer),
	get(M, text_buffer, TB),
	get(TB, file, File),
	(   source_file(Source),
	    send(File, same, Source)
	->  get(M, caret, Caret),
	    get(M, line_number, M?caret, Line),
	    (	auto_call(prolog_break_at(Source, Line, Caret))
	    ->	(   get(TB, margin_width, 0)
		->  send(TB, margin_width, 22)
		;   true
		),
		send(M, report, status, 'Break-point set')
	    ;	send(M, report, warning, 'Failed to set break-point')
	    )
	;   send(M, report, error, 'Source file is not loaded')
	).


delete_breakpoint(M) :->
	"Delete selected breakpoint"::
	(   get(M, selected_fragment, F),
	    F \== @nil,
	    get(F, attribute, clause, ClauseRef),
	    get(F, attribute, pc, PC)
	->  '$break_at'(ClauseRef, PC, false)
	;   send(M, report, warning, 'No selected breakpoint')
	).


		 /*******************************
		 *	  CLAUSE FWD/BWD	*
		 *******************************/

forward_clause(M, Start:int, EOC:int) :<-
	"Find end of first clause after Start"::
	new(Here, number(Start)),
	repeat,
	(   send(@prolog_full_stop, search, M, Here)
	->  get(@prolog_full_stop, register_start, 1, Stop),
	    (   get(M, scan_syntax, 0, Stop, tuple(code,_))
	    ->	!,
	        EOC = Stop
	    ;	send(Here, value, Stop),
		fail
	    )
	;   !,
	    fail
	).

at_start_of_clause(M, Pos:[int]) :->
	"Succeeds if this is the start of a clause"::
	(   Pos == @default
	->  get(M, caret, C)
	;   C = Pos
	),
	get(M, scan, C, word, 0, start, SOW),
	SOW == C,
	(   send(M, looking_at, ':-', C)
	;   get(M, text_buffer, TB),
	    get(TB, scan, C, term, 1, end, TE),
	    get(TB, skip_comment, TE, Neck),
	    send(M, looking_at, ':-\\|-->\\|:<-\\|\\.', Neck)
	).

backward_clause(M, Start:int, BOC:int) :<-
	"Find start of clause or previous clause"::
	(   send(M, at_start_of_clause, Start)
	->  From is Start - 1
	;   From is Start
	),
	get(M, beginning_of_clause, From, BOC).

forward_clause(M, Arg:[int]) :->
	"Go forwards by <arg> clauses"::
	default(Arg, 1, Times),
	get(M, caret, Caret),
	(   Times > 0
	->  do_n_times(Times, M, forward_clause, Caret, Pos)
	;   NegTimes is -Times,
	    do_n_times(NegTimes, M, backward_clause, Caret, Pos)
	),
	send(M, caret, Pos).

backward_clause(M, Arg:[int]) :->
	"Go backwards by <arg> clauses"::
	default(Arg, 1, Times),
	Forward is -Times,
	send(M, forward_clause, Forward).

do_n_times(0, _, _, Pos, Pos) :- !.
do_n_times(N, M, Sel, Here, End) :-
	get(M, Sel, Here, Pos), !,
	NN is N - 1,
	do_n_times(NN, M, Sel, Pos, End).
do_n_times(_, _, _, Pos, Pos).


		 /*******************************
		 *	 PREDICATE FWD/BWD	*
		 *******************************/

at_start_of_predicate(M, Start:[int]) :->
	(   Start == @default
	->  get(M, caret, P0)
	;   P0 = Start
	),
	send(M, at_start_of_clause, P0),
	get(M, name_and_arity, P0, tuple(Name, Arity)),
	(   get(M, backward_clause, P0, P1)
	->  \+ get(M, name_and_arity, P1, tuple(Name, Arity))
	;   true
	).
	    

backward_predicate(M, P0:int, BPred:int) :<-
	"Find start of this/previous predicate"::
	(   send(M, at_start_of_predicate, P0)
	->  P1 is P0-1
	;   P1 is P0
	),
	get(M, beginning_of_clause, P1, BOC),
	(   get(M, name_and_arity, BOC, tuple(Name, Arity))
	->  new(BP, number(BOC)),	% clause
	    repeat,
		(   get(M, backward_clause, BP, BPC),
		    send(BP, larger, BPC)
		->  (   get(M, name_and_arity, BPC, tuple(Name, Arity))
		    ->  send(BP, value, BPC),
			fail
		    ;   !,
		        get(BP, value, BPred)
		    )
		;   !,
		    fail
		)
	;   get(M, backward_clause, P0-1, P2),
	    (	get(M, name_and_arity, P2, tuple(Name, Arity))
	    ->	get(M, backward_predicate, P2+1, BPred)
	    ;	BPred = P2
	    )
	).

forward_predicate(M, P0:int, End:int) :<-
	"Find end of predicate"::
	get(M, forward_clause, P0, EOC),
	get(M, backward_clause, EOC, BOC),
	(   get(M, name_and_arity, BOC, tuple(Name, Arity))
	->  new(Here, number(EOC)),
	    repeat,
		get(M, skip_comment, Here, BONC),
		(   get(M, name_and_arity, BONC, tuple(Name, Arity))
		->  get(M, forward_clause, BONC, EONC),
		    send(Here, value, EONC),
		    fail
		;   !,
		    get(Here, value, End)
		)
	;   End = EOC
	).


forward_predicate(M, Arg:[int]) :->
	"Move forwards by <arg> predicates"::
	default(Arg, 1, Times),
	get(M, caret, P0),
	do_n_times(Times, M, forward_predicate, P0, P),
	send(M, caret, P).

backward_predicate(M, Arg:[int]) :->
	"Move backwards by <arg> predicates"::
	default(Arg, 1, Times),
	get(M, caret, P0),
	do_n_times(Times, M, backward_predicate, P0, P),
	send(M, caret, P).

:- emacs_end_mode.


%	Load syntax colouring support.

:- [prolog_colour].
