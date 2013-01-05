package org.sdu.client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.channels.SocketChannel;

import org.sdu.net.NetworkClient;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.net.SessionHandler;

/**
 * EventDispatcher class coordinates events between network and UI.
 * 
 * @version 0.1 rev 8002 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class EventDispatcher extends SessionHandler
{
	private UIHandler handler;
	private ClientUI ui;
	private NetworkClient client;
	private Session session;
	
	private WindowListener exitListener = new WindowListener() {
		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {
			client.shutdown();
			System.exit(0);
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			if(handler != null) {
				handler.onClosing(ui);
			}
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
	};
	
	/**
	 * Initialize a EventDispatcher object.
	 */
	public EventDispatcher()
	{
		ui = new ClientUI();
		ui.getFrame().addWindowListener(exitListener);
		client = new NetworkClient();
		handler = null;
	}
	
	/**
	 * Attach a handler to UI.
	 */
	public void attach(UIHandler handler)
	{
		if(this.handler != null)
			this.handler.onDetach(ui);
		this.handler = handler;
		handler.setDispatcher(this);
		handler.onAttach(ui);
	}
	
	/**
	 * Detach a handler from UI.
	 */
	public void deattach()
	{
		if(handler != null) {
			handler.onDetach(ui);
			handler = null;
		}
	}

	/**
	 * Connect to a server.
	 */
	public boolean connect(String hostname, int port)
	{
		return client.connect(hostname, port, this);
	}
	
	/**
	 * Disconnect from a server.
	 */
	public void disconnect()
	{
		if(session != null) {
			session.close();
		}
	}
	
	/**
	 * Get current session.
	 */
	public Session getSession()
	{
		return session;
	}
	
	/**
	 * Get current UI.
	 */
	public ClientUI getUI()
	{
		return ui;
	}
	
	// Skip server-specified call backs.
	@Override
	public boolean onAcceptingSession(SocketChannel c) {
		return true;
	}

	// Skip server-specified call backs.
	@Override
	public void onSessionAccepted(Session s) {}

	// Skip server-specified call backs.
	@Override
	public void onUnregisteredSession(SocketChannel c) {}
	
	// Redirect messages to handler.
	@Override
	public void onSessionClosed(Session s)
	{
		if(handler != null)
			handler.onSessionClosed(s);
	}

	// Redirect messages to handler.
	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		if(handler != null)
			handler.onPacketReceived(s, p);
	}

	// Redirect messages to handler.
	@Override
	public void onConnected(Session s)
	{
		if(handler != null)
			handler.onConnected(s);
	}

	// Redirect messages to handler.
	@Override
	public void onConnectFailure(SocketChannel c)
	{
		if(handler != null)
			handler.onConnectFailure(c);
	}
}