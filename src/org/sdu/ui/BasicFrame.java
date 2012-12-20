package org.sdu.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Basic class implements the basic UI.
 * 
 * @version 0.1 rev 8000 Dec. 19, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class BasicFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Point mouseCoord = null;
	
	/**
	 * Construct a basic frame.
	 */
	public BasicFrame()
	{
		super();
		initializeBasicFrame();
	}
	
	/**
	 * Construct a basic frame with title.
	 * 
	 * @param title
	 */
	public BasicFrame(String title)
	{
		super(title);
		initializeBasicFrame();
	}
	
	/**
	 * Make the frame movable.
	 */
	private void makeMovable()
	{
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				mouseCoord = arg0.getPoint();
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mouseCoord = null;
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Point p = arg0.getLocationOnScreen();
				if(mouseCoord != null)
					setLocation(p.x - mouseCoord.x, p.y - mouseCoord.y);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {}
		});
	}
	
	/**
	 * Initialize the frame.
	 */
	private void initializeBasicFrame()
	{
		setUndecorated(true);
		makeMovable();

		setContentPane(new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g)
			{
				// TODO paint frame here.
			}
		});
	}
}