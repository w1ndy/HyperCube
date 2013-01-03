package org.sdu.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.sdu.command.PacketLoginSystem;
import org.sdu.command.PacketResolver;
import org.sdu.net.NetworkClient;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.ui.AvatarBox;
import org.sdu.ui.HyperLink;
import org.sdu.ui.PasswordBox;
import org.sdu.ui.StatusNotifier;
import org.sdu.ui.StatusNotifier.NotifyType;
import org.sdu.ui.TextBox;
import org.sdu.ui.UIHelper;

/**
 * LoginUIHandler handles UI events in login frame.
 * 
 * @version 0.1 rev 8005 Jan. 4, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class LoginUIHandler implements UIHandler
{
	private EventDispatcher dispatcher;
	private ClientFrame frame;
	private AvatarBox avatarBox;
	private TextBox	userBox;
	private PasswordBox passBox;
	private HyperLink registerLink;
	private StatusNotifier notifier;
	
	private NetworkClient client;
	
	private InputVerifier userBoxVerifier = new InputVerifier() {
		private boolean check(String str)
		{
			if(str.length() < 2) return false;
			for(int i = 2; i < str.length(); i++) {
				if(!Character.isDigit(str.charAt(i))) return false;
			}
			return true;
		}
		
		@Override
		public boolean verify(JComponent arg0) {
			if(arg0 instanceof TextBox) {
				TextBox box = (TextBox) arg0;
				if(!check(box.getText())) {
					box.onFailed();
					notifier.stop();
					notifier.setNotifyType(NotifyType.Error);
					notifier.setStatus(new String[] {
							(String)UIHelper.getResource("ui.string.login.notify.incorrectname"),
							(String)UIHelper.getResource("ui.string.login.notify.usageprompt")});
					notifier.start();
					return false;
				}
			}
			return true;
		}
	};
	
	private KeyListener userBoxKeyListener = new KeyListener() {
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
	
	private KeyListener passBoxKeyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				frame.startProgressBar();
				avatarBox.setEnabled(false);
				userBox.setEditable(false);
				passBox.setEditable(false);
				startLogin();
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
	public LoginUIHandler(EventDispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
		createAvatarBox();
		createUserBox();
		createPasswordBox();
		createRegisterLink();
	}
	
	/**
	 * Start login procedure.
	 */
	private void startLogin()
	{
		client.connect("127.0.0.1", 21071, dispatcher);
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
		userBox.setInputVerifier(userBoxVerifier);
		//userBox.addInputMethodListener(userBoxIME);
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
	public void onAttach(final NetworkClient client, final ClientUI ui) {
		this.client = client;
		
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
		notifier.setBounds(UIHelper.NotifyOffsetX, UIHelper.NotifyOffsetY,
				UIHelper.NotifyWidth, UIHelper.NotifyHeight);
		frame.add(notifier);
	}

	/**
	 * Notified when detaching from a UI.
	 */
	@Override
	public void onDetach(final NetworkClient client, final ClientUI ui) {
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

	@Override
	public void onClosing(ClientUI ui) {
		ui.getFrame().dispose();
	}

	@Override
	public void onConnected(Session s) {
		s.post(new PacketLoginSystem((byte) 0x00, (byte) 0x01,
				userBox.getText(), new String(passBox.getPassword()),
				avatarBox.isInvisible() ? (byte) 0x00 : (byte) 0x01));
	}

	@Override
	public void onConnectFailure() {
		frame.stopProgressBar();
		avatarBox.setEnabled(true);
		userBox.setEditable(true);
		passBox.setEditable(true);
		notifier.setNotifyType(NotifyType.Error);
		notifier.setStatus(new String[] {
				(String)UIHelper.getResource("ui.string.login.networkdown"),
				(String)UIHelper.getResource("ui.string.login.checknetwork") });
		notifier.start();
	}

	@Override
	public void onNetworkData(Session s, Packet p) {
		PacketResolver resolver = new PacketResolver(p);
		if(resolver.getStatusMain() == 0) {
			System.out.println("LoginSuccess");
			frame.stopProgressBar();
			frame.startExpanding(650);
		} else {
			frame.stopProgressBar();
			avatarBox.setEnabled(true);
			userBox.setEditable(true);
			passBox.setEditable(true);
			notifier.setNotifyType(NotifyType.Error);
			switch(resolver.getStatusSub())
			{
			case 0x00:
				System.out.println("UnknownError");
				notifier.setStatus(
						new String[] {
							(String)UIHelper.getResource("ui.string.login.notify.unknown"),
							(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
						});
				break;
			case 0x01:
				System.out.println("UnsupportedVersion");
				notifier.setStatus(
						new String[] {
								(String)UIHelper.getResource("ui.string.login.notify.unsupportver"),
								(String)UIHelper.getResource("ui.string.login.notify.redirect"),
								(String)UIHelper.getResource("ui.string.login.notify.download")
						});
				break;
			case 0x02:
				System.out.println("WrongPass");
				notifier.setStatus(
						(String)UIHelper.getResource("ui.string.login.notify.wrongpass"));
				passBox.onWrongPassword();
				break;
			case 0x03:
				System.out.println("UnregisterUser");
				notifier.setStatus(
						(String)UIHelper.getResource("ui.string.login.notify.unregister"));
				userBox.onFailed();
				break;
			case 0x04:
				System.out.println("FrozenUser");
				notifier.setStatus(
						new String[] {
								(String)UIHelper.getResource("ui.string.login.notify.frozen"),
								(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
						});
				userBox.onFailed();
				break;
			case 0x05:
				System.out.println("AlreadyOnline");
				notifier.setStatus(
						new String[] {
								(String)UIHelper.getResource("ui.string.login.notify.online"),
								(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
						});
				break;
			}
			notifier.start();
			s.close();
		}
	}

	@Override
	public void onSessionClosed() {
		if(!passBox.isEditable()) {
			frame.stopProgressBar();
			avatarBox.setEnabled(true);
			userBox.setEditable(true);
			passBox.setEditable(true);
			notifier.setNotifyType(NotifyType.Error);
			notifier.setStatus(new String[] {
					(String)UIHelper.getResource("ui.string.login.networkdown"),
					(String)UIHelper.getResource("ui.string.login.checknetwork") });
			notifier.start();
		}
	}
}