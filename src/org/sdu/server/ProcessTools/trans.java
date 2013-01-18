package org.sdu.server.ProcessTools;

import java.util.Hashtable;
import java.util.Observable;
import org.sdu.database.Message;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;
import org.sdu.server.UI.TrackDataObserver;


public class trans extends Observable{
	public byte[] timestamp;
	public long times;
//	public trans(TrackDataObserver d) {
//		this.addObserver(d);
//	}
	public Packet PushforNotification(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) throws Exception {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Message[] messa = db.getMessage(UserMap.get(s));
		if (messa.length == 0) {
			Pack.SetData(Val.Check_F,Val.NoNewMess,Val.DataTrans,Val.SendNotificationR);
	//		setChanged();
		//	notifyObservers(new String[]{db.getRealName(UserMap.get(s)),"鎷夊彇閫氱煡","鏃,"鏃犳渶鏂伴€氱煡"});
			return Pack.GetData();
			
		}
		Pack.SetData(Val.Check_T,Val.Blank,Val.DataTrans,Val.SendNotificationR);
			String dat = "";
			String dat = "";
			for (int i = 0;i<messa.length;i++){
			Pack.SetParamS(messa[i].from);
			Pack.SetParamS(messa[i].message);
			dat = dat+messa[i].from+" : "+messa[i].message+"   ";
			}
		//	setChanged();
		//	notifyObservers(new String[]{db.getRealName(UserMap.get(s)),"鎷夊彇閫氱煡",dat,"鏂伴€氱煡"});
		return Pack.GetData();
	}
	public Packet ChangeStatus(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Check_T,Val.DataTrans,Val.ChangeStatusReply);
		setChanged();
		try {
			notifyObservers(new String[]{db.getRealName(UserMap.get(s)),"鏀瑰彉绛惧悕",db.getStatus(UserMap.get(s)),"鎴愬姛"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Pack.GetData();
	}
	public Packet ChangeStatus(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Check_T,Val.DataTrans,Val.ChangeStatusReply);
	//	setChanged();
		//try {
		//	notifyObservers(new String[]{db.getRealName(UserMap.get(s)),"鏀瑰彉绛惧悕",db.getStatus(UserMap.get(s)),"鎴愬姛"});
		//} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		return Pack.GetData();}
	//}
	public Packet QueryUserData(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_T,Val.Blank,Val.DataTrans,Val.QueryUserData);
		String username = ProD.GetParam();
		try {
			Pack.SetParamS(username);
			if (db.getOnline(username)&(db.getVisible(username))){Pack.SetParamB(Val.Online);}
			if (db.getOnline(username)&(!db.getVisible(username))){Pack.SetParamB(Val.Offline);}
			if (!db.getOnline(username)){Pack.SetParamB(Val.Offline);}
			Pack.SetParamS(db.getStatus(username));
			Pack.SetParamS(db.getHeadImage(username));
	//		setChanged();
	//		notifyObservers(new String[]{db.getRealName(UserMap.get(s)),"鏌ヨ鐢ㄦ埛淇℃伅","鏌ヨ鐨勬樀绉颁负锛+db.getNickname(UserMap.get(s)),"鎴愬姛"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Pack.GetData();

	}
//	public static Packet PushforFriendList(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
//	PacketDataBuilder Pack = new PacketDataBuilder();
//	Pack.SetData(Val.Check_T,Val.Blank,Val.SendFriendList,Val.SendFriendName);
//	try {
//		Pack.SetParamS(db.getFriendList(UserMap.get(s)));
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return Pack.GetData();
//}
//public static Packet PushforFriendDetail(PacketDataPro ProD, DatabaseInterface db,Hashtable<Session,String> UserMap,Session s) {
//	PacketDataBuilder Pack = new PacketDataBuilder();
//	Pack.SetData(Val.Check_T,Val.Blank,Val.SendFriendList,Val.SendFriendDetail);
//	username = 
//	Pack.SetParamS(db.)
//	return Pack.GetData();
//}
}