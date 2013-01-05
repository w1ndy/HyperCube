package org.sdu.client;

import java.nio.channels.SocketChannel;

import org.sdu.net.Packet;
import org.sdu.net.Session;

/**
 * MainUIHandler class handles UI events in main frame.
 * 
 * @version 0.1 rev 8000 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class MainUIHandler extends UIHandler
{

	@Override
	public void onAttach(ClientUI ui)
	{
		getFrame().startExpanding(680);
		// TODO Initialize main frame.
	}

	@Override
	public void onDetach(ClientUI ui)
	{
		// TODO Free resources while detaching.
	}

	@Override
	public void onClosing(ClientUI ui)
	{
		// TODO On frame closing, usually hide it.
		// dispose frame in debug environment.
		getFrame().dispose();
	}

	// Skip server-specified call backs
	@Override
	public boolean onAcceptingSession(SocketChannel c)
	{
		return true;
	}

	// Skip server-specified call backs
	@Override
	public void onSessionAccepted(Session s) {}

	// Skip server-specified call backs
	@Override
	public void onUnregisteredSession(SocketChannel c) {}
	
	@Override
	public void onSessionClosed(Session s)
	{
		// TODO Session closed abruptly.
	}


	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		// TODO Process network events.
	}

	@Override
	public void onConnected(Session s)
	{
		// TODO If reconnected to server.
	}

	@Override
	public void onConnectFailure(SocketChannel c)
	{
		// TODO If reconnecting failed.
	}
}