package org.sdu.net;

import java.nio.ByteBuffer;

/**
 * HeaderPacket is a 3-byte packet indicating the beginning of a data
 * packet.
 * 
 * @version 0.1 rev 8000 Dec. 31, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class HeaderPacket extends Packet
{
	private static final byte PacketDelimiter = 0x02;
	
	/**
	 * Initialize a HeaderPacket object.
	 */
	public HeaderPacket(Packet p)
	{
		super.dataBuffer = ByteBuffer.allocate(3);
		super.dataBuffer.put(PacketDelimiter);
		super.dataBuffer.put((byte) ((p.getLength() & 0xff00) >> 8));
		super.dataBuffer.put((byte) ((p.getLength()) & 0xff));
		super.dataBuffer.flip();
	}
}
