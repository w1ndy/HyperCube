package org.sdu.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.sdu.util.DebugFramework;

/**
 * NetworkServer class listens on a specified port, accepting and processing
 * the incoming connections.
 * 
 * @version 0.1 rev 8003 Jan. 17, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class NetworkServer implements Runnable
{
	private static final int DispatcherPoolDefaultSize = 16;
	
	private ServerSocketChannel channelServer;
	private DispatcherPool pool;
	private SessionHandler handler;
	private DebugFramework debug;
	
	private int port;
	private boolean isRunning;
	
	/**
	 * Initialize a NetworkServer object listening on given port with a
	 * SessionHandler receiving events.
	 * @throws IOException 
	 */
	public NetworkServer(SessionHandler handler, int port)
	{
		this.handler = handler;
		this.port = port;
		this.pool = new DispatcherPool(DispatcherPoolDefaultSize);
		this.isRunning = false;
		this.debug = DebugFramework.getFramework();
	}
	
	/**
	 * Start the server in blocking or non-blocking mode.
	 * @throws IOException 
	 */
	public boolean start(boolean isBlocking) throws IOException
	{
		if(isRunning) {
			debug.print("Another instance is running.");
			return false;
		}

		channelServer = ServerSocketChannel.open();
		channelServer.configureBlocking(true);
		channelServer.bind(new InetSocketAddress(port));
		
		isRunning = true;
		if(isBlocking)
			run();
		else
			(new Thread(this)).start();
		return true;
	}
	
	/**
	 * Stop the server.
	 */
	public void stop()
	{
		if(isRunning) {
			try {
				isRunning = false;
				channelServer.close();
			} catch (Exception e) {
				System.out.println("Error while closing server: " + e);
			}
		}
	}
	
	/**
	 * Whether the server is running.
	 */
	public boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * Accept incoming connections and process them.
	 */
	@Override
	public void run() {
		try {
			pool.start(handler);
			while(isRunning) {
				SocketChannel c = channelServer.accept();
				if(!handler.onAcceptingSession(c)) {
					c.close();
					handler.onUnregisteredSession(c);
				} else {
					Session s = pool.nextDispatcher().register(c);
					if(s == null)
						handler.onUnregisteredSession(c);
					else
						handler.onSessionAccepted(s);
				}
			}
		} catch(Exception e) {
			System.out.println("Unexpected fatal error: " + e);
			isRunning = false;
			try {
				channelServer.close();
			} catch (Exception e1) {}
		} finally {
			try {
				if(pool.isRunning())
					pool.stop();
				handler.onShutdown();
			} catch (Exception e) {
				System.out.println("Unexpected fatal error: " + e);
			}
		}
	}
	
	/**
	 * Get the port on which server is running.
	 * @return
	 */
	public int getPort()
	{
		return port;
	}
	
	/**
	 * Get event handler.
	 */
	public SessionHandler getHandler()
	{
		return handler;
	}
}
