package org.sdu.command;

import java.nio.ByteBuffer;

/**
 * Build the packet on the client to send to the server.
 * 
 * @version 0.1 rev 8001 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PacketBufferFactory{
	/**
	 *  
	 * @param instMain --> The main instruction
	 * @param instDeputy --> The deputy instruction
	 * @param list --> The list of the strings
	 * @return The byte[] part of the new packet
	 */
	public static ByteBuffer makePacket(int instMain, int instSub, byte[]...list){
		int length = list.length;
		int length_buf = 2;
		ByteBuffer buf;
		
		/**
		 * Get the length of the whole packet to create a ByteBuffer.
		 */
		for (int i = 0; i < length; i++){
			length_buf += 3;
			length_buf += list[i].length;
		}
		
		/**
		 * Build the part of instructions.
		 */
		buf = ByteBuffer.allocate(length_buf);
		
		buf.put((byte) instMain);
		buf.put((byte) instSub);

		/**
		 * Build the part of each param.
		 */				
		for (int i = 0; i < length; i++){			
			buf.put((byte) 0x05);
			buf.put((byte) ((list[i].length >> 8) & 0xff));
			buf.put((byte) (list[i].length & 0xff));
			
			buf.put(list[i]);

		}
		
		buf.flip();
		return buf;
		
	}
}