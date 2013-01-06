package org.sdu.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * RefreshablePanel class implements a pull-down-refresh panel.
 * 
 * @version 0.1 rev 8000 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class RefreshablePanel extends ScrollPanel
{
	private static final long serialVersionUID = 1L;
	private static final int BannnerHeight = 40;

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
			}
			setOffset(offset);
		}
	});
	
	private Timer RestoreBanner = new Timer(UIHelper.normalAnimationRate, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int offset = getOffset();
			offset += 30;
			if(offset > -BannnerHeight) {
				offset = -BannnerHeight;
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
					if(-getOffset() > BannnerHeight) {
						System.out.println("Restore to banner location.");
						RestoreBanner.start();
						onRefresh();
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
}