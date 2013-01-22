package org.sdu.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Generate SQL query.
 * 
 * @version 0.1 rev 8000 Jan. 22, 2013
 * Copyright (c) HyperCube Dev Team
 */
@SuppressWarnings("serial")
public class Filter extends JFrame {
	private static final int MAX_CONDITION = 128;
	private static final String[] compareListNull = { "=", "like", "空", "非空" };
	private static final String[] compareList = { "=", "like" };
	private JPanel listPane = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(listPane);
	private JPanel[] conditionPane = new JPanel[MAX_CONDITION];
	private JButton[] deleteButton = new JButton[MAX_CONDITION];
	private JComboBox<?>[] colBox = new JComboBox[MAX_CONDITION],
			compareBox = new JComboBox[MAX_CONDITION],
			enumBox = new JComboBox[MAX_CONDITION];
	private JTextField[] field = new JTextField[MAX_CONDITION];
	private boolean[] showEnumBox = new boolean[MAX_CONDITION],
			nullList = new boolean[MAX_CONDITION];
	private int conditionNum = 0;
	private String[] colList = new String[50], colNameList = new String[50];
	private int[] colTypeList = new int[50];
	private String[][] enumList = new String[50][];

	public Filter(Database db) {
		super("筛选");
		setSize(500, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		((JComponent) getContentPane()).setBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10));

