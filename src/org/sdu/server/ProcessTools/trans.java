package org.sdu.server.ProcessTools;

import java.util.Hashtable;

import org.sdu.database.Message;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;


public class trans {
	public static byte[] timestamp;
	public static long times;
	public static Packet PushforNotification(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) throws Exception {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Message[] messa = db.getMessage(UserMap.get(s));
		if (messa.length == 0) {
			Pack.SetData(Val.Check_F,Val.NoNewMess,Val.DataTrans,Val.SendNotificationR);
			return Pack.GetData();
			
		}
		Pack.SetData(Val.Check_T,Val.Blank,Val.DataTrans,Val.SendNotificationR);
			for (int i = 0;i<messa.length;i++){
			Pack.SetParamS(messa[i].from);
			Pack.SetParamS(messa[i].message);}
		return Pack.GetData();
	}
	public static Packet PushforFriendList(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Blank,Val.SendFriendList,Val.SendFriendName);
		try {
			Pack.SetParamS(db.getFriendList(UserMap.get(s)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Pack.GetData();
	}
	public static Packet PushforFriendDetail(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Blank,Val.SendFriendList,Val.SendFriendDetail);
		//TODO unfinished
		return Pack.GetData();
	}
	public static Packet PushforState(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Blank,Val.DataTrans,Val.SendStateData);
		try {
			Pack.SetParamS(db.    (UserMap.get(s)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Pack.GetData();
	}
}
