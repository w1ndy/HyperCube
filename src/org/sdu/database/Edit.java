package org.sdu.database;

import java.awt.*;
import javax.swing.*;

import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 * Create and edit information.
 * 
 * @version 0.1 rev 8002 Jan. 3, 2012
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
public class Edit extends JFrame {
	private Main main;
	private int mode;
	private static final String[] name = { "添加", "编辑" };

	/**
	 * mode 0 = add mode 1 = edit
	 */
	public Edit(Main frame, int mode) {
		super(name[mode] + "资料");
		main=frame;
		this.mode=mode;
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				main.refresh();
			}
		});
		setBounds(150, 150, 500, 400);
		setResizable(false);

		JPanel buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton button = new JButton("保存");
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(button);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("基本信息", basicInfo());
		tabbedPane.addTab("更多信息", moreInfo());
	}

	private JPanel basicInfo() {
		JPanel basicInfo = new JPanel(true);
		basicInfo.setOpaque(false);
		basicInfo.setLayout(null);

		// picture
		try {
			URL picURL = new URL("http://127.0.0.1/pic/1.jpg");
			JLabel pic = new JLabel(new ImageIcon(
					(ImageIO.read(picURL)).getScaledInstance(150, 200,
							java.awt.Image.SCALE_SMOOTH)));
			pic.setBounds(35, 25, 150, 200);
			basicInfo.add(pic);
		} catch (Exception e) {
		}
		JLabel picback = new JLabel(new ImageIcon("art/database/addpic.png"));
		picback.setBounds(35, 25, 158, 208);
		basicInfo.add(picback);

		// name
		JTextField nameField = new JTextField();
		JLabel nameLabel = new JLabel("姓名：");
		nameField.setBounds(320, 10, 134, 28);
		nameLabel.setBounds(250, 10, 134, 28);
		nameLabel.setLabelFor(nameField);
		basicInfo.add(nameField);
		basicInfo.add(nameLabel);

		// engname
		JTextField engnameField = new JTextField();
		JLabel engnameLabel = new JLabel("英文姓名：");
		engnameField.setBounds(320, 45, 134, 28);
		engnameLabel.setBounds(250, 45, 134, 28);
		engnameLabel.setLabelFor(engnameField);
		basicInfo.add(engnameField);
		basicInfo.add(engnameLabel);

		// id
		JTextField idField = new JTextField();
		JLabel idLabel = new JLabel("学号：");
		idField.setBounds(320, 80, 134, 28);
		idLabel.setBounds(250, 80, 134, 28);
		idLabel.setLabelFor(idField);
		basicInfo.add(idField);
		basicInfo.add(idLabel);

		// sex
		String[] sex = { "男", "女" };
		JComboBox sexBox = new JComboBox(sex);
		JLabel sexLabel = new JLabel("性别：");
		sexBox.setBounds(320, 115, 134, 28);
		sexLabel.setBounds(250, 115, 134, 28);
		sexLabel.setLabelFor(sexBox);
		basicInfo.add(sexBox);
		basicInfo.add(sexLabel);

		// hometown
		JTextField hometownField = new JTextField();
		JLabel hometownLabel = new JLabel("籍贯：");
		hometownField.setBounds(320, 150, 134, 28);
		hometownLabel.setBounds(250, 150, 134, 28);
		hometownLabel.setLabelFor(hometownField);
		basicInfo.add(hometownField);
		basicInfo.add(hometownLabel);

		// country
		JTextField countryField = new JTextField();
		JLabel countryLabel = new JLabel("国别：");
		countryField.setBounds(320, 185, 134, 28);
		countryLabel.setBounds(250, 185, 134, 28);
		countryLabel.setLabelFor(countryField);
		basicInfo.add(countryField);
		basicInfo.add(countryLabel);

		// nation
		String[] nation = { "汉族", "东乡族", "乌孜别克族", "仡佬族", "仫佬族", "佤族", "侗族",
				"俄罗斯族", "傣族", "哈尼族", "哈萨克族", "回族", "土家族", "土族", "壮族", "布依族",
				"彝族", "普米族", "朝鲜族", "柯尔克孜族", "水族", "满族", "珞巴族", "瑶族", "畲族",
				"白族", "纳西族", "维吾尔族", "羌族", "苗族", "蒙古族", "藏族", "裕固族", "达斡尔族",
				"鄂伦春族", "鄂温克族", "锡伯族", "门巴族", "黎族", "其他" };
		JComboBox nationBox = new JComboBox(nation);
		JLabel nationLabel = new JLabel("民族：");
		nationBox.setBounds(320, 220, 134, 28);
		nationLabel.setBounds(250, 220, 134, 28);
		nationLabel.setLabelFor(nationBox);
		basicInfo.add(nationBox);
		basicInfo.add(nationLabel);

		return basicInfo;
	}

	private JPanel moreInfo() {
		JPanel moreInfo = new JPanel(true);
		moreInfo.setOpaque(false);
		moreInfo.setLayout(null);

		// married
		String[] married = { "否", "是" };
		JComboBox marriedBox = new JComboBox(married);
		JLabel marriedLabel = new JLabel("婚否：");
		marriedBox.setBounds(100, 10, 134, 28);
		marriedLabel.setBounds(30, 10, 134, 28);
		marriedLabel.setLabelFor(marriedBox);
		moreInfo.add(marriedBox);
		moreInfo.add(marriedLabel);

		// identity
		String[] identity = { "群众", "中国共产党党员", "中国共产党预备党员", "中国共产主义青年团团员",
				"少先队员", "民革会员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员",
				"九三学社社员", "台盟盟员", "无党派民主人士", "港澳同胞" };
		JComboBox identityBox = new JComboBox(identity);
		JLabel identityLabel = new JLabel("政治面貌：");
		identityBox.setBounds(100, 45, 134, 28);
		identityLabel.setBounds(30, 45, 134, 28);
		identityLabel.setLabelFor(identityBox);
		moreInfo.add(identityBox);
		moreInfo.add(identityLabel);

		// birth
		JFormattedTextField birthField = new JFormattedTextField(
				DateFormat.getDateInstance());
		JLabel birthLabel = new JLabel("出生日期：");
		birthField.setText(DateFormat.getDateInstance().format(new Date()));
		birthField.setBounds(100, 80, 134, 28);
		birthLabel.setBounds(30, 80, 134, 28);
		birthLabel.setLabelFor(birthField);
		moreInfo.add(birthField);
		moreInfo.add(birthLabel);

		/*
		 * // name JTextField nameField = new JTextField(); JLabel nameLabel =
		 * new JLabel("姓名："); nameField.setBounds(320, 10, 134, 28);
		 * nameLabel.setBounds(250, 10, 134, 28);
		 * nameLabel.setLabelFor(nameField); basicInfo.add(nameField);
		 * basicInfo.add(nameLabel);
		 * 
		 * // engname JTextField engnameField = new JTextField(); JLabel
		 * engnameLabel = new JLabel("英文姓名："); engnameField.setBounds(320, 45,
		 * 134, 28); engnameLabel.setBounds(250, 45, 134, 28);
		 * engnameLabel.setLabelFor(engnameField); basicInfo.add(engnameField);
		 * basicInfo.add(engnameLabel);
		 * 
		 * // id JTextField idField = new JTextField(); JLabel idLabel = new
		 * JLabel("学号："); idField.setBounds(320, 80, 134, 28);
		 * idLabel.setBounds(250, 80, 134, 28); idLabel.setLabelFor(idField);
		 * basicInfo.add(idField); basicInfo.add(idLabel);
		 * 
		 * // sex String[] sex = { "男", "女" }; JComboBox sexBox = new
		 * JComboBox(sex); JLabel sexLabel = new JLabel("性别：");
		 * sexBox.setBounds(320, 115, 134, 28); sexLabel.setBounds(250, 115,
		 * 134, 28); sexLabel.setLabelFor(sexBox); basicInfo.add(sexBox);
		 * basicInfo.add(sexLabel);
		 * 
		 * // hometown JTextField hometownField = new JTextField(); JLabel
		 * hometownLabel = new JLabel("籍贯："); hometownField.setBounds(320, 150,
		 * 134, 28); hometownLabel.setBounds(250, 150, 134, 28);
		 * hometownLabel.setLabelFor(hometownField);
		 * basicInfo.add(hometownField); basicInfo.add(hometownLabel);
		 * 
		 * // country JTextField countryField = new JTextField(); JLabel
		 * countryLabel = new JLabel("国别："); countryField.setBounds(320, 185,
		 * 134, 28); countryLabel.setBounds(250, 185, 134, 28);
		 * countryLabel.setLabelFor(countryField); basicInfo.add(countryField);
		 * basicInfo.add(countryLabel);
		 * 
		 * // nation String[] nation = { "汉族", "东乡族", "乌孜别克族", "仡佬族", "仫佬族",
		 * "佤族", "侗族", "俄罗斯族", "傣族", "哈尼族", "哈萨克族", "回族", "土家族", "土族", "壮族",
		 * "布依族", "彝族", "普米族", "朝鲜族", "柯尔克孜族", "水族", "满族", "珞巴族", "瑶族", "畲族",
		 * "白族", "纳西族", "维吾尔族", "羌族", "苗族", "蒙古族", "藏族", "裕固族", "达斡尔族", "鄂伦春族",
		 * "鄂温克族", "锡伯族", "门巴族", "黎族", "其他" }; JComboBox nationBox = new
		 * JComboBox(nation); JLabel nationLabel = new JLabel("民族：");
		 * nationBox.setBounds(320, 220, 134, 28); nationLabel.setBounds(250,
		 * 220, 134, 28); nationLabel.setLabelFor(nationBox);
		 * basicInfo.add(nationBox); basicInfo.add(nationLabel);
		 */
		return moreInfo;
	}
}
