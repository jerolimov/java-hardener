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
import org.objectweb.asm.Type;

/**
 * This method visitor instance surrounds each method call with an IFNULL
 * check. (Constrain: Works only for method with zero or one argument.)
 * 
 * @author Christoph Jerolimov
 */
public class CheckNullMethodVisitor extends MethodVisitor {
	public CheckNullMethodVisitor(MethodVisitor visitor) {
		super(Opcodes.ASM4, visitor);
	}
	
	/**
	 * Delegates the implementation dependend on the argument cound to
	 * {@link #invokeMethodWithoutArguments(int, String, String, String)} or
	 * {@link #invokeMethodWithOneArgument(int, String, String, String)}.
	 * 
	 * All other method calls will be handled by the original implementation
	 * which just call the next visitor in the visitor chain.
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		
		if (opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEDYNAMIC) {
			Type type = Type.getType(desc);
			
			int argumentCount = type.getArgumentTypes().length;
			if (argumentCount == 0) {
				// can handle with dup
				invokeMethodWithoutArguments(opcode, owner, name, desc);
			} else if (argumentCount == 1 && type.getArgumentTypes()[0].getSize() < 2) {
				// can handle with dup2 (works not if the argument is a long or double)
				invokeMethodWithOneArgument(opcode, owner, name, desc);
			} else {
				//System.out.println(Printer.OPCODES[opcode] + "\tUnsupported method: " + owner + "." + name + " " + desc);
				super.visitMethodInsn(opcode, owner, name, desc);
			}
			
		} else {
			super.visitMethodInsn(opcode, owner, name, desc);
		}
	}
	
	/**
	 * Surrounds the original invoke call with an IFNULL check.
	 * If the object is null a default value will be pushed to the stack.
	 * 
	 * This method duplicates the current object reference with DUP before
	 * checking it with IFNULL. The original instance will be removed with POP
	 * before {@link #pushDefault(Type) the default value will pushed}.
	 * 
	 * @param opcode
	 * @param owner
	 * @param name
	 * @param desc
	 */
	private void invokeMethodWithoutArguments(int opcode, String owner, String name, String desc) {
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
		pushDefault(Type.getReturnType(desc));
		
		super.visitLabel(behind); // Label to jump of the reference is null path
	}
	
	/**
	 * Surrounds the original invoke call with an IFNULL check.
	 * If the object is null a default value will be pushed to the stack.
	 * 
	 * This method duplicates the current object reference with DUP2 and POP (to
	 * remove the argument from the stack) before checking it with IFNULL.
	 * The original instance reference and the argument will be removed with
	 * POP2 before {@link #pushDefault(Type) the default value will pushed}.
	 * 
	 * Notice: This works only for 32 bit arguments: Objects, integers, etc.
	 * but doesn't work with long or double values.
	 * 
	 * @param opcode
	 * @param owner
	 * @param name
	 * @param desc
	 */
	private void invokeMethodWithOneArgument(int opcode, String owner, String name, String desc) {
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
		pushDefault(Type.getReturnType(desc));
		
		super.visitLabel(behind); // Label to jump of the reference is null path
	}
	
	/**
	 * Push the default value to the stack for the given type.
	 * 
	 * @param type
	 */
	private void pushDefault(Type type) {
		switch (type.getSort()) {
		case Type.VOID:
			break;
		case Type.BOOLEAN:
			super.visitIntInsn(Opcodes.BIPUSH, 0); // Push false to the stack
			break;
		case Type.BYTE:
			super.visitIntInsn(Opcodes.BIPUSH, 0); // Push byte 0 to the stack
			break;
		case Type.CHAR:
			super.visitIntInsn(Opcodes.BIPUSH, 0); // Push char 0 to the stack
			break;
		case Type.SHORT:
			super.visitIntInsn(Opcodes.SIPUSH, 0); // Push short 0 to the stack
			break;
		case Type.INT:
			super.visitInsn(Opcodes.ICONST_0); // Push int 0 to the stack
			break;
		case Type.FLOAT:
			super.visitInsn(Opcodes.FCONST_0); // Push float 0 to the stack
			break;
		case Type.LONG:
			super.visitInsn(Opcodes.LCONST_0); // Push long 0 to the stack
			break;
		case Type.DOUBLE:
			super.visitInsn(Opcodes.DCONST_0); // Push double 0 to the stack
			break;
		default:
			super.visitInsn(Opcodes.ACONST_NULL); // Push NULL to the stack
			break;
		}
	}
}
