		    SWI-Prolog Distribution Directory

** Last update: Feb  1 2002 (Version 5.0.0)

AUTHOR
======
	Jan Wielemaker
	SWI
	University of Amsterdam
	Roetersstraat 15
	1018 WB  Amsterdam
	The Netherlands
	E-mail: jan@swi.psy.uva.nl

WEB-HOME
========

Please find the up-to-date information on  SWI-Prolog at the link below.
You will find the latest sources (also   accessible through CVS), a FAQ,
mailinglist archive, various additional packages, etc.

	http://www.swi-prolog.org


DOCUMENTATION
=============

Documentation is available  on-line  using   the  help/1  and  apropos/1
commands. HTML and PDF versions of  the   manual  are available from the
SWI-Prolog download page. The add-on  packages   are  documented  in the
doc/packages directory of the installed system.


DIRECTORIES
===========

The SWI-Prolog source tree consists of the following directories:

./src		C sources
./boot		Prolog system predicates defined in Prolog
./library	Prolog user's library
./include	Include files.  Mostly for handling foreign-code
./man		Manual (LaTeX)
./packages	Additional packages:
	clib	Process management and TCP/IP sockets
	cpp	C++ wrapper for foreign-language interface
	mp	Interface to gmp: arbitrary precession arithmetic
	db	Interface to BerkeleyDB 3.0 or higher
	smgl	XML/SGML interface as well as RDF parser
	table	Simple table-access routines (for static DB)
	xpce	The Prolog (portable) GUI toolkit and GUI tracer


BUILDING SWI-Prolog
===================

Unix
----

SWI-Prolog configuration under  Unix  is   driven  by  the  GNU-autoconf
package. Please check INSTALL for the   generic installation options and
INSTALL.notes  for  some  SWI-Prolog  specific  things.  An  interactive
web-page for discussing portability issues is at

	http://gollem.swi.psy.uva.nl/twiki/pl/bin/view/Porting/WebHome

SWI-Prolog should compile using any Unix-type   system with 32 or larger
(64) word-length and an ANSI-compliant C-compiler.

If SWI-Prolog crashes during the installation,  the most likely cause is
that the detection of mmap()  for   stack-memory  management or handling
exception-based stack guarding is not configured   properly.  If you are
knowledgeable in these issues, check src/test/mmap.c and src/pl-setup.c.
Otherwise, run configure using the options below,   first  to get rid of
exception-handling and second to  fall  back   to  malloc()  based stack
management.

	./configure --disable-segv-handling
	./configure --disable-mapped-stacks

Run `make clean && make' to recompile after reconfiguration.

Just getting the Prolog part of the  system compiled is generally a good
test that the system functions properly.   A  more exhaustive test-suite
can be activated using

	make check

After installing the basic system (SWI-Prolog/lite)  you can install the
add-ons. To do this, go to the  packages directory. Edit in Makefile the
PKG macro to define the packages you wish to install. Make sure you have
all required software around. Notably the XPCE toolkit requires

	* libjpeg  (http://www.ijg.org/)
	* libXPM   (http://www-sop.inria.fr/koala/lehors/xpm.html)
	* The X11 development headers and libraries

Now run

	./configure
	make
	make install


MS-Windows
----------

Unless there is a special reason, please  use the binary distribution on
MS-Windows.

Otherwise, get yourself the following tools and resources

	* Windows-NT or Windows-2000 (Makefiles require CMD.EXE)
	* MSVC 5.0 or later
	* MIME, JPEG and XPM libraries. You find precompiled versions in

		ftp://ftp.swi.psy.uva.nl/xpce/Windows/lib

Now proceed as follows

	* Unpack the sources (anywhere)
	* Edit ...\pl\src\rules.mk following the instructions there
	* Open a command window and switch to ...\pl\src
	* Make sure PATH LIB and INCLUDE are set up properly for the
	  compiler you want to use
	* Compile the system

		nmake /f Makefile.mak
	
	* Install the system

		nmake /f Makefile.mak install

Thats  all.  You  can  now  start    plwin.exe  or  plcon.exe  from  the
$(prefix)\pl\bin\ directory. If you want  you   can  associate  .PL with
plwin.exe and/or add plwin.exe to your start menu.


GNU-EMACS INTERFACE
===================

The Prolog-mode of GNU-Emacs is rather poor. Personally, I use XPCE (see
Prolog WWW home). For those whishing a proper Prolog mode for GNU-Emacs,
I got the following pointers from Sam Steel:

``Fortunately there seems to be such an  interface: though I suspect you
know it and know of some problem I  have missed. Though I have only used
it for a short while, it seems to work  for me. The only problem is that
the interaction between it  and  the  pager   for  the  online  help  is
imperfect.

It is the prolog.el mode for Emacs-20 at

   http://www.freesoft.cz/~pdm/software/emacs/prolog-mode/

There is also apparently a version for Emacs-19 at

   http://w1.858.telia.com/~u85810764/Prolog-mode/index.html

but I have not used this.

Both appear to be freely redistributable.  SWI Prolog is one of the
flavours of Prolog explicitly handled.''


MODIFYING SWI-Prolog
====================

You are free to modify the  SWI-Prolog   kernel  under  the LGPL and the
Prolog sources under the GPL.


CONTRIBUTING
============

There are a number of ways in which you can contribute to the success of
SWI-Prolog. If you encounter problems or   have suggestions, please send
them to prolog-bugs@swi.psy.uva.nl. If you   write  applications, please
acknowledge  the  use  of  SWI-Prolog  in   your  application.  If  your
application has a web-page, please link to the SWI-Prolog home page (see
the top of  this  file).  If   you  write  reusable  libraries, consider
providing them to the SWI-Prolog user community   as  a package (see the
SWI-Prolog home page).


COMMERCIAL USAGE
================

SWI-Prolog licensing aims at a large  audience, combining ideas from the
Free Software Foundation and the less  principal Open Source Initiative.
The license aims at:

    * Make SWI-Prolog itself and its libraries are `As free as possible'.
    * Allow for easy integration of contributions.
    * Free software can build on SWI-Prolog without limitations.
    * Non-free (open or proprietary) software can be produced using
      SWI-Prolog, although contributed pure GPL components cannot be
      used.

Please check www.swi-prolog.org for details on   commercial usage of the
system as well as for SUPPORT and SPONSORING options.

