package org.sdu.test;
import java.net.Socket;

import org.sdu.network.IncomingPacket;
import org.sdu.network.PacketDataPro;
public class ModelTest {
	public static Socket s;
	public static byte[] arr = {0x01,0x01,0x05,};
		public static void main(String[] Args){
			IncomingPacket tmp = new IncomingPacket(s,arr);
			System.out.println(PacketDataPro.GetParam());
		}
}
