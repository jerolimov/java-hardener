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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceClassVisitor;

import de.fhkoeln.gm.cui.javahardener.defectdemos.DemoMethodCall;
import de.fhkoeln.gm.cui.javahardener.out.ClassPrinter;

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Class<?> c = DemoMethodCall.class;
		String className = c.getName();
		String outFolder = "out";
		
		String classFilename = "/" + className.replace('.', '/') + ".class";
		InputStream is = c.getResourceAsStream(classFilename);
		
		
		ClassReader classReader = new ClassReader(is);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		
		classReader.accept(new ClassPrinter(classWriter), 0);
		
		if (!new File(outFolder + classFilename).getParentFile().exists()) {
			new File(outFolder + classFilename).getParentFile().mkdirs();
		}
		OutputStream os = new FileOutputStream(outFolder + classFilename);
		os.write(classWriter.toByteArray());
		os.close();
		
		is = c.getResourceAsStream(classFilename);
		classReader = new ClassReader(is);
        classReader.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
		
        
        ClassNode classNode = new ClassNode();
        
        
        is = c.getResourceAsStream(classFilename);
		classReader = new ClassReader(is);
        classReader.accept(classNode, 0);
		
        for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
        	
        	System.out.println(methodNode.name);
        	
        }
        
	}
}
