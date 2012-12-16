package org.sdu.network;

/**
 * Warp byte stream data into abstract cloneable Packet class.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public abstract class Packet implements Cloneable
{
	/**
	 * Retrive byte data from packet.
	 * 
	 * @return	Data reference.
	 */
	public abstract byte[] getData();
	
	/**
	 * Clone a packet object.
	 */
	public abstract Packet clone();
}
