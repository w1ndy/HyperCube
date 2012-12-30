package org.sdu.net;

import java.nio.channels.SocketChannel;

/**
 * SessionHandler class is a super class which handles network events
 * implemented with nio.
 * 
 * @version 0.1 rev 8000 Dec. 30, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public abstract class SessionHandler
{
	/**
	 * Notified before accepting a session, returning false will block the
	 * connection.
	 */
	public abstract boolean onAcceptingSession(SocketChannel c);
	
	/**
	 * Notified when a session is successfully accepted.
	 */
	public abstract void onSessionAccepted(Session s);
	
	/**
	 * Notified when a session is closed.
	 */
	public abstract void onSessionClosed(Session s);
	
	/**
	 * Notified when a session is refused.
	 */
	public abstract void onUnregisteredSession(SocketChannel c);
	
	/**
	 * Notified when a session receives a packet.
	 */
	public abstract void onPacketReceived(Session s, Packet p);
}