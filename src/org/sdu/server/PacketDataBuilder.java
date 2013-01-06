package org.sdu.server;

import java.nio.ByteBuffer;

import org.sdu.net.Packet;


/**
 * 
 * */
public class PacketDataBuilder {
	ByteBuffer DataArr;
	public PacketDataBuilder(){
		DataArr = ByteBuffer.allocate(0xffff);
		//DataArr.limit(0);
	}
	/**
	 * Set the Main Flag,subsidiary Flag,First Instruction and Second Instruction
	 * @param list
	 */
	public void SetData(byte...list){
		DataArr.put(list,0,list.length);
	}
	/**
	 * Set the parameter for Byte
	 * @param list
	 */
	public void SetParamB(byte...list){
		DataArr.put((byte) 0x05);
		DataArr.put((byte) ((list.length >> 8)&0xff));
		DataArr.put((byte) ((list.length)&0xff));
		DataArr.put(list);
	}
	/**
	 * Set the parameter for String list
	 * @param tmp
	 */
	public void SetParamS(String tmp){
		DataArr.put((byte) 0x05);
		DataArr.put((byte) ((tmp.getBytes().length >> 8)&0xff));
		DataArr.put((byte) ((tmp.getBytes().length)&0xff));
		DataArr.put(tmp.getBytes());
	}
	/**
	 * return the Data of Sending Packet
	 * @return
	 */
	public Packet GetData(){
		DataArr.flip();
		return new Packet(DataArr);
	}
}
