package org.sdu.command;

import org.sdu.net.Packet;
import org.sdu.util.DebugFramework;

/**
 * Build the respond-enquiry packet on the client to send to the server.
 * 
 * @version 0.1 rev 8000 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketRespondEnquiry extends Packet{

	public PacketRespondEnquiry(){
		try {
			dataBuffer = PacketBufferFactory.makePacket(
					Command.cmdMainRespond, Command.cmdRespondEnquiry);
		} catch(Exception e) {
			DebugFramework.getFramework().print("Encoding not found: " + e);
		}
	}
	
}
