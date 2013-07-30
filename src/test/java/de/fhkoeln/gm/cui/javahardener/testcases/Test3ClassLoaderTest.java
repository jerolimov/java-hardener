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

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.gm.cui.javahardener.JHClassLoader;

public class Test3ClassLoaderTest {
	private ClassLoader classLoader;
	private Class<?> test3Class;
	private Class<?> test3DelegateClass;
	private Method test3DelegateReturnVoidMethod;
	private Method test3DelegateReturnBooleanMethod;
	private Method test3DelegateReturnByteMethod;
	private Method test3DelegateReturnCharMethod;
	private Method test3DelegateReturnShortMethod;
	private Method test3DelegateReturnIntMethod;
	private Method test3DelegateReturnFloatMethod;
	private Method test3DelegateReturnLongMethod;
	private Method test3DelegateReturnDoubleMethod;
	private Method test3DelegateReturnObjectMethod;
	
	@Before
	public void setUp() throws Exception {
		classLoader = new JHClassLoader();
		test3Class = classLoader.loadClass(Test3.class.getName());
		test3DelegateClass = classLoader.loadClass(Test3Delegate.class.getName());
		
		test3DelegateReturnVoidMethod = test3DelegateClass.getMethod("returnVoid");
		test3DelegateReturnBooleanMethod = test3DelegateClass.getMethod("returnBoolean");
		test3DelegateReturnByteMethod = test3DelegateClass.getMethod("returnByte");
		test3DelegateReturnCharMethod = test3DelegateClass.getMethod("returnChar");
		test3DelegateReturnShortMethod = test3DelegateClass.getMethod("returnShort");
		test3DelegateReturnIntMethod = test3DelegateClass.getMethod("returnInt");
		test3DelegateReturnFloatMethod = test3DelegateClass.getMethod("returnFloat");
		test3DelegateReturnLongMethod = test3DelegateClass.getMethod("returnLong");
		test3DelegateReturnDoubleMethod = test3DelegateClass.getMethod("returnDouble");
		test3DelegateReturnObjectMethod = test3DelegateClass.getMethod("returnObject");
	}
	
	@Test
	public void testWithDelegateObject() throws Exception {
		// Test3Delegate test3Delegate = new test3Delegate(new Test3());
		Object test3 = test3Class.newInstance();
		Object test3Delegate = test3DelegateClass.getConstructor(test3Class).newInstance(test3);
		
		test3DelegateReturnVoidMethod.invoke(test3Delegate);
		Assert.assertEquals(true, test3DelegateReturnBooleanMethod.invoke(test3Delegate));
		Assert.assertEquals((byte) 1, (byte) test3DelegateReturnByteMethod.invoke(test3Delegate));
		Assert.assertEquals((char) 1, (char) test3DelegateReturnCharMethod.invoke(test3Delegate));
		Assert.assertEquals((short) 1, (short) test3DelegateReturnShortMethod.invoke(test3Delegate));
		Assert.assertEquals((int) 1, (int) test3DelegateReturnIntMethod.invoke(test3Delegate));
		Assert.assertEquals((float) 1, (float) test3DelegateReturnFloatMethod.invoke(test3Delegate), (float) 0);
		Assert.assertEquals((long) 1, (long) test3DelegateReturnLongMethod.invoke(test3Delegate));
		Assert.assertEquals((double) 1, (double) test3DelegateReturnDoubleMethod.invoke(test3Delegate), (double) 0);
		Assert.assertNotNull(test3DelegateReturnObjectMethod.invoke(test3Delegate));
	}
	
	@Test
	public void testWithoutDelegateObject() throws Exception {
		// Test3Delegate test3Delegate = new test3Delegate(null);
		Object test3Delegate = test3DelegateClass.getConstructor(test3Class).newInstance(new Object[1]);
		
		test3DelegateReturnVoidMethod.invoke(test3Delegate);
		Assert.assertEquals(false, test3DelegateReturnBooleanMethod.invoke(test3Delegate));
		Assert.assertEquals((byte) 0, (byte) test3DelegateReturnByteMethod.invoke(test3Delegate));
		Assert.assertEquals((char) 0, (char) test3DelegateReturnCharMethod.invoke(test3Delegate));
		Assert.assertEquals((short) 0, (short) test3DelegateReturnShortMethod.invoke(test3Delegate));
		Assert.assertEquals((int) 0, (int) test3DelegateReturnIntMethod.invoke(test3Delegate));
		Assert.assertEquals((float) 0, (float) test3DelegateReturnFloatMethod.invoke(test3Delegate), (float) 0);
		Assert.assertEquals((long) 0, (long) test3DelegateReturnLongMethod.invoke(test3Delegate));
		Assert.assertEquals((double) 0, (double) test3DelegateReturnDoubleMethod.invoke(test3Delegate), (double) 0);
		Assert.assertNull(test3DelegateReturnObjectMethod.invoke(test3Delegate));
	}
}
