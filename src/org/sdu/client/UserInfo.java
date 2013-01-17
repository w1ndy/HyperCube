package org.sdu.client;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.sdu.command.PacketGetAvatar;
import org.sdu.command.PacketResolver;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.util.DebugFramework;

/**
 * UserInfo class manages current user's information.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class UserInfo
{
	private Image avatar;
	private String nickname, signature, userId, sessionId;
	
	public UserInfo(String userId, String sessionId)
	{
		this.userId = userId;
		this.sessionId = sessionId;
		generateDebugInfo();
	}
	
	public int sendFetchRequest(Session s)
	{
		s.post(new PacketGetAvatar(userId));
		return 1;
	}
	
	public int recvFetchRequest(Session s, Packet p, PacketResolver resolver)
	{
		switch(resolver.getInstMain())
		{
		case 0x03:
			if(resolver.getInstSub() == 0x06) {
				try {
					if(resolver.getStatusMain() != 0) {
						throw new Exception("Failed to fetch user's avatar.");
					} else {
						PacketResolver.par part = resolver.getList().get(0);
						if(part == null) 
							throw new Exception("Failed to fetch user's avatar.");
						
						byte[] buffer = new byte[part.length];
						ByteBuffer data = p.getData();
						data.position(part.pos);
						data.get(buffer);
						
						URL url = new URL(new String(buffer, "UTF-8"));
						InputStream input = url.openStream();
						File f = new File("users/" + userId + "/avatar.jpg");
						f.getParentFile().mkdirs();
						OutputStream output = new FileOutputStream(f);
						
						int length;
						byte[] rbuf = new byte[1024];
						while((length = input.read(rbuf)) != -1)
							output.write(rbuf, 0, length);
						
						input.close();
						output.close();
					}
					avatar = ImageIO.read(new File("users/" + userId + "/avatar.jpg"));
				} catch(Exception e) {
					DebugFramework.getFramework().print(e.getMessage());
					avatar = null;
				}
			}
		}
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