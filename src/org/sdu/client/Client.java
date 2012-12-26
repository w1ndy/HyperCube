package org.sdu.client;

import org.sdu.ui.UIHelper;
import org.sdu.util.DebugFramework;

/**
 * Client class implements a student client.
 * 
 * @version 0.1 rev 8001 Dec. 26, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Client
{
	private ClientUI ui;
	
	/**
	 * Initialize Client object.
	 */
	public Client()
	{
		ui = new ClientUI();
	}
	
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("client.log");
		UIHelper.loadResource("art/resource.xml");
		new Client();
	}
}
