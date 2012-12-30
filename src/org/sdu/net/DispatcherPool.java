package org.sdu.net;

import java.io.IOException;

/**
 * DispatcherPool manages dispatchers by a heap, and extract the one
 * with minimized connection count for each session.
 * 
 * @version 0.1 rev 8000 Dec. 30, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class DispatcherPool
{
	private Dispatcher[] pool;
	
	/**
	 * Initialize a DispatcherPool object with specified size.
	 */
	public DispatcherPool(int size)
	{
		pool = new Dispatcher[size];
		
		for(int i = 0; i < size; i++) {
			pool[i] = new Dispatcher();
		}
	}
	
	/**
	 * Start the pool allocation.
	 */
	public void start(SessionHandler h) throws IOException
	{
		for(Dispatcher d : pool) {
			d.start(h);
		}
	}
	
	/**
	 * Stop the pool allocation.
	 */
	public void stop() throws IOException
	{
		for(Dispatcher d : pool) {
			d.stop();
		}
	}
	
	/**
	 * Get next best dispatcher.
	 */
	public Dispatcher nextDispatcher()
	{
		int m = 2147483647;
		Dispatcher d = null;
		for(int i = 0; i < pool.length; i++) {
			int t = pool[i].countSession();
			if(t == 0) 
				return pool[i];
			else if(t < m) {
				m = t;
				d = pool[i];
			}
		}
		return d;
	}
}