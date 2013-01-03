package org.sdu.command;


import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the delete-user packet on the client to send to the server.
 * 
 * @version 0.1 rev 8001 Jan. 3, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketDeleteUser extends Packet{

	public PacketDeleteUser(String username){
		try {
			dataBuffer = PacketBufferFactory.makePacket(
					Command.cmdMainChange, Command.cmdDeleteUser,
					username.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}

	
}
