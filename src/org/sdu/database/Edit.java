package org.sdu.database;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import javax.imageio.ImageIO;

/**
 * Create and edit information.
 * 
 * @version 0.1 rev 8101 Jan. 5, 2013
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
public class Edit extends JFrame {
	private static final String[] name = { "添加", "编辑" };
	JTextField idField;
	private Main main;
	private int mode;
	private JComponent[][] field = new JComponent[7][7];
	private JLabel pic;
	private String id;
	private ResultSet rs;
	private String[][] content;

	class NumberInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent field) {
			boolean flag = false;
			if (field instanceof JTextField) {
				try {
					Long.parseLong(((JTextField) field).getText());
					flag = true;
				} catch (Exception e) {
				}
			}
			return flag;
		}
	}

	class NullInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent field) {
			boolean flag = false;
			if (field instanceof JTextField) {
				if (((JTextField) field).getText().length() > 0)
					flag = true;
			}
			return flag;
		}
	}

	/**
	 * mode 0 = add, mode 1 = edit
	 */
	public Edit(Main frame, int mode, String id) {
		super(name[mode] + "资料");
		main = frame;
		this.mode = mode;
		this.id=id;
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				main.refresh();
			}
		});
		setBounds(150, 150, 500, 400);
		setResizable(false);

		// Get all info
		if (mode == 1) {
			try {
				rs = frame.database.getOne(id);
				rs.next();
				content=new String[7][7];
				for (int i=0;i<7;i++)
					for (int j=0;j<7;j++)
						content[i][j]=rs.getString(List.columnName[i][j]);
				String picAddress=rs.getString("pic");
				rs.close();
				URL picURL = new URL("http://" + main.database.webserverAddress
						+ "/pic/"
						+ picAddress.substring(0, picAddress.length() - 5)
						+ "/" + picAddress.substring(picAddress.length() - 5)
						+ ".jpg");
				pic = new JLabel(new ImageIcon(
						(ImageIO.read(picURL)).getScaledInstance(150, 200,
								java.awt.Image.SCALE_SMOOTH)));
				pic.setBounds(40, 10, 150, 200);
			} catch (Exception e) {
			}
		}
		
		JPanel buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton button = new JButton("保存");
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(button);
		getRootPane().setDefaultButton(button);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("基本", basicInfo());
		tabbedPane.addTab("更多", contactInfo());
		tabbedPane.addTab("培养", teachInfo());
		tabbedPane.addTab("其他", studyInfo());

		setVisible(true);
		idField.requestFocus();
	}

	private JPanel basicInfo() {
		JPanel basicInfo = new JPanel(true);
		basicInfo.setOpaque(false);
		basicInfo.setLayout(null);

		// picture
		try {
			basicInfo.add(pic);
		} catch (Exception e) {
		}
		JLabel picback = new JLabel(new ImageIcon("art/database/addpic.png"));
		picback.setBounds(40, 10, 158, 208);
		basicInfo.add(picback);

		// id
		idField = new JTextField();
		idField.setInputVerifier(new NullInputVerifier());
		JLabel idLabel = new JLabel("学号*：");
		idField.setBounds(90, 230, 134, 28);
		idLabel.setBounds(20, 230, 134, 28);
		idLabel.setLabelFor(idField);
		basicInfo.add(idField);
		basicInfo.add(idLabel);
		if (mode == 1)
			(idField).setText(id);

		for (int i = 0; i < 7; i++)
			field[0][i] = field(basicInfo, 0, i);

		return basicInfo;
	}

	private JPanel contactInfo() {
		JPanel contactInfo = new JPanel(true);
		contactInfo.setOpaque(false);
		contactInfo.setLayout(null);

		for (int i = 1; i < 3; i++)
			for (int j = 0; j < 7; j++)
				field[0][j] = field(contactInfo, i, j);

		return contactInfo;
	}

	private JPanel teachInfo() {
		JPanel teachInfo = new JPanel(true);
		teachInfo.setOpaque(false);
		teachInfo.setLayout(null);

		for (int i = 3; i < 5; i++)
			for (int j = 0; j < 7; j++)
				field[0][j] = field(teachInfo, i, j);

		return teachInfo;
	}

	private JPanel studyInfo() {
		JPanel studyInfo = new JPanel(true);
		studyInfo.setOpaque(false);
		studyInfo.setLayout(null);

		for (int i = 5; i < 7; i++)
			for (int j = 0; j < 7; j++)
				field[0][j] = field(studyInfo, i, j);

		return studyInfo;
	}

	// type 1 = TextField, type 2 = NumberField, type 3 = DateField, type 4 =
	// ComboBox
	JComponent field(JPanel pane, int x, int y) {
		JComponent field = null;
		switch (List.columnType[x][y]) {
		case 1:
			field = new JTextField();
			if (mode == 1)
				((JTextField) field).setText(content[x][y]);
			break;
		case 2:
			field = new JTextField();
			field.setInputVerifier(new NumberInputVerifier());
			if (mode == 1)
				((JTextField) field).setText(content[x][y]);
			break;
		case 3:
			field = new JFormattedTextField(DateFormat.getDateInstance());
			if (mode == 1)
				((JFormattedTextField) field).setText(content[x][y]);
			break;
		case 4:
			String[] list=main.database.getEnumList(x, y);
			field = new JComboBox(list);
			if (mode == 1)
				for (int i=1;i<list.length;i++)
					if (content[x][y].equals(list[i])) {
						((JComboBox) field).setSelectedIndex(i);
						break;
					}
		}
		JLabel label = new JLabel(List.column[x][y] + "：");
		if (x % 2 == 0) {
			field.setBounds(320, y * 35 + 20, 134, 28);
			label.setBounds(250, y * 35 + 20, 134, 28);
		} else {
			field.setBounds(90, y * 35 + 20, 134, 28);
			label.setBounds(20, y * 35 + 20, 134, 28);
		}
		label.setLabelFor(field);
		pane.add(field);
		pane.add(label);
		return field;
	}
}
