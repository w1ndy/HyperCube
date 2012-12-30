package org.sdu.net;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * WriteQueue class provides a thread-safe packet queue for writing.
 * 
 * @version 0.1 rev 8000 Dec. 31, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class WriteQueue
{
	private ConcurrentLinkedQueue<Packet> queuePacket;
	
	/**
	 * Initialize a WriteQueue object.
	 */
	public WriteQueue()
	{
		queuePacket = new ConcurrentLinkedQueue<Packet>();
	}
	
	/**
	 * Is queue empty.
	 */
	public boolean isEmpty()
	{
		return queuePacket.isEmpty();
	}
	
	/**
	 * Append a packet to the end of queue.
	 */
	public void push(Packet p)
	{
		queuePacket.offer(p);
	}
	
	/**
	 * Peek and remove a packet from the beginning of queue.
	 */
	public Packet pop()
	{
		return queuePacket.poll();
	}
}