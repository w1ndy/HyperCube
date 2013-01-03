package org.sdu.client;

import org.sdu.ui.UIHelper;
import org.sdu.util.DebugFramework;

/**
 * Client class implements a student client.
 * 
 * @version 0.1 rev 8005 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class Client
{	
	private EventDispatcher dispatcher;
	
	/**
	 * Initialize Client object.
	 */
	public Client()
	{
		dispatcher = new EventDispatcher();
		dispatcher.attach(new LoginUIHandler(dispatcher));
	}
	
	/**
	 * Main entrance of the application.
	 */
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
