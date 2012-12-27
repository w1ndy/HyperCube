package org.sdu.database;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Database management application.
 * 
 * @version 0.1 rev 8005 Dec. 27, 2012
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
public class Main extends JFrame {
	private Dimension modeButton = new Dimension(30, 30);
	private Dimension dataButton = new Dimension(25, 25);
	private String[] name = new String[1000], idList = new String[1000],
			idNum = new String[1000], faculty = new String[1000],
			pic = new String[1000];
	private String listLabel = "";
	private Database stuData = new Database("stu");
	private boolean[] buffered = new boolean[1000];
	private BufferedImage[] bufferedImage = new BufferedImage[1000];
	private BufferedImage nopic;
	private Container contentPane;
	private JPanel listPane;
	private JToggleButton textmode, pictextmode, picmode;
	private Color chosen = new Color(0x3A70D6);
	private int currentMode = 1;

	// mode 1 = text-only; mode 2 = picture + text; mode 3 = picture-only

	private BufferedImage paintPic(int index) {
		if (!buffered[index])
			try {
				URL picURL = new URL("http://" + stuData.webserverAddress
						+ "/pic/"
						+ pic[index].substring(0, pic[index].length() - 5)
						+ "/" + pic[index].substring(pic[index].length() - 5)
						+ ".jpg");
				BufferedImage input = ImageIO.read(picURL);
				Image scaledImage = input.getScaledInstance(75, 100,
						Image.SCALE_DEFAULT);
				bufferedImage[index] = new BufferedImage(75, 100,
						BufferedImage.TYPE_INT_RGB);
				bufferedImage[index].createGraphics().drawImage(scaledImage, 0,
						0, null);
				buffered[index] = true;
			} catch (Exception e) {
			}
		return bufferedImage[index];
	}

	class PictextList extends JPanel implements ListCellRenderer {
		private boolean isSelected;
		private int index;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			this.isSelected = isSelected;
			this.index = index;
			return this;
		}

		public void paintComponent(Graphics g) {
			if (isSelected) {
				g.setColor(chosen);
				g.fillRect(0, 0, 200, 100);
				g.setColor(Color.white);
			}
			g.drawImage(nopic, 0, 0, this);
			if (pic[index].length() == 32)
				g.drawImage(paintPic(index), 0, 0, this);
			g.drawString(name[index], 80, 20);
			g.drawString(idList[index], 80, 40);
			g.drawString(faculty[index], 80, 60);
			g.drawString("山东大学", 80, 80);
		}

