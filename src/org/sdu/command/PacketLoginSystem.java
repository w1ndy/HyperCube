package org.sdu.command;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the login-system packet on the client to send to the server.
 * 
 * @version 0.1 rev 8001 Jam. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketLoginSystem extends Packet{

	public PacketLoginSystem(byte versionMain, byte versionSub, String username, String password, String status){
		try {
			dataBuffer = PacketBufferFactory.makePacket(Command.cmdMainLogin, Command.cmdLoginSystem,
					new byte[]{versionMain, versionSub},
					username.getBytes("UTF-8"),
					password.getBytes("UTF-8"),
					status.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
