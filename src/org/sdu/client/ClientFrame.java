package org.sdu.client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import org.sdu.ui.BasicFrame;
import org.sdu.ui.ProgressBar;
import org.sdu.ui.UIHelper;

/**
 * ClientFrame class handles basic stuffs in frame.
 * 
 * @version 0.1 rev 8003 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientFrame extends BasicFrame
{
	private static final long serialVersionUID = 1L;
	private ProgressBar progressor;
	private int targetHeight = 0;
	
	private Action actionCloseOnEscape = new Action() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			WindowEvent wev = new WindowEvent(ClientFrame.this, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener arg0) {}

		@Override
		public Object getValue(String arg0) {
			return null;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public void putValue(String arg0, Object arg1) {}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener arg0) {}

		@Override
		public void setEnabled(boolean arg0) {}
	};
	
	private Timer timerRollingExpand = new Timer(10, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(getHeight() < targetHeight) {
				setSize(getWidth(), getHeight() + 25);
				progressor.setBounds(UIHelper.progressBarLoginOffsetX, getHeight() - 9,
						UIHelper.progressBarWidth, UIHelper.progressBarHeight);
			} else {
				setSize(getWidth(), targetHeight);
				progressor.setBounds(UIHelper.progressBarLoginOffsetX, getHeight() - 9,
						UIHelper.progressBarWidth, UIHelper.progressBarHeight);
				timerRollingExpand.stop();
			}
		}
	});
	
	/**
	 * Initialize a ClientFrame object.
	 */
	public ClientFrame()
	{
		super();
		getRootPane().getActionMap().put("on_escape", actionCloseOnEscape);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "on_escape");
		
		progressor = new ProgressBar(UIHelper.progressBarColor);
		progressor.setBounds(UIHelper.progressBarLoginOffsetX, UIHelper.progressBarLoginOffsetY,
				UIHelper.progressBarWidth, UIHelper.progressBarHeight);
		this.add(progressor);
		
		timerRollingExpand.setRepeats(true);
	}
	
	/**
	 * Initialize a ClientFrame object with title and subtitle.
	 */
	public ClientFrame(String title, String subtitle)
	{
		super(title, subtitle);
		getRootPane().getActionMap().put("on_escape", actionCloseOnEscape);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "on_escape");
	}
	
	/**
	 * Start progress indicator.
	 */
	public void startProgressBar()
	{
		progressor.start();
	}
	
	/**
	 * Stop progress indicator.
	 */
	public void stopProgressBar()
	{
		progressor.stop();
	}
	
	/**
	 * Get progress indicator instance.
	 */
	public ProgressBar getProgressBar()
	{
		return progressor;
	}
	
	/**
	 * Start expanding animation.
	 */
	public void startExpanding(int targetHeight)
	{
		this.targetHeight = targetHeight;
		timerRollingExpand.start();
	}
	
	/**
	 * Stop expanding animation.
	 */
	public void stopExpanding()
	{
		timerRollingExpand.stop();
	}
}