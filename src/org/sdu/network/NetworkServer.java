package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.net.ServerSocket;

/**
 * NetworkServer class provides an simple interface for servers.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkServer
{
	private ServerSocket 	socket		= null;
	private DebugFramework 	debugger	= null;
	private Connection[] 	threadList	= null;
	private SessionHandler	handler		= null;
	
	/**
	 * Initialize NetworkServer
	 */
	public NetworkServer()
	{
		debugger = DebugFramework.getFramework();
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
		if(socket != null) {
			debugger.print("Server is already startup and running.");
			return false;
		}
		
		int i;
		handler = h;
		try {
			socket = new ServerSocket(port);
			threadList = new Connection[connNum];
			
			handler.onServerStart();
			for(i = 0; i < connNum; i++) {
				threadList[i] = new Connection(socket, h, i + 1);
			}
		} catch(Exception e) {
			if(threadList != null) {
				for(i = 0; i < threadList.length; i++) {
					if(threadList[i] != null) threadList[i].shutdown();
				}
				threadList = null;
			}
			if(socket != null) {
				try {
					socket.close();
				} catch(Exception err) {}
				socket = null;
			}
			debugger.print(e);
			return false;
		}
		return true;
	}
	
	/**
	 * Shutdown the server.
	 */
	public void shutdown()
	{
		if(socket == null) {
			debugger.print("Server is not running.");
			return ;
		}
		
		int i;
		for(i = 0; i < threadList.length; i++) {
			threadList[i].shutdown();
		}
		
		try
		{
			socket.close();
		} catch(Exception err) {}
		handler.onServerClose();
		threadList = null;
		socket = null;
	}
}