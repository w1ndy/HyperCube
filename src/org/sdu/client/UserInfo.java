package org.sdu.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sdu.command.PacketResolver;
import org.sdu.net.Packet;
import org.sdu.net.Session;

/**
 * UserInfo class manages current user's information.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class UserInfo
{
	private Image avatar;
	private String nickname, signature;
	
	public UserInfo(String userId, String sessionId)
	{
		generateDebugInfo();
	}
	
	public int sendFetchRequest(Session s)
	{
		return 0;
	}
	
	public int recvFetchRequest(Session s, Packet p, PacketResolver resolver)
	{
		return 0;
	}
	
	private void generateDebugInfo()
	{
		try {
			avatar = ImageIO.read(new File("art/testavatar.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			avatar = null;
		}
		nickname = "Arthas";
		signature = "这就是传说中的签名档！！！颤抖吧凡人！！";
	}
	
	public Image getUserAvatar()
	{
		return avatar;
	}
	
	public String getNickName()
	{
		return nickname;
	}
	
	public String getSignature()
	{
		return signature;
	}
}