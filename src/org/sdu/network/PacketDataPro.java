package org.sdu.network;

public class PacketDataPro {
	private byte[] opt;
	public PacketDataPro(IncomingPacket indata){
		opt = indata.getData();
	}
	public byte GetDelimiter(){
		return opt[Val.loc_delimiter];
	}
	public byte[] GetDadding(){
		byte[] temp = null;
		for (int i=1;i>=Val.length_Dadding;i++)
		{
			temp[i] = opt[i+3];
		}
		return temp;
	}
	public int GetLength(){
		return (opt[Val.loc_Length] << 8)+opt[Val.loc_Length+1];
	}
	public int GetFInst(){
		return opt[Val.loc_Inst];
	}
	public int GetSInst(){
		return opt[Val.loc_Inst+1];
	}
	public String[] GetParam(){
		int count = 1;
		int point = Val.loc_Inst+Val.length_Inst;
		int param_length;
		String[] tmp = null;
		while (point >= this.GetLength());{
			if (opt[point] == Val.Param_Head) {
				param_length = (opt[++point] << 8)+opt[++point];
				tmp[count] = opt.toString().substring(point, point+param_length);
				count++;
				point=point+param_length-1;
			}
		}
		tmp[0] = ""+count;
		return tmp;
	}
}
