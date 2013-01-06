package org.sdu.test;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import org.sdu.command.PacketResolver;
import org.sdu.command.PacketResolver.par;
import org.sdu.net.Packet;

public class PacketResovlerTest {

	public static void main(String[] args){
		par pp;
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
		PacketResolver re = new PacketResolver(p);
		
		LinkedList<par> list = re.getList();
		len = list.size();

		
		for (int i=0; i <= len-1; i++){
			pp = list.get(i);
			System.out.println(pp.pos);
			System.out.println(pp.length);
			byte[] bb = new byte[pp.length + 1];
			ByteBuffer buffer = p.getData();
			buffer.position(pp.pos);
			buffer.get(bb, 0, pp.length);
			String ss = new String(bb);
			System.out.println(ss);
			
		}
		
	}
}
