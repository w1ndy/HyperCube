package org.sdu.server.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.sdu.server.Core;
import org.sdu.server.DatabaseInterface;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.ProgressBar;

public class ServerMan {
	public static DatabaseInterface db;
	private static Table table;
	static Core core;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("服务器管理程序");
		
		Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setBounds(10, 10, 94, 14);
		label.setText("服务器管理");
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("开启与关闭");
		group.setBounds(110, 10, 260, 50);
		
		Button button = new Button(group, SWT.NONE);
		button.setBounds(10, 0, 94, 28);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					try {
						core = new Core(db);
						MessageBox message = new MessageBox(shell,SWT.OK);
						message.setMessage("服务器已运行");
		                message.setText("服务器已运行");
		                message.open();
					} catch (Exception e1) {	
						if (e1.getMessage().equals("DB")){
							MessageBox message = new MessageBox(shell,SWT.OK);
							message.setMessage("服务器连接数据库失败");
			                message.setText("失败");
			                message.open();
						}
					}
			}
		});
		button.setText("启动服务器");
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.setBounds(142, 0, 94, 28);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					core.CloseServer();
					MessageBox message = new MessageBox(shell,SWT.OK);
					message.setMessage("服务器已关闭");
	                message.setText("服务器已关闭");
	                message.open();
				} catch (Exception e1) {
					MessageBox message = new MessageBox(shell,SWT.OK);
					message.setMessage("服务器未运行");
	                message.setText("错误");
	                message.open();
				}
				
			}
		});
		button_1.setText("关闭服务器");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(10, 76, 59, 14);
		label_1.setText("在线动态");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 96, 430, 93);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(146);
		tableColumn.setText("学号");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(81);
		tableColumn_3.setText("姓名");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(89);
		tableColumn_1.setText("昵称");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(112);
		tableColumn_2.setText("动作");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
