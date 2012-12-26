package org.sdu.server.ProcessTools;

import java.net.Socket;

import org.sdu.network.PacketDataPro;
import org.sdu.network.SendingPacket;
import org.sdu.server.ProcessTools.SecPro.login;

public class InstPro {
	private byte cmd1;
	private byte cmd2;
	byte[] Nullarr;
	public byte[] Push(byte c1, byte c2,PacketDataPro ProD){
		cmd1 = c1;
		cmd2 = c2;
		switch(cmd1)
		{
		case 0x01: return {login loginA = new login(); loginA.Push(cmd2,ProD);}
		//case 0x02: return logout.Push(cmd2,ProD);....
		//case 0x03: return trans.Push(cmd2,ProD);....
		//case 0x04: return detect.Push(cmd2,ProD);....
		//default : return Warming....;
		}
		return Nullarr;
	}
}
