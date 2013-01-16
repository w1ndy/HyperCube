package org.sdu.server.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Track extends Shell {
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Track shell = new Track(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Track(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(null);
		
		Group group = new Group(this, SWT.NONE);
		group.setBounds(3, 3, 709, 438);
		group.setText("实时信息");
		
		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 22, 690, 394);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("来源");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("请求");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("内容");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(715, 3, 137, 65);
		composite.setLayout(null);
		
		Label label = new Label(composite, SWT.NONE);
		label.setBounds(5, 5, 48, 14);
		label.setText("数据流量");
		
		ProgressBar progressBar = new ProgressBar(composite, SWT.NONE);
		progressBar.setBounds(5, 25, 126, 14);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setBounds(99, 45, 32, 14);
		label_1.setText("1000");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button.setBounds(753, 413, 94, 28);
		button.setText("关闭");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_1.setBounds(753, 368, 94, 28);
		button_1.setText("显示通知");
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_2.setBounds(753, 320, 94, 28);
		button_2.setText("显示留言");
		
		Button button_3 = new Button(this, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_3.setBounds(753, 272, 94, 28);
		button_3.setText("显示聊天纪录");
		
		Button button_4 = new Button(this, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_4.setBounds(753, 228, 94, 28);
		button_4.setText("显示错误信息");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("监控");
		setSize(868, 524);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
