package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * NetworkClient class provide an interface for clients to communicate with server.
 * 
 * @version 0.1 rev 8002 Dec. 18, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkClient
{
	private DebugFramework debugger;
	
	/**
	 * Initialize NetworkClient.
	 */
	public NetworkClient()
	{
		debugger = DebugFramework.getFramework();
	}
	
	/**
	 * Connect to address on port, and create a new session.
	 * 
	 * @param address	Destination address
	 * @param port		Destination port
	 * @param h			Session handler
	 * @return			If the connection is established.
	 */
	public boolean connect(String address, int port, SessionHandler h)
	{
		Socket s;
		
		Postman.initialize();
		
		try {
			s = new Socket();
			s.connect(new InetSocketAddress(address, port), 10000);
			Session session = new Session(s);
			if(!h.onNewSession(session)) return false;
			(new Thread(session)).start();
		} catch (Exception e) {
			debugger.print(e);
			debugger.print("Failed to connect to server " + address + " at port " + port + ".");
			return false;
		}
		
		return true;
	}
}
