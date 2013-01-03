package org.sdu.server;

import java.nio.ByteBuffer;


/**
 * 
 * */
public class PacketDataBuilder {
	ByteBuffer DataArr;
	public PacketDataBuilder(){
		DataArr = null;
	}
	public void SetData(byte...list){
		DataArr.put(list);
	}
	public void SetParamB(byte...list){
		DataArr.put((byte) 0x05);
		DataArr.put((byte) list.length);
		DataArr.put(list);
	}
	public void SetParamS(String tmp){
		DataArr.put((byte) 0x05);
		DataArr.put((byte) tmp.getBytes().length);
		DataArr.put(tmp.getBytes());
	}
}
