package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * ProgressBar class implements a dynamic progress bar.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ProgressBar extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private LinearGradientPaint paint;
	private int width, height;
	private float xpos;
	private boolean running = false;
	private Timer timerDraw;
	private float[] position = {0.0f, 0.4f, 0.6f, 1.0f};
	private Color alpha;
	private Color[] colors = new Color[4];
	
	/**
	 * Initialize ProgressBar class.
	 * 
	 * @param c
	 */
	public ProgressBar(Color c)
	{
		super();
		this.width = UIHelper.progressBarWidth;
		this.height = UIHelper.progressBarHeight;
		initialize(c);
	}
	
	/**
	 * Initialize ProgressBar object.
	 * 
	 * @param c
	 * @param width
	 */
	public ProgressBar(Color c, int width)
	{
		super();
		this.width = width;
		this.height = UIHelper.progressBarHeight;
		initialize(c);
	}
	
	/**
	 * Initialize ProgressBar object.
	 * 
	 * @param c
	 * @param width
	 * @param height
	 */
	public ProgressBar(Color c, int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
		initialize(c);
	}
	
	private void initialize(Color c)
	{
		alpha = new Color(c.getRGB() & 0x00FFFFFF, true);
		colors[0] = alpha;
		colors[1] = c;
		colors[2] = c;
		colors[3] = alpha;
		
		paint = new LinearGradientPaint(0.0f, 0.0f, UIHelper.progressBarInterval, 0.0f, position, colors);
		timerDraw = new Timer(UIHelper.progressBarFrequency, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(xpos > width) xpos = -UIHelper.progressBarInterval;
				xpos += UIHelper.progressBarRate;
				repaint();
			}
		});
		timerDraw.setRepeats(true);
	}
	
	/**
	 * Is progress bar running.
	 * 
	 * @return
	 */
	public boolean isRunning()
	{
		return running;
	}
	
	/**
	 * Start rolling progress bar.
	 */
	public void start()
	{
		running = true;
		xpos = -UIHelper.progressBarInterval;
		timerDraw.start();
	}
	
	/**
	 * Stop and hide progress bar.
	 */
	public void stop()
	{
		running = false;
		timerDraw.stop();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(!running) return;
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.translate(xpos, 0.0f);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, width, height);
	}
}