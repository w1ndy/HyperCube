package org.sdu.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * BorderFlashAnimation class implements a animation flashing the border of textbox.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class BorderFlashAnimation implements ActionListener
{
	private RectBorder border;
	private Timer timerFlash;
	
	private int stage = 0, times = 0;
	private int r = 0, g = 0, b = 0;
	private int rw = 0xFF0000, gw = 0, bw = 0;
	private boolean uprise = true;
	private JComponent comp;

	/**
	 * Initialize a BorderFlashAnimation object.
	 * 
	 * @param c
	 * @param b
	 */
	public BorderFlashAnimation(JComponent c, RectBorder b)
	{
		border = b;
		timerFlash = new Timer(UIHelper.normalFadeFrequency, this);
		timerFlash.setRepeats(true);
		comp = c;
	}
	
	/**
	 * Set original color of the border.
	 * 
	 * @param color
	 */
	public void setColor(Color color)
	{
		r = color.getRGB() & 0xFF0000;
		g = color.getRGB() & 0x00FF00;
		b = color.getRGB() & 0x0000FF;
	}
	
	/**
	 * Set warning color.
	 * 
	 * @param color
	 */
	public void setWarningColor(Color color)
	{
		rw = color.getRGB() & 0xFF0000;
		gw = color.getRGB() & 0x00FF00;
		bw = color.getRGB() & 0x0000FF;
	}
	
	/**
	 * Start animation.
	 */
	public void start()
	{
		timerFlash.start();
	}
	
	/**
	 * Stop animation.
	 */
	public void stop()
	{
		// End animation.
		stage = 0;
		times = 0;
		border.setColor(new Color((0xff << 24) | r | g | b));
		timerFlash.stop();
	}
	
	/**
	 * When timer hit.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(uprise) {
			stage += UIHelper.normalFadeRate;
			if(stage > 255) {
				stage = 255;
				uprise = false;
			}
		} else {
			stage -= UIHelper.normalFadeRate;
			if(stage < 0) {
				stage = 0;
				uprise = true;
				times++;
				if(times == 2) stop();
			}
		}
		
		int rs, gs, bs;
		rs = (int) (r + (float)((rw - r) * stage) / 256.0f) & 0xFF0000;
		gs = (int) (g + (float)((gw - g) * stage) / 256.0f) & 0x00FF00;
		bs = (int) (b + (float)((bw - b) * stage) / 256.0f) & 0x0000FF;
		border.setColor(new Color((0xff << 24) | rs | gs | bs));
		comp.repaint();
	}
}