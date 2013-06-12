package de.fhkoeln.gm.cui.javahardener.defectdemos;

@SuppressWarnings("null")
public class DemoMethodCall {
	public static void crashInStaticFunction() {
		Object o = null;
		o.toString();
	}
	
	public void crashInMethod() {
		Object o = null;
		o.toString();
	}
}
