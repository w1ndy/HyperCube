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
import java.util.Observer;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
 * @version 0.1 rev 8007 Dec. 28, 2012.
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
	
	private static final int Focus_CheckVersion = 0x05;
	private static final int Focus_LoginWindow = 0xB0;
	private int currentFocus;

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
	public ClientUI(Observer listener)
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

		labelLoading = new JLabel((String)UIHelper.getResource("ui.string.login.checkversion"), JLabel.CENTER);
		labelLoading.setFont((Font)UIHelper.getResource("ui.font.text"));
		labelLoading.setBounds(
				UIHelper.loginPromptOffsetX, UIHelper.loginPromptOffsetY, 
				UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		labelLoading.setBackground(new Color(0, true));
		frame.add(labelLoading);
		
		frame.add(progressor);
		progressor.start();

		fadeAnimation = new ComponentFadeAnimation(frame);
		fadeAnimation.add(avatarBox);
		fadeAnimation.add(userBox);
		fadeAnimation.add(passBox);
		fadeAnimation.add(registerLink);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		currentFocus = Focus_CheckVersion;
		
		addObserver(listener);
		setChanged();
		notifyObservers(new UIEvent(UIEvent.CheckVersion));
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
			private boolean validate(String text)
			{
				int i = 0, n = 0;
				boolean start = true;
				if(text == null || text.length() == 0) return false;
				for(i = 0; i < text.length(); i++) {
					if(Character.isLetter(text.charAt(i))) {
						if(!start) return false;
						n++;
						if(n > 2) return false;
					} else if(Character.isDigit(text.charAt(i))){
						start = false;
					} else {
						return false;
					}
				}
				return true;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!validate(userBox.getText())) userBox.onFailed();
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
					notifyObservers(new UIEvent(UIEvent.Login));
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
	 * Call if version is correct.
	 */
	public void onVersionOK()
	{
		if(currentFocus == Focus_CheckVersion) {
			frame.remove(labelLoading);
			progressor.stop();
			fadeAnimation.reset(0.0f);
			fadeAnimation.fadeIn(true);
			currentFocus = Focus_LoginWindow;
		}
	}
	
	/**
	 * Call if network is disconnected.
	 */
	public void onDisconnected()
	{
		switch(currentFocus)
		{
		case Focus_LoginWindow:
			progressor.start();
			fadeAnimation.fadeOut(true);
			
		case Focus_CheckVersion:
			frame.remove(labelLoading);
			labelLoading = new JLabel(
					(String)UIHelper.getResource("ui.string.login.waitnet"), 
					new ImageIcon((Image)UIHelper.getResource("ui.common.net")),
					JLabel.CENTER);
			labelLoading.setBackground(new Color(0, true));
			labelLoading.setFont((Font)UIHelper.getResource("ui.font.text"));
			labelLoading.setBounds(
					UIHelper.loginPromptOffsetX, UIHelper.loginPromptOffsetY,
					UIHelper.loginPromptWidth, UIHelper.loginPromptHeight);
			labelLoading.setVerticalTextPosition(JLabel.BOTTOM);
			labelLoading.setHorizontalTextPosition(JLabel.CENTER);
			frame.add(labelLoading);
			frame.repaint();
			break;
		}
	}
}
