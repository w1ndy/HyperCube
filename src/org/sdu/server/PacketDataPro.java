package org.sdu.server;

import java.nio.ByteBuffer;

/**
 * 
 */
public class PacketDataPro {
	private ByteBuffer opt;
	public PacketDataPro(ByteBuffer indata){
		opt = indata;
	}
	public int GetLength(){
		return opt.capacity();
	}
	public int GetFInst(){
		return opt.get(0);
	}
	public int GetSInst(){
		return opt.get(1);
	}
	public String GetParam(){
		opt.position(2);
		int param_length;
		String tmp = null;
		while (opt.position() >= opt.limit());{
			if (opt.get(opt.position())== 0x05) {
				param_length = (opt.get(opt.position()+1) << 8)+opt.get(opt.position()+2);
				opt.position(opt.position()+3);
				opt.limit(opt.position()+param_length);
				tmp = opt.slice().toString();
				opt.position(opt.limit());
				opt.limit(opt.capacity());
				}
		}
		return tmp;
	}
}
