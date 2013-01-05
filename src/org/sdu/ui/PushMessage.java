package org.sdu.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

/**
 * PushMessage is a push notification component.
 * 
 * @version 0.1 rev 8000 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PushMessage extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	public static final int Width = 265;
	public static final int Height = 70;
	public static final int Spacing = 6;
	
	private Stroke stroke;
	private Color msgColor;
	
	public PushMessage()
	{
		super();
		stroke = new BasicStroke(1.0f);
		
		msgColor = UIHelper.darkColor;
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				msgColor = UIHelper.lightColor;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				msgColor = UIHelper.darkColor;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	@Override
	public void paintComponent(Graphics graph)
	{
		Graphics2D g = (Graphics2D) graph;
		g.setColor(msgColor);
		g.setStroke(stroke);
		g.drawRect(1, 1, Width - 1, Height - 1);
		g.fillRect(1, 1, Spacing, Height - 1);
	}
}