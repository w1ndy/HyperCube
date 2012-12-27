package org.sdu.database;

import java.awt.*;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * Create and edit information.
 * 
 * @version 0.1 rev 8001 Dec. 27, 2012
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
public class Edit extends JFrame {
	private Main mainWindow;
	private static final String[] name={"添加","编辑"};

	/**
	 * mode 0 = add
	 * mode 1 = edit
	 * 
	 * @param mode
	 */
	public static void create(Main mainWindow, int mode) {
		try {
			Edit frame = new Edit(mode);
			frame.mainWindow = mainWindow;
			frame.setVisible(true);
		} catch (Exception e) {
		}
	}

	/**
	 * Create the frame.
	 */
	public Edit(int mode) {
		super(name[mode]+"资料");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				mainWindow.refresh();
			}
		});
		setBounds(100, 100, 450, 300);
		setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton button_1 = new JButton("放弃");
		panel.add(button_1);
		
		JButton button = new JButton("保存");
		panel.add(button);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
	}
}
