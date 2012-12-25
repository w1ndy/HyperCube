package org.sdu.test;

import org.sdu.ui.UIHelper;
import org.sdu.ui.LoginFrame;
import org.sdu.util.DebugFramework;

import javax.swing.JFrame;

/**
 * A unit test for LoginFrame class and some other components.
 * 
 * @version 0.1 rev 8002 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class LoginFrameTest
{
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("LoginFrameTest.log");
		UIHelper.loadResource("art/resource.xml");
		LoginFrame frame = new LoginFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}