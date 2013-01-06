package org.sdu.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * ComponentFadeAnimation class fades a group of translucent component in
 * or out.
 * 
 * @version 0.1 rev 8001 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ComponentFadeAnimation
{
	private Timer timerFadeIn, timerFadeOut;
	private List<TranslucentComponent> componentList;
	private float fadeRate = (float)UIHelper.normalAnimationRate / 255.0f;
	private float globalOpacity = 1.0f;
	private JFrame frame;
	private boolean manage;
	
	/**
	 * Initialize a ComponentFadeAnimation object.
	 * 
	 * @param frame
	 */
	public ComponentFadeAnimation(JFrame frame)
	{
		componentList = new ArrayList<TranslucentComponent>();
		this.frame = frame;
		
		timerFadeIn = new Timer(UIHelper.normalFadeFrequency, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				globalOpacity += fadeRate;
				if(globalOpacity > 1.0f) {
					globalOpacity = 1.0f;
					timerFadeIn.stop();
				}
				update();
			}
		});
		
		timerFadeOut = new Timer(UIHelper.normalFadeFrequency, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				globalOpacity -= fadeRate;
				if(globalOpacity < 0.0f) {
					globalOpacity = 0.0f;
					timerFadeOut.stop();
					if(manage) {
						for(TranslucentComponent c : componentList) {
							if(c instanceof Component)
								ComponentFadeAnimation.this.frame.remove((Component)c);
						}
					}
				}
				update();
			}
		});
		
		timerFadeIn.setRepeats(true);
		timerFadeOut.setRepeats(true);
	}
	
	/**
	 * Update global opacity.
	 */
	private void update()
	{
		for(TranslucentComponent c : componentList) {
			c.setOpacity(globalOpacity);
			if(c instanceof Component)
				((Component)c).repaint();
		}
		frame.repaint();
	}
	
	/**
	 * Reset global opacity to alpha.
	 * 
	 * @param alpha
	 */
	public void reset(float alpha)
	{
		globalOpacity = alpha;
		update();
	}
	
	public boolean add(TranslucentComponent c)
	{
		return componentList.add(c);
	}
	
	public boolean remove(TranslucentComponent c)
	{
		return componentList.remove(c);
	}
	
	public void fadeIn(boolean addAll)
	{
		if(addAll) {
			for(TranslucentComponent c : componentList) {
				if(c instanceof Component)
					ComponentFadeAnimation.this.frame.add((Component)c);
			}
		}
		if(timerFadeOut.isRunning()) timerFadeOut.stop();
		timerFadeIn.start();
	}
	
	public void fadeOut(boolean removeAll)
	{
		manage = removeAll;
		if(timerFadeIn.isRunning()) timerFadeIn.stop();
		timerFadeOut.start();
	}

	public void stop()
	{
		if(timerFadeIn.isRunning()) timerFadeIn.stop();
		if(timerFadeOut.isRunning()) timerFadeOut.stop();
		frame.repaint();
	}
}