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

package de.fhkoeln.gm.cui.javahardener.testcases;

public class Test3Delegate {
	private Test3 test3;
	
	public Test3Delegate(Test3 test3) {
		this.test3 = test3;
	}
	
	public void returnVoid() {
		test3.returnVoid();
	}
	
	public boolean returnBoolean() {
		return test3.returnBoolean();
	}
	
	public byte returnByte() {
		return test3.returnByte();
	}
	
	public char returnChar() {
		return test3.returnChar();
	}
	
	public short returnShort() {
		return test3.returnShort();
	}
	
	public int returnInt() {
		return test3.returnInt();
	}
	
	public float returnFloat() {
		return test3.returnFloat();
	}
	
	public long returnLong() {
		return test3.returnLong();
	}
	
	public double returnDouble() {
		return test3.returnDouble();
	}
	
	public Object returnObject() {
		return test3.returnObject();
	}
}
