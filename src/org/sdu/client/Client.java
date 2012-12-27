package org.sdu.client;

import org.sdu.ui.UIHelper;
import org.sdu.util.DebugFramework;

/**
 * Client class implements a student client.
 * 
 * @version 0.1 rev 8003 Dec. 28, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Client
{	
	private ClientUI ui;
	private EventListener listener;
	
	/**
	 * Initialize Client object.
	 */
	public Client()
	{
		listener = new EventListener();
		ui = new ClientUI(listener);
	}
	
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("client.log");
		UIHelper.loadResource("art/resource.xml");
		try {
			new Client();
		} catch(Exception e) {
			DebugFramework.getFramework().print("Fatal error: " + e);
		}
	}
}
