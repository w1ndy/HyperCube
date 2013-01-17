package org.sdu.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public UserInfo()
	{
		generateDebugInfo();
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