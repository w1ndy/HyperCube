package org.sdu.server;

import java.net.Socket;

import org.sdu.network.PacketDataPro;
import org.sdu.network.Val;
import org.sdu.network.IncomingPacket;
import org.sdu.network.SendingPacket;
import org.sdu.network.Session;
import org.sdu.server.ProcessTools.InstPro;

public class process {
	private static IncomingPacket indata;
	
	public static void Push(IncomingPacket p){
		indata = p;
	}
	
	public static SendingPacket GetData(){
		PacketDataPro ProD = new PacketDataPro(indata);
		byte[] tmp = InstPro.Push(ProD.GetFInst(),ProD.GetSInst(),ProD.GetParam());
		SendingPacket a = new SendingPacket(indata.getSocket(),tmp);
		return a;

	}
}
