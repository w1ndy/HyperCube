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
	

