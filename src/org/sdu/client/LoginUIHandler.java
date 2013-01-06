package org.sdu.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.SocketChannel;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import org.sdu.command.PacketLoginSystem;
import org.sdu.command.PacketResolver;
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
 * @version 0.1 rev 8008 Jan. 6, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class LoginUIHandler extends UIHandler
{
	private AvatarBox avatarBox;
	private TextBox	userBox;
	private PasswordBox passBox;
	private HyperLink registerLink;
	private StatusNotifier notifier;
	
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
				if(passBox.getPassword().length == 0)
					passBox.onWrongPassword();
				else
					startLogin();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	};
	
	private enum CancelReason
	{
		UnknownError,
		NetworkUnreachable,
		UnsupportedVersion,
		IncorrectPassword,
		UnregisteredUser,
		FrozenUser,
		AlreadyOnline
	}
	
	/**
	 * Initialize a LoginUIHandler object.
	 */
	public LoginUIHandler()
	{
		createAvatarBox();
		createUserBox();
		createPasswordBox();
		createRegisterLink();
		createNotifier();
	}
	
	/**
	 * Start login procedure.
	 */
	private void startLogin()
	{
		getFrame().startProgressBar();
		avatarBox.setEnabled(false);
		userBox.setEditable(false);
		passBox.setEditable(false);
		getDispatcher().connect("127.0.0.1", 21071);
	}
	
	/**
	 * Cancel login procedure.
	 */
	private void cancelLogin(CancelReason reason)
	{
		getFrame().stopProgressBar();
		avatarBox.setEnabled(true);
		userBox.setEditable(true);
		passBox.setEditable(true);
		
		String[] str;
		switch(reason)
		{
		case AlreadyOnline:
			log("Failed login attempt: User already online.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.online"),
					(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
			};
			break;
		case FrozenUser:
			log("Failed login attempt: User is frozen.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.frozen"),
					(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
			};
			break;
		case IncorrectPassword:
			log("Failed login attempt: Password is incorrect.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.wrongpass")
			};
			break;
		case NetworkUnreachable:
			log("Failed login attempt: Server is unreachable.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.networkdown"),
					(String)UIHelper.getResource("ui.string.login.checknetwork")
			};
			break;
		case UnknownError:
			log("Failed login attempt: Server returned unknown error.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.unknown"),
					(String)UIHelper.getResource("ui.string.login.notify.contactadmin")
			};
			break;
		case UnregisteredUser:
			log("Failed login attempt: User unregistered.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.unregister")
			};
			break;
		case UnsupportedVersion:
			log("Failed login attempt: Client version is not supported.");
			str = new String[] {
					(String)UIHelper.getResource("ui.string.login.notify.unsupportver"),
					(String)UIHelper.getResource("ui.string.login.notify.redirect"),
					(String)UIHelper.getResource("ui.string.login.notify.download")
			};
			break;
		default:
			log("Failed login attempt: No reason.");
			str = null;
			break;
		}
		notifier.stop();
		notifier.setNotifyType(NotifyType.Error);
		notifier.setStatus(str);
		notifier.start();
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
	 * Add rolling notifier
	 */
	private void createNotifier()
	{
		notifier = new StatusNotifier();
		notifier.setBounds(UIHelper.NotifyOffsetX, UIHelper.NotifyOffsetY,
				UIHelper.NotifyWidth, UIHelper.NotifyHeight);
	}
	@Override
	public void onClosing(ClientUI ui)
	{
		ui.getFrame().dispose();
	}

	@Override
	public void onConnected(Session s)
	{
		s.post(new PacketLoginSystem((byte) 0x00, (byte) 0x01,
				userBox.getText(), new String(passBox.getPassword()),
				avatarBox.isInvisible() ? (byte) 0x00 : (byte) 0x01));
	}

	@Override
	public boolean onAcceptingSession(SocketChannel c)
	{
		return true;
	}

	@Override
	public void onSessionAccepted(Session s) {}

	@Override
	public void onSessionClosed(Session s)
	{
		if(!passBox.isEditable()) {
			getFrame().stopProgressBar();
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

	@Override
	public void onUnregisteredSession(SocketChannel c) {}

	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		PacketResolver resolver = new PacketResolver(p);
		if(resolver.getStatusMain() == 0) {
			log("Login succeeded.");
			getFrame().stopProgressBar();
			getDispatcher().attach(new MainUIHandler());
		} else {
			switch(resolver.getStatusSub())
			{
			case 0x01:
				cancelLogin(CancelReason.UnsupportedVersion);
				break;
			case 0x02:
				cancelLogin(CancelReason.IncorrectPassword);
				break;
			case 0x03:
				cancelLogin(CancelReason.UnregisteredUser);
				break;
			case 0x04:
				cancelLogin(CancelReason.FrozenUser);
				break;
			case 0x05:
				cancelLogin(CancelReason.AlreadyOnline);
				break;
			case 0x00:
			default:
				cancelLogin(CancelReason.UnknownError);
				break;
			}
			s.close();
		}
	}

	@Override
	public void onConnectFailure(SocketChannel c)
	{
		cancelLogin(CancelReason.NetworkUnreachable);
	}

	@Override
	public void onAttach(final ClientUI ui)
	{
		ui.getFrame().setTitle((String)UIHelper.getResource("ui.string.login.title"));
		ui.getFrame().setSubtitle((String)UIHelper.getResource("ui.string.login.subtitle"));
		ui.getFrame().setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		
		ui.getFrame().add(avatarBox);
		ui.getFrame().add(userBox);
		ui.getFrame().add(passBox);
		ui.getFrame().add(registerLink);
		ui.getFrame().add(notifier);

		ui.getFrame().setVisible(true);
		
		// DEBUG redirect login to main.
		getDispatcher().attach(new MainUIHandler());
	}

	@Override
	public void onDetach(final ClientUI ui)
	{
		ui.getFrame().remove(notifier);
		ui.getFrame().remove(avatarBox);
		ui.getFrame().remove(userBox);
		ui.getFrame().remove(passBox);
		ui.getFrame().remove(registerLink);
	}
}