		public Dimension getPreferredSize() {
			return new Dimension(200, 100);
		}
	}

	class PicList extends JPanel implements ListCellRenderer {
		private boolean isSelected;
		private int index;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			this.isSelected = isSelected;
			this.index = index;
			return this;
		}

		public void paintComponent(Graphics g) {
			if (isSelected) {
				g.setColor(chosen);
				g.fillRect(0, 0, 75, 120);
				g.setColor(Color.white);
			}
			g.drawImage(nopic, 0, 0, this);
			if (pic[index].length() == 32)
				g.drawImage(paintPic(index), 0, 0, this);
			g.drawString(name[index], 0, 115);
		}

		public Dimension getPreferredSize() {
			return new Dimension(75, 120);
		}
	}

	/**
	 * mode 1 = text-only mode 2 = picture + text mode 3 = picture-only
	 * 
	 * @param mode
	 * @return
	 */
	private JScrollPane mainList(int mode) {
		JScrollPane listScroller;
		if (mode == 1) {
			String[] columnName = { "姓名", "学号", "院系", "身份证号" };
			String[][] cellData = new String[idList.length][4];
			for (int i = 0; i < idList.length; i++) {
				cellData[i][0] = name[i];
				cellData[i][1] = idList[i];
				cellData[i][2] = faculty[i];
				cellData[i][3] = idNum[i];
			}
			DefaultTableModel model = new DefaultTableModel(cellData,
					columnName) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			JTable table = new JTable(model);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

					}
				}
			});
			listScroller = new JScrollPane(table);
		} else {
			JList list;
			list = new JList(idList);
			if (mode == 2)
				list.setCellRenderer(new PictextList());
			else
				list.setCellRenderer(new PicList());
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			list.setVisibleRowCount(-1);
			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

					}
				}

			});
			listScroller = new JScrollPane(list);
		}
		return listScroller;
	}

	private void getData(String query) {
		String[] id = new String[1000];
		try {
			ResultSet rs;
			if (query == null)
				rs = stuData.getAll();
			else
				rs = stuData.getAll();
			int i = 0;
			while ((i < 1000) && rs.next()) {
				name[i] = rs.getString("name");
				id[i] = rs.getString("id");
				idNum[i] = rs.getString("idnum");
				faculty[i] = rs.getString("faculty");
				pic[i] = rs.getString("pic");
				i++;
			}
			if ((i == 1000) && rs.next()) {
				if (query == null)
					listLabel = stuData.getAllCount() + "个中的前1000个";
				else
					listLabel = stuData.getAllCount() + "个中的前1000个";
				idList = id;
			} else {
				listLabel = "共" + i + "个";
				idList = new String[i];
				for (int j = 0; j < i; j++)
					idList[j] = id[j];
			}
			rs.close();
			stuData.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数据库读取错误", "运行时错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		// Window
		super("数据库管理");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				stuData.close();
				System.exit(0);
			}
		});
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu dataMenu = new JMenu("数据");
		menuBar.add(dataMenu);

		JMenuItem importData = new JMenuItem("导入数据库...");
		dataMenu.add(importData);

		JMenuItem exportData = new JMenuItem("导出数据库...");
		dataMenu.add(exportData);

		JMenuItem exportCurrentData = new JMenuItem("导出当前条目...");
		dataMenu.add(exportCurrentData);

		// Buttons
		JButton statButton = new JButton("统计");
		// statButton.addActionListener(this);

		final JButton filterButton = new JButton("筛选");
		// filterButton.addActionListener(this);
		getRootPane().setDefaultButton(filterButton);

		JButton addButton = new JButton(new ImageIcon(
				"art/database/addicon.png"));
		addButton.setPreferredSize(dataButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton deleleButton = new JButton(new ImageIcon(
				"art/database/deleteicon.png"));
		deleleButton.setPreferredSize(dataButton);

		textmode = new JToggleButton(
				new ImageIcon("art/database/texticon.png"), true);
		textmode.setSelectedIcon(new ImageIcon(
				"art/database/texticonselected.png"));
		textmode.setPreferredSize(modeButton);

		pictextmode = new JToggleButton(new ImageIcon(
				"art/database/pictexticon.png"));
		pictextmode.setSelectedIcon(new ImageIcon(
				"art/database/pictexticonselected.png"));
		pictextmode.setPreferredSize(modeButton);

		picmode = new JToggleButton(new ImageIcon("art/database/picicon.png"));
		picmode.setSelectedIcon(new ImageIcon(
				"art/database/piciconselected.png"));
		picmode.setPreferredSize(modeButton);

		ButtonGroup mode = new ButtonGroup();
		mode.add(textmode);
		mode.add(pictextmode);
		mode.add(picmode);

		class modeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (textmode.isSelected()) {
					if (currentMode != 1) {
						listPane.removeAll();
						listPane.add(mainList(1));
						listPane.validate();
						currentMode = 1;
					}
				} else if (pictextmode.isSelected()) {
					if (currentMode != 2) {
						listPane.removeAll();
						listPane.add(mainList(2));
						listPane.validate();
						currentMode = 2;
					}
				} else if (picmode.isSelected()) {
					if (currentMode != 3) {
						listPane.removeAll();
						listPane.add(mainList(3));
						listPane.validate();
						currentMode = 3;
					}
				}
			}
		}
		textmode.addActionListener(new modeListener());
		pictextmode.addActionListener(new modeListener());
		picmode.addActionListener(new modeListener());

		// Get all students' data
		getData(null);

		// Label
		JLabel totalNum = new JLabel(listLabel);

		// List
		try {
			nopic = ImageIO.read(new File("art/database/nopic.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "找不到图片", "缺少资源",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		// Lay out
		listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		listPane.add(mainList(1));
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

		contentPane = getContentPane();
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
					frame.setIconImage(new ImageIcon("art/database/icon.png")
							.getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
