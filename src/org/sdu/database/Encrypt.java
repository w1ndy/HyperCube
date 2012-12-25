package org.sdu.database;

import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * Encrypt the password by using MD5 twice. After the first round, attach the ID
 * after the string.
 * 
 * @version 0.1 rev 8001 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
class Encrypt {
	/**
	 * MD5 encryption
	 */
	private static String getMD5Str(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException caught!");
			System.exit(-1);
		}
		byte[] byteArray = md.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static String password(String id, String pass) {
		return (getMD5Str(getMD5Str(pass) + id));
	}
}
