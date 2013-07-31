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
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.gm.cui.javahardener.JHClassLoader;

public class Test1ClassLoaderTest {
	private ClassLoader classLoader;
	private Class<?> test1Class;
	private Method test1GetStringLengthMethod;
	
	@Before
	public void setUp() throws Exception {
		classLoader = new JHClassLoader();
		test1Class = classLoader.loadClass(Test1.class.getName());
		test1GetStringLengthMethod = test1Class.getMethod("getStringLength", Map.class, String.class);
	}
	
	@Test
	public void testClassLoading() throws Exception {
		Assert.assertNotNull(classLoader);
		Assert.assertNotNull(test1Class);
		Assert.assertNotNull(test1GetStringLengthMethod);
		Assert.assertNotNull(classLoader.getParent().loadClass(Test1.class.getName()));
	}
	
	@Test
	public void testWithDefaultClassLoader() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Test1 test1 = new Test1();
		Assert.assertEquals(5, test1.getStringLength(map, "key"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testNoMapWithDefaultClassLoader() {
		Test1 test1 = new Test1();
		test1.getStringLength(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNoKeyWithDefaultClassLoader() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Test1 test1 = new Test1();
		test1.getStringLength(map, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testIncorrectKeyWithDefaultClassLoader() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Test1 test1 = new Test1();
		test1.getStringLength(map, "incorrectkey");
	}
	
	@Test
	public void testWithJHClassLoader() throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Object test1 = test1Class.newInstance();
		Assert.assertEquals(5, test1GetStringLengthMethod.invoke(test1, map, "key"));
	}

	@Test
	public void testNoMapWithJHClassLoader() throws Exception {
		Object test1 = test1Class.newInstance();
		Assert.assertEquals(0, test1GetStringLengthMethod.invoke(test1, null, null));
	}
	
	@Test
	public void testNoKeyWithJHClassLoader() throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Object test1 = test1Class.newInstance();
		Assert.assertEquals(0, test1GetStringLengthMethod.invoke(test1, map, null));
	}
	
	@Test
	public void testIncorrectKeyWithJHClassLoader() throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", "value");
		
		Object test1 = test1Class.newInstance();
		Assert.assertEquals(0, test1GetStringLengthMethod.invoke(test1, map, "incorrectkey"));
	}
	
}
