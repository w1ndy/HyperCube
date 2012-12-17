package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;

/**
 * Connection class accepts incoming socket connections and creates a session.
 * 
 * @version 0.1 rev 8001 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Connection implements Runnable
{
	private ServerSocket 	socket;
	private SessionHandler	handler;
	private DebugFramework 	debugger;
	private ExecutorService pool;
	
	/**
	 * Initialize a listener thread on ServerSocket s, and create the sessions
	 * in pool p using handler h.
	 * 
	 * @param p		Thread pool
	 * @param s		Socket to be listened
	 * @param h		Session handler
	 */
	public Connection(ExecutorService p, ServerSocket s, SessionHandler h)
	{
		if(p == null || s == null || h == null)
			throw new InvalidParameterException();
		
		pool 	= p;
		socket 	= s;
		handler = h;
		
		debugger = DebugFramework.getFramework();
		(new Thread(this)).start();
	}
	
	/**
	 * Connection thread main loop, waiting for incoming session.
	 */
	@Override
	public void run()
	{
		Socket s;
		while(!socket.isClosed()) {
			try {
				while(!socket.isClosed()) {
					synchronized(socket) {
						s = socket.accept();
					}
					Session session = new Session(s);
					if(!handler.onNewSession(session)) continue;
					debugger.print("Incoming session accepted: " + s.getInetAddress());
					pool.execute(session);
				}
			} catch(Exception e) {
				if(socket.isClosed()) debugger.print("Connection closed.");
				else debugger.print(e);
			}
		}
	}
	
	/**
	 * Shutdown the thread.
	 * ServerSocket must be closed later on.
	 * 
	 * @deprecated
	 */
	public void shutdown()
	{
	}
}