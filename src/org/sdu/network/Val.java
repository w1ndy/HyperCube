package org.sdu.network;
/**
 * The Value	
 * @deprecated
 * @author Celr
 *		loc --> location 
 */
public class Val {
	/**
	 * Packet framework
	 */
	public final static int loc_Inst = 0;
	public final static int length_Inst = 2;
	/**
	 * Check Index 
	 */
	public final static String Version = "alpha001";
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
	// TODO public final static byte In
	
	/**
	 * The Param
	 */
	public final static byte Param_UserName = 1;
	public final static byte Param_Password = 2;
	public final static byte Param_Hiden = 3;
}
