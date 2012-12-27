package org.sdu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

/**
 * PasswordBox class implements a PasswordField with description.
 * 
 * @version 0.1 rev 8002 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class PasswordBox extends JPasswordField
{
	private static final long serialVersionUID = 1L;
	
	private String desc;
	private RectBorder border;
	private BorderFlashAnimation aniFlash;
	
	/**
	 * Initialize a PasswordBox object.
	 */
	public PasswordBox()
	{
		super();
		this.desc = "";
		initialize();
	}
	
	/**
	 * Initialize a PasswordBox object with specified description.
	 * 
	 * @param desc
	 */
	public PasswordBox(String desc)
	{
		super();
		this.desc = desc;
		initialize();
	}
	
	/**
	 * Initialize a PasswordBox object with specified description and max length.
	 * 
	 * @param desc
	 * @param maxLength
	 */
	public PasswordBox(String desc, int maxLength)
	{
		super(maxLength);
		this.desc = desc;
		initialize();
	}
	
	private void initialize()
	{
		border = new RectBorder(UIHelper.darkColor);
		super.setBorder(border);
		super.setFont((Font)UIHelper.getResource("ui.font.text"));
		setForeground(Color.GRAY);
		setEchoChar((char)0);
		setText(desc);

		aniFlash = new BorderFlashAnimation(this, border);
		aniFlash.setColor(UIHelper.darkColor);
		
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(getPassword().length == desc.length() && desc.equals(new String(getPassword()))) {
					setText("");
					setEchoChar('*');
					setForeground(Color.BLACK);
				}
				border.setColor(UIHelper.lightColor);
				aniFlash.setColor(UIHelper.lightColor);
				repaint();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(getPassword().length == 0) {
					setText(desc);
					setEchoChar((char)0);
					setForeground(Color.GRAY);
				}
				border.setColor(UIHelper.darkColor);
				aniFlash.setColor(UIHelper.darkColor);
				repaint();
			}
		});
	}
	
	/**
	 * Set PasswordBox description.
	 * 
	 * @param desc
	 */
	public void setDescription(String desc)
	{
		this.desc = desc;
	}
	
	/**
	 * Get PasswordBox description.
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return this.desc;
	}
	
	/**
	 * Show wrong-password animation.
	 */
	public void onWrongPassword()
	{
		aniFlash.start();
	}
}