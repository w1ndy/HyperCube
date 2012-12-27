package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * TextBox class implements a TextField with description.
 * 
 * @version 0.1 rev 8003 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class TextBox extends JTextField implements TranslucentComponent
{
	private static final long serialVersionUID = 1L;
	
	private String desc = "";
	private RectBorder border;
	private BorderFlashAnimation aniFlash;
	private float globalOpacity = 1.0f;
	
	/**
	 * Initialize a TextBox object.
	 */
	public TextBox()
	{
		super();
		initialize();
	}
	
	/**
	 * Initialize a TextBox object with specified description.
	 * 
	 * @param desc
	 */
	public TextBox(String desc)
	{
		super(desc);
		this.desc = desc;
		super.setForeground(Color.GRAY);
		initialize();
	}
	
	/**
	 * Initialize a TextBox object with specified description and maximum length.
	 * 
	 * @param desc
	 * @param maxLength
	 */
	public TextBox(String desc, int maxLength)
	{
		super(desc, maxLength);
		this.desc = desc;
		super.setForeground(Color.GRAY);
		initialize();
	}
	
	private void initialize()
	{
		border = new RectBorder(UIHelper.darkColor);
		super.setBorder(border);
		super.setFont((Font)UIHelper.getResource("ui.font.text"));
		setText(desc);
		setForeground(Color.GRAY);
		
		aniFlash = new BorderFlashAnimation(this, border);
		aniFlash.setColor(UIHelper.darkColor);
		
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(getText().equals(desc)) {
					setText("");
					setForeground(Color.BLACK);
				}
				border.setColor(UIHelper.lightColor);
				aniFlash.setColor(UIHelper.lightColor);
				repaint();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(getText().equals("")) {
					setText(desc);
					setForeground(Color.GRAY);
				}
				border.setColor(UIHelper.darkColor);
				aniFlash.setColor(UIHelper.darkColor);
				repaint();
			}
		});
	}
	
	/**
	 * Set TextBox description.
	 * 
	 * @param desc
	 */
	public void setDescription(String desc)
	{
		this.desc = desc;
	}
	
	/**
	 * Get TextBox description.
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return this.desc;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalOpacity));
		super.paintComponent(g);
	}
	
	/**
	 * On data fails show animation.
	 */
	public void onFailed()
	{
		aniFlash.start();
	}

	@Override
	public void setOpacity(float alpha) {
		globalOpacity = alpha;
		border.setOpacity(alpha);
	}

	@Override
	public float getOpacity() {
		return globalOpacity;
	}
}