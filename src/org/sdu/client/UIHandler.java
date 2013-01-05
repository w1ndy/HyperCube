package org.sdu.client;

import org.sdu.net.SessionHandler;
import org.sdu.util.DebugFramework;

/**
 * UIHandler interface handles UI events.
 * 
 * @version 0.1 rev 8002 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public abstract class UIHandler extends SessionHandler
{
	private EventDispatcher dispatcher = null;
	
	/**
	 * Notified when attaching to a UI.
	 */
	public abstract void onAttach(final ClientUI ui);
	
	/**
	 * Notified when detaching from a UI.
	 */
	public abstract void onDetach(final ClientUI ui);
	
	/**
	 * Notified when ui is closing.
	 */
	public abstract void onClosing(final ClientUI ui);
	
	/**
	 * Call by dispatcher to set an instance of dispatcher.
	 */
	public void setDispatcher(EventDispatcher d)
	{
		dispatcher = d;
	}
	
	/**
	 * Get parent dispatcher.
	 */
	public EventDispatcher getDispatcher()
	{
		return dispatcher;
	}
	
	/**
	 * Get UI frame.
	 */
	public ClientFrame getFrame()
	{
		return (dispatcher != null) ? dispatcher.getUI().getFrame() : null;
	}
	
	/**
	 * Output log string.
	 */
	public void log(String str)
	{
		DebugFramework.getFramework().print(str);
	}
}