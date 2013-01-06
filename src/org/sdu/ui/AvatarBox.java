package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * AvatarBox class implements an avatar box with switchable status icon.
 * 
 * @version 0.1 rev 8003 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class AvatarBox extends JLabel implements TranslucentComponent
{
	private static final long serialVersionUID = 1L;
	
	private Image imageAvatar, imageNoAvatar, imageAvatarFrame, imageOnline, imageInvisible;
	private Timer timerInvisibleToOnline, timerOnlineToInvisible;
	private boolean bInvisible;
	private int statusOpacity;
	private float globalOpacity = 1.0f;
	
	/**
	 * Initialize avatar box.
	 */
	public AvatarBox()
	{
		super();
		
		// Fetch resources.
		imageAvatarFrame = (Image)UIHelper.getResource("ui.avatarbox.frame");
		imageNoAvatar = (Image)UIHelper.getResource("ui.avatarbox.none");
		imageOnline = (Image)UIHelper.getResource("ui.avatarbox.online");
		imageInvisible = (Image)UIHelper.getResource("ui.avatarbox.invisible");
		
		bInvisible = false;
		statusOpacity = 220;
		
		// Fade animations.
		timerInvisibleToOnline = new Timer(UIHelper.normalFadeFrequency, new ActionListener() {
			boolean isFadingOut = true;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isFadingOut) {
					statusOpacity -= UIHelper.normalAnimationRate;
					if(statusOpacity < 0) {
						statusOpacity = 0;
						bInvisible = false;
						isFadingOut = false;
					}
				} else {
					statusOpacity += UIHelper.normalAnimationRate;
					if(statusOpacity > 220) {
						statusOpacity = 220;
						isFadingOut = true;
						timerInvisibleToOnline.stop();
					}
				}
				repaint();
			}
		});
		
		timerOnlineToInvisible = new Timer(UIHelper.normalFadeFrequency, new ActionListener() {
			boolean isFadingOut = true;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isFadingOut) {
					statusOpacity -= UIHelper.normalAnimationRate;
					if(statusOpacity < 0) {
						statusOpacity = 0;
						bInvisible = true;
						isFadingOut = false;
					}
				} else {
					statusOpacity += UIHelper.normalAnimationRate;
					if(statusOpacity > 220) {
						statusOpacity = 220;
						isFadingOut = true;
						timerOnlineToInvisible.stop();
					}
				}
				repaint();
			}
		});
		
		timerInvisibleToOnline.setRepeats(true);
		timerOnlineToInvisible.setRepeats(true);
		setAvatar(null);
		
		// Add clickable status icon.
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!AvatarBox.this.isEnabled()) return ;
				if(bInvisible) {
					if(timerOnlineToInvisible.isRunning())
						timerOnlineToInvisible.stop();
					timerInvisibleToOnline.start();
				} else {
					if(timerInvisibleToOnline.isRunning())
						timerInvisibleToOnline.stop();
					timerOnlineToInvisible.start();
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
	public void paintComponent(Graphics g)
	{
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalOpacity));
		g.drawImage(imageAvatarFrame, 0, 0, this);
		if(imageAvatar != null)
			g.drawImage(imageAvatar, UIHelper.avatarOffsetX, UIHelper.avatarOffsetY, this);
		else
			g.drawImage(imageNoAvatar, UIHelper.avatarOffsetX, UIHelper.avatarOffsetY, this);
		
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalOpacity * (float)statusOpacity / 255));
		if(bInvisible)
			g.drawImage(imageInvisible, UIHelper.avatarStatusIconOffsetX, UIHelper.avatarStatusIconOffsetY, this);
		else
			g.drawImage(imageOnline, UIHelper.avatarStatusIconOffsetX, UIHelper.avatarStatusIconOffsetY, this);
	}
	
	/**
	 * @return the imageAvatar
	 */
	public Image getAvatar() {
		return imageAvatar;
	}

	/**
	 * @param imageAvatar the imageAvatar to set
	 */
	public void setAvatar(Image imageAvatar) {
		this.imageAvatar = imageAvatar;
	}

	/**
	 * Is login status invisible.
	 */
	public boolean isInvisible()
	{
		return bInvisible;
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