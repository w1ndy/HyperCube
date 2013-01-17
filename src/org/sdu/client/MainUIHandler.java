package org.sdu.client;

import java.awt.Color;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.SocketChannel;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.ui.AvatarBox;
import org.sdu.ui.DashboardPanel;
import org.sdu.ui.PanelSwitcher;
import org.sdu.ui.PushMessage;
import org.sdu.ui.RefreshablePanel;
import org.sdu.ui.TextBox;
import org.sdu.ui.UIHelper;

import com.sun.awt.AWTUtilities;

/**
 * MainUIHandler class handles UI events in main frame.
 * 
 * @version 0.1 rev 8001 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class MainUIHandler extends UIHandler
{
	private AvatarBox userAvatarBox;
	private TextBox signatureTextBox;
	private PanelSwitcher switcher;
	private DashboardPanel panel;
	private ClientFrame frame;
	private UserInfo info;
	private TrayIcon trayIcon;
	
	public MainUIHandler(UserInfo info)
	{
		this.info = info;
		createUserAvatarBox();
		createSignatureTextBox();
		createPanelSwitcher();
		createDashboardPanel();
		createFriendPanel();
		createTrayIcon();
	}
	
	private void createTrayIcon()
	{
		try {
			if(!SystemTray.isSupported()) {
				throw new Exception("System tray not supported.");
			}
			trayIcon = new TrayIcon((Image)UIHelper.getResource("ui.common.icon"), "HyperCube");
			trayIcon.setImageAutoSize(true);
			SystemTray.getSystemTray().add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
			trayIcon = null;
		}
	}

	private void createFriendPanel()
	{
		JPanel p = new JPanel();
		p.setBackground(Color.LIGHT_GRAY);
		switcher.addPanel(p);
	}

	private void createDashboardPanel()
	{
		panel = new DashboardPanel();

		PushMessage msg = new PushMessage();
		msg.setTitle("标题");
		msg.setContent("这是一条很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的消息");
		panel.add(msg);

		msg = new PushMessage();
		msg.setTitle("Title");
		msg.setContent("这是一条很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的消息");
		panel.add(msg);
		
		switcher.addPanel(new RefreshablePanel(panel) {
			private static final long serialVersionUID = 1L;
			
			ActionListener finisher = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					finishRefresh();
				}
			};
			Timer stopRefresh = new Timer(2000, finisher);
			@Override
			public void onRefresh()
			{
				stopRefresh.setRepeats(false);
				stopRefresh.start();
			}
		});
	}

	private void createPanelSwitcher()
	{
		switcher = new PanelSwitcher(35);
		switcher.setBounds(5, 110, 300, 560);
	}

	private void createUserAvatarBox()
	{
		userAvatarBox = new AvatarBox(true);
		userAvatarBox.setLocation(15, 15);
		userAvatarBox.setAvatar(info.getUserAvatar());
	}
	
	private void createSignatureTextBox()
	{
		signatureTextBox = new TextBox(Color.BLACK, Color.BLACK);
		signatureTextBox.setLocation(115, 69);
		signatureTextBox.setOpaque(false);
		signatureTextBox.setText(info.getSignature());
		signatureTextBox.setToolTipText(signatureTextBox.getOriginalText());
		signatureTextBox.setEditable(false);
		
		final Border b = signatureTextBox.getBorder();
		signatureTextBox.setBorder(null);
		signatureTextBox.setFocusable(false);
		
		signatureTextBox.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				signatureTextBox.setFocusable(true);
				signatureTextBox.requestFocus();
				signatureTextBox.setEditable(true);
				signatureTextBox.setBorder(b);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		signatureTextBox.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					signatureTextBox.setFocusable(false);
					signatureTextBox.setEditable(false);
					signatureTextBox.setBorder(null);
					signatureTextBox.setToolTipText(signatureTextBox.getOriginalText());
					AWTUtilities.setWindowOpaque(frame, false);
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
	}
	
	@Override
	public void onAttach(ClientUI ui)
	{
		frame = getFrame();

		frame.startExpanding(680);
		frame.setTitle(info.getNickName());
		frame.setSubtitle("");
		frame.add(userAvatarBox);
		frame.add(signatureTextBox);
		frame.add(switcher);
		frame.setTitleLocation(110, 17);
		frame.setLocation(frame.getX() + 300, frame.getY() - 250);
		// TODO Initialize main frame.
	}

	@Override
	public void onDetach(ClientUI ui)
	{
		// TODO Free resources while detaching.
	}

	@Override
	public void onClosing(ClientUI ui)
	{
		// TODO On frame closing, usually hide it.
		// dispose frame in debug environment.
		getFrame().dispose();
	}

	// Skip server-specified call backs
	@Override
	public boolean onAcceptingSession(SocketChannel c)
	{
		return true;
	}

	// Skip server-specified call backs
	@Override
	public void onSessionAccepted(Session s) {}

	// Skip server-specified call backs
	@Override
	public void onUnregisteredSession(SocketChannel c) {}
	
	@Override
	public void onSessionClosed(Session s)
	{
		// TODO Session closed abruptly.
	}


	@Override
	public void onPacketReceived(Session s, Packet p)
	{
		// TODO Process network events.
	}

	@Override
	public void onConnected(Session s)
	{
		// TODO If reconnected to server.
	}

	@Override
	public void onConnectFailure(SocketChannel c)
	{
		// TODO If reconnecting failed.
	}
}