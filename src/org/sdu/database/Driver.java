package org.sdu.database;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.lang.reflect.Method;

/**
 * Launch the database management application. Optimized for menu bar and dock
 * on OS X.
 * 
 * @version 0.1 rev 8007 Jan. 6, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Driver {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			if (System.getProperty("os.name").startsWith("Mac OS")) {
				// Title
				System.setProperty(
						"com.apple.mrj.application.apple.menu.about.name",
						"数据库");
				// Menu bar
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				// Dock Icon

				Class<?> app = Class.forName("com.apple.eawt.Application");
				Method getapp = app
						.getMethod("getApplication", new Class<?>[0]);
				Object app_obj = getapp.invoke(null, new Object[0]);
				Method seticon = app.getMethod("setDockIconImage",
						new Class[] { Image.class });
				seticon.invoke(app.cast(app_obj), new Object[] { ImageIO
						.read(new File("art/database/icon.png")) });
			}
		} catch (Exception e) {
		}

		try {
			Configure.read();
			if (!new File(Configure.siteDirectory + "pic/").isDirectory())
				JOptionPane.showMessageDialog(null,
						"如果需要添加、删除照片，\n请在网页服务器上运行此程序。", "提示",
						JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "启动失败",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setIconImage(ImageIO.read(new File(
							"art/database/icon.png")));
					frame.nopic = ImageIO.read(new File(
							"art/database/nopic.png"));
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "初始化错误", "无法启动",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}
		});
	}
}
