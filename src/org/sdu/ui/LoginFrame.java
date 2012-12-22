package org.sdu.ui;

/**
 * LoginFrame class implements a login window.
 * 
 * @version 0.1 rev 8000 Dec. 23, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class LoginFrame extends BasicFrame
{
	private static final long serialVersionUID = 1L;
	
	private AvatarBox avatarBox;
	
	/**
	 * Initialize login frame.
	 */
	public LoginFrame()
	{
		super((String)UIHelper.getResource("ui.string.login.title"),
				(String)UIHelper.getResource("ui.string.login.subtitle"));
		setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		
		// Add avatar box component.
		avatarBox = new AvatarBox();
		avatarBox.setBounds(UIHelper.avatarBoxOffsetX, UIHelper.avatarBoxOffsetY,
				UIHelper.avatarBoxWidth, UIHelper.avatarBoxHeight);
		add(avatarBox);
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