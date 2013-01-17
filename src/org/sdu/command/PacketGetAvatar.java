package org.sdu.command;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the get-avatar packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketGetAvatar extends Packet{

	public PacketGetAvatar(String signature,String id){
		try {
			dataBuffer = PacketBufferFactory.makePacket(Command.cmdMainSend, Command.cmdGetAvatar,
					id.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
