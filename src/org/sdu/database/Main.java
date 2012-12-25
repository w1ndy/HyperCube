package org.sdu.database;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

/**
 * Demo of database management application.
 * 
 * @version 0.1 rev 8002 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Main extends JFrame {

	private JList list;
	private Dimension modeButton = new Dimension(30, 30);
	private Dimension dataButton = new Dimension(25, 25);

	class pictextlist extends JPanel implements ListCellRenderer {
		private boolean isSelected;
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index,
			      boolean isSelected, boolean cellHasFocus) {
			this.isSelected=isSelected;
			return this;
		}

		public void paintComponent(Graphics g) {
			if (isSelected) {
				g.setColor(UIManager.getColor("controlHighlight"));
				g.fillRect(0, 0, 200, 100);
			}
			g.drawImage(new ImageIcon("art/database/nopic.png").getImage(), 0, 0, this);
			try {
				URL picURL = new URL("http://127.0.0.1/pic/1.jpg");
				g.drawImage(
						new ImageIcon(new ImageIcon(picURL).getImage()
								.getScaledInstance(75, 100,
										java.awt.Image.SCALE_SMOOTH))
								.getImage(), 0, 0, this);
			} catch (MalformedURLException e) {}
			g.setColor(new Color(0x000000));
			g.drawString("zgy", 80, 20);
			g.drawString("sdfkj", 80, 40);
			g.drawString("sdfkj", 80, 60);
			g.drawString("sdfkj", 80, 80);
		}

		public Dimension getPreferredSize() {
			return new Dimension(200, 100);
		}
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		// Window
		super("数据库管理");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu dataMenu = new JMenu("数据");
		menuBar.add(dataMenu);

		JMenuItem importData = new JMenuItem("导入数据库...");
		dataMenu.add(importData);
		
		JMenuItem exportData=new JMenuItem("导出数据库...");
		dataMenu.add(exportData);
		
		JMenuItem exportCurrentData=new JMenuItem("导出当前条目...");
		dataMenu.add(exportCurrentData);
		
		// Buttons
		JButton statButton = new JButton("统计");
		// statButton.addActionListener(this);

		final JButton filterButton = new JButton("筛选");
		// filterButton.addActionListener(this);
		getRootPane().setDefaultButton(filterButton);

		JButton addButton = new JButton(new ImageIcon("art/database/addicon.png"));
		addButton.setPreferredSize(dataButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton deleleButton = new JButton(new ImageIcon("art/database/deleteicon.png"));
		deleleButton.setPreferredSize(dataButton);

		JToggleButton textmode = new JToggleButton(new ImageIcon(
				"art/database/texticon.png"), true);
		textmode.setSelectedIcon(new ImageIcon("art/database/texticonselected.png"));
		textmode.setPreferredSize(modeButton);

		JToggleButton pictextmode = new JToggleButton(new ImageIcon(
				"art/database/pictexticon.png"));
		pictextmode.setSelectedIcon(new ImageIcon("art/database/pictexticonselected.png"));
		pictextmode.setPreferredSize(modeButton);

		JToggleButton picmode = new JToggleButton(new ImageIcon(
				"art/database/picicon.png"));
		picmode.setSelectedIcon(new ImageIcon("art/database/piciconselected.png"));
		picmode.setPreferredSize(modeButton);

		// Label
		JLabel totalNum = new JLabel("Showing 1~1000 of 47362");

		// List
		String[] name = new String[1000];
		for (int i = 0; i < 1000; i++)
			name[i] = "1";
		list = new JList(name);
		list.setCellRenderer(new pictextlist());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					filterButton.doClick(); // emulate button click
				}
			}
		});

		// Lay out
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setAlignmentX(LEFT_ALIGNMENT);

		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		listPane.add(listScroller);
		listPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		topPane.add(totalNum);
		topPane.add(Box.createHorizontalGlue());
		topPane.add(textmode);
		topPane.add(pictextmode);
		topPane.add(picmode);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(addButton);
		buttonPane.add(deleleButton);
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(statButton);
		buttonPane.add(filterButton);

		Container contentPane = getContentPane();
		contentPane.add(topPane, BorderLayout.NORTH);
		contentPane.add(listPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);
	}

	/**
	 * @param args
	 *            Launch
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setIconImage(new ImageIcon("art/database/icon.png").getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
