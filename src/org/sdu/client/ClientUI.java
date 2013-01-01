package org.sdu.client;

import java.awt.Image;

import javax.swing.JFrame;

import org.sdu.ui.ComponentFadeAnimation;
import org.sdu.ui.UIHelper;

/**
 * ClientUI class implements a user interface of student user.
 * 
 * @version 0.1 rev 8008 Jan. 1, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientUI
{
	private ClientFrame frame;
	private ComponentFadeAnimation fadeAnimation;
	
	/**
	 * Initialize ClientUI object.
	 */
	public ClientUI()
	{
		frame = new ClientFrame();
		frame.setIconImage((Image)UIHelper.getResource("ui.common.icon"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fadeAnimation = new ComponentFadeAnimation(frame);
	}
	
	/**
	 * Start progress indicator.
	 */
	public void startProgressBar()
	{
		frame.startProgressBar();
	}
	
	/**
	 * Stop progress indicator.
	 */
	public void stopProgressBar()
	{
		frame.stopProgressBar();
	}
	
	/**
	 * Get client's frame.
	 */
	public ClientFrame getFrame()
	{
		return frame;
	}
	
	/**
	 * Get fade animator.
	 */
	public ComponentFadeAnimation getFader()
	{
		return fadeAnimation;
	}
}
