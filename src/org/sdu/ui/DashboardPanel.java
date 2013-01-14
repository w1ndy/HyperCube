package org.sdu.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * DashboardPanel class implements a dashboard of messages.
 * 
 * @version 0.1 rev 8001 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class DashboardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private MouseListener mouseListener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
			getParent().dispatchEvent(arg0);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			getParent().dispatchEvent(arg0);
		}
	};
	
	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent arg0) {
			getParent().dispatchEvent(arg0);
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {}
	};
	
	public DashboardPanel() {
		super(new DashboardLayout());
		setBackground(Color.WHITE);
	}
	
	@Override
	public Component add(Component c)
	{
		c.addMouseListener(mouseListener);
		c.addMouseMotionListener(mouseMotionListener);
		return super.add(c);
	}
	
	@Override
	public void remove(Component c)
	{
		c.removeMouseListener(mouseListener);
		c.removeMouseMotionListener(mouseMotionListener);
		remove(c);
	}
}