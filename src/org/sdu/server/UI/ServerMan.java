package org.sdu.server.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.sdu.server.Core;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.core.databinding.Binding;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.Binding;

public class ServerMan {
	private static Table table;
	private static  ServerDataObserver d;
	static Core core;
	private static TableItem item1;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
				Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				Display display = Display.getDefault();
				final Shell shell = new Shell(SWT.CLOSE | SWT.MIN | SWT.TITLE);
				shell.setSize(482, 501);
				shell.setText("服务器管理程�);
				
				Label label = new Label(shell, SWT.NONE);
				label.setAlignment(SWT.CENTER);
				label.setBounds(10, 10, 94, 14);
				label.setText("服务器管�);
				
				Group group = new Group(shell, SWT.NONE);
				group.setText("开启与关闭");
				group.setBounds(110, 10, 260, 61);
				
				Button button = new Button(group, SWT.NONE);
				button.setBounds(10, 22, 94, 28);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
							try {
								if (core == null){
									core = new Core();
								MessageBox message = new MessageBox(shell,SWT.OK);
								message.setMessage("服务器已运行");
				                message.setText("服务器已运行");
				                message.open();}else
				                {MessageBox message = new MessageBox(shell,SWT.OK);
								message.setMessage("服务器已经在运行，请勿重复开�);
				                message.setText("错误");
				                message.open();}
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
				button.setText("启动服务�);
				
				Button button_1 = new Button(group, SWT.NONE);
				button_1.setBounds(144, 22, 94, 28);
				button_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							core.CloseServer();
							core = null;
							item1.setText("");
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
				button_1.setText("关闭服务�);
				
				Label label_1 = new Label(shell, SWT.NONE);
				label_1.setAlignment(SWT.CENTER);
				label_1.setBounds(10, 76, 59, 14);
				label_1.setText("在线动�);
				
				table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
				table.setBounds(10, 96, 456, 367);
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
				TableItem item1=new TableItem(table,SWT.NONE); 
				TableItem item2=new TableItem(table,SWT.NONE); 
				TableItem item3=new TableItem(table,SWT.NONE); 
				TableItem item4=new TableItem(table,SWT.NONE); 
				TableItem item5=new TableItem(table,SWT.NONE); 
				TableItem item6=new TableItem(table,SWT.NONE); 
				TableItem item7=new TableItem(table,SWT.NONE); 
				TableItem item8=new TableItem(table,SWT.NONE); 
				TableItem item9=new TableItem(table,SWT.NONE); 
				TableItem item10=new TableItem(table,SWT.NONE); 
				TableItem item11=new TableItem(table,SWT.NONE); 
				TableItem item12=new TableItem(table,SWT.NONE); 
				TableItem item13=new TableItem(table,SWT.NONE); 
				TableItem item14=new TableItem(table,SWT.NONE); 
				TableItem item15=new TableItem(table,SWT.NONE); 
				TableItem item16=new TableItem(table,SWT.NONE);
				d = new ServerDataObserver(item1,display);
				shell.open();
				shell.layout();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
			}
	}
	