		JButton addButton = new JButton("添加");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conditionNum < MAX_CONDITION)
					addCondition();
				else
					JOptionPane.showMessageDialog(Filter.this, "条件过多！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JButton finishButton = new JButton("完成");
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedCol = new int[conditionNum];
				for (int i = 0; i < conditionNum; i++)
					selectedCol[i] = colBox[i].getSelectedIndex();
				String query = "";
				boolean firstCol = true;
				for (int i = 0; i < 50; i++) {
					boolean firstCondition = true;
					for (int j = 0; j < conditionNum; j++)
						if (i == selectedCol[j]) {
							if (firstCol)
								firstCol = false;
							else if (firstCondition)
								query += "and";
							if (firstCondition) {
								query += "(";
								firstCondition = false;
							} else
								query += " or ";
							query += colNameList[i];
							switch (compareBox[j].getSelectedIndex()) {
							case 0:
								query += "=";
								break;
							case 1:
								query += " like ";
								break;
							case 2:
								query += " is null";
								continue;
							case 3:
								query += " is not null";
								continue;
							}
							switch (colTypeList[i]) {
							case 1:
							case 3:
								query += "'" + field[j].getText() + "'";
								break;
							case 2:
								query += field[j].getText();
								break;
							case 4:
								query += String.valueOf(enumBox[j]
										.getSelectedIndex());
							}
						}
					if (!firstCondition)
						query += ")";
				}
				if (firstCol)
					query = null;
				System.out.println(query); // TODO
				setVisible(false);
			}
		});

		listPane.setOpaque(false);
		listPane.setLayout(new WrapLayout(FlowLayout.LEFT));

		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout(0, 0));
		bottomPane.add(addButton, BorderLayout.WEST);
		bottomPane.add(finishButton, BorderLayout.EAST);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(bottomPane, BorderLayout.SOUTH);

		colList[0] = "学号";
		colNameList[0] = "id";
		colTypeList[0] = 1;
		for (int x = 0; x < 7; x++)
			for (int y = 0; y < 7; y++) {
				colList[x * 7 + y + 1] = List.COLUMN[x][y];
				colNameList[x * 7 + y + 1] = List.COLUMN_NAME[x][y];
				colTypeList[x * 7 + y + 1] = List.COLUMN_TYPE[x][y];
				if (List.COLUMN_TYPE[x][y] == 4)
					enumList[x * 7 + y + 1] = db.getEnumList(x, y);
			}
	}

	private class DeleteListener implements ActionListener {
		private JPanel pane;

		DeleteListener(JPanel pane) {
			this.pane = pane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			listPane.remove(pane);
			scrollPane.validate();
			scrollPane.updateUI();

			boolean flag = false;
			conditionNum--;
			for (int i = 0; i < conditionNum; i++) {
				if (conditionPane[i] == pane)
					flag = true;
				if (flag) {
					conditionPane[i] = conditionPane[i + 1];
					deleteButton[i] = deleteButton[i + 1];
					colBox[i] = colBox[i + 1];
					compareBox[i] = compareBox[i + 1];
					enumBox[i] = enumBox[i + 1];
					field[i] = field[i + 1];
					showEnumBox[i] = showEnumBox[i + 1];
					nullList[i] = nullList[i + 1];
				}
			}
			conditionPane[conditionNum] = null;
			deleteButton[conditionNum] = null;
			colBox[conditionNum] = null;
			compareBox[conditionNum] = null;
			enumBox[conditionNum] = null;
			field[conditionNum] = null;
			showEnumBox[conditionNum] = false;
			nullList[conditionNum] = false;
		}
	}

	private class NullListener implements ActionListener {
		private JComboBox<?> box;

		NullListener(JComboBox<?> box) {
			this.box = box;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = -1;
			for (int i = 0; i < conditionNum; i++)
				if (compareBox[i] == box) {
					index = i;
					break;
				}
			if (index != -1)
				if (box.getSelectedIndex() > 1)
					field[index].setEnabled(false);
				else
					field[index].setEnabled(true);
		}
	}

	private class ColumnListener implements ActionListener {
		private JComboBox<?> box;

		ColumnListener(JComboBox<?> box) {
			this.box = box;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = -1;
			for (int i = 0; i < conditionNum; i++)
				if (colBox[i] == box) {
					index = i;
					break;
				}
			if (index != -1) {
				int selected = box.getSelectedIndex();
				switch (colTypeList[selected]) {
				case 1:
					if (nullList[index]) {
						nullList[index] = false;
						conditionPane[index].remove(compareBox[index]);
						compareBox[index] = new JComboBox<String>(compareList);
						conditionPane[index].add(compareBox[index], 2);
					} else
						compareBox[index].setEnabled(true);
					if (showEnumBox[index]) {
						showEnumBox[index] = false;
						conditionPane[index].remove(enumBox[index]);
						conditionPane[index].add(field[index]);
					}
					field[index].setEnabled(true);
					break;
				case 2:
				case 3:
					if (!nullList[index]) {
						nullList[index] = true;
						conditionPane[index].remove(compareBox[index]);
						compareBox[index] = new JComboBox<String>(
								compareListNull);
						compareBox[index].addActionListener(new NullListener(
								compareBox[index]));
						conditionPane[index].add(compareBox[index], 2);
					} else
						compareBox[index].setEnabled(true);
					if (showEnumBox[index]) {
						showEnumBox[index] = false;
						conditionPane[index].remove(enumBox[index]);
						conditionPane[index].add(field[index]);
					}
					field[index].setEnabled(true);
					break;
				case 4:
					if (nullList[index]) {
						nullList[index] = false;
						conditionPane[index].remove(compareBox[index]);
						compareBox[index] = new JComboBox<String>(compareList);
						conditionPane[index].add(compareBox[index], 2);
					} else
						compareBox[index].setSelectedIndex(0);
					compareBox[index].setEnabled(false);
					if (!showEnumBox[index]) {
						showEnumBox[index] = true;
						conditionPane[index].remove(field[index]);
						enumBox[index] = new JComboBox<String>(
								enumList[selected]);
						enumBox[index].setPreferredSize(new Dimension(135, 25));
						conditionPane[index].add(enumBox[index]);
					}
					field[index].setEnabled(true);
				}
				listPane.validate();
			}
		}
	}

	private void addCondition() {
		conditionPane[conditionNum] = new JPanel();

		deleteButton[conditionNum] = new JButton("删除");
		deleteButton[conditionNum].addActionListener(new DeleteListener(
				conditionPane[conditionNum]));

		colBox[conditionNum] = new JComboBox<String>(colList);
		colBox[conditionNum].addActionListener(new ColumnListener(
				colBox[conditionNum]));

		compareBox[conditionNum] = new JComboBox<String>(compareList);

		field[conditionNum] = new JTextField();
		field[conditionNum].setColumns(10);

		conditionPane[conditionNum].setOpaque(false);
		conditionPane[conditionNum].setLayout(new FlowLayout());
		conditionPane[conditionNum].add(deleteButton[conditionNum]);
		conditionPane[conditionNum].add(colBox[conditionNum]);
		conditionPane[conditionNum].add(compareBox[conditionNum]);
		conditionPane[conditionNum].add(field[conditionNum]);
		listPane.add(conditionPane[conditionNum]);
		conditionNum++;
		scrollPane.validate();
	}
}
