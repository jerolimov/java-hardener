
Fachhochschule Köln Campus Gummersbach<br/>
Fakultät für Informatik und Ingenieurwissenschaften

Christoph Jerolimov, 11084742<br/>2013 Spring Semester

This is a project was developed for the
[Compiler and Interpreter](http://www.gm.fh-koeln.de/ehses/compiler/)
elective subject provided by
[Prof.&nbsp;Dr.&nbsp;Ehses](http://www.gm.fh-koeln.de/ehses/).

# Java-hardener

Java-hardener makes java-bytecode (a little bit) more fault tolerant against `NullPointerExceptions`.

It's inspired by [Objective-C](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/) which ignores signals (method calls) to nil-Objects.

**Example:**

	List nullList = null;
	
	System.out.println("List size: " + nullList.size());
	// Output: "List size: 0"
	
	if (!nullList.isEmpty()) {
		// Will run this code also if the nullList is null!
	}

It's based on the bytecode manipulation framework [ASM 4](http://asm.ow2.org/).
You can run the hardening mechanism over your compiled class-files (with a small bytecode to bytecode compiler)
or integrate it as `ClassLoader` which modifies your code on-the-fly when it was loaded.

## Getting started

### Prepare your IDE with Maven

Dependencies and the IDE configuration files are not part of the git repository.
To download them use the maven plugin of your IDE or one of these commands:

Use your IDE maven-plugins to import the projects or configure your project
with one of these maven 2+ (tested with maven 3.1) commands:

	mvn eclipse:clean eclipse:eclipse -DdownloadSources
	mvn idea:clean idea:eclipse

Supported maven 2+ (tested with maven 3) commands:

	mvn compile   # download the dependencies and compile the sources
	mvn package   # compiles, test and package the java-hardener sources

See `pom.xml` for required dependencies (only asm is needed at runtime).

### Testing it

When you opened the project with your IDE or run the maven package process
you can test java-hardener with the `src/test/java/Demo.java` class.

* To debug the result and show java assembler code use `JHPrint`.
* To compile the file (to stdout!!) use `JHCompile`.
* Use `JHRun` to run a programm with **activated NPE production**.

You can run this classes also from the commandline with this small wrapper scripts:

	# Debug
	./jhprint target/test-classes/Demo.class   [or ./jhprint Demo]
	
	# Compile and run (Without ASM or java-hardener dependency!)
	./jhcompile target/test-classes/Demo.class > Demo.class
	java -cp . Demo
	
	# Run directly with JHClassLoader
	./jhrun Demo


## Academic-project disclaimer (or „this is fun only“)

> Since this is a academic project it's focusud on the possibility of
> byte-code manipulation instead of creating a commercial-prooven software.
> If you start programming and have to many NullPointerExceptions you may be
> intressting in this. But the best way to fix such issues is understanding
> the general problem and improve your code. :-D
> 
> But if you think this may interessting for your commercial project feel free
> to write me a mail.

## Copyright

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

## Links

* [Objective-C programming guide](http://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/ProgrammingWithObjectiveC/)
* [ASM](http://asm.ow2.org/)
* [ASM 4.0 javadoc](http://asm.ow2.org/asm40/javadoc/user/overview-summary.html)
* [ASM 4.0 Guide by Eric Bruneton](http://download.forge.objectweb.org/asm/asm4-guide.pdf) (pdf)
* <strike>[ASM Eclipse Plugin](http://asm.ow2.org/eclipse/index.html)</strike> (Doesn't work with the last and current version of Eclipse)
