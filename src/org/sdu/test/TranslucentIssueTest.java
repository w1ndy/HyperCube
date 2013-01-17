package org.sdu.test;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class TranslucentIssueTest extends JFrame
{
	public static void main(String[] args)
	{
		(new TranslucentIssueTest()).setVisible(true);
	}
	
	public TranslucentIssueTest()
	{
		super();
		setUndecorated(true);
		setLayout(new FlowLayout());
		setSize(300, 300);
		AWTUtilities.setWindowOpaque(this, false);
		
		JTextField box = new JTextField();
		//box.setBounds(30, 150, 100, 25);
		add(box);
	}
}