package org.sdu.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * PanelSwitcher class provides a gesture-based panel switcher.
 * 
 * @version 0.1 rev 8001 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class PanelSwitcher extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JPanel> panels;
	private int switcherHeight, activatedPanel, panelOffset;
	private Point dragStart = null;
	private boolean trackUnchecked = true;
	private Timer forwardRestoreTimer, backwardRestoreTimer;
	
	private MouseListener mouseListener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			dragStart = e.getLocationOnScreen();
			panelOffset = 0;
			trackUnchecked = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dragStart = null;
			if(panelOffset > getWidth())
				panelOffset = getWidth();
			if(panelOffset < -getWidth())
				panelOffset = -getWidth();
			if(panelOffset > 0)
				backwardRestoreTimer.start();
			if(panelOffset < 0)
				forwardRestoreTimer.start();
		}
	};
	
	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent e) {
			if(dragStart != null) {
				if(trackUnchecked) {
					if(Math.abs(e.getLocationOnScreen().x - dragStart.x)
							< Math.abs(e.getLocationOnScreen().y - dragStart.y)) {
						dragStart = null;
					} else {
						trackUnchecked = false;
						onSwitchStart();
					}
				} else {
					panelOffset = e.getLocationOnScreen().x - dragStart.x;
					updateLocation();
					if(panelOffset > (float)getWidth() * 0.5f &&
							activatedPanel > 0 ) {
						activatedPanel--;
						dragStart.x += (float)getWidth();
					} else if(panelOffset < -(float)getWidth() * 0.5f &&
							activatedPanel < panels.size() - 1) {
						activatedPanel++;
						dragStart.x -= (float)getWidth();
					}
					repaint();
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {}
	};
	
	/**
	 * Initialize a PanelSwitcher object.
	 */
	public PanelSwitcher(int switcherHeight)
	{
		super();
		this.switcherHeight = switcherHeight;
		activatedPanel = 0;
		panels = new ArrayList<JPanel>();
		
		// Construct restore animation timer.
		forwardRestoreTimer = new Timer(UIHelper.normalAnimationRate, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelOffset += 50;
				if(panelOffset > 0) {
					forwardRestoreTimer.stop();
					onSwitchEnd();
				}
				updateLocation();
				repaint();
			}
		});

		// Construct restore animation timer.
		backwardRestoreTimer = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelOffset -= 50;
				if(panelOffset < 0) {
					backwardRestoreTimer.stop();
					onSwitchEnd();
				}
				updateLocation();
				repaint();
			}
		});
		
		forwardRestoreTimer.setRepeats(true);
		backwardRestoreTimer.setRepeats(true);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int offsetX = (int) ((getWidth() - panels.size() * 22 + 7) * 0.5f);
				int offsetY = getHeight() - (int) ((PanelSwitcher.this.switcherHeight + 15) * 0.5f);
				int dx = arg0.getPoint().x - offsetX, dy = arg0.getPoint().y - offsetY;
				if(dy >= 0 && dy <= 15 && dx >= 0) {
					int n = dx / 22;
					dx %= 22;
					if(dx <= 15) {
						panelOffset = (n - activatedPanel) * getWidth();
						activatedPanel = n;
						if(panelOffset < 0)
							forwardRestoreTimer.start();
						else
							backwardRestoreTimer.start();
					}
				}
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

	@Override
	public Component add(Component c)
	{
		c.addMouseListener(mouseListener);
		c.addMouseMotionListener(mouseMotionListener);
		return super.add(c);
	}
	
	/**
	 * Update panels' location whenever needed.
	 */
	private void updateLocation()
	{
		panels.get(activatedPanel).setLocation(panelOffset, 0);
		for(int i = 0; i < panels.size(); i++) {
			panels.get(i).setLocation(panelOffset + (i - activatedPanel) * getWidth(), 0);
		}
	}
	
	/**
	 * Notified when switch animation starts.
	 */
	private void onSwitchStart()
	{
		panelOffset = 0;
		//if(activatedPanel > 0)
		//	panels.get(activatedPanel - 1).setVisible(true);
		//if(activatedPanel < panels.size() - 1)
		//	panels.get(activatedPanel + 1).setVisible(true);
	}

	/**
	 * Notified when switch animation ends.
	 */
	private void onSwitchEnd()
	{
		panelOffset = 0;
		//if(activatedPanel > 0)
		//	panels.get(activatedPanel - 1).setVisible(false);
		//if(activatedPanel < panels.size() - 1)
		//	panels.get(activatedPanel + 1).setVisible(false);
	}
	
	/**
	 * Add a panel to switcher.
	 */
	public void addPanel(JPanel panel)
	{
		panel.setBounds(panels.size() * getWidth(), 0, getWidth(), getHeight() - switcherHeight);
		//if(!panels.isEmpty())
		//	panel.setVisible(false);
		panels.add(panel);
		add(panel);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(!panels.isEmpty()) {
			int offsetX, offsetY;
			
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			offsetX = (int) ((getWidth() - panels.size() * 22 + 7) * 0.5f);
			offsetY = getHeight() - (int) ((switcherHeight + 15) * 0.5f);
			
			for(int i = 0; i < panels.size(); i++) {
				if(i == activatedPanel)
					g.setColor(UIHelper.lightColor);
				else
					g.setColor(UIHelper.darkColor);
				
				g.fillOval(offsetX + 22 * i, offsetY, 15, 15);
			}
		}
	}
}