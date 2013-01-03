package org.sdu.commandOLD;

import java.security.*;

import org.sdu.util.DebugFramework;


/**
 * Encrypt the password by using MD5 twice. After the first round, attach the ID
 * after the string.
 * 
 * @version 0.1 rev 8002 Dec. 26, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Encrypt {
	
	private static final char[] byte_map = 
		{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static String getMD5Str(String str)
	{
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			DebugFramework.getFramework().print("MD5 encryption failed: " + e);
			return null;
		}
		
		byte[] byteArray = md.digest();
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			strbuf.append(byte_map[(byteArray[i] & 0xF0) >> 8]).append(
					byte_map[byteArray[i] & 0x0F]);
		}
		return strbuf.toString();
	}

	/**
	 * Encrypt the password using MD5.
	 * 
	 * @param id
	 * @param pass
	 * @return
	 */
	public static String encryptPassword(String id, String pass)
	{
		return (getMD5Str(getMD5Str(pass) + id));
	}
}
