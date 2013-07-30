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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Printer;

public class CheckNullMethodVisitor extends MethodVisitor {
	public CheckNullMethodVisitor(MethodVisitor visitor) {
		super(Opcodes.ASM4, visitor);
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		
		if (opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEDYNAMIC) {
			
			if ((owner + "." + name).equals("java/lang/String.length")) {
				
				// Instead of the original invoke call:
				//super.visitMethodInsn(opcode, owner, name, desc);
				
				Label fallback = new Label();
				Label behind = new Label();
				
				// We surround the original call with an IFNULL check:
				super.visitInsn(Opcodes.DUP); // Duplicate stack pointer of the current object
				super.visitJumpInsn(Opcodes.IFNULL, fallback); // Skip method call if reference is null
				super.visitMethodInsn(opcode, owner, name, desc); // Original method call
				super.visitJumpInsn(Opcodes.GOTO, behind); // Jump over the reference is null path
				
				// But if not we need add a default value to the stack:
				super.visitLabel(fallback); // Reference is null path
				super.visitInsn(Opcodes.POP); // Pop the dup value from stack
				super.visitInsn(Opcodes.ICONST_0); // Push int 0 to the stack
				
				super.visitLabel(behind); // Label to jump of the reference is null path
				
			} else if ((owner + "." + name).equals("java/util/Map.get")) {
				
				// Instead of the original invoke call:
				//super.visitMethodInsn(opcode, owner, name, desc);
				
				Label fallback = new Label();
				Label behind = new Label();
				
				// We surround the original call with an IFNULL check:
				super.visitInsn(Opcodes.DUP2); // Duplicate stack pointer of the current object AND the argument
				super.visitInsn(Opcodes.POP); // Remove the argument again
				super.visitJumpInsn(Opcodes.IFNULL, fallback); // Skip method call if reference is null
				super.visitMethodInsn(opcode, owner, name, desc); // Original method call
				super.visitJumpInsn(Opcodes.GOTO, behind); // Jump over the reference is null path
				
				// But if not we need add a default value to the stack:
				super.visitLabel(fallback); // Reference is null path
				super.visitInsn(Opcodes.POP2); // Pop the dup value from stack AND the argument
				super.visitInsn(Opcodes.ACONST_NULL); // Push NULL to the stack
				
				super.visitLabel(behind); // Label to jump of the reference is null path

			} else if ((owner + "." + name).equals("java/util/Deque.getFirst")) {
				
				// Instead of the original invoke call:
				//super.visitMethodInsn(opcode, owner, name, desc);
				
				Label fallback = new Label();
				Label behind = new Label();
				
				// We surround the original call with an IFNULL check:
				super.visitInsn(Opcodes.DUP); // Duplicate stack pointer of the current object
				super.visitJumpInsn(Opcodes.IFNULL, fallback); // Skip method call if reference is null
				super.visitMethodInsn(opcode, owner, name, desc); // Original method call
				super.visitJumpInsn(Opcodes.GOTO, behind); // Jump over the reference is null path
				
				// But if not we need add a default value to the stack:
				super.visitLabel(fallback); // Reference is null path
				super.visitInsn(Opcodes.POP); // Pop the dup value from stack
				super.visitInsn(Opcodes.ACONST_NULL); // Push NULL to the stack
				
				super.visitLabel(behind); // Label to jump of the reference is null path

			} else if ((owner + "." + name).equals("java/lang/Boolean.booleanValue")) {
				
				// Instead of the original invoke call:
				//super.visitMethodInsn(opcode, owner, name, desc);
				
				Label fallback = new Label();
				Label behind = new Label();
				
				// We surround the original call with an IFNULL check:
				super.visitInsn(Opcodes.DUP); // Duplicate stack pointer of the current object
				super.visitJumpInsn(Opcodes.IFNULL, fallback); // Skip method call if reference is null
				super.visitMethodInsn(opcode, owner, name, desc); // Original method call
				super.visitJumpInsn(Opcodes.GOTO, behind); // Jump over the reference is null path
				
				// But if not we need add a default value to the stack:
				super.visitLabel(fallback); // Reference is null path
				super.visitInsn(Opcodes.POP); // Pop the dup value from stack
				super.visitIntInsn(Opcodes.BIPUSH, 0); // Push false to the stack
				
				super.visitLabel(behind); // Label to jump of the reference is null path
				
			} else {
				System.out.println(Printer.OPCODES[opcode] + "\tUnsupported method: " + owner + "." + name + " " + desc);
				
				super.visitMethodInsn(opcode, owner, name, desc);
			}
			
		} else {
			super.visitMethodInsn(opcode, owner, name, desc);
		}
		
	}
}
