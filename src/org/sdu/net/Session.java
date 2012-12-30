package org.sdu.net;

import java.nio.channels.SocketChannel;

/**
 * Session class warps a SocketChannel object into a operational transmitter
 * with asynchronized method.
 * 
 * @version 0.1 rev 8000 Dec. 30, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Session
{
	private SocketChannel channel;
	
	/**
	 * Initialize a Session object.
	 * Do not call new directly.
	 */
	private Session()
	{
		// TODO initialize the session.
	}
	
	public void post(Packet p)
	{
		// TODO post a packet here.
	}
	
	public void post(Packet ... list)
	{
		// TODO post packets here.
	}
	
	public SocketChannel getChannel()
	{
		return channel;
	}
	
	// TODO getReadQueue()
	
	// TODO getWriteQueue()
	
	// TODO static createSession()
}