package org.sdu.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

/**
 * DraggableBorderFrame class implements a variant of draggable and
 * borderless JFrame.
 * 
 * @version 0.1 rev 8000 Dec. 19, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class DraggableBorderlessFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Point mouseCoord = null;
	
	public DraggableBorderlessFrame()
	{
		super();
		initializeDBF();
	}
	
	public DraggableBorderlessFrame(String title)
	{
		super(title);
		initializeDBF();
	}
	
	/**
	 * Initialize the frame.
	 * Use signature DBF to explicitly distinguish from initializers of subclasses.
	 */
	public void initializeDBF()
	{
		setUndecorated(true);
		
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
				setLocation(p.x - mouseCoord.x, p.y - mouseCoord.y);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {}
		});
	}
}