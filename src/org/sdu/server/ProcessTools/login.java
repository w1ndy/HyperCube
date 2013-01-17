package org.sdu.server.ProcessTools;

import java.util.Hashtable;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.ID_Manager;
import org.sdu.server.Val;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
/**
 * This Model check the version,username and password and return a Packet
 * @author Celr
 *
 */
public class login {
	public static Packet Push(PacketDataPro ProD, DatabaseInterface db,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap,Session s) throws Exception {
		byte version[] = ProD.GetParamB();
			 String username = ProD.GetParam();
			 String password = ProD.GetParam();
		PacketDataBuilder Pack = new PacketDataBuilder();
		if ((version[0] == Val.F_version) && (version[1] == Val.S_version)) {
			try {
				if (db.checkPassword(username, password)) {
					if (ProD.GetParamB()[0] == 0){db.setInvisible(username);}
					else{db.setVisible(username);}
					Pack.SetData(Val.Check_T,Val.Blank, Val.Login, Val.LoginCheck);
					Pack.SetParamS(ID_Manager.setID(username));
					synchronized("Map")
					{SessionMap.put(username,s);
					UserMap.put(s,username);}
				} else {
					if (!db.checkExist(username)){
						Pack.SetData(Val.Check_F, Val.NotExist, Val.Login,Val.LoginCheck);
						return Pack.GetData();
					}
					if (db.getOnline(username)) {
						Pack.SetData(Val.Check_F, Val.AlreadyOnline, Val.Login,Val.LoginCheck);
						return Pack.GetData();
					}
					if (db.Freeze(username)) {
						Pack.SetData(Val.Check_F, Val.Freeze, Val.Login,Val.LoginCheck);
						return Pack.GetData();
	 				}
					Pack.SetData(Val.Check_F, Val.WrongPass, Val.Login,Val.LoginCheck);
					return Pack.GetData();
				}
			} catch (Exception e) {
				Pack.SetData(Val.Check_F, Val.Unknow, Val.Login, Val.LoginCheck);
				e.printStackTrace();
				throw new Exception("Unknow");
			}
		} else {
			Pack.SetData(Val.Check_F, Val.UnSupportVer, Val.Login,Val.LoginCheck);
		}
		return Pack.GetData();
	}
}
