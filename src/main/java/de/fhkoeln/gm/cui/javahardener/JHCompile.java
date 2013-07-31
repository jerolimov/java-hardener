package de.fhkoeln.gm.cui.javahardener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

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
