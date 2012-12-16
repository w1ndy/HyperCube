package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Observer;

/**
 * Session class allows servers or clients to post and receive the data
 * in a non-blocking way.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Session
{
	private DebugFramework 	debugger;
	private Socket 			socket;
	private Worker			worker;

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
		
		debugger = DebugFramework.getFramework();
		socket = s;
		worker = new Worker(socket);
		
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
		if(worker == null) return false;
		worker.addPacket(p);
		return true;
	}
	
	/**
	 * Add a observer which observes incoming packet.
	 * 
	 * @param o	Observer
	 * @return	If the operation is successful
	 */
	public boolean addIncomingPacketObserver(Observer o)
	{
		if(worker == null) return false;
		worker.addObserver(o);
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
		worker.shutdown();
		worker = null;
	}
}