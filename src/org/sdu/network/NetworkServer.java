package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.net.ServerSocket;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * NetworkServer class provides an simple interface for servers.
 * 
 * @version 0.1 rev 8002 Dec. 18, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkServer
{
	private ServerSocket 	socket;
	private DebugFramework 	debugger;
	private ExecutorService	pool;
	private SessionHandler	handler;
	private Connection[]	connList;
	
	/**
	 * Initialize NetworkServer
	 */
	public NetworkServer()
	{
		Postman.initialize();
		debugger = DebugFramework.getFramework();
		pool = Executors.newCachedThreadPool();
	}
	
	/**
	 * Start a server listener on port, allowing maximum connection count of connNum,
	 * and pass session to SessionHandler h.
	 * 
	 * @param port		Port to be listened
	 * @param connNum	Maximum connection count
	 * @param h			Session handler
	 * @return			If the server is up
	 */
	public boolean start(int port, int connNum, SessionHandler h)
	{
		if(connNum <= 0 || h == null)
			throw new InvalidParameterException();
		
		if(socket != null) {
			debugger.print("Server is already startup and running.");
			return false;
		}
		
		int i;
		handler = h;
		try {
			if(!handler.onServerStart()) {
				handler = null;
				return false;
			}
			
			socket = new ServerSocket(port);
			connList = new Connection[connNum];
			
			for(i = 0; i < connNum; i++) {
				connList[i] = new Connection(pool, socket, handler);
			}
		} catch(Exception e) {
			if(socket != null) {
				try {
					socket.close();
				} catch(Throwable t) {}
				socket = null;
			}
			
			if(connList != null) {
				pool.shutdown();
				try
				{
					pool.awaitTermination(500, TimeUnit.MILLISECONDS);
				} catch(Throwable t) {}
				pool.shutdownNow();
				
				connList = null;
			}
			
			handler = null;
			debugger.print(e);
			debugger.print("Failed to set up server.");
			return false;
		}
		return true;
	}
	
	/**
	 * Shutdown the server.
	 */
	public void shutdown()
	{
		if(socket == null || socket.isClosed()) {
			debugger.print("Server is not running.");
			socket = null;
			return ;
		}
		
		try
		{
			socket.close();
			pool.shutdown();
			pool.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch(Throwable t) {}
		pool.shutdownNow();
		Postman.release();
		
		handler.onServerClose();
		connList = null;
		socket = null;
	}
}
