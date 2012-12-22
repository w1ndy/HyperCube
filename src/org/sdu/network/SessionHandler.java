package org.sdu.network;

/**
 * SessionHandler class is designed for the Server side to handle each session.
 * It can be implemented in either blocking way or non-blocking way.
 * 
 * @version 0.1 rev 8002 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public interface SessionHandler
{
	/**
	 * When server is about to accept incoming connection.
	 * 
	 * @return 	Whether the server start should proceeded.
	 */
	public boolean onServerStart();
	
	/**
	 * When server is closed.
	 */
	public void onServerClose();
	
	/**
	 * Handle the incoming session.
	 * 
	 * @param s		Reference to a session
	 * @return		Whether accept the session or not
	 */
	public boolean onNewSession(Session s);
}
