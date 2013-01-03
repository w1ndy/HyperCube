package org.sdu.server.ProcessTools.SecPro;

import org.sdu.database.Database;
import org.sdu.net.Packet;
import org.sdu.server.Val;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;

public class login {
	public static Packet Push(PacketDataPro ProD,Database db) {
			if (ProD.GetParam() == Val.F_version&ProD.GetParam() == Val.S_version) {
			if (db.check(ProD.GetParam(),ProD.GetParam())) {
				ProD.GetParam();
				PacketDataBuilder Pack = new PacketDataBuilder();
				Pack.SetData(Val.Check_T,Val.Login,Val.LoginCheck);
				Pack.SetParamS("Welcome!");
			}
			else{
				PacketDataBuilder Pack = new PacketDataBuilder();
				Pack.SetData(Val.Check_F,Val.Unknow,Val.Login,Val.LoginCheck);
			}
		}
		// TODO return push data here.
		return null;
	}
}
