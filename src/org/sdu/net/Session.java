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
	private SocketChannel 	channel;
	private Dispatcher 		dispatcher;
	
	private ReadQueue 	readQueue;
	private WriteQueue 	writeQueue;
	
	/**
	 * Initialize a Session object.
	 * Do not call new directly.
	 */
	public Session(Dispatcher d, SocketChannel c)
	{
		dispatcher = d;
		channel = c;
		readQueue = new ReadQueue();
		writeQueue = new WriteQueue();
	}
	
	public void post(Packet p)
	{
		writeQueue.push(new HeaderPacket(p));
		writeQueue.push(p);
		dispatcher.beginWrite(this);
	}
	
	public void post(Packet ... list)
	{
		for(Packet p : list) {
			writeQueue.push(new HeaderPacket(p));
			writeQueue.push(p);
		}
		dispatcher.beginWrite(this);
	}
	
	public SocketChannel getChannel()
	{
		return channel;
	}
	
	public ReadQueue getReadQueue()
	{
		return readQueue;
	}
	
	public WriteQueue getWriteQueue()
	{
		return writeQueue;
	}
}
