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
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.gm.cui.javahardener.JHClassLoader;

public class Test2ClassLoaderTest {
	private ClassLoader classLoader;
	private Class<?> test2Class;
	private Method test2IsFirstEntryTrueMethod;
	
	@Before
	public void setUp() throws Exception {
		classLoader = new JHClassLoader();
		test2Class = classLoader.loadClass(Test2.class.getName());
		test2IsFirstEntryTrueMethod = test2Class.getMethod("isFirstEntryTrue", Map.class, String.class);
	}
	
	@Test
	public void testClassLoading() throws Exception {
		Assert.assertNotNull(classLoader);
		Assert.assertNotNull(test2Class);
		Assert.assertNotNull(test2IsFirstEntryTrueMethod);
		Assert.assertNotNull(classLoader.getParent().loadClass(Test2.class.getName()));
	}
	
	@Test
	public void testWithDefaultClassLoader() {
		Deque<Boolean> entries = new LinkedList<>();
		entries.add(true);
		Map<String, Deque<Boolean>> map = new LinkedHashMap<>();
		map.put("key", entries);
		
		Test2 test2 = new Test2();
		Assert.assertEquals(true, test2.isFirstEntryTrue(map, "key"));
	}

	@Test
	public void testNoMapWithDefaultClassLoader() {
		Test2 test2 = new Test2();
		Assert.assertEquals(true, test2.isFirstEntryTrue(null, "key"));
	}

	@Test
	public void testNoEntriesWithDefaultClassLoader() {
		Map<String, Deque<Boolean>> map = new LinkedHashMap<>();
		map.put("key", new LinkedList<Boolean>());
		
		Test2 test2 = new Test2();
		Assert.assertEquals(true, test2.isFirstEntryTrue(map, "key"));
	}
	
	@Test
	public void testWithJHClassLoader() throws Exception {
		Deque<Boolean> entries = new LinkedList<>();
		entries.add(true);
		Map<String, Deque<Boolean>> map = new LinkedHashMap<>();
		map.put("key", entries);
		
		Object test2 = test2Class.newInstance();
		Assert.assertEquals(true, test2IsFirstEntryTrueMethod.invoke(test2, map, "key"));
	}

	@Test
	public void testNoMapWithJHClassLoader() throws Exception {
		Object test2 = test2Class.newInstance();
		Assert.assertEquals(true, test2IsFirstEntryTrueMethod.invoke(test2, null, "key"));
	}

	@Test
	public void testNoEntriesWithJHClassLoader() throws Exception {
		Map<String, Deque<Boolean>> map = new LinkedHashMap<>();
		map.put("key", new LinkedList<Boolean>());
		
		Object test2 = test2Class.newInstance();
		Assert.assertEquals(true, test2IsFirstEntryTrueMethod.invoke(test2, map, "key"));
	}
}
