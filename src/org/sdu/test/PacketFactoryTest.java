package org.sdu.test;

import org.sdu.command.PacketCheckVersion;
import org.sdu.network.Packet;

/**
 * PacketFactoryTest class do a unit test for PacketDataFactory class.
 * 
 * @version 0.1 rev 8001 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketFactoryTest
{
	public static void main(String[] args)
	{
		Packet p = new PacketCheckVersion("中文数据测试", null);
		int n = 0;
		for(byte b : p.getData()) {
			System.out.printf("0x%02X ", b);
			n++;
			if(n >= 10) {
				System.out.println();
				n = 0;
			}
		}
	}
}