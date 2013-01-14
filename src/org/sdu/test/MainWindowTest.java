package org.sdu.test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.sdu.client.ClientFrame;
import org.sdu.ui.AvatarBox;
import org.sdu.ui.DashboardPanel;
import org.sdu.ui.PanelSwitcher;
import org.sdu.ui.PushMessage;
import org.sdu.ui.RefreshablePanel;
import org.sdu.ui.UIHelper;
import org.sdu.util.DebugFramework;

/**
 * Create a test-purposed main window.
 *
 * @version 0.1 rev 8005 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class MainWindowTest extends ClientFrame
{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("MainWindowTest.log");
		UIHelper.loadResource("art/resource.xml");
		new MainWindowTest();
	}
	
	public MainWindowTest()
	{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(UIHelper.loginFrameWidth, UIHelper.loginFrameHeight);
		setTitle("Arthas");
		titleOffsetX += 95;
		titleOffsetY += 5;
		
		AvatarBox avatar = new AvatarBox(true);
		avatar.setBounds(UIHelper.LoginAvatarBoxOffsetX, 10,
				UIHelper.DefaultAvatarBoxWidth, UIHelper.DefaultAvatarBoxHeight);
		try {
			avatar.setAvatar(ImageIO.read(new File("art/testavatar.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		add(avatar);
		
		DashboardPanel dash = new DashboardPanel();
		
		PushMessage msg1 = new PushMessage();
		msg1.setTitle("管理猿 (10086) 给您发来一条消息");
		msg1.setContent("您好，欢迎使用HyperCube 2013。\n这是Dashboard界面。您可以在这里接收最新发布的公告及留言等信息。");
		dash.add(msg1);
		
		PushMessage msg2 = new PushMessage();
		msg2.setTitle("图书馆还书通知");
		msg2.setContent("您的一本《失落的秘符》即将到期。");
		dash.add(msg2);
		
		PushMessage msg3 = new PushMessage();
		msg3.setTitle("您有 2 条未读公告");
		msg3.setContent("2012 - 2013第一学期期末考试安排已公布。");
		dash.add(msg3);
		
		PanelSwitcher switcher = new PanelSwitcher(35);
		switcher.setBounds(5, 110, 300, 560);
		JPanel p1, p2, p3;
		p2 = new JPanel();
		p3 = new JPanel();
		p2.setBackground(Color.BLUE);
		p3.setBackground(Color.GREEN);
		switcher.addPanel(new RefreshablePanel(dash) {
			private static final long serialVersionUID = 1L;

			protected void onRefresh()
			{
				Timer t = new Timer(2000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						finishRefresh();
					}
				});
				t.setRepeats(false);
				t.start();
			}
		});
		//p1.add(label);
//		switcher.addPanel(new RefreshablePanel(p1) {
//			private static final long serialVersionUID = 1L;
//
//			protected void onRefresh()
//			{
//				Timer t = new Timer(2000, new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						finishRefresh();
//					}
//				});
//				t.setRepeats(false);
//				t.start();
//			}
//		});
		switcher.addPanel(p2);
		switcher.addPanel(p3);
		add(switcher);
		
		setVisible(true);
		
		startExpanding(680);
	}
}