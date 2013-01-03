package org.sdu.client;

import java.util.Observable;
import java.util.Observer;

import org.sdu.network.IncomingPacket;
import org.sdu.network.Session;
import org.sdu.network.SessionHandler;

/**
 * EventListener class processes the data received from UI or network.
 * 
 * @deprecated
 * @version 0.1 rev 8002 Jan. 1, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class EventListener implements Observer, SessionHandler
{
	//private NetworkClient client;
	//private Session session;
	private boolean isRunning = true;
	
	@Override
	public void update(Observable observ, Object obj)
	{
		//if(observ instanceof ClientUI) {
			//onUIEvent((ClientUI)observ, ((UIEvent)obj).getEventId());
		//} else {
			if(obj == null) {
				if(isRunning) {
					if(!onConnect()) {
						// TODO network error handle.
					}
				}
			} else if(obj instanceof IncomingPacket){
				onNetworkEvent((IncomingPacket)obj);
			}
		//}
	}
	
	public void onUIEvent(ClientUI ui, int eventId)
	{
		switch(eventId)
		{
		case UIEvent.CheckVersion:
			//ui.onVersionOK();
			break;
		case UIEvent.Login:
			//ui.onDisconnected();
			break;
		}
	}
	
	public void onNetworkEvent(IncomingPacket packet)
	{
	}
	
	public boolean onConnect()
	{
		return false;
	}

	@Override
	public boolean onServerStart() {
		return true;
	}

	@Override
	public void onServerClose() {}

	@Override
	public boolean onNewSession(Session s) {
		//session = s;
		return true;
	}
}