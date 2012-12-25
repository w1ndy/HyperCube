package org.sdu.client;

/**
 * Client class implements a student client.
 * 
 * @version 0.1 rev 8000 Dec. 25, 2012.
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
		new Client();
	}
}
