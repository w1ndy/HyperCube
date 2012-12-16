package org.sdu.network;

/**
 * IncomingPacket class inherited from Packet class.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class IncomingPacket extends Packet
{
	private byte[] arr;
	
	/**
	 * Create a clone of data and make a packet.
	 * 
	 * @param data	Reference to the byte data array of input.
	 */
	public IncomingPacket(byte[] data)
	{
		arr = data.clone();
	}

	/**
	 * Make a packet and set the input as source.
	 * 
	 * @param data	Reference to the byte data array of input.
	 */
	public IncomingPacket(byte[] data, boolean setAsSource)
	{
		arr = data;
	}

	@Override
	public byte[] getData() {
		return arr;
	}

	@Override
	public IncomingPacket clone() {
		return new IncomingPacket(arr);
	}
}