package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Observable;

/**
 * Session class allows servers or clients to post and receive the data
 * in a non-blocking way.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Session extends Observable implements Runnable
{
	private DebugFramework 	debugger;
	private Socket 			socket;

	/**
	 * Delimiter identifies the beginning of a packet.
	 */
	public final static byte delimiter = (byte) 0x02;
	
	/**
	 * Create a session on specified socket.
	 * The control of socket will be taken over.
	 * 
	 * @param s	Socket to create a session on
	 * @throws IOException
	 * @throws InvalidParameterException
	 */
	public Session(Socket s) throws IOException, InvalidParameterException
	{
		if(s == null)
			throw new InvalidParameterException();
		
		debugger 	= DebugFramework.getFramework();
		socket 		= s;
		
		debugger.print("Session begin: " + s.getInetAddress());
	}

	/**
	 * Post a packet in a non-blocking way.
	 * 
	 * @param p	Packet to be posted
	 * @return	If the operation is successful
	 */
	public boolean post(Packet p)
	{
		// TODO add a postman.
		return true;
	}
	
	/**
	 * Test if the connection is available
	 * 
	 * @return If socket is alive
	 */
	public boolean isAlive()
	{
		return !socket.isClosed();
	}
	
	/**
	 * Close the socket connection.
	 */
	public void close()
	{
		if(socket != null) {
			try {
				socket.close();
			} catch (Throwable t) {}
		}
	}

	@Override
	public void run()
	{
		InputStream istream;
		byte[] data;
		int length;
		
		byte b;
		
		try {
			istream = socket.getInputStream();
			while(!socket.isClosed()) {
				while((b = (byte) istream.read()) == delimiter) {
					length = (istream.read() << 4) + istream.read();
					data = new byte[length];
					istream.read(data, 0, length);
					IncomingPacket p = new IncomingPacket(socket, data);
					notifyObservers(p);
					debugger.print("" + super.countObservers() + " observer(s) notified.");
				}
				System.out.print(b);
			}
		} catch(Exception e) {
			if(socket.isClosed()) debugger.print("Session with " + socket.getInetAddress() + " finished.");
			else {
				debugger.print(e);
				debugger.print("Session with " + socket.getInetAddress() + " ended unexpectedly.");
			}
		}
		b = (byte) 0x02;
	}
}