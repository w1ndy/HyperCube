package org.sdu.command;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the get-information packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketAskInformation extends Packet{

	public PacketAskInformation(String friendName){
		try {
			dataBuffer = PacketBufferFactory.makePacket(Command.cmdMainSend, Command.cmdGetInformation,
					friendName.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
