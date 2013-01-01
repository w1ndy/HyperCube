package org.sdu.client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.sdu.ui.BasicFrame;
import org.sdu.ui.ProgressBar;
import org.sdu.ui.UIHelper;

/**
 * ClientFrame class handles basic stuffs in frame.
 * 
 * @version 0.1 rev 8000 Jan. 1, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientFrame extends BasicFrame
{
	private static final long serialVersionUID = 1L;
	private ProgressBar progressor;
	
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
	
	public ClientFrame()
	{
		super();
		getRootPane().getActionMap().put("on_escape", actionCloseOnEscape);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "on_escape");
		
		progressor = new ProgressBar(UIHelper.progressBarColor);
		progressor.setBounds(UIHelper.progressBarLoginOffsetX, UIHelper.progressBarLoginOffsetY,
				UIHelper.progressBarWidth, UIHelper.progressBarHeight);
	}
	
	public ClientFrame(String title, String subtitle)
	{
		super(title, subtitle);
		getRootPane().getActionMap().put("on_escape", actionCloseOnEscape);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "on_escape");
	}
	
	public void startProgressBar()
	{
		progressor.start();
	}
	
	public void stopProgressBar()
	{
		progressor.stop();
	}
	
	public ProgressBar getProgressBar()
	{
		return progressor;
	}
}