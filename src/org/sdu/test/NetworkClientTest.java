package org.sdu.test;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.sdu.net.NetworkClient;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.net.SessionHandler;
import org.sdu.util.DebugFramework;

/**
 * NetworkClientTest class is a unit test for NetworkClient class.
 * 
 * @version 0.1 rev 8000 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkClientTest extends SessionHandler
{
	private NetworkClient client;
	
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("NetworkClientTest.log");
		new NetworkClientTest();
	}
	
	public NetworkClientTest()
	{
		client = new NetworkClient();
		client.connect("127.0.0.1", 21071, this);
	}
	
	@Override
	public boolean onAcceptingSession(SocketChannel c)
	{
		return true;
	}

	@Override
	public void onSessionAccepted(Session s) {}

	@Override
	public void onSessionClosed(Session s)
	{
		System.out.println("Session closed.");
		client.shutdown();
	}

	@Override
	public void onUnregisteredSession(SocketChannel c) {}

	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		String str = new String(p.getDataArray());
		System.out.println("Recv (" + p.getLength() + " bytes): " + str);
	}

	@Override
	public void onConnected(Session s)
	{
		// Send hello, world to server.
		String str = "Hello, world";
		byte[] bytearr = str.getBytes();
		ByteBuffer buf = ByteBuffer.allocate(bytearr.length);
		buf.put(bytearr);
		buf.flip();
		Packet p = new Packet(buf);
		s.post(p);
	}

	@Override
	public void onConnectFailure(SocketChannel c)
	{
		System.out.println("Unable to connect to host!");
		client.shutdown();
	}
	
}