package org.sdu.command;

import java.net.Socket;
import java.nio.charset.Charset;

import org.sdu.network.ModifiablePacket;

/**
 * Build the check-version packet on the client to send to the server.
 * 
 * @version 0.1 rev 8002 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketCheckVersion extends ModifiablePacket{

	public PacketCheckVersion(String version, Socket socket){
		try {
			PacketDataFactory.makePacket(this, socket,
					Command.cmdMainLogin, Command.cmdVerifyVersion,
					version.getBytes("UTF-8"));
		} catch(Exception e) {
			
		}
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
