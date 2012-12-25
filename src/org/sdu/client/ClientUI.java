package org.sdu.client;

import javax.swing.JFrame;

import org.sdu.ui.AvatarBox;
import org.sdu.ui.BasicFrame;
import org.sdu.ui.PasswordBox;
import org.sdu.ui.RectBorder;
import org.sdu.ui.TextBox;
import org.sdu.ui.UIHelper;

/**
 * ClientUI class implements a user interface of student user.
 * 
 * @version 0.1 rev 8001 Dec. 26, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientUI
{
	private BasicFrame frame;
	private AvatarBox avatarBox;
	private TextBox	userBox;
	private PasswordBox passBox;
	
	/**
	 * Initialize ClientUI object.
	 */
	public ClientUI()
	{
		frame = new BasicFrame((String)UIHelper.getResource("ui.string.login.title"),
								(String)UIHelper.getResource("ui.string.login.subtitle"));
		frame.setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		
		avatarBox = new AvatarBox();
		avatarBox.setBounds(UIHelper.avatarBoxLoginOffsetX, UIHelper.avatarBoxLoginOffsetY,
				UIHelper.avatarBoxWidth, UIHelper.avatarBoxHeight);
		frame.add(avatarBox);
		
		userBox = new TextBox("UserName");
		userBox.setBorder(new RectBorder(UIHelper.darkColor));
		userBox.setBounds(UIHelper.usernameBoxOffsetX, UIHelper.usernameBoxOffsetY,
				UIHelper.textBoxWidth, UIHelper.textBoxHeight);
		frame.add(userBox);
		
		passBox = new PasswordBox("Password");
		passBox.setBorder(new RectBorder(UIHelper.darkColor));
		passBox.setBounds(UIHelper.passwordBoxOffsetX, UIHelper.passwordBoxOffsetY,
				UIHelper.textBoxWidth, UIHelper.textBoxHeight);
		frame.add(passBox);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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
