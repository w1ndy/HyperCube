package org.sdu.ui;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

/**
 * ScrollPanel class provides a scrollable container.
 * 
 * @version 0.1 rev 8000 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ScrollPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private int offset;
	
	/**
	 * Initialize a ScrollPanel object.
	 */
	public ScrollPanel(JPanel p)
	{
		super(null);
		panel = p;
		offset = 0;
		add(panel);

		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					int amount = e.getScrollAmount() * 5;
					if(e.getWheelRotation() < 0) {
						if(offset - amount < 0)
							offset = 0;
						else
							offset -= amount;
					} else {
						if(offset + amount + getHeight() > panel.getHeight())
							offset = panel.getHeight() - getHeight();
						else
							offset += amount;
					}
					panel.setLocation(0, -offset);
					repaint();
				}
			}
		});
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h)
	{
		panel.setBounds(0, -offset, w,
				(panel.getBounds().height > h) ? panel.getBounds().height : h);
		super.setBounds(x, y, w, h);
	}
}