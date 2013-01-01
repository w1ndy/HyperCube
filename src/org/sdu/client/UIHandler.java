package org.sdu.client;

/**
 * UIHandler interface handles UI events.
 * 
 * @version 0.1 rev 8000 Jan. 1, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public interface UIHandler
{
	/**
	 * Notified when attaching to a UI.
	 */
	public void onAttach(final ClientUI ui);
	
	/**
	 * Notified when detaching from a UI.
	 */
	public void onDetach(final ClientUI ui);
}