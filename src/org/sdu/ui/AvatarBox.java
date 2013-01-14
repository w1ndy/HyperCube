package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * AvatarBox class implements an avatar box with switchable status icon.
 * 
 * @version 0.1 rev 8004 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class AvatarBox extends JComponent implements TranslucentComponent
{
	private static final long serialVersionUID = 1L;
	
	private Image imageAvatar, imageNoAvatar, imageAvatarFrame, imageOnline, imageInvisible;
	private Timer timerSwitchStatus;
	private boolean bInvisible, bFadingOut, bStatusMutable;
	private int statusOpacity;
	private float globalOpacity = 1.0f;
	
	private MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(!AvatarBox.this.isEnabled()) return ;
			if(timerSwitchStatus.isRunning()) {
				timerSwitchStatus.stop();
				bFadingOut = true;
			}
			timerSwitchStatus.start();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}		
	};
	
	/**
	 * Initialize avatar box.
	 */
	public AvatarBox(boolean isStatusMutable)
	{
		super();
		super.setSize(UIHelper.DefaultAvatarBoxWidth, UIHelper.DefaultAvatarBoxHeight);
		bStatusMutable = isStatusMutable;
		initialize();
	}
	
	public AvatarBox(int width, int height, boolean isStatusMutable)
	{
		super();
		super.setSize(width, height);
		bStatusMutable = isStatusMutable;
		initialize();
	}
	
	private void initialize()
	{
		// Fetch resources.
		imageAvatarFrame = (Image)UIHelper.getResource("ui.avatarbox.frame");
		imageNoAvatar	 = (Image)UIHelper.getResource("ui.avatarbox.none");
		imageOnline		 = (Image)UIHelper.getResource("ui.avatarbox.online");
		imageInvisible	 = (Image)UIHelper.getResource("ui.avatarbox.invisible");
		
		bInvisible = false;
		statusOpacity = 230;
		setAvatar(null);
		
		// Fade animations.
		bFadingOut = true;
		timerSwitchStatus = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(bFadingOut) {
					statusOpacity -= 40;
					if(statusOpacity < 0) {
						statusOpacity = 0;
						switchStatus();
						bFadingOut = false;
					}
				} else {
					statusOpacity += 40;
					if(statusOpacity > 230) {
						statusOpacity = 230;
						bFadingOut = true;
						timerSwitchStatus.stop();
					}
				}
				repaint();
			}
		});
		timerSwitchStatus.setRepeats(true);
		
		// Add status indicator listener.
		if(bStatusMutable)
			addMouseListener(mouseListener);
	}

	public void switchStatus()
	{
		bInvisible = !bInvisible;
		onStatusChanged(bInvisible);
	}
	
	public void drawAvatarFrame(Graphics2D g, int w, int h)
	{		
		g.drawImage(imageAvatarFrame, 0, 0, 10, 10,
				0, 0, 10, 10, null);
		g.drawImage(imageAvatarFrame, w - 10, 0, w,
				10, 20, 0, 30, 10, null);
		g.drawImage(imageAvatarFrame, 0, h - 10, 10,
				h, 0, 20, 10, 30, null);
		g.drawImage(imageAvatarFrame, w - 10, h - 10, w, h,
				20, 20, 30, 30, null);

		g.drawImage(imageAvatarFrame, 10, 0, w - 10, 10,
				10, 0, 20, 10, null);
		g.drawImage(imageAvatarFrame, 0, 10, 10, h - 10,
				0, 10, 10, 20, null);
		g.drawImage(imageAvatarFrame, 10, h - 10, w - 10, h,
				10, 20, 20, 30, null);
		g.drawImage(imageAvatarFrame, w - 10, 10, w, h - 10,
				20, 10, 30, 20, null);
	}
	
	@Override
	public void paintComponent(Graphics og)
	{
		Graphics2D g = (Graphics2D)og;
		int w = getWidth(), h = getHeight();
		
		g.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, globalOpacity));

		drawAvatarFrame(g, w, h);
		if(imageAvatar != null) {
			g.drawImage(imageAvatar, 5, 5, null);
		} else {
			g.drawImage(imageNoAvatar, 5, 5, null);
		}


		if(bStatusMutable) {
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER,
					globalOpacity * (float)statusOpacity / 255));
			
			if(bInvisible) {
				g.drawImage(imageInvisible, w - 21, h - 21, null);
			} else {
				g.drawImage(imageOnline, w - 21, h - 21, null);
			}
		}
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
	
	/**
	 * Set component's opacity.
	 */
	@Override
	public void setOpacity(float alpha) {
		globalOpacity = alpha;
	}

	/**
	 * Get component's opacity.
	 */
	@Override
	public float getOpacity() {
		return globalOpacity;
	}
	
	/**
	 * Notified when status changed.
	 */
	public void onStatusChanged(boolean isInvisible)
	{
	}
}