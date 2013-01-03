package org.sdu.net;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.sdu.util.DebugFramework;

/**
 * SessionHandler class is a super class which handles network events
 * implemented with nio.
 * 
 * @version 0.1 rev 8004 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public abstract class SessionHandler implements ReadQueueHandler
{
	private static final int MaximumWorkerCount = 64;
	private static final int ReadBufferSize = 2048;
	private static final int MaximumReadBufferCount = 32;
	
	private ExecutorService workerExecutor;
	private ConcurrentLinkedQueue<ByteBuffer> readBufferQueue;
	private int numBufferCreated;
	
	/**
	 * Initialize a SessionHandler object.
	 */
	public SessionHandler()
	{
		workerExecutor = Executors.newFixedThreadPool(MaximumWorkerCount);
		readBufferQueue = new ConcurrentLinkedQueue<ByteBuffer>();
		readBufferQueue.offer(ByteBuffer.allocateDirect(ReadBufferSize));
		numBufferCreated = 1;
	}
	
	/**
	 * Notified before accepting a session, returning false will block the
	 * connection.
	 */
	public abstract boolean onAcceptingSession(SocketChannel c);
	
	/**
	 * Notified when a session is successfully accepted.
	 */
	public abstract void onSessionAccepted(Session s);
	
	/**
	 * Notified when a session is closed.
	 */
	public abstract void onSessionClosed(Session s);
	
	/**
	 * Notified when a session is refused.
	 */
	public abstract void onUnregisteredSession(SocketChannel c);
	
	/**
	 * Notified when a session receives a packet.
	 */
	public abstract void onPacketReceived(Session s, Packet p);
	
	/**
	 * Notified when connected to host. (Client use)
	 */
	public abstract void onConnected(Session s);
	
	/**
	 * Notified when failed to connect. (Client use)
	 */
	public abstract void onConnectFailure(SocketChannel c);
	
	/**
	 * Notified when a packet is resolved by ReadQueue.
	 */
	@Override
	public void onPacketResolved(final Session s, final Packet p)
	{
		workerExecutor.execute(new Runnable() {
			@Override
			public void run() {
				onPacketReceived(s, p);
			}
		});
	}
	
	/**
	 * Notified when incoming data available. (Internal Event)
	 */
	public void onReadEvent(final Dispatcher d, final Session s)
	{
		try {
			int k;
			
			// register self with ReadQueue
			s.getReadQueue().associateHandler(s, this);
			
			do {
				// Get a vacant buffer.
				ByteBuffer buf = readBufferQueue.poll();
				if(buf == null) {
					if(numBufferCreated < MaximumReadBufferCount) {
						++numBufferCreated;
						readBufferQueue.offer(ByteBuffer.allocateDirect(ReadBufferSize));
						buf = readBufferQueue.poll();
					} else {
						buf = ByteBuffer.allocate(ReadBufferSize);
					}
				}
				
				// Read data into buffer.
				buf.clear();
				k = s.getChannel().read(buf);
				if(k > 0) {
					// push into ReadQueue
					buf.flip();
					s.getReadQueue().resolve(readBufferQueue, buf);
				} else if(k == 0) {
					readBufferQueue.offer(buf);
				} else {
					d.unregister(s);
					workerExecutor.shutdown();
					s.getReadQueue().releaseExecutor();
					onSessionClosed(s);
				}
			} while(k > 0);
		} catch(Exception e) {
			DebugFramework.getFramework().print("Failed to read: " + e);
		}
	}
	
	/**
	 * Notified when write buffer is ready.
	 */
	public void onWriteEvent(Dispatcher d, Session s)
	{
		try {
			Packet p = s.getWriteQueue().pop();
			if(p != null) {
				s.getChannel().write(p.getData());
				if(s.getWriteQueue().isEmpty()) {	
					d.endWrite(s);
				}
			}
		} catch(Exception e) {
			DebugFramework.getFramework().print("Failed to write: " + e);
		}
	}
}