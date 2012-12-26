package org.sdu.network;

public class PacketDataBuilder {
	byte[] DataArr;
	public PacketDataBuilder(){
		DataArr = null;
	}
	public void SetDelimiter(int delimiter){
		DataArr[Val.loc_delimiter] = (byte) delimiter;
	}
	public void SetDadding(byte[] Dadding){
		for (int i=Val.loc_Dadding;i<=Val.loc_Dadding+Val.length_Dadding;i++)
			DataArr[i]=Dadding[i-Val.loc_Dadding+1];
	}
	public void Inst 
}
