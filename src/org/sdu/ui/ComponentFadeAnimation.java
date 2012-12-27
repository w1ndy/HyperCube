package org.sdu.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * ComponentFadeAnimation class fades a group of translucent component in
 * or out.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ComponentFadeAnimation
{
	private Timer timerFadeIn, timerFadeOut;
	private List<TranslucentComponent> componentList;
	private float fadeRate = (float)UIHelper.normalFadeRate / 255.0f;
	private float globalOpacity = 1.0f;
	
	public ComponentFadeAnimation()
	{
		componentList = new ArrayList<TranslucentComponent>();
		
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
				}
				update();
			}
		});
		
		timerFadeIn.setRepeats(true);
		timerFadeOut.setRepeats(true);
	}
	
	private void update()
	{
		for(TranslucentComponent c : componentList) {
			c.setOpacity(globalOpacity);
			if(c instanceof JComponent) ((JComponent)c).repaint();
		}
	}
	
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
	
	public void fadeIn()
	{
		if(timerFadeOut.isRunning()) timerFadeOut.stop();
		timerFadeIn.start();
	}
	
	public void fadeOut()
	{
		if(timerFadeIn.isRunning()) timerFadeIn.stop();
		timerFadeOut.start();
	}

	public void stop()
	{
		if(timerFadeIn.isRunning()) timerFadeIn.stop();
		if(timerFadeOut.isRunning()) timerFadeOut.stop();
	}
}