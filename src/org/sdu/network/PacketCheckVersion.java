package org.sdu.network;

import java.net.Socket;

/**
 * Build the check-version packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketCheckVersion extends ModifiablePacket{

	public PacketCheckVersion(String version, Socket socket){
		PacketDataFactory.makePacket(this, socket, 1, 1, version);
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return arr;
	}

	@Override
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return s;
	}



}
