package org.sdu.client;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.SocketChannel;

import javax.swing.border.Border;

import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.ui.AvatarBox;
import org.sdu.ui.TextBox;

/**
 * MainUIHandler class handles UI events in main frame.
 * 
 * @version 0.1 rev 8001 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class MainUIHandler extends UIHandler
{
	public AvatarBox userAvatarBox;
	public TextBox signatureTextBox;
	
	public MainUIHandler()
	{
		createUserAvatarBox();
		createSignatureTextBox();
	}
	
	public void createUserAvatarBox()
	{
		userAvatarBox = new AvatarBox(true);
		userAvatarBox.setLocation(15, 15);
	}
	
	public void createSignatureTextBox()
	{
		signatureTextBox = new TextBox(Color.BLACK, Color.BLACK);
		signatureTextBox.setLocation(115, 73);
		signatureTextBox.setOpaque(false);
		signatureTextBox.setText("这是签名档。");
		signatureTextBox.setEditable(false);
		
		final Border b = signatureTextBox.getBorder();
		signatureTextBox.setBorder(null);
		
		signatureTextBox.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
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
					signatureTextBox.setEditable(false);
					signatureTextBox.setBorder(null);
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
		ClientFrame frame = getFrame();
		
		frame.setTitle("Arthas");
		frame.setSubtitle("");
		frame.startExpanding(680);
		frame.add(userAvatarBox);
		frame.add(signatureTextBox);
		frame.setTitleLocation(110, 17);
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