package org.sdu.test;

import javax.swing.JFrame;

import org.sdu.ui.BasicFrame;
import org.sdu.ui.UIHelper;

/**
 * A unit test for BasicFrame class and some other components.
 * 
 * @version 0.1 rev 8000 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class BasicFrameTest
{
	public static void main(String[] args)
	{
		UIHelper.loadResource("art/resource.xml");
		BasicFrame frame = new BasicFrame("测试窗口", "HyperCube");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(0, 0);
		frame.setVisible(true);
	}
}
