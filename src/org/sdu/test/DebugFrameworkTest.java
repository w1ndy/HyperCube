package org.sdu.test;

import java.security.InvalidParameterException;

import org.sdu.util.DebugFramework;

/**
 * Test DebugFramework class.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class DebugFrameworkTest implements Runnable
{
	/**
	 * Create thread.
	 */
	public DebugFrameworkTest()
	{
		(new Thread(this)).start();
	}
	
	/**
	 * Create five threads and test thread-security of the class.
	 */
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("DebugFrameworkTest.log");
		new DebugFrameworkTest();
		new DebugFrameworkTest();
		new DebugFrameworkTest();
		new DebugFrameworkTest();
		new DebugFrameworkTest();
		
		// Test exception output.
		DebugFramework.getFramework().print(new InvalidParameterException());
	}

	/**
	 * Write five sample debug strings.
	 */
	@Override
	public void run() {
		DebugFramework framework = DebugFramework.getFramework();
		for(int i = 0; i < 5; i++) {
			framework.print("Hello @ " + i + " times");
		}
	}
}