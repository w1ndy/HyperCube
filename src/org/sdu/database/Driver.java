package org.sdu.database;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.apple.eawt.*;

/**
 * Launch the database management application on OS X. Optimized for menu bar
 * and dock.
 * 
 * @version 0.1 rev 8002 Dec. 28, 2012
 * Copyright (c) HyperCube Dev Team
 */
public class Driver {
	public static void main(String[] args) {
		if (System.getProperty("os.name").equals("Mac OS X")) {
			// Title
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name", "数据库");
			// Menu bar
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			// Dock Icon
			Application.getApplication().setDockIconImage(
					new ImageIcon("art/database/icon.png").getImage());
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setIconImage(new ImageIcon("art/database/icon.png")
							.getImage());
					frame.nopic = ImageIO.read(new File(
							"art/database/nopic.png"));
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "找不到图片", "缺少文件",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}
		});
	}
}
