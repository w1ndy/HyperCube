package org.sdu.net;

import java.nio.channels.ServerSocketChannel;

import org.sdu.util.DebugFramework;

/**
 * NetworkServer class listens on a specified port, accepting and processing
 * the incoming connections.
 * 
 * @version 0.1 rev 8000 Dec. 30, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkServer implements Runnable
{
	private ServerSocketChannel channelServer;
	private SessionHandler handler;
	private DebugFramework debug;
	
	private int port;
	private boolean isRunning;
	
	/**
	 * Initialize a NetworkServer object listening on given port with a
	 * SessionHandler receiving events.
	 */
	public NetworkServer(SessionHandler handler, int port)
	{
		this.handler = handler;
		this.port = port;
		this.isRunning = false;
		this.debug = DebugFramework.getFramework();
	}
	
	/**
	 * Start the server in blocking or non-blocking mode.
	 */
	public boolean start(boolean isBlocking)
	{
		if(isRunning) {
			debug.print("Another instance is running.");
			return false;
		}
		
		isRunning = true;
		if(isBlocking)
			run();
		else
			(new Thread(this)).start();
		return true;
	}
	
	/**
	 * Stop the server.
	 */
	public void stop()
	{
		isRunning = false;
	}
	
	/**
	 * Accept incoming connections and process them.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
