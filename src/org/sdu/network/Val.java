package org.sdu.network;
/**
 * The Value	
 * @author Celr
 *		loc --> location 
 */
public class Val {
	/**
	 * Packet framework
	 */
	public final static byte delimiter = 2;
	public final static int loc_delimiter = 1;
	public final static int length_delimiter = 1;
	public final static int loc_Length = loc_delimiter+length_delimiter;
	public final static int length_Length = 2;
	public final static int loc_Dadding =loc_Length+length_Length;	
	public final static int length_Dadding = 10;
	public final static int loc_Inst = loc_Dadding+length_Dadding;
	public final static int length_Inst = 2;
	public final static byte Param_Head = 5;
	public final static int length_paramHead = 1;
	public final static int length_paramlength = 2;
	/**
	 * Check Index 
	 */
	public final static String Version = "alpha001";
	//Dadding
	/**
	 * The Inst
	 */
	public final static byte Inst_login = 1;
	public final static byte Inst_logout = 2;
	public final static byte Inst_Trans = 3;
	public final static byte Inst_detect = 4;
	/**
	 * The SubInst
	 */
	public final static byte Inst_ChcekVersion = 1;
	public final static byte Inst_LoginMassage = 2;
	public final static byte In
	/**
	 * The Param
	 */
	public final static byte Param_UserName = 1;
	public final static byte Param_Password = 2;
	public final static byte Param_Hiden = 3;
	
	
}
