package org.sdu.server;


import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.ProcessTools.*;

import java.nio.ByteBuffer;
import java.util.Hashtable;
/**
 * process for Incoming Packet and return the request data
 * post Packet to target
 * @author Celr
 *
 */
public class process {
	private static ByteBuffer indata;
	private static DatabaseInterface db;
	private static Session s;
	private static Hashtable<String,Session> SessionMap;
	private static Hashtable<Session,String> UserMap;
	public static void Push(ByteBuffer p,DatabaseInterface db,Session s,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap){
		indata = p;
		process.s = s;
		process.db = db;
		process.SessionMap = SessionMap;
		process.UserMap = UserMap;
	}
	
	public static Packet GetData() throws Exception{
		PacketDataPro ProD = new PacketDataPro(indata);
		switch(ProD.GetFInst())
		{
		case 0x01: 
			 if(ProD.GetSInst() == 0x01) 
			 {return login.Push(ProD,db,SessionMap,UserMap,s);}
		case 0x02: 
			 if(ProD.GetSInst() == 0x01) 
			 {return logout.Push(ProD,db,SessionMap,UserMap,s);}
		case 0x03: {
			 if(ProD.GetSInst() == 0x01)
			 {return trans.PushforNotification(ProD,db,UserMap,s);}
			 if(ProD.GetSInst() == 0x04)
			 {return trans.PushforFriendList(ProD, db, UserMap, s);}
		}
		//default : return Warming....;
		}
		return null;
		

	}
}
