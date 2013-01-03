package org.sdu.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import org.sdu.ui.AvatarBox;
import org.sdu.ui.HyperLink;
import org.sdu.ui.PasswordBox;
import org.sdu.ui.StatusNotifier;
import org.sdu.ui.TextBox;
import org.sdu.ui.UIHelper;

/**
 * LoginUIHandler handles UI events in login frame.
 * 
 * @version 0.1 rev 8001 Jan. 3, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class LoginUIHandler implements UIHandler
{
	private ClientFrame frame;
	private AvatarBox avatarBox;
	private TextBox	userBox;
	private PasswordBox passBox;
	private HyperLink registerLink;
	private StatusNotifier notifier;
	
	KeyListener userBoxKeyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				passBox.requestFocus();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	};
	
	KeyListener passBoxKeyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				frame.startProgressBar();
				avatarBox.setEnabled(false);
				userBox.setEditable(false);
				passBox.setEditable(false);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	};
	
	/**
	 * Initialize a LoginUIHandler object.
	 */
	public LoginUIHandler()
	{
		createAvatarBox();
		createUserBox();
		createPasswordBox();
		createRegisterLink();
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
		userBox.addKeyListener(userBoxKeyListener);
	}
	
	/**
	 * Add password box control.
	 */
	private void createPasswordBox()
	{
		passBox = new PasswordBox((String)UIHelper.getResource("ui.string.login.password"));
		passBox.setBounds(UIHelper.passwordBoxOffsetX, UIHelper.passwordBoxOffsetY,
				UIHelper.textBoxWidth, UIHelper.textBoxHeight);
		passBox.addKeyListener(passBoxKeyListener);
	}
	
	/**
	 * Add register link control.
	 */
	private void createRegisterLink()
	{
		registerLink = new HyperLink(
				(String)UIHelper.getResource("ui.string.login.register.desc"),
				(String)UIHelper.getResource("ui.string.login.register.url"));
		registerLink.setBounds(UIHelper.registerLinkOffsetX, UIHelper.registerLinkOffsetY,
				UIHelper.registerLinkWidth, UIHelper.registerLinkHeight);
	}
	
	/**
	 * Notified when attaching to a UI.
	 */
	@Override
	public void onAttach(final ClientUI ui) {
		frame = ui.getFrame();
		frame.setTitle((String)UIHelper.getResource("ui.string.login.title"));
		frame.setSubtitle((String)UIHelper.getResource("ui.string.login.subtitle"));
		frame.setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		frame.setVisible(true);
		
		ui.getFader().add(avatarBox);
		ui.getFader().add(userBox);
		ui.getFader().add(passBox);
		ui.getFader().add(registerLink);
		ui.getFader().reset(0.0f);
		ui.getFader().fadeIn(true);
		
		notifier = new StatusNotifier();
		notifier.setBounds(4, 35, 280, 50);
		notifier.setStatus(new String[] { "服务器无响应", "请检查网络连接"});
		notifier.start();
		frame.add(notifier);
	}

	/**
	 * Notified when detaching from a UI.
	 */
	@Override
	public void onDetach(final ClientUI ui) {
		frame.remove(notifier);
		ui.getFader().reset(1.0f);
		ui.getFader().fadeOut(true);
		
		Timer timerCleanup = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.getFader().remove(avatarBox);
				ui.getFader().remove(userBox);
				ui.getFader().remove(passBox);
				ui.getFader().remove(registerLink);
			}
		});
		
		timerCleanup.setRepeats(false);
		timerCleanup.start();
	}
}