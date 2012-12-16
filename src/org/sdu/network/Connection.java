package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connection class accepts incoming socket connections and creates a session.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Connection implements Runnable
{
	private ServerSocket 	socket;
	private SessionHandler	handler;
	private DebugFramework 	debugger;
	
	private int 	connid;
	private boolean closed = false;
	
	/**
	 * Initialize a connection thread on ServerSocket s with the specified id, 
	 * and create the sessions using handler h.
	 * 
	 * @param s		ServerSocket
	 * @param c		Session handler
	 * @param id	Connection ID (random one is acceptable)
	 */
	public Connection(ServerSocket s, SessionHandler h, int id)
	{
		socket = s;
		handler = h;
		connid = id;
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
		while(!closed) {
			try
			{
				while(!closed)
				{
					synchronized(socket) {
						s = socket.accept();
					}
					debugger.print("Incoming connection accepted by Thread "
							+ connid + ": " + s.getInetAddress());
					handler.handle(new Session(s));
				}
			} catch(Exception e) {
				debugger.print(e);
			}
			if(closed) debugger.print("Connection " + connid + " closed.");
		}
	}
	
	/**
	 * Shutdown the thread.
	 * ServerSocket must be closed later on.
	 */
	public void shutdown()
	{
		if(closed) return ;
		closed = true;
	}
}