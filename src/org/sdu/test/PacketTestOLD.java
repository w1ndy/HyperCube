package org.sdu.test;

import java.net.Socket;

import org.sdu.commandOLD.PacketLoginSystem;
import org.sdu.network.Packet;


/**
 * Test the classes of building a new packet and print out the packet.
 * 
 * @deprecated
 * @version 0.1 rev 8001 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketTestOLD {
	
	public static void main(String[] args){
		Socket s = null;
		Packet p = new PacketLoginSystem("姓名","密码","隐身",s);
		byte[] b = p.getData();
		
		for (int i = 0; i < b.length; i++)
			System.out.printf("0x%02X ", b[i]);

	}
	
}
