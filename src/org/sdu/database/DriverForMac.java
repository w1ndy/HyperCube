package org.sdu.database;

import java.awt.*;
import javax.swing.*;
import com.apple.eawt.*;

/**
 * Launch the database management application on OS X. Optimized for menu bar
 * and dock.
 * 
 * @version 0.1 rev 8001 Dec. 25, 2012
 * Copyright (c) HyperCube Dev Team
 */
public class DriverForMac {
	public static void main(String[] args) {
		// Title
		System.setProperty("com.apple.mrj.application.apple.menu.about.name",
				"数据库");
		// Menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// Dock Icon
		Application app = Application.getApplication();
		app.setDockIconImage(new ImageIcon("art/database/icon.png").getImage());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
