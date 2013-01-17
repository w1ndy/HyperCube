package org.sdu.server.ProcessTools;

import java.util.Hashtable;
import java.util.Observable;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;

public class logout extends Observable {
	public Packet Push(PacketDataPro ProD,DatabaseInterface db,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_F,Val.Blank,Val.Logout,Val.LogoutReply);
		setChanged();
		try {
			notifyObservers(new String[]{UserMap.get(s),db.getRealName(UserMap.get(s)),db.getNickname(UserMap.get(s)),"用户登出"});
		} catch (Exception e) {
			e.printStackTrace();
		}
		SessionMap.remove(UserMap.get(s));
		UserMap.remove(s);
		return Pack.GetData();
	}
}
