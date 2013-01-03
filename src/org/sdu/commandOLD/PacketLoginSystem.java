package org.sdu.commandOLD;

import java.net.Socket;
import org.sdu.network.ModifiablePacket;
import org.sdu.util.DebugFramework;

/**
 * Build the login-system packet on the client to send to the server.
 * 
 * @deprecated
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketLoginSystem extends ModifiablePacket{

	public PacketLoginSystem(String username, String password, String status, Socket socket){
		try {
			PacketDataFactory.makePacket(this, socket,
					Command.cmdMainLogin, Command.cmdLoginSystem,
					username.getBytes("UTF-8"),
					password.getBytes("UTF-8"),
					status.getBytes("UTF-8"));
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
