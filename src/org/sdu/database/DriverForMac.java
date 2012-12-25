package org.sdu.database;

import java.awt.*;
import javax.swing.*;
import com.apple.eawt.*;

public class DriverForMac {

	/**
	 * @param args
	 * Launch on OS X
	 */
	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "数据库");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		Application app=Application.getApplication();
		app.setDockIconImage (new ImageIcon ("art/database/icon.png").getImage());
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
