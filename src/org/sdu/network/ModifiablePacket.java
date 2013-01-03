package org.sdu.network;

import java.net.Socket;

/**
 * ModifiablePacket class inherited from Packet class.
 * 
 * @deprecated
 * @version 0.1 rev 8002 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public abstract class ModifiablePacket implements Packet {
	protected byte[] arr;
	protected Socket s;
	
	public ModifiablePacket(){
		arr = null;
		s = null;
	}

	public void setData(byte[] b) {
		arr = b;
	}

	public void setSocket(Socket ss) {
		s= ss;
	}
	
}
	
