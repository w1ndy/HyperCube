package org.sdu.net;

/**
 * ReadQueueHandler interface handles events from ReadQueue.
 *
 * @version 0.1 rev 8000 Dec. 31, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public interface ReadQueueHandler
{
	/**
	 * Notified when a packet is resolved by ReadQueue.
	 */
	public void onPacketResolved(final Session s, final Packet p);
}
