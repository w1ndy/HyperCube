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
import java.util.ArrayList;
import java.util.Date;

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
	private String title = "", author = "";
	private Date date = null;
	private ArrayList<String> multiline = null;
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
			g.drawString("无标题", 20, 28);
		else
			g.drawString(title, 20, 26);
		if(!timerExpand.isRunning() && !timerCollapse.isRunning()) {
			g.setFont((Font)UIHelper.getResource("ui.font.text"));
			if(!isExpanded) {
				if(multiline == null || multiline.size() == 0) {
					g.drawString("无内容", 20, 50);
				} else {
					if(multiline.size() != 1)
						g.drawString(multiline.get(0) + "....", 21, 50);
					else
						g.drawString(multiline.get(0), 21, 50);
				}
				g.setColor(UIHelper.darkColor);
				g.drawString("更多内容", 20, 75);
			} else {
				if(multiline == null || multiline.size() == 0) {
					g.drawString("无内容", 20, 50);
				} else {
					int i;
					for(i = 0; i < multiline.size(); i++) {
						g.drawString(multiline.get(i), 20, 50 + 22 * i);
					}
					g.setColor(UIHelper.darkColor);
					
					String sign = "Posted by ";
					if(author == "")
						sign += "Annoymous";
					else
						sign += author;
					if(date != null)
						sign += " @ " + date;
					
					g.drawString(sign, 20, 60 + 22 * i);
				}
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
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {		
		if(multiline == null)
			multiline = new ArrayList<String>();
		
		int s = 0;
		for(int i = 1; i <= content.length(); i++) {
			String str = content.substring(s, i);
			if(fontMetrics.stringWidth(str) > 220) {
				multiline.add(str);
				s = i;
			}
		}
		multiline.add(content.substring(s));
		contentHeight = 22 * multiline.size() + 90;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}