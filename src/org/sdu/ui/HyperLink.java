package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.sdu.util.DebugFramework;

/**
 * HyperLink class implements a web link component.
 * 
 * @version 0.1 rev 8001 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class HyperLink extends JLabel implements TranslucentComponent
{
	private static final long serialVersionUID = 1L;

	private URI	uri;
	private float globalOpacity = 1.0f;
	
	/**
	 * Initialize a HyperLink object.
	 * 
	 * @param desc
	 * @param url
	 */
	public HyperLink(String desc, String url)
	{
		super();
		try {
			this.uri = new URI(url);
		} catch(Exception e) {
			uri = null;
			DebugFramework.getFramework().print("Url \"" + url + "\" is invalid: " + e);
		}
		
		setText("<HTML><FONT color=\"#000099\"><U>" + desc + "</U></FONT></HTML>");
		setFont((Font)UIHelper.getResource("ui.font.text"));
		setHorizontalAlignment(SwingConstants.LEFT);
		setOpaque(false);
		setBackground(Color.WHITE);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if(uri != null && Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(uri);
					}
				} catch(Exception e) {
					DebugFramework.getFramework().print("Cannot open specified url: " + e);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
	}
	
	/**
	 * Set the web url that component will open.
	 * 
	 * @param url
	 */
	public void setUrl(String url)
	{
		try {
			this.uri = new URI(url);
		} catch(Exception e) {
			uri = null;
			DebugFramework.getFramework().print("Url \"" + url + "\" is invalid: " + e);
		}
	}
	
	/**
	 * Get the web url that component will open.
	 * 
	 * @return
	 */
	public String getUrl()
	{
		if(uri == null) return "";
		return uri.toString();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalOpacity));
		super.paintComponent(g);
	}
	
	@Override
	public void setOpacity(float alpha) {
		globalOpacity = alpha;
	}

	@Override
	public float getOpacity() {
		return globalOpacity;
	}
}