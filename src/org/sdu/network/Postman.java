package org.sdu.network;

import java.io.OutputStream;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Postman class is static class helper sending packet asynchronously.
 * 
 * @version 0.1 rev 8000 Dec. 18, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Postman implements Runnable
{
	/**
	 * Maximum thread the postman is allowed to use.
	 */
	public static final int postThreadMaxCount = 10;
	
	private static ExecutorService pool = null;
	private static PriorityQueue<Packet> sendQueue = null;
	
	/**
	 * Initialize the postman.
	 */
	public synchronized static void initialize()
	{
		if(pool == null)
			pool = Executors.newFixedThreadPool(postThreadMaxCount);
		if(sendQueue == null)
			sendQueue = new PriorityQueue<Packet>();
	}
	
	/**
	 * Release the postman.
	 */
	public static void release()
	{
		try {
			synchronized(pool) {
				pool.shutdown();
				pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
				pool.shutdownNow();
				pool = null;
			}
			synchronized(sendQueue) {
				sendQueue = null;
			}
		} catch(Throwable t) {}
	}
	
	/**
	 * Send a packet.
	 * 
	 * @param p		Packet to be sent.
	 */
	public static void postPacket(Packet p)
	{
		try {
			synchronized(sendQueue) {
				sendQueue.offer(p);
			}
			synchronized(pool) {
				pool.execute(new Postman());
			}
		} catch(Throwable t) {}
	}

	/**
	 * Run the posting task.
	 */
	@Override
	public void run() {
		try {
			synchronized(sendQueue) {
				Packet p = sendQueue.poll();
				OutputStream ostream = p.getSocket().getOutputStream();
				
				// pack the packet according to format.
				ostream.write(Session.delimiter);
				ostream.write((p.getData().length >> 4) & 0xff);
				ostream.write(p.getData().length & 0xff);
				ostream.write(p.getData());
			}
		} catch(Throwable t) {}
	}
}