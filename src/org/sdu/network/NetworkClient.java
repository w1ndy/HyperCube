package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;

/**
 * NetworkClient class provide an interface for clients to communicate with server.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
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
		
		try {
			s = new Socket(address, port);
		} catch (UnknownHostException e) {
			debugger.print("Cannot reach server " + address + " at port " + port + ".");
			return false;
		} catch (IOException e) {
			debugger.print(e);
			return false;
		}
		
		try {
			h.handle(new Session(s));
			return true;
		} catch (InvalidParameterException | IOException e) {
			debugger.print(e);
			return false;
		}
	}
}