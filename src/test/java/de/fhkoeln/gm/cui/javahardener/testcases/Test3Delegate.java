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
