package org.sdu.server.ProcessTools;

import java.util.Hashtable;
import java.util.Observable;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.server.DatabaseInterface;
import org.sdu.server.ID_Manager;
import org.sdu.server.Val;
import org.sdu.server.PacketDataBuilder;
import org.sdu.server.PacketDataPro;
import org.sdu.server.UI.ServerDataObserver;
/**
 * This Model check the version,username and password and return a Packet
 * @author Celr
 *
 */
public class login extends Observable{
	public login(ServerDataObserver d){
		this.addObserver(d);
	}
	public Packet Push(PacketDataPro ProD, DatabaseInterface db,Hashtable<String,Session> SessionMap,Hashtable<Session,String> UserMap,Session s) throws Exception {
		byte version[] = ProD.GetParamB();
			 String username = ProD.GetParam();
			 String password = ProD.GetParam();
		PacketDataBuilder Pack = new PacketDataBuilder();
		if ((version[0] == Val.F_version) && (version[1] == Val.S_version)) {
			try {
				if (db.checkPassword(username, password)) {
					//if (ProD.GetParamB()[0] == 0){db.setVisible(username,false);}
					//else{db.setVisible(username,true);}					
					Pack.SetData(Val.Check_T,Val.Blank, Val.Login, Val.LoginCheck);
					Pack.SetParamS(ID_Manager.setID(username));
					SessionMap.put(username,s);
					UserMap.put(s,username);
					setChanged();
					notifyObservers(new String[]{username,db.getRealName(username),db.getNickname(username),"上线"});
				} else {
					if (!db.checkExist(username)){
						Pack.SetData(Val.Check_F, Val.NotExist, Val.Login,Val.LoginCheck);
						setChanged();
						notifyObservers(new String[]{username,"无","无","该用户不存在"});
						return Pack.GetData();
					}
					if (db.getOnline(username)) {
						Pack.SetData(Val.Check_F, Val.AlreadyOnline, Val.Login,Val.LoginCheck);
						setChanged();
						notifyObservers(new String[]{username,db.getRealName(username),db.getNickname(username),"登录失败：该用户重复登录"});
						return Pack.GetData();
					}
					if (db.getFreeze(username)) {
						Pack.SetData(Val.Check_F, Val.Freeze, Val.Login,Val.LoginCheck);
						setChanged();
						notifyObservers(new String[]{username,db.getRealName(username),db.getNickname(username),"登录失败：该用户已被冻结"});
						return Pack.GetData();
	 				}
					Pack.SetData(Val.Check_F, Val.WrongPass, Val.Login,Val.LoginCheck);
					setChanged();
					notifyObservers(new String[]{username,db.getRealName(username),db.getNickname(username),"登录失败：该用户密码错误"});
					return Pack.GetData();
				}
			} catch (Exception e) {
				Pack.SetData(Val.Check_F, Val.Unknow, Val.Login, Val.LoginCheck);
				e.printStackTrace();
				setChanged();
				notifyObservers(new String[]{username,"无","无","登录失败：未知错误"});
				throw new Exception("Unknow");
			}
		} else {
			Pack.SetData(Val.Check_F, Val.UnSupportVer, Val.Login,Val.LoginCheck);
			setChanged();
			notifyObservers(new String[]{username,db.getRealName(username),db.getNickname(username),"登录失败：版本过低"});
		}
		return Pack.GetData();
	}
}
