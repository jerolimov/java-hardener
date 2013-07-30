package de.fhkoeln.gm.cui.javahardener.out;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;


public class FieldPrinter extends FieldVisitor {
	public FieldPrinter(FieldVisitor visitor) {
		super(Opcodes.ASM4, visitor);
	}
}
