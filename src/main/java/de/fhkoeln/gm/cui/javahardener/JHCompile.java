/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Christoph Jerolimov
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.fhkoeln.gm.cui.javahardener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Small demo client to compile a classfile with the same rules the
 * JHClassLoader uses. Prints the result to stdout.
 * 
 * @author Christoph Jerolimov
 */
public class JHCompile {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Usage: <class file or classname>");
			System.exit(-1);
		}
		
		InputStream is;
		File file = new File(args[0]);
		if (file.exists() && file.canRead()) {
			is = new FileInputStream(file);
		} else {
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0].replace('.', '/') + ".class");
		}
		
		if (is == null) {
			System.err.println("Class file or class in classpath not found: " + args[0]);
			System.exit(-2);
		}
		
		try {
			ClassReader classReader = new ClassReader(is);
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			
			ClassVisitor classVisitor = classWriter;
			classVisitor = new CheckNullClassVisitor(classVisitor);
			classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
			
			System.out.write(classWriter.toByteArray());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}
}
