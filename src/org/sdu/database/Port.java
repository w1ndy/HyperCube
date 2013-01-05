package org.sdu.database;

import javax.swing.*;
import java.awt.event.*;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Import and export window.
 * 
 * @version 0.1 rev 8000 Jan. 5, 2013
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
class Port extends JFrame {
	private static final String[] NAME = { "导入", "导出" };
	private JTextField csvField,picField;

	Port(final Main frame, final int mode, final String[] id) {
		super(NAME[mode] + "数据");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				if (mode==0) frame.refresh();
			}
		});
		setBounds(150, 150, 370, 200);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel csvLabel = new JLabel("CSV文件：");
		csvLabel.setBounds(30, 35, 63, 16);
		getContentPane().add(csvLabel);
		
		JLabel picLabel = new JLabel("照片文件夹：");
		picLabel.setBounds(30, 75, 78, 16);
		getContentPane().add(picLabel);
		
		csvField = new JTextField();
		csvField.setBounds(120, 30, 134, 28);
		getContentPane().add(csvField);
		csvField.setColumns(10);
		csvLabel.setLabelFor(csvField);
		
		picField = new JTextField();
		picField.setBounds(120, 70, 134, 28);
		getContentPane().add(picField);
		picField.setColumns(10);
		picLabel.setLabelFor(picField);
		
		final JButton csvButton = new JButton("浏览...");
		csvButton.setBounds(266, 30, 80, 29);
		getContentPane().add(csvButton);
		final JFileChooser csvChooser=new JFileChooser();
		csvChooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File file) {
				boolean flag=false;
				if (file.isDirectory()||file.toString().endsWith(".csv"))
					flag=true;
				return flag;
			}

			@Override
			public String getDescription() {
				return "CSV 逗号分割的文件";
			}
		});
		csvButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result;
				if (mode==0)
					result=csvChooser.showOpenDialog(getParent());
				else
					result=csvChooser.showSaveDialog(getParent());
				if (result==JFileChooser.APPROVE_OPTION)
					csvField.setText(csvChooser.getSelectedFile().toString());
			}
		});
		
		final JButton picButton = new JButton("浏览...");
		picButton.setBounds(266, 70, 80, 29);
		getContentPane().add(picButton);
		final JFileChooser picChooser=new JFileChooser();
		picChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		picButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result;
				if (mode==0)
					result=picChooser.showOpenDialog(getParent());
				else
					result=picChooser.showSaveDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION)
					picField.setText(picChooser.getSelectedFile().toString());
			}
		});

		final JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(30, 130, 160, 20);
		getContentPane().add(progressBar);
		
		final JButton button = new JButton("开始");
		button.setBounds(230, 126, 117, 29);
		getContentPane().add(button);
		getRootPane().setDefaultButton(button);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				button.setEnabled(false);
				csvButton.setEnabled(false);
				picButton.setEnabled(false);
				csvField.setEnabled(false);
				picField.setEnabled(false);
				// TODO
			}
		});

		setVisible(true);
	}
}
