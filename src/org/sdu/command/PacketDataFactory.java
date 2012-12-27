package org.sdu.command;

import java.net.Socket;

import org.sdu.network.ModifiablePacket;

/**
 * Build the packet on the client to send to the server.
 * 
 * @version 0.1 rev 8002 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketDataFactory{
	/**
	 *  
	 * @param instMain --> The main instruction
	 * @param instDeputy --> The deputy instruction
	 * @param list --> The list of the strings
	 * @return The byte[] part of the new packet
	 */
	public static void makePacket(ModifiablePacket p, Socket s, int instMain, int instSub, byte[]...list){
		int length = list.length;
		byte[] arr;
		int length_packet = 2;
		int point = 0;
		
		/**
		 * Get the length of the whole packet to create a byte[].
		 */
		for (int i = 0; i < length; i++){
			length_packet += 3;
			length_packet += list[i].length;
		}
		
		/**
		 * Build the part of instructions.
		 */
		arr = new byte[length_packet];
		arr[point] = (byte) instMain; point++;
		arr[point] = (byte) instSub; point++;

		/**
		 * Build the part of each param.
		 */				
		for (int i = 0; i < length; i++){			
			arr[point] = 0x05; point++;
			arr[point] = (byte) ((list[i].length >> 8) & 0xff); point++;
			arr[point] = (byte) (list[i].length & 0xff); point++;

			System.arraycopy(list[i], 0, arr, point, list[i].length);
			point += list[i].length;
		}
		
		/**
		 * Fill the packet
		 */
		p.setData(arr);
		p.setSocket(s);
	}
}