package org.sdu.test;

import org.sdu.net.NetworkServer;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.net.SessionHandler;
import org.sdu.util.DebugFramework;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * A echo server test.
 * 
 * @version 0.1 rev 8006 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ServerSimulator extends SessionHandler
{
	public static final int port = 21071;
	public static final DebugFramework debugger = DebugFramework.getFramework();
	
	private NetworkServer server;
	
	public static void main(String[] args)
	{
		debugger.setLogFileName("ServerSimulator.log");
		new ServerSimulator();
	}

	/**
	 * Initialize echo server.
	 */
	public ServerSimulator()
	{
		server = new NetworkServer(this, port);
		try {
			server.start(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAcceptingSession(SocketChannel c)
	{
		return true;
	}

	@Override
	public void onSessionAccepted(Session s)
	{
		try {
			debugger.print("Session from " + s.getChannel().getRemoteAddress() + " accepted.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSessionClosed(Session s)
	{
		try {
			debugger.print("Session from " + s.getChannel().getRemoteAddress() + " closed.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUnregisteredSession(SocketChannel c)
	{
		try {
			debugger.print("Session from " + c.getRemoteAddress() + " failed to register.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		try {
			ByteBuffer buf;
			debugger.print("Received " + p.getLength() + " bytes from client " + s.getChannel().getRemoteAddress() + ".");
			
			switch(p.getData().get(0))
			{
			case 0x01:
				if(p.getData().get(1) == 0x01) {
					buf = ByteBuffer.allocate(10);
					buf.put(new byte[] { 0, 0, 1, 1, 5, 0, 3, 66, 67, 65 });
					buf.flip();
					s.post(new Packet(buf));
				}
				break;
			case 0x03:
				if(p.getData().get(1) == 0x06) {
					byte[] addr = new byte[] { 0, 0, 3, 5,
							5, 0, 6, 'A', 'r', 't', 'h', 'a', 's',
							5, 0, 1, 1,
							5, 0, 0x0c, 'J', 'u', 's', 't', ' ', 'a', ' ', 't', 'e', 's', 't', '.',
							5, 0, 0x16, 'h', 't', 't', 'p', ':', '/', '/', '1', '2', '7', '.', '0', '.', '0', '.', '1', '/', '1', '.', 'j', 'p', 'g'};
					buf = ByteBuffer.allocate(addr.length);
					buf.put(addr);
					buf.flip();
					s.post(new Packet(buf));
				}
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnected(Session s) {}

	@Override
	public void onConnectFailure(SocketChannel c) {}
}
