package de.fhkoeln.gm.cui.javahardener.defectdemos;

@SuppressWarnings("null")
public class DemoMethodCall {
	public static void main(String[] args) {
		System.out.println(DemoMethodCall.crashInStaticFunction());
		System.out.println(new DemoMethodCall().crashInMethod());
	}
	
	public static String crashInStaticFunction() {
		Object o = null;
		return o.toString();
	}
	
	public String crashInMethod() {
		Object o = null;
		return o.toString();
	}

	@SuppressWarnings("unused")
	public String checkNull() {
		Object o = null;
		if (o != null) {
			return o.toString();
		} else {
			return null;
		}
	}
}
