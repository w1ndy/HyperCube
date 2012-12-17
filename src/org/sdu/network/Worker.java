package org.sdu.network;

import org.sdu.util.DebugFramework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.PriorityQueue;

/**
 * Worker class creates a thread to pass the data in / out over a socket,
 * separating every incoming packet. 
 * 
 * @version 0.1 rev 8001 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Worker extends Observable implements Runnable
{
	private PriorityQueue<Packet> postQueue;
	
	private boolean 		closed = false;
	private InputStream 	istream;
	private OutputStream 	ostream;
	private Socket			socket;
	private DebugFramework 	debugger;
	
	/**
	 * Specify the checking interval (millisecond) for each loop.
	 */
	public final static int checkFrequency = 250;
	
	/**
	 * Create a worker thread automatically.
	 * 
	 * @param s				The socket that worker thread will work on.
	 * @throws IOException
	 */
	public Worker(Socket s) throws IOException
	{
		postQueue = new PriorityQueue<Packet>();
		istream = s.getInputStream();
		ostream = s.getOutputStream();
		socket 	= s;
		
		socket.setKeepAlive(true);
		socket.setTcpNoDelay(true);
		
		(new Thread(this)).start();
	}

	/**
	 * Shutdown socket connection and the worker thread.
	 */
	public void shutdown()
	{
		if(closed) return ;
		closed = true;
		try {
			socket.close();
		} catch (IOException e) {}
		notify();
	}

	/**
	 * Add a packet to post queue.
	 * 
	 * @param p	Packet to post.
	 */
	public void addPacket(Packet p)
	{
		Packet packet = p.clone();
		synchronized(postQueue)
		{
			postQueue.offer(packet);
		}
		notify();
	}

	/**
	 * Wake up worker thread.
	 */
	public void forceActivate()
	{
		notify();
	}

	private Packet getNextOutcomingPacket()
	{
		synchronized(postQueue)
		{
			return postQueue.poll();
		}
	}
	
	/**
	 * Worker thread main loop, delivering and accepting data.
	 */
	@Override
	public void run() {
		Packet 	p;
		int 	len;
		byte 	len_a, len_b;
		byte[] 	header = new byte[3], data;
		
		while(!closed)
		{
			if(!closed) {
				// Extract to-be-posted packets from queue.
				while((p = getNextOutcomingPacket()) != null)
				{
					// Max packet length should not exceed 0xffff because
					// we use two byte to represent packet length.
					if(p.getData().length > 0xffff) {
						debugger.print("Packet too long: " + p.getData().length);
						continue;
					}
					
					// Save length into two bytes.
					len_a = (byte)((p.getData().length >> 8) & 0xff);
					len_b = (byte)(p.getData().length & 0xff);
					
					// Packet header format:
					//     	[Delimiter 1 byte]
					//		[Length 2 bytes]
					try {
						ostream.write(Session.delimiter);
						ostream.write(len_a);
						ostream.write(len_b);
						ostream.write(p.getData());
						ostream.flush();
					} catch (IOException e) {
						// Possibly socket is closed.
						debugger.print("Packet send failure: I/O error.");
						break;
					}
				}
			}
			
			if(!closed) {
				try {
					// Header requires 3 bytes.
					while(istream.available() >= 3) {
						len = istream.read(header, 0, 3);
						if(len < 0) break;
						
						if(header[0] == Session.delimiter) {
							// Read packet.
							len = ((int)header[1]) << 8 + (int)header[2];
							data = new byte[len];
							len = istream.read(data, 0, len);
							if(len < 0) break;
							notifyObservers(new IncomingPacket(data, true));
						}
					}
				} catch (IOException e) {
					// Possibly socket is closed.
					debugger.print("Packet recv failure: I/O error.");
				}
			}
			
			try {
				if(!closed) wait(checkFrequency);
			} catch (InterruptedException e) {}
		}
	}
}