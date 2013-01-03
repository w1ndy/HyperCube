package org.sdu.test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.sdu.net.Packet;

public class PacketResovlerTest {

	public static void main(String[] args){
		
		String s = "李毅大帝 isn't a みんな 的神。";
		int len = s.getBytes().length;
		
		ByteBuffer buf = ByteBuffer.allocate(7+len);
		buf.put((byte) 0x00);
		buf.put((byte) 0x00);
		buf.put((byte) 0x01);
		buf.put((byte) 0x01);
		buf.put((byte) 0x05);		
		buf.put((byte) ((byte) (len >> 8) & 0xff));
		buf.put((byte) (len & 0xff));

		buf.put(s.getBytes());
		System.out.println(s.getBytes());
		
		Packet p = new Packet(buf);
		ByteBuffer bu = p.getData();
		System.out.println(bu.get());
		System.out.println(bu.get());
		System.out.println(bu.get());
		System.out.println(bu.get());
		
		byte[] b = bu.array();
		
		String ss = new String(b);
		System.out.println(ss);
	}
}
