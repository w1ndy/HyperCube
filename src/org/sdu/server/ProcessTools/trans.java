package org.sdu.server.ProcessTools;

import java.util.HashMap;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;


public class trans {
	public static byte[] timestamp;
	public static long times;
	public static Packet PushforNotification(PacketDataPro ProD, DatabaseInterface db,HashMap<Session,String> UserMap,Session s) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		timestamp = ProD.GetParamB();
		times = (timestamp[0]<<56)+(timestamp[1]<<48)+(timestamp[2]<<40)+(timestamp[3]<<32)+(timestamp[4]<<24)+(timestamp[5]<<16)+(timestamp[6]<<8)+(timestamp[7]);
		Pack.SetData(Val.Check_T,Val.DataTrans,Val.SendNotificationR);
		try {
			Pack.SetParamS(db.getNotification(UserMap.get(s), times));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Pack.GetData();
	}
}
