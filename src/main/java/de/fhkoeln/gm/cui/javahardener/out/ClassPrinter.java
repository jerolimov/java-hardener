package de.fhkoeln.gm.cui.javahardener.out;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor {

	public ClassPrinter(ClassVisitor visitor) {
		super(Opcodes.ASM4, visitor);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		System.out.println(name + " extends " + superName + " {");
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor visitor = super.visitField(access, name, desc, signature, value);
		System.out.println("  " + desc + " " + name);
		return new FieldPrinter(visitor);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
		System.out.println(" " + name + desc);
		return new MethodPrinter(visitor);
	}
	
	@Override
	public void visitEnd() {
		super.visitEnd();
		System.out.println("}");
	}
}
