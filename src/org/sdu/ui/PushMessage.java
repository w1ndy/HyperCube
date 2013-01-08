package org.sdu.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

/**
 * PushMessage is a push notification component.
 * 
 * @version 0.1 rev 8001 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PushMessage extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private boolean isActive = false;
	private String title = null;
	private String content = null;
	
	public PushMessage()
	{
		super();
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				isActive = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				isActive = false;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(isActive)
			g.drawImage((Image)UIHelper.getResource("ui.main.active"), 0, 0, null);
		else
			g.drawImage((Image)UIHelper.getResource("ui.main.inactive"), 0, 0, null);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont((Font)UIHelper.getResource("ui.font.msgtitle"));
		if(title == null)
			g.drawString("无标题", 20, 26);
		else
			g.drawString(title, 20, 26);
		g.setFont((Font)UIHelper.getResource("ui.font.text"));
		if(content == null)
			g.drawString("无内容", 21, 46);
		else
			g.drawString(content, 21, 46);
		g.setColor(UIHelper.darkColor);
		g.drawString("更多内容", 19, 75);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}