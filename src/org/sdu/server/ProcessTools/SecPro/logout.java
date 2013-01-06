package org.sdu.server.ProcessTools.SecPro;

import org.sdu.database.Database;
import org.sdu.net.Packet;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.Val;

public class logout {
	public static Packet Push(PacketDataPro ProD,Database db) {
		PacketDataBuilder Pack = new PacketDataBuilder();
		Pack.SetData(Val.Check_F,Val.Logout);
		return Pack.GetData();
	}
}
