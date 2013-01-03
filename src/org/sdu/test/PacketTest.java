package org.sdu.test;

import java.nio.ByteBuffer;

import org.sdu.command.*;
import org.sdu.net.Packet;


/**
 * Test the classes of building a new packet and print out the packet.
 * 
 * @version 0.1 rev 8002 Jan. 3. 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketTest {
	
	public static void main(String[] args){
		Packet p = new PacketLoginSystem((byte)1, (byte)1, "username","password", (byte)1);
		ByteBuffer buf = p.getData();

		while(buf.hasRemaining()) {
			System.out.printf("%02X", buf.get());
		}
		
	}
	
}
