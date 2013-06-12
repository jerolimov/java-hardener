
Fachhochschule Köln Campus Gummersbach<br/>
Fakultät für Informatik und Ingenieurwissenschaften

This is a **WORK IN PROCESS project (will be finished end of july)**
developed for the [Compiler and Interpreter](http://www.gm.fh-koeln.de/ehses/compiler/)
elective subject provided by [Prof.&nbsp;Dr.&nbsp;Ehses](http://www.gm.fh-koeln.de/ehses/).

Christoph Jerolimov, 11084742<br/>2013 Spring Semester

# Java-hardener

Java-hardener makes java-bytecode (a little bit) more fault tolerant against `NullPointerExceptions`.
It's inspired by the [Objective-C](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/)
language which ignores signals (method calls) to nil-Objects.

It's a univerity project. More details about this later. :-)

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

Dependencies and the eclipse configuration files are not part of the
git repository. To download them use maven 2+ (tested with maven 3):

	mvn eclipse:clean eclipse:eclipse

Required dependencies:

* asm-4.x.jar
* hamcrest-core-1.x.jar (for asm)
* junit-4.x.jar

## Brainstorming (DE)

TODO: Translate this

* Irgendwo anmerken das dies nicht für den Produktiveinsatz gedacht ist. :-)
* Analyse / Einarbeitung
  * ASM
  * Eclipse Plugin das asm befehle anzeigt verwenden?

* invoke_method
* invoke_interface
* invoke_dynamic??

* Analyse der möglichen Problemfälle
  * Funktionsaufrufe auf null
  * Variablenaufrufe auf null
  * Array length aufrufe auf null
  * Autoboxing !? Sind im bytecode ja normale Aufrufe.. Trotzdem irgendwas beachten? (Siehe Optimierungsmöglichkeiten)
  * Was ist mit verketteten Befehle?
* Optimierungsmöglichkeit:
  * Was ist wenn dies Eingebunden in Schleifen ist?
  * Was ist wenn sie mehrmals hintereinander aufgerufen wird?
  * Wenn Variable zuletzt gesetzt wurde ist mit new, kann sie nicht null sein.
  * Wenn Variable zuletzt gesetzt wurde mit einem "String" oder einem primitiven Typen, kann sie nicht null sein.
  * Nicht für "System.out" etc.
  * Nicht wenn Ergebnis von Integer.valueOf(), String.forInteger() oder ähnliche
  * Nicht wenn Feld final ist?
* Statistiken ausgeben
* Anaylse Umsetzungsmöglichkeiten (aus variable.doAnything() wird z.b.)
  * if (variable != null) variable.doAnything()
  * variable != null && variable.doAnything()
  * Springmarke mit label / goto?
* Weitere analyse möglicher Probleme:
  * Stack size anpassen?
  * Labels anpassem
* Toolchain
  * Shell-Script das Class-Dateien bearbeitet.
  * Classloader schreiben?
  * Shell-Script das Classloader setzt und Programm startet (`javahardener` oder `jarh`)?


## Links

* [Objective-C programming guide](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/)
* [ASM](http://asm.ow2.org/)
* [ASM 4.0 javadoc](http://asm.ow2.org/asm40/javadoc/user/overview-summary.html)
* [ASM Eclipse Plugin](http://asm.ow2.org/eclipse/index.html)
  * doesn't work our of the box with juno :-(
