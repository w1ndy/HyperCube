package org.sdu.command;

import java.net.Socket;
import org.sdu.network.ModifiablePacket;
import org.sdu.util.DebugFramework;

/**
 * Build the logout-system packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketLogoutSystem extends ModifiablePacket{

	public PacketLogoutSystem(Socket socket){
		try {
			PacketDataFactory.makePacket(this, socket,
					Command.cmdMainLogout, Command.cmdLogoutSystem);
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
