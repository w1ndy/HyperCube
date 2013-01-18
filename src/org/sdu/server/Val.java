package org.sdu.server;
/**
 * Values for Server
 * @author Celr
 *
 */
public final class Val {
	/**
	 * Version
	 */
	public final static byte F_version = 00;
	public final static byte S_version = 01;
	/**
	 *  Flag Code
	 */
	public final static byte Online = 0x00;
	public final static byte Offline = 0x01;
	public final static byte UnSupportVer = 0x01;
	public final static	byte WrongPass = 0x02;
	public final static	byte UnReg = 0x03;
	public final static	byte Freeze = 0x04;
	public final static	byte AlreadyOnline = 0x05;
	public final static	byte AlreadyExist = 0x01;
	public final static	byte NoAuthority = 0x02;
	public final static	byte NotExist = 0x03;
	public final static	byte NoNewMess = 0x01;
	
	//For All
	public final static byte Check_T = 0x00;
	public final static byte Check_F = 0x01;
	public final static	byte Unknow = 0x00;
	public final static byte Blank = 0x00;
	/**
	 * Inst Code
	 */
	//STC
	public final static	byte Login = 0x01;
		public final static	byte LoginCheck =0x01;
	public final static	byte Logout = 0x02;
		public final static	byte LogoutReply = 0x01;
	public final static	byte DataTrans = 0x03;
		public final static	byte SendNotificationA = 0x01;
		public final static	byte SendNotificationR = 0x02;
		public final static	byte ChangeStatusReply = 0x03;
		public final static	byte QueryUserData = 0x06;
		
	

	


	//public final static	byte SendMessage = 0x03;
	

	

	
}