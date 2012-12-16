package org.sdu.network;

/**
 * SessionHandler class is designed for the Server side to handle each session.
 * It can be implemented in either blocking way or non-blocking way.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public interface SessionHandler
{
	/**
	 * Handle the incoming session.
	 * 
	 * @param s		Reference to a session.
	 */
	public void handle(Session s);
}