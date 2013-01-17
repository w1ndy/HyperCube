package org.sdu.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Observable;

import javax.imageio.ImageIO;

import org.sdu.command.PacketGetAvatar;
import org.sdu.command.PacketResolver;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.ui.AvatarBox;

/**
 * UserInfo class manages current user's information.
 * 
 * @version 0.1 rev 8000 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class UserInfo extends Observable
{
	private AvatarBox avatar;
	private String nickname, signature, userId, sessionId;
	
	public UserInfo(String userId, String sessionId, boolean bInvisible)
	{
		this.userId = userId;
		this.sessionId = sessionId;
		avatar = new AvatarBox(true);
		if(bInvisible) avatar.switchStatus();
		generateDebugInfo();
	}
	
	public int sendFetchRequest(Session s)
	{
		s.post(new PacketGetAvatar(userId));
		return 1;
	}
	
	private void readNickName(PacketResolver.par part, Packet p)
	{
		ByteBuffer data = p.getData();
		byte[] buf = new byte[part.length];
		data.position(part.pos);
		data.get(buf);
		try {
			nickname = new String(buf, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
			nickname = "(NONE)";
		}
	}
	
	private void readSignature(PacketResolver.par part, Packet p)
	{
		ByteBuffer data = p.getData();
		byte[] buf = new byte[part.length];
		data.position(part.pos);
		data.get(buf);
		try {
			signature = new String(buf, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
			signature = "(NONE)";
		}
	}
	
	private void readAvatar(PacketResolver.par part, Packet p)
	{
		try {
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
			avatar.setAvatar(ImageIO.read(f));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int recvFetchRequest(Session s, Packet p, PacketResolver resolver)
	{
		switch(resolver.getInstMain())
		{
		case 0x03:
			if(resolver.getInstSub() == 0x05) {
				readNickName(resolver.getList().get(0), p);
				readSignature(resolver.getList().get(2), p);
				readAvatar(resolver.getList().get(3), p);
			}
			break;
		}
		return 0;
	}
	
	private void generateDebugInfo()
	{
		try {
			avatar.setAvatar(ImageIO.read(new File("art/testavatar.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		nickname = "Arthas";
		signature = "这就是传说中的签名档！！！颤抖吧凡人！！";
	}
	
	public AvatarBox getUserAvatar()
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