package org.sdu.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * DashboardPanel class implements a dashboard of messages.
 * 
 * @version 0.1 rev 8000 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class DashboardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<PushMessage> messages;
	
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
		super(null);
		setBackground(Color.WHITE);
		
		messages = new ArrayList<PushMessage>();
	}
	
	@Override
	public Component add(Component c)
	{
		if(c instanceof PushMessage) {
			PushMessage m = (PushMessage)c;
			m.setBounds(8, 10 + 100 * messages.size(), 280, 90);
			messages.add(m);
			if(getHeight() < 100 * messages.size())
				setSize(getWidth(), 100 * messages.size());
			
			m.addMouseListener(mouseListener);
			m.addMouseMotionListener(mouseMotionListener);
		}
		return super.add(c);
	}
	
	@Override
	public void remove(Component c)
	{
		if(c instanceof PushMessage) {
			PushMessage m = (PushMessage) c;
			m.removeMouseListener(mouseListener);
			m.removeMouseMotionListener(mouseMotionListener);
			
			int i;
			for(i = 0; i < messages.size(); i++) {
				if(messages.get(i) == m) {
					for(int j = i + 1; j < messages.size(); j++) {
						messages.get(i).setBounds(0, 90 * (j - 1), 280, 90);
					}
				}
			}
			if(i != messages.size()) messages.remove(i);
		}
		remove(c);
	}
}