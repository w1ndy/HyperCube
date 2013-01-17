package org.sdu.command;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the ask-push packet on the client to send to the server.
 * 
 * @version 0.1 rev 8001 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketAskPush extends Packet{

	public PacketAskPush(){
		try {			
			dataBuffer = PacketBufferFactory.makePacket(Command.cmdMainSend, Command.cmdAskPush);
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
