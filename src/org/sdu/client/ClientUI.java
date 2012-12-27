package org.sdu.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import org.sdu.ui.AvatarBox;
import org.sdu.ui.BasicFrame;
import org.sdu.ui.ComponentFadeAnimation;
import org.sdu.ui.HyperLink;
import org.sdu.ui.PasswordBox;
import org.sdu.ui.ProgressBar;
import org.sdu.ui.TextBox;
import org.sdu.ui.UIHelper;

/**
 * ClientUI class implements a user interface of student user.
 * 
 * @version 0.1 rev 8006 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientUI extends Observable
{
	private BasicFrame frame;
	private AvatarBox avatarBox;
	private TextBox	userBox;
	private PasswordBox passBox;
	private HyperLink registerLink;
	private ProgressBar progressor;
	private JLabel labelLoading;

	private ComponentFadeAnimation fadeAnimation;
	
	private Action actionCloseOnEscape = new Action() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(frame != null) {
				WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			}
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
	
	/**
	 * Initialize ClientUI object.
	 */
	public ClientUI()
	{
		frame = new BasicFrame((String)UIHelper.getResource("ui.string.login.title"),
								(String)UIHelper.getResource("ui.string.login.subtitle"));
		
		frame.setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		frame.setIconImage((Image)UIHelper.getResource("ui.common.icon"));
		frame.getRootPane().getActionMap().put("on_escape", actionCloseOnEscape);
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "on_escape");
		
		createAvatarBox();
		createUserBox();
		createPasswordBox();
		createRegisterLink();
		createProgressBar();

		labelLoading = new JLabel("检查版本....");
		labelLoading.setFont((Font)UIHelper.getResource("ui.font.text"));
		labelLoading.setBounds(120, 110, 60, 15);
		labelLoading.setBackground(new Color(0, true));
		frame.add(labelLoading);
		
		frame.add(progressor);
		progressor.start();

		fadeAnimation = new ComponentFadeAnimation();
		fadeAnimation.add(avatarBox);
		fadeAnimation.add(userBox);
		fadeAnimation.add(passBox);
		fadeAnimation.add(registerLink);
		fadeAnimation.reset(0.0f);
		
//		Timer timerDebug = new Timer(3000, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				frame.remove(labelLoading);
//				progressor.stop();
//				
//				frame.add(avatarBox);
//				frame.add(userBox);
//				frame.add(passBox);
//				frame.add(registerLink);
//				
//				fadeAnimation.fadeIn();
//			}
//		});
//		timerDebug.setRepeats(false);
//		timerDebug.start();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		setChanged();
		notifyObservers(UIEvent.CheckVersion);
	}

	/**
	 * Add avatar box control.
	 */
	private void createAvatarBox()
	{
		avatarBox = new AvatarBox();
		avatarBox.setBounds(UIHelper.avatarBoxLoginOffsetX, UIHelper.avatarBoxLoginOffsetY,
				UIHelper.avatarBoxWidth, UIHelper.avatarBoxHeight);
	}
	
	/**
	 * Add user box control.
	 */
	private void createUserBox()
	{
		userBox = new TextBox((String)UIHelper.getResource("ui.string.login.username"));
		userBox.setBounds(UIHelper.usernameBoxOffsetX, UIHelper.usernameBoxOffsetY,
				UIHelper.textBoxWidth, UIHelper.textBoxHeight);
		userBox.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(userBox.getText().length() == 0) userBox.onFailed();
					else passBox.requestFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
	}
	
	/**
	 * Add password box control.
	 */
	public void createPasswordBox()
	{
		passBox = new PasswordBox((String)UIHelper.getResource("ui.string.login.password"));
		passBox.setBounds(UIHelper.passwordBoxOffsetX, UIHelper.passwordBoxOffsetY,
				UIHelper.textBoxWidth, UIHelper.textBoxHeight);
		passBox.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					progressor.start();
					avatarBox.setEnabled(false);
					userBox.setEditable(false);
					passBox.setEditable(false);
					setChanged();
					//passBox.onWrongPassword();
					// TODO post a notification here.
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
	}
	
	/**
	 * Add register link control.
	 */
	public void createRegisterLink()
	{
		registerLink = new HyperLink(
				(String)UIHelper.getResource("ui.string.login.register.desc"),
				(String)UIHelper.getResource("ui.string.login.register.url"));
		registerLink.setBounds(UIHelper.registerLinkOffsetX, UIHelper.registerLinkOffsetY,
				UIHelper.registerLinkWidth, UIHelper.registerLinkHeight);
	}
	
	/**
	 * Add progress bar control.
	 */
	public void createProgressBar()
	{
		progressor = new ProgressBar(UIHelper.progressBarColor);
		progressor.setBounds(UIHelper.progressBarLoginOffsetX, UIHelper.progressBarLoginOffsetY,
				UIHelper.progressBarWidth, UIHelper.progressBarHeight);
	}
	
	/**
	 * @return the avatarBox
	 */
	public AvatarBox getAvatarBox() {
		return avatarBox;
	}

	/**
	 * @param avatarBox the avatarBox to set
	 */
	public void setAvatarBox(AvatarBox avatarBox) {
		this.avatarBox = avatarBox;
	}
}
