package org.sdu.test;

import javax.swing.JFrame;

import org.sdu.ui.BasicFrame;
import org.sdu.ui.UIHelper;

public class BasicFrameTest
{
	public static void main(String[] args)
	{
		UIHelper.loadResource();
		BasicFrame frame = new BasicFrame("²âÊÔ´°¿Ú", "HyperCube");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(0, 0);
		frame.setVisible(true);
	}
}