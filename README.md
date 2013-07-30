
Fachhochschule Köln Campus Gummersbach<br/>
Fakultät für Informatik und Ingenieurwissenschaften

Christoph Jerolimov, 11084742<br/>2013 Spring Semester

This is a project was developed for the
[Compiler and Interpreter](http://www.gm.fh-koeln.de/ehses/compiler/)
elective subject provided by
[Prof.&nbsp;Dr.&nbsp;Ehses](http://www.gm.fh-koeln.de/ehses/).

# Java-hardener

Java-hardener makes java-bytecode (a little bit) more fault tolerant against `NullPointerExceptions`.
It's inspired by the [Objective-C](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/)
language which ignores signals (method calls) to nil-Objects.

It shall use the [ASM](http://asm.ow2.org/) bytecode manipulation and analysis framework
and will be implemented as a bytecode-to-bytecode post-processor and maybe also as java `ClassLoader`.

**Example:**

	List nullList = null;
	
	System.out.println("List size: " + nullList.size());
	// Should output "List size: 0"
	
	if (!nullList.isEmpty()) {
		// Will run this code also if the nullList is null.
	}

## User introduction

* Come back later. See above. :-)

## Developer introduction

Dependencies and the IDE configuration files are not part of the git repository.
To download them use the maven plugin of your IDE or one of these commands:

Use your IDE maven-plugins to import the projects or configure your project
with one of these maven 2+ (tested with maven 3.1) commands:

	mvn eclipse:clean eclipse:eclipse -DdownloadSources
	mvn idea:clean idea:eclipse

Supported maven 2+ (tested with maven 3) commands:

	mvn compile # download the dependencies and compile the sources
	mvn package # compiles, test and package the java-hardener sources

Required dependencies:

* asm-4.x.jar
* hamcrest-core-1.x.jar (for asm)
* junit-4.x.jar

## Brainstorming (DE)

TODO: Translate this

* Irgendwo anmerken das dies nicht für den Produktiveinsatz gedacht ist. :-)
* Analyse / Einarbeitung
  * Bytecode
    * `invoke_method`
    * `invoke_interface`
    * `invoke_dynamic`???
  * ASM
  * Eclipse Plugin das asm befehle anzeigt verwenden?
* Analyse der möglichen Problemfälle
  * Methodenaufrufe auf null-Objekte.
  * Variablenaufrufe (setzen oder laden) auf null-Objekte.
  * length-Anfragen auf null-Arrays.
  * Was ist mit verketteten Befehle?
  * Autoboxing sind im bytecode ja normale Aufrufe.. Trotzdem irgendwas beachten? Kann man das ggf. Erkennen (vgl. Optimierung `Integer.valueOf()`)?
* Optimierungsmöglichkeit:
  * Was ist wenn dies Eingebunden in Schleifen ist?
  * Was ist wenn sie mehrmals hintereinander aufgerufen wird?
  * Wenn Variable zuletzt gesetzt wurde ist mit new, kann sie nicht null sein.
  * Wenn Variable zuletzt gesetzt wurde mit einem "String" oder einem primitiven Typen, kann sie nicht null sein.
  * Nicht für `System.[in,out,err].*`-Aufrufe.
  * Nicht wenn Ergebnis von `Integer.valueOf()`, `Integer.toString()`, etc.
  * Nicht wenn Feld `final` ist?
* Statistiken ausgeben
* Anaylse Umsetzungsmöglichkeiten (aus `variable.doAnything()` wird z.b.)
  * `if (variable != null) variable.doAnything()`
  * `variable != null && variable.doAnything()`
  * Springmarke mit `label` / `goto`?
* Weitere analyse möglicher Probleme:
  * Stack size anpassen?
  * Labels anpassem
* Toolchain
  * Shell-Script das Class-Dateien bearbeitet.
  * Classloader schreiben?
  * Ein kleines Shell-Script welches den Classloader setzt (für bestimmte Klassen?
    * zB `javahardener -Dharden=methodcalls -cp … Main`?
    * oder `jarh -Dharden=methodcalls beispiel.jar`?

### Academic-project disclaimer (or „this is fun only“)

Since this is a academic project it's focusud on the possibility of
byte-code manipulation instead of creating a commercial-prooven software.
If you start programming and have to many NullPointerExceptions you may be
intressting in this. But the best way to fix such issues is understanding
the general problem and improve your code. :-)

But if you think this may interessting for your commercial project feel free
to write me a mail.

### Copyright

	The MIT License (MIT)
	
	Copyright (c) 2013 Christoph Jerolimov
	
	Permission is hereby granted, free of charge, to any person obtaining a copy of
	this software and associated documentation files (the "Software"), to deal in
	the Software without restriction, including without limitation the rights to
	use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
	the Software, and to permit persons to whom the Software is furnished to do so,
	subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
	FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
	COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
	CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

### Links

* [Objective-C programming guide](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/)
* [ASM](http://asm.ow2.org/)
* [ASM 4.0 javadoc](http://asm.ow2.org/asm40/javadoc/user/overview-summary.html)
* [ASM 4.0 Guide by Eric Bruneton](http://download.forge.objectweb.org/asm/asm4-guide.pdf) (pdf)
* [ASM Eclipse Plugin](http://asm.ow2.org/eclipse/index.html)
  * doesn't work our of the box with juno :-(
