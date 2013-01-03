package org.sdu.commandOLD;

import java.net.Socket;
import org.sdu.network.ModifiablePacket;
import org.sdu.util.DebugFramework;

/**
 * Build the verify-version packet on the client to send to the server.
 * 
 * @version 0.1 rev 8002 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketVerifyVersion extends ModifiablePacket{

	public PacketVerifyVersion(String version, Socket socket){
		try {
			PacketDataFactory.makePacket(this, socket,
					Command.cmdMainLogin, Command.cmdVerifyVersion,
					version.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
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
