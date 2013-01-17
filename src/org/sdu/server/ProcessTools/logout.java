package org.sdu.server.ProcessTools;

import java.util.Hashtable;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;

public class logout {
	public static Packet Push(PacketDataPro ProD,DatabaseInterface db,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_F,Val.Logout);
		SessionMap.remove(UserMap.get(s));
		UserMap.remove(s);
		return Pack.GetData();
	}
}
