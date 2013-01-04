package org.sdu.server.ProcessTools.SecPro;

import org.sdu.database.Database;
import org.sdu.net.Packet;
import org.sdu.server.Val;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;

public class login {
	public static Packet Push(PacketDataPro ProD,Database db) {
			byte version[] = ProD.GetParamB();
			if ((version[0] == Val.F_version)&&(version[1] == Val.S_version)) {
			if (db.checkPassword(ProD.GetParam(),ProD.GetParam())) {
				ProD.GetParam();
				PacketDataBuilder Pack = new PacketDataBuilder();
				Pack.SetData(Val.Check_T,Val.Login,Val.LoginCheck);
				Pack.SetParamS("Welcome!");
				return Pack.GetData();
			}
			else{
				PacketDataBuilder Pack = new PacketDataBuilder();
				Pack.SetData(Val.Check_F,Val.Unknow,Val.Login,Val.LoginCheck);
				return Pack.GetData();
			}
		}
		// TODO return push data here.
		return null;
	}
}
