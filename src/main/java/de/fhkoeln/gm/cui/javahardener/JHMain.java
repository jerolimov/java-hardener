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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Small demo client to runs a java programm with the JHClassLoader.
 * Requires a combined classpath with JH and the final program. Runs the first
 * argument classname main method with all other arguments as arg-Array.
 * 
 * @author Christoph Jerolimov
 */
public class JHMain {
	public static void main(String[] args) throws Throwable {
		//System.out.println("SystemClassLoader: " + ClassLoader.getSystemClassLoader());
		if (args.length == 0) {
			System.err.println("Usage: <mainclass> [arguments...]");
			System.exit(-1);
		}
		
		String mainclass = args[0];
		String[] arguments = new String[args.length - 1];
		System.arraycopy(args, 1, arguments, 0, arguments.length);
		
		ClassLoader classLoader = new JHClassLoader();
		Method main = classLoader.loadClass(mainclass).getMethod("main", String[].class);
		try {
			main.invoke(null, new Object[] { arguments });
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}
