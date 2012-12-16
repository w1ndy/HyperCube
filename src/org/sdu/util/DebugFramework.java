package org.sdu.util;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Provide a framework for application to write debug information.
 * 
 * @version 0.1 rev 8001 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class DebugFramework
{
	private PrintWriter writer = null;
	
	@Override
	public void finalize()
	{
		if(writer != null) writer.close();
	}
	
	/**
	 * Set a new log file for the output.
	 * NOT THREAD-SAFE.
	 * 
	 * @param newFileName	Path to log file
	 * @return				Is the operation successful
	 */
	public boolean setLogFileName(String newFileName)
	{
		if(writer != null) writer.close();
		try
		{
			writer = new PrintWriter(newFileName);
		} catch(Exception e) {
			System.out.println(e);
			writer = null;
			return false;
		}
		writer.println("Log started on " + (new Date()));
		writer.println();
		writer.flush();
		return true;
	}
	
	/**
	 * Print a line of debug message attached with date and source information.
	 * 
	 * @param msg	Message
	 */
	public void print(String msg)
	{
		System.out.println(msg);
		
		if(writer == null) {
			System.out.println("DebugFramework Error: Set log filename first.");
			return ;
		}
		
		Exception e = new Exception();
		int i;
		
		e.fillInStackTrace();
		StackTraceElement[] elem = e.getStackTrace();
		for(i = 0; i < elem.length; i++) {
			if(elem[i].getClassName() != "DebugFrameWork") break;
		}
		if(i < elem.length) {
			synchronized(writer)
			{
				writer.printf("[%s] @ %s:%d: %s\r\n",
						(new Date()).toString(),
						elem[i].getFileName(),
						elem[i].getLineNumber(),
						msg);
				writer.flush();
			}
		}
	}
	
	/**
	 * Print the details of an exception.
	 * 
	 * @param e		Exception
	 */
	public void print(Exception e)
	{
		if(e == null) {
			print("Unknown exception.");
		} else {
			print(e.toString());
			printStackTrace();
		}
	}
	
	/**
	 * Print current stack trace.
	 */
	public void printStackTrace()
	{
		if(writer == null) {
			System.out.println("DebugFramework Error: Set log filename first.");
			return ;
		}
		Exception e = new Exception();
		int i;
		
		e.fillInStackTrace();
		StackTraceElement[] elem = e.getStackTrace();
		for(i = 0; i < elem.length; i++) {
			if(elem[i].getClassName() != "DebugFramework") break;
		}
		
		synchronized(writer)
		{
			writer.printf("[%s] @ %s:%d: Stacktrace:\r\n",
					(new Date()).toString(),
					elem[i].getFileName(),
					elem[i].getLineNumber());
			for(i++; i < elem.length; i++) {
				writer.printf("\t%s::%s() at %s:%d.\r\n",
						elem[i].getClassName(), elem[i].getMethodName(),
						elem[i].getFileName(), elem[i].getLineNumber());
			}
			writer.flush();
		}
	}
	
	private static DebugFramework framework = null;
	private synchronized static void createInstance()
	{
		if(framework != null) return ;
		framework = new DebugFramework();
	}
	
	/**
	 * Get the global instance of DebugFramework.
	 * @return		DebugFramework
	 */
	public static DebugFramework getFramework()
	{
		if(framework == null) createInstance();
		return framework;
	}
}