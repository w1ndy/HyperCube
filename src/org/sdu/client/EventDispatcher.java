package org.sdu.client;

/**
 * EventDispatcher class coordinates events between network and UI.
 * 
 * @version 0.1 rev 8000 Jan. 1, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class EventDispatcher
{
	private UIHandler handler;
	private ClientUI ui;
	
	/**
	 * Initialize a EventDispatcher object.
	 */
	public EventDispatcher()
	{
		ui = new ClientUI();
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
		handler.onAttach(ui);
	}
	
	/**
	 * Detach a handler from UI.
	 */
	public void deattach()
	{
		if(handler != null)
			handler.onDetach(ui);
	}
}