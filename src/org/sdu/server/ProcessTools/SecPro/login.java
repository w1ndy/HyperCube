package org.sdu.server.ProcessTools.SecPro;

import org.sdu.network.PacketDataBuilder;
import org.sdu.network.PacketDataPro;
import org.sdu.network.Val;

public class login {
	public static byte[] Push(byte c1, byte c2, String[] ProD) {
		switch (c2) {
		case 0x01: {
			if (ProD[0] == Val.Version) {
				PacketDataBuilder BuPacket = new PacketDataBuilder();
				BuPacket.SetHead((byte) 0X01,(byte) 0X01, c1, c2);
				BuPacket.SetParamN((byte)0x00);
				return BuPacket.GetData();
			} else {
				PacketDataBuilder BuPacket = new PacketDataBuilder();
				BuPacket.SetHead((byte) 0X01, (byte) 0X01, c1, c2);
				BuPacket.SetParamN((byte) 0x01);
				BuPacket.SetParamN((byte) 0x01);
				return BuPacket.GetData();
			}

		}
		}
		// TODO return push data here.
		return null;
	}
}
