package org.sdu.server;


import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.ProcessTools.*;
import org.sdu.server.UI.ServerDataObserver;
import org.sdu.server.UI.TrackDataObserver;

import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.Observer;
/**
 * process for Incoming Packet and return the request data
 * post Packet to target
 * @author Celr
 *
 */
public class process {
	private ByteBuffer indata;
	private DatabaseInterface db;
	private Session s;
	private static Hashtable<String,Session> SessionMap;
	private static Hashtable<Session,String> UserMap;
	
	public login logMod;
	public logout logoutMod;
	public trans tranMod;
	private ServerDataObserver d;
	private TrackDataObserver d1;
	public process(ServerDataObserver d,TrackDataObserver d1) {
		this.d = d;
		logMod = new login(d);
		logoutMod = new logout(d);
		tranMod = new trans(d1);
	}

	public void Push(ByteBuffer p,DatabaseInterface db,Session s,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap){
		indata = p;
		this.s = s;
		this.db = db;
		process.SessionMap = SessionMap;
		process.UserMap = UserMap;
	}
	
	public Packet GetData() throws Exception{
		PacketDataPro ProD = new PacketDataPro(indata);
		switch(ProD.GetFInst())
		{
		case 0x01: 
			 if(ProD.GetSInst() == 0x01) 
			 {return logMod.Push(ProD,db,SessionMap,UserMap,s);}
		case 0x02: 
			 if(ProD.GetSInst() == 0x01) 
			 {return logoutMod.Push(ProD,db,SessionMap,UserMap,s);}
		case 0x03: {
			 if(ProD.GetSInst() == 0x01)
			 {return tranMod.PushforNotification(ProD,db,UserMap,s);}
  			 if(ProD.GetSInst() == 0x03)
  			 {return tranMod.ChangeStatus(ProD, db, UserMap, s);}
  			 if(ProD.GetSInst() == 0x05)
  			 {return tranMod.QueryUserData(ProD, db, UserMap, s);}
		}
		//default : return Warming....;
		}
		return null;
		

	}
}
