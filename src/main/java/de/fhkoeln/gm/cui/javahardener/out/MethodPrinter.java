package de.fhkoeln.gm.cui.javahardener.out;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class MethodPrinter extends MethodVisitor {
	public MethodPrinter(MethodVisitor visitor) {
		super(Opcodes.ASM4, visitor);
	}
	
	@Override
	public void visitCode() {
		super.visitCode();
		System.out.println("    code:");
	}
	
	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
		super.visitFrame(type, nLocal, local, nStack, stack);
		System.out.println("    frame " + type + " " + nLocal);
	}
	
	@Override
	public void visitInsn(int opcode) {
		super.visitInsn(opcode);
		System.out.println("    operation " + opcode);
	}
	
	@Override
	public void visitIntInsn(int opcode, int operand) {
		super.visitIntInsn(opcode, operand);
		System.out.println("    operation " + opcode + " with int " + operand);
	}
	
	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		System.out.println("    operation " + opcode + " with var " + var);
	}
	
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		System.out.println("    operation " + opcode + " with type " + type);
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
		System.out.println("    operation " + opcode + " with field " + owner + " " + name);
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		super.visitMethodInsn(opcode, owner, name, desc);
		System.out.println("    operation " + opcode + " with method " + owner + " " + name);
	}
	
	@Override
	public void visitInvokeDynamicInsn(String name, String desc, Handle bsm,
			Object... bsmArgs) {
		super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		System.out.println("    invoke dynamic " + name + " " + desc + " bsm: " + bsm + " (" + bsmArgs + ")");
	}
	
	@Override
	public void visitJumpInsn(int opcode, Label label) {
		super.visitJumpInsn(opcode, label);
		System.out.println("    jump " + opcode + " to " + label);
	}
	
	@Override
	public void visitLabel(Label label) {
		super.visitLabel(label);
		System.out.println("    label: " + label);
	}
	
	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
		System.out.println("    local variable: " + signature + " " + name + " " + desc);
	}
}
