import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import de.fhkoeln.gm.cui.javahardener.defectdemos.DemoMethodCall;
import de.fhkoeln.gm.cui.javahardener.out.ClassPrinter;

public class Test {
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
		
	}
}
