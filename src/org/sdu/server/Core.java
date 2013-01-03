package org.sdu.server;


import org.sdu.network.IncomingPacket;
import org.sdu.network.SendingPacket;
import org.sdu.network.NetworkServer;
import org.sdu.network.Packet;
import org.sdu.network.Postman;
import org.sdu.network.Session;
import org.sdu.network.SessionHandler;
import org.sdu.util.DebugFramework;
/**
 * 
 * @author Celr
 * 
 */
public class Core implements SessionHandler {
	private NetworkServer server;
	public static final int port = 21071;
	public static final DebugFramework debugger = DebugFramework.getFramework();
	
	public static void main(String[] args) {
		debugger.setLogFileName("EchoServerTest.log");
		new Core();
		// Tracker Server unfinished
	}

	public Core()
	{
		server = new NetworkServer(this, port);
		server.start(true);
	}
	
	public boolean onServerStart() {
		return true;
	}

	public void onServerClose() {
	}

	public boolean onNewSession(Session s) {
		s.addObserver(this);
		return true;
	}

	public void update(Observable session, Object p) {
		if (session instanceof Session && p instanceof Packet) {
			try {
				process.Push((IncomingPacket) p);// Process
				SendingPacket a = process.GetData();
				Postman.postPacket(a);// Send back the Packet
			} catch (Exception e) {
			e.printStackTrace();
			}
		}
	}

}
