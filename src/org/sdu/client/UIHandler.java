package org.sdu.client;

import org.sdu.net.NetworkClient;
import org.sdu.net.Packet;
import org.sdu.net.Session;

/**
 * UIHandler interface handles UI events.
 * 
 * @version 0.1 rev 8001 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public interface UIHandler
{
	/**
	 * Notified when attaching to a UI.
	 */
	public void onAttach(final NetworkClient client, final ClientUI ui);
	
	/**
	 * Notified when detaching from a UI.
	 */
	public void onDetach(final NetworkClient client, final ClientUI ui);
	
	/**
	 * Notified when ui is closing.
	 */
	public void onClosing(final ClientUI ui);
	
	/**
	 * Notified when connected.
	 */
	public void onConnected(Session s);
	
	/**
	 * Notified when failed to connect.
	 */
	public void onConnectFailure();
	
	/**
	 * Notified when data is available.
	 */
	public void onNetworkData(Session s, Packet p);
}