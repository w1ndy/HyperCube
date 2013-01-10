package org.sdu.server;


import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.ProcessTools.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
/**
 * process for Incoming Packet and return the request data
 * post Packet to target
 * @author Celr
 *
 */
public class process {
	private static ByteBuffer indata;
	private static DatabaseInterface db1;
	private static Session ss;
	private static HashMap<String,Session> SessionMap;
	private static HashMap<Session,String> UserMap;
	public static void Push(ByteBuffer p,DatabaseInterface db,Session s){
		indata = p;
		ss = s;
		db1 = db;
	}
	
	public static Packet GetData(){
		PacketDataPro ProD = new PacketDataPro(indata);
		switch(ProD.GetFInst())
		{
		case 0x01: 
			 if(ProD.GetSInst() == 0x01) 
			 {return login.Push(ProD,db1,SessionMap,UserMap,ss);}
		case 0x02: 
			 if(ProD.GetSInst() == 0x01) 
			 {return logout.Push(ProD,db1,SessionMap,UserMap,ss);}
		case 0x03: 
			 if(ProD.GetSInst() == 0x01)
			 {return trans.PushforNotification(ProD,db1,UserMap,ss);}
		//default : return Warming....;
		}
		return null;
		

	}
}
