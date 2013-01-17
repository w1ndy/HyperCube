package org.sdu.server.UI;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

public class ServerDataObserver implements Observer{
	static TableItem item1;
	static private Display d;
	
	public ServerDataObserver(TableItem i1,Display d){
		item1 = i1;
		ServerDataObserver.d = d;
	}
	public ServerDataObserver() {
		// TODO Auto-generated constructor stub
	}
	public static String[] ServerData1 = {"","","",""};
	public static String[] ServerData2 = {"","","",""};
	public static String[] ServerData3 = {"","","",""};
	public static String[] ServerData4 = {"","","",""};
	public static String[] ServerData5 = {"","","",""};
	public static String[] ServerData6 = {"","","",""};
	public static String[] ServerData7 = {"","","",""};
	public static String[] ServerData8 = {"","","",""};
	public static String[] ServerData9 = {"","","",""};
	public static String[] ServerData10 = {"","","",""};
	public static String[] ServerData11 = {"","","",""};
	public static String[] ServerData12 = {"","","",""};
	public static String[] ServerData13 = {"","","",""};
	public static String[] ServerData14 = {"","","",""};
	public static String[] ServerData15 = {"","","",""};
	public static String[] ServerData16 = {"","","",""};
	public static String[] ServerData17 = {"","","",""};
	
	
	@Override
	public void update(Observable o, final Object arg) {
	d.syncExec(new Runnable(){

		@Override
		public void run() {
			item1.setText((String[]) arg);
			
		}
		
	});
		
	}
	}
	

