package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * StatusNotifier class implements a notifier component, which displays
 * an array of string to notify users.
 * 
 * @version 0.1 rev 8000 Jan. 3, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class StatusNotifier extends JComponent
{
	/**
	 * Types of notification.
	 */
	public enum NotifyType
	{
		Info,
		Warning,
		Error
	}
	
	private static final long serialVersionUID = 1L;
	private Image notifyIcon;
	private boolean bRunning;
	private NotifyType notifyType;
	private String[] status;
	private int nCasting, currentY, previousY, lineHeight, alpha, opacity;
	private final float varRate = 0.1f;
	private FontMetrics fontMetrics;
	
	// fade the component in and launch activator.
	private Timer timerLauncher = new Timer(UIHelper.normalFadeFrequency,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opacity += varRate * 255.0f;
					if(opacity >= 255) {
						opacity = 255;
						timerLauncher.stop();
						timerActivator.start();
					}
					repaint();
				}
	});
	
	// fade the component out and do cleaning.
	private Timer timerShutdown = new Timer(UIHelper.normalFadeFrequency,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opacity -= varRate * 255.0f;
					if(opacity <= 0) {
						opacity = 0;
						timerShutdown.stop();
					}
					repaint();
				}
	});
	
	// Adjust the position and alpha of the text.
	private Timer timerAdjustor = new Timer(UIHelper.NotifyAdjustorInterval,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					currentY -= varRate * (float)lineHeight;
					previousY -= varRate * (float)lineHeight;
					alpha += varRate * 255.0f;
					if(alpha > 255) alpha = 255;
					if(currentY <= 0) {
						currentY = 0;
						previousY = -lineHeight;
						alpha = 255;
						repaint();
						timerAdjustor.stop();
						return ;
					}
					repaint();
				}
	});
	
	// Activate adjustor repeatedly.
	private Timer timerActivator = new Timer(UIHelper.NotifyActivatorInterval,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(++nCasting >= status.length) {
						timerActivator.stop();
						timerShutdown.start();
					}
					if(timerAdjustor.isRunning())
						timerAdjustor.stop();
					currentY = lineHeight;
					previousY = 0;
					alpha = 0;
					timerAdjustor.start();
				}
	});
	
	/**
	 * Initialize a StatusNotifier object.
	 */
	public StatusNotifier()
	{
		super();
		notifyType = NotifyType.Error;
		bRunning = false;
		status = null;
		setBackground(new Color(0, true));
		notifyIcon = (Image)UIHelper.getResource("ui.status.notify");

		fontMetrics = getFontMetrics((Font)UIHelper.getResource("ui.font.notify"));
		lineHeight = fontMetrics.getHeight();
		
		timerLauncher.setRepeats(true);
		timerShutdown.setRepeats(true);
		timerAdjustor.setRepeats(true);
		timerActivator.setRepeats(true);
		timerActivator.setInitialDelay(0);
	}
	
	/**
	 * Set the type of notifications.
	 */
	public void setNotifyType(NotifyType type)
	{
		notifyType = type;
	}
	
	/**
	 * Get the type of notifications.
	 */
	public NotifyType getNotifyType()
	{
		return notifyType;
	}
	
	/**
	 * Start rolling out the notifications.
	 */
	public void start()
	{
		bRunning = true;
		nCasting = -1;
		timerLauncher.start();
	}
	
	/**
	 * Stop rolling.
	 */
	public void stop()
	{
		bRunning = false;
		timerLauncher.stop();
		timerActivator.stop();
		timerAdjustor.stop();
		timerShutdown.stop();
	}
	
	/**
	 * Is component running.
	 */
	public boolean isRunning()
	{
		return this.bRunning;
	}

	/**
	 * Set a status notification.
	 */
	public void setStatus(String status)
	{
		this.status = new String[] { status };
		nCasting = -1;
	}
	
	/**
	 * Set status notifications.
	 */
	public void setStatus(String[] status)
	{
		this.status = status;
		nCasting = -1;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		// Only the component is running should we paint.
		if(bRunning) {
			// Get separate factor.
			int s = getWidth() - UIHelper.NotifyIconSize;
			g.setFont(fontMetrics.getFont());
			
			// Draw previous and current strings.
			if(status != null) {
				if(nCasting - 1 >= 0 && nCasting <= status.length) {
					// Draw previous.
					g.setColor(new Color(255, 255, 255, 255 - alpha));
					g.drawString(
							status[nCasting - 1],
							s - UIHelper.NotifyTextSpacing - fontMetrics.stringWidth(status[nCasting - 1]),
							previousY + fontMetrics.getAscent());
				}
				if(nCasting >= 0 && nCasting < status.length) {
					// Draw current.
					g.setColor(new Color(255, 255, 255, alpha));
					g.drawString(
							status[nCasting],
							s - UIHelper.NotifyTextSpacing - fontMetrics.stringWidth(status[nCasting]),
							currentY + fontMetrics.getAscent());
				}
			}
			
			// Draw notify icon.
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity / 255.0f));
			switch(notifyType)
			{
			case Info:
				g.drawImage(notifyIcon, s, 0, getWidth(), UIHelper.NotifyIconSize,
						0, 0, UIHelper.NotifyIconSize, UIHelper.NotifyIconSize, this);
				break;
			case Warning:
				g.drawImage(notifyIcon, s, 0, getWidth(), UIHelper.NotifyIconSize,
						UIHelper.NotifyIconSize, 0,
						2 * UIHelper.NotifyIconSize, UIHelper.NotifyIconSize, this);
				break;
			case Error:
				g.drawImage(notifyIcon, s, 0, getWidth(), UIHelper.NotifyIconSize,
						2 * UIHelper.NotifyIconSize, 0,
						3 * UIHelper.NotifyIconSize, UIHelper.NotifyIconSize, this);
				break;
			}
		}
	}
}