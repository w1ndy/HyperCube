package org.sdu.net;

import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Packet class warps byte buffer into a packet for network transmission.
 * 
 * @version 0.1 rev 8001 Dec. 31, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Packet
{
	protected ByteBuffer dataBuffer;
	
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
		dataBuffer = buf.duplicate();
		dataBuffer.rewind();
	}
	
	/**
	 * Set buffer source. Buffer requires persistence.
	 */
	public void setData(ByteBuffer buf)
	{
		dataBuffer = buf.duplicate();
		dataBuffer.rewind();
	}
	
	/**
	 * Set buffer source with non-persistent buffer.
	 */
	public void setNonpersistentData(ByteBuffer buf)
	{
		dataBuffer = ByteBuffer.allocate(buf.capacity());
		buf.mark();
		buf.rewind();
		dataBuffer.put(buf);
		dataBuffer.flip();
		buf.reset();
	}
	
	/**
	 * Get buffer
	 */
	public ByteBuffer getData()
	{
		return dataBuffer.duplicate();
	}
	
	/**
	 * Get buffer copy.
	 */
	public ByteBuffer getDataCopy()
	{
		ByteBuffer buf = ByteBuffer.allocate(dataBuffer.capacity());
		dataBuffer.mark();
		dataBuffer.rewind();
		buf.put(dataBuffer);
		buf.flip();
		dataBuffer.reset();
		return buf;
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