package de.fhkoeln.gm.cui.javahardener.defectdemos;

import org.junit.Test;

public class DemoClassesTest {	
	@Test(expected = NullPointerException.class)
	public void testCrashInStaticFunction() {
		DemoMethodCall.crashInStaticFunction();
	}

	@Test(expected = NullPointerException.class)
	public void testCrashInMethod() {
		new DemoMethodCall().crashInMethod();
	}
}
