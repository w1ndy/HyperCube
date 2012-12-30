package org.sdu.net;

import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Packet class warps byte buffer into a packet for network transmission.
 * 
 * @version 0.1 rev 8000 Dec. 30, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Packet
{
	private ByteBuffer dataBuffer;
	
	/**
	 * Initialize a Packet object.
	 */
	public Packet()
	{
		dataBuffer = null;
	}
	
	/**
	 * Initialize a Packet object. Buffer requires persistence.
	 */
	public Packet(ByteBuffer buf)
	{
		dataBuffer = buf;
	}
	
	/**
	 * Set buffer source. Buffer requires persistence.
	 */
	public void setData(ByteBuffer buf)
	{
		dataBuffer = buf;
	}
	
	/**
	 * Set buffer source with non-persistent buffer.
	 */
	public void setNonpersistentData(ByteBuffer buf)
	{
		dataBuffer = buf.duplicate();
	}
	
	/**
	 * Get buffer
	 */
	public ByteBuffer getData()
	{
		return dataBuffer;
	}
	
	/**
	 * Get buffer copy.
	 */
	public ByteBuffer getDataCopy()
	{
		return dataBuffer.duplicate();
	}
	
	/**
	 * Get byte array in buffer.
	 */
	public byte[] getDataArray()
	{
		return dataBuffer.array();
	}
	
	/**
	 * Get data length.
	 */
	public int getLength()
	{
		return dataBuffer.remaining();
	}
	
	/**
	 * Get socket of the packet. (for backward compatibility).
	 * @deprecated
	 */
	public Socket getSocket()
	{
		return null;
	}
}