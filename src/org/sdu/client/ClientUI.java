package org.sdu.client;

import org.sdu.ui.AvatarBox;
import org.sdu.ui.BasicFrame;
import org.sdu.ui.UIHelper;

/**
 * ClientUI class implements a user interface of student user.
 * 
 * @version 0.1 rev 8000 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class ClientUI
{
	private BasicFrame frame;
	private AvatarBox avatarBox;
	
	/**
	 * Initialize ClientUI object.
	 */
	public ClientUI()
	{
		frame = new BasicFrame((String)UIHelper.getResource("ui.string.login.title"),
								(String)UIHelper.getResource("ui.string.login.subtitle"));
		
		avatarBox = new AvatarBox();
		avatarBox.setBounds(UIHelper.avatarBoxOffsetX, UIHelper.avatarBoxOffsetY,
				UIHelper.avatarBoxWidth, UIHelper.avatarBoxHeight);
		frame.add(avatarBox);
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
