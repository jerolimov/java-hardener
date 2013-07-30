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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class JHClassLoader extends ClassLoader {
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.startsWith("java")) {
			return super.loadClass(name);
		}
		
		InputStream is = getResourceAsStream(name.replace('.', '/') + ".class");
		if (is == null) {
			throw new ClassNotFoundException("Resource not found in ClassLoader hierachy: " + name);
		}
		
		try {
			ClassReader classReader = new ClassReader(is);
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			
			ClassVisitor classVisitor = classWriter;
			// debug: classVisitor = new TraceClassVisitor(classVisitor, new PrintWriter(System.out)); // post
			classVisitor = new CheckNullClassVisitor(classVisitor);
			// debug: classVisitor = new TraceClassVisitor(classVisitor, new PrintWriter(System.out)); // pre
			classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
			
			byte[] b = classWriter.toByteArray();
			return defineClass(name, b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("Error while load class: " + name, e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}
}
