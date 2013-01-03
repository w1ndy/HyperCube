package org.sdu.net;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ReadQueue class resolves byte data stream into packet format.
 * 
 * @version 0.1 rev 8002 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ReadQueue
{
	private ReadQueueHandler handler;
	private Session session;

	private ByteBuffer currentBuffer;
	private ExecutorService readerExecutor;
	private int headerCompletence, lengthPacket, leftBytes;
	
	/**
	 * Initialize a ReadQueue object.
	 */
	public ReadQueue()
	{
		handler = null;
		session = null;
		
		currentBuffer = null;
		readerExecutor = Executors.newFixedThreadPool(1);
		
		headerCompletence = 0;
		lengthPacket = 0;
		leftBytes = 0;
	}
	
	/**
	 * Associate a handler with this queue.
	 */
	public void associateHandler(Session s, ReadQueueHandler h)
	{
		handler = h;
		session = s;
	}
	
	/**
	 * Release worker executor.
	 */
	public void releaseExecutor()
	{
		if(readerExecutor != null)
			readerExecutor.shutdown();
	}
	
	/**
	 * Resolve byte data stream and recycle the buffer.
	 */
	public void resolve(final ConcurrentLinkedQueue<ByteBuffer> readBufferQueue, final ByteBuffer buf)
	{
		readerExecutor.execute(new Runnable() {
			@Override
			public void run() {
				while(buf.hasRemaining()) {
					// In case that header is truncated.
					switch(headerCompletence)
					{
					case 0:
						if(buf.get() == HeaderPacket.PacketDelimiter) {
							switch(buf.remaining())
							{
							case 0:
								headerCompletence = 1;
								return ;
							case 1:
								headerCompletence = 2;
								lengthPacket = buf.get() << 8;
								return ;
							default:
								headerCompletence = 3;
								lengthPacket = (buf.get() << 8) + buf.get();
								leftBytes = lengthPacket;
								break;
							}
						}
						break;
					case 1:
						if(buf.hasRemaining()) {
							lengthPacket = buf.get() << 8;
							headerCompletence = 2;
							if(buf.hasRemaining()) {
								headerCompletence = 3;
								lengthPacket += buf.get();
								leftBytes = lengthPacket;
							}
						}
						break;
					case 2:
						if(buf.hasRemaining()) {
							headerCompletence = 3;
							lengthPacket += buf.get();
							leftBytes = lengthPacket;
						}
						break;
					}
					
					if(headerCompletence == 3) {
						if(currentBuffer == null) 
							currentBuffer = ByteBuffer.allocate(lengthPacket);
						
						if(leftBytes > buf.remaining()) {
							leftBytes -= buf.remaining();
							currentBuffer.put(buf);
						} else {
							int old_limit = buf.limit();
							buf.limit(buf.position() + leftBytes);
							currentBuffer.put(buf);
							buf.limit(old_limit);
							currentBuffer.flip();
							if(handler != null)
								handler.onPacketResolved(session, new Packet(currentBuffer));
							currentBuffer = null;
							
							headerCompletence = 0;
							lengthPacket = 0;
							leftBytes = 0;
						}
					}
				}
				readBufferQueue.offer(buf);
			}
		});
	}
}