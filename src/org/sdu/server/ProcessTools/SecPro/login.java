package org.sdu.server.ProcessTools.SecPro;

import org.sdu.database.Database;
import org.sdu.net.Packet;
import org.sdu.server.Val;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
/**
 * This Model check the version,username and password and return a Packet
 * @author Celr
 *
 */
public class login {
	public static Packet Push(PacketDataPro ProD, Database db) {
		byte version[] = ProD.GetParamB();
			 String username = ProD.GetParam();
			 String password = ProD.GetParam();
		PacketDataBuilder Pack = new PacketDataBuilder();
		if ((version[0] == Val.F_version) && (version[1] == Val.S_version)) {
			try {
				if (db.checkPassword(username, password)) {
					ProD.GetParam();
					Pack.SetData(Val.Check_T, Val.Login, Val.LoginCheck);
					Pack.SetParamS("Welcome!");
				} else {
					if (!db.checkExist(username)){
						Pack.SetData(Val.Check_F, Val.NotExist, Val.Login,Val.LoginCheck);
						return Pack.GetData();
					}
					if (db.getOnline(username)) {
						Pack.SetData(Val.Check_F, Val.AlreadyOnline, Val.Login,Val.LoginCheck);
						return Pack.GetData();
					}
//					if (db.Freeze()) {
//						Pack.SetData(Val.Check_F, Val.Freeze, Val.Login,Val.LoginCheck);
//						return Pack.GetData();
//	 				}
					Pack.SetData(Val.Check_F, Val.WrongPass, Val.Login,Val.LoginCheck);
					return Pack.GetData();
				}
			} catch (Exception e) {
				Pack.SetData(Val.Check_F, Val.Unknow, Val.Login, Val.LoginCheck);
				e.printStackTrace();
			}
		} else {
			Pack.SetData(Val.Check_F, Val.UnSupportVer, Val.Login,Val.LoginCheck);
		}
		return Pack.GetData();
	}
}
