package org.sdu.command;

import java.util.Date;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the ask-push packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketAskPush extends Packet{

	public PacketAskPush(){
		try {
			long date = (new Date()).getTime();
			String datee = String.format("%08d", date);
			
			dataBuffer = PacketBufferFactory.makePacket(Command.cmdMainSend, Command.cmdAskPush,
					datee.getBytes("UTF-8"));
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
