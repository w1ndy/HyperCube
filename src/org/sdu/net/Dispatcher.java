package org.sdu.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.sdu.util.DebugFramework;

/**
 * Dispatcher class dispatches network event from sessions to handler.
 * 
 * @version 0.1 rev 8002 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class Dispatcher implements Runnable
{
	private Selector selector;
	private Object selectorGuard;
	private boolean bRunning;
	private int numSession;
	private SessionHandler handler;
	
	/**
	 * Initialize a Dispatcher object.
	 */
	public Dispatcher()
	{
		selector = null;
		selectorGuard = new Object();
		bRunning = false;
		numSession = 0;
	}
	
	/**
	 * Get current session count.
	 */
	public int countSession()
	{
		return numSession;
	}
	
	/**
	 * Start the dispatcher.
	 */
	public void start(SessionHandler h) throws IOException
	{
		if(bRunning) {
			DebugFramework.getFramework().print("Dispatcher is already running.");
			return ;
		}
		
		handler = h;
		bRunning = true;
		selector = Selector.open();
		(new Thread(this)).start();
	}
	
	/**
	 * Stop the dispatcher.
	 */
	public void stop() throws IOException
	{
		if(selector != null) {
			bRunning = false;
			selector.wakeup();
		}
	}

	/**
	 * Register the channel and create a session.
	 */
	public Session register(SocketChannel c)
	{
		Session s = null;
		try {
			synchronized(selectorGuard) {
				selector.wakeup();
				s = new Session(this, c);
				c.configureBlocking(false);
				c.register(selector, SelectionKey.OP_READ, s);
			}
		} catch(Exception e) {
			DebugFramework.getFramework().print("Failed to register session: " + e);
			return null;
		}
		++numSession;
		return s;
	}
	
	/**
	 * Unregister a session.
	 */
	public void unregister(Session s)
	{
		try {
			synchronized(selectorGuard) {
				selector.wakeup();
				s.getChannel().register(selector, 0);
			}
		} catch(Exception e) {
			DebugFramework.getFramework().print("Failed to unregister session: " + e);
		}
		--numSession;
	}
	
	/**
	 * Begin write procedure.
	 */
	public void beginWrite(Session s)
	{
		SelectionKey key = s.getChannel().keyFor(selector);
		synchronized(selectorGuard) {
			selector.wakeup();
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}
	
	/**
	 * End write procedure.
	 */
	public void endWrite(Session s)
	{
		SelectionKey key = s.getChannel().keyFor(selector);
		synchronized(selectorGuard) {
			selector.wakeup();
			key.interestOps(SelectionKey.OP_READ);
		}
	}
	
	/**
	 * Process and dispatch events.
	 */
	@Override
	public void run() {
		try {
			while(bRunning) {
				synchronized(selectorGuard) {}
				selector.select();
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()) {
					SelectionKey key = iter.next();
					iter.remove();
					if(key.isValid()) {
						if(key.isReadable())
							handler.onReadEvent(this, (Session)key.attachment());
						if(key.isWritable())
							handler.onWriteEvent(this, (Session)key.attachment());
					}
				}
			}
		} catch(Exception e) {
			DebugFramework.getFramework().print("Failed to dispatch event: " + e);
		}
		
		try {
			selector.close();
		} catch (Throwable t) {}
	}
	
	/**
	 * Get selector of NIO.
	 */
	public Selector getSelector()
	{
		return selector;
	}
	
	/**
	 * Is dispatcher running.
	 */
	public boolean isRunning()
	{
		return bRunning;
	}
}
