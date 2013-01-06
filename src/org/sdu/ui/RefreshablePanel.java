package org.sdu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * RefreshablePanel class implements a pull-down-refresh panel.
 * 
 * @version 0.1 rev 8001 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class RefreshablePanel extends ScrollPanel
{
	private static final long serialVersionUID = 1L;
	private static final int BannerHeight = 45;

	private Point origin;
	private boolean checked = false, isRefreshing = false;
	
	private Timer RestoreZero = new Timer(UIHelper.normalAnimationRate, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int offset = getOffset();
			offset += 10;
			if(offset > 0) {
				offset = 0;
				RestoreZero.stop();
				isRefreshing = false;
			}
			setOffset(offset);
		}
	});
	
	private Timer RestoreBanner = new Timer(UIHelper.normalAnimationRate, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int offset = getOffset();
			offset += 30;
			if(offset > -BannerHeight) {
				offset = -BannerHeight;
				RestoreBanner.stop();
			}
			setOffset(offset);
		}
	});
	
	private Timer RestorePage = new Timer(UIHelper.normalAnimationRate, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int offset = getOffset();
			offset -= 30;
			if(offset < getPageLength() - getHeight()) {
				offset = getPageLength() - getHeight();
				RestorePage.stop();
			}
			setOffset(offset);
		}
	});
	
	/**
	 * Initialize a RefreshablePanel object.
	 */
	public RefreshablePanel(JPanel p) {
		super(p);
		RestoreZero.setRepeats(true);
		RestoreBanner.setRepeats(true);
		RestorePage.setRepeats(true);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				origin = arg0.getLocationOnScreen();
				checked = false;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				origin = null;
				if(checked) {
					checked = false;
					if(-getOffset() > BannerHeight) {
						System.out.println("Restore to banner location.");
						RestoreBanner.start();
						if(!isRefreshing) {
							isRefreshing = true;
							onRefresh();
						}
					} else if(-getOffset() > 0) {
						System.out.println("Restore to 0");
						RestoreZero.start();
					} else if(getOffset() + getHeight() > getPageLength()) {
						System.out.println("Restore to page length");
						RestorePage.start();
					}
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(origin != null) {
					if(!checked) {
						if(Math.abs(e.getLocationOnScreen().x - origin.x)
								> Math.abs(e.getLocationOnScreen().y - origin.y)) {
							origin = null;
						} else {
							checked = true;
						}
					} else {
						setOffset(getOffset() - e.getLocationOnScreen().y + origin.y);
						origin = e.getLocationOnScreen();
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {}
		});
	}
	
	// Override this to get refresh indication.
	protected void onRefresh() {}
	
	/**
	 * Refreshing is complete.
	 */
	public void finishRefresh()
	{
		RestoreZero.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont((Font)UIHelper.getResource("ui.font.prompt"));
		g.setColor(UIHelper.darkColor);
		g.drawImage((Image)UIHelper.getResource("ui.main.refresh"), 0, -getOffset() - 40, null);
		FontMetrics fm = g.getFontMetrics();
		String str;
		if(isRefreshing) {
			str = (String)UIHelper.getResource("ui.string.main.refreshing");
		} else {
			str = (String)UIHelper.getResource("ui.string.main.refresh.release");
		}
		g.drawString(str, (getWidth() - fm.stringWidth(str)) / 2, -getOffset() - 20);
	}
}