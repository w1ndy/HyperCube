package org.sdu.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * PushMessage is a push notification component.
 * 
 * @version 0.1 rev 8001 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PushMessage extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private boolean isUnread = true;
	private boolean isExpanded = false;
	private String title = null;
	private String content = null;
	private String omitted = null;
	private FontMetrics fontMetrics = null;
	private Image imageActive, imageInactive;
	private int currentHeight, contentHeight;
	
	Timer timerExpand = new Timer(25, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentHeight += 25;
			if(currentHeight > contentHeight) {
				currentHeight = contentHeight;
				timerExpand.stop();
			}
			setPreferredSize(new Dimension(280, currentHeight));
			getParent().invalidate();
		}
	});
	
	Timer timerCollapse = new Timer(25, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentHeight -= 25;
			if(currentHeight < 90) {
				currentHeight = 90;
				timerCollapse.stop();
				isExpanded = false;
			}
			setPreferredSize(new Dimension(280, currentHeight));
			getParent().invalidate();
		}
	});
	
	public PushMessage()
	{
		super();
		initialize();
	}
	
	public PushMessage(String title, String content)
	{
		super();
		initialize();
		setTitle(title);
		setContent(content);
	}
	
	private void initialize()
	{
		setPreferredSize(new Dimension(280, 90));
		fontMetrics = this.getFontMetrics((Font)UIHelper.getResource("ui.font.text"));
		imageActive = (Image)UIHelper.getResource("ui.main.active");
		imageInactive = (Image)UIHelper.getResource("ui.main.inactive");
		currentHeight = 90;
		contentHeight = 300;
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!isExpanded) {
					if(timerCollapse.isRunning()) {
						timerCollapse.stop();
					}
					timerExpand.start();
					isExpanded = true;
					isUnread = false;
				} else {
					if(timerExpand.isRunning()) {
						timerExpand.stop();
					}
					timerCollapse.start();
				}
				getParent().invalidate();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	public void drawBox(Graphics g)
	{
		if(isUnread || isExpanded) {
			g.drawImage(imageActive, 0, 0, 280, 16, 0, 0, 280, 16, null);
			g.drawImage(imageActive, 0, 16, 280, currentHeight - 16, 0, 16, 280, 32, null);
			g.drawImage(imageActive, 0, currentHeight - 16, 280, currentHeight, 0, 32, 280, 48, null);
		} else {
			g.drawImage(imageInactive, 0, 0, null);
		}
	}
	
	public void drawText(Graphics g, boolean drawPreview)
	{
		g.setFont((Font)UIHelper.getResource("ui.font.msgtitle"));
		if(title == null || title.length() == 0)
			g.drawString("无标题", 20, 26);
		else
			g.drawString(title, 20, 26);
		if(!timerExpand.isRunning() && !timerCollapse.isRunning()) {
			if(!isExpanded) {
				g.setFont((Font)UIHelper.getResource("ui.font.text"));
				if(omitted == null || omitted.length() == 0)
					g.drawString("无内容", 20, 46);
				else {
					if(omitted.length() != content.length())
						g.drawString(omitted + "....", 21, 46);
					else
						g.drawString(omitted, 21, 46);
				}
				g.setColor(UIHelper.darkColor);
				g.drawString("更多内容", 20, 75);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		drawBox(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawText(g, true);
	}

	public String omitString(String str, int length)
	{
		if(fontMetrics.stringWidth(str) < length)
			return str;
		
		int pos = 1;
		while(pos < str.length()) {
			String result = str.substring(0, pos);
			if(str.charAt(pos) == '\t' || str.charAt(pos) == '\n')
				return result;
			if(fontMetrics.stringWidth(result) > length)
				return result;
			pos++;
		}
		
		return "";
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
		this.omitted = omitString(content, 215);
	}
}