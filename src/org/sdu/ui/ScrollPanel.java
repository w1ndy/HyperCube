package org.sdu.ui;

import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

/**
 * ScrollPanel class provides a scrollable container.
 * 
 * @version 0.1 rev 8003 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
@SuppressWarnings("serial")
public class ScrollPanel extends JPanel
{
	public static int ScrollingSpeed = 10;
	
	private JPanel panel;
	private int offset;

	/**
	 * Initialize a ScrollPanel object.
	 */
	public ScrollPanel(JPanel p)
	{
		super(null);
		setBackground(Color.WHITE);
		panel = p;
		offset = 0;
		add(panel);

		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(offset == 0 && panel.getHeight() < getHeight()) {
					return ;
				}
				if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					int amount = e.getScrollAmount() * ScrollingSpeed;
					if(e.getWheelRotation() < 0) {
						if(offset - amount < 0)
							offset = 0;
						else
							offset -= amount;
					} else {
						if(getRemainingPageHeight() - amount < 0) {
							offset = panel.getHeight() - getHeight();
							if(offset < 0) offset = 0;
						}else
							offset += amount;
					}
					panel.setLocation(0, -offset);
					repaint();
				}
			}
		});
	}
	
	/**
	 * Get panel's offset.
	 */
	public int getOffset()
	{
		return offset;
	}
	
	/**
	 * Set panel's offset.
	 */
	public void setOffset(int value)
	{
		offset = value;
		panel.setLocation(0, -offset);
		repaint();
	}
	
	/**
	 * Get panel page length.
	 */
	public int getPageLength()
	{
		return panel.getHeight();
	}

	/**
	 * Get visible area height.
	 */
	public int getRemainingPageHeight()
	{
		return getPageLength() - getOffset() - getHeight();
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h)
	{
		panel.setBounds(0, -offset, w, panel.getHeight());
		super.setBounds(x, y, w, h);
	}
}