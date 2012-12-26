package org.sdu.server;

import java.net.Socket;

import org.sdu.network.PacketDataPro;
import org.sdu.network.Val;
import org.sdu.network.IncomingPacket;
import org.sdu.network.SendingPacket;
import org.sdu.network.Session;
import org.sdu.server.ProcessTools.DaddingPro;
import org.sdu.server.ProcessTools.InstPro;

public class process {
	private IncomingPacket indata;
	private byte opt[];
	private final int length_dadding = 10; 
	public void Push(IncomingPacket p){
		indata = p;
		opt = indata.getData();
	}
	
	public SendingPacket GetData(){
		byte Dadding[];
		InstPro InstA = new InstPro();
		DaddingPro DaddingA = new DaddingPro();
		PacketDataPro ProD = new PacketDataPro(indata);
		//if (ProD.GetDelimiter() != Val.delimiter) return something to Warming;
		//DaddingA unfinished
		return new SendingPacket(indata.getSocket(),InstA.Push(opt[14],opt[15],ProD));

	}
}
