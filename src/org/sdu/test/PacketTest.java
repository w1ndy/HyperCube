package org.sdu.test;

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
		Packet p = new PacketLoginSystem("0.1","姓名","密码","隐身");
		byte[] b = p.getDataArray();
		
		for (int i = 0; i < b.length; i++)
			System.out.printf("0x%02X ", b[i]);

	}
	
}
