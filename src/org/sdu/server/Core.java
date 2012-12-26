package org.sdu.server;

import java.util.Observable;
import java.util.Observer;

import org.sdu.network.IncomingPacket;
import org.sdu.network.SendingPacket;
import org.sdu.network.NetworkServer;
import org.sdu.network.Packet;
import org.sdu.network.Postman;
import org.sdu.network.Session;
import org.sdu.network.SessionHandler;

/**
 * 
 * @author Celr
 * 
 */
public class Core implements Observer, SessionHandler {
	private NetworkServer server;
	public static final int port = 21071;

	// LOOOOOOOOOOOOOOOOOOOOOOOOOK here ...
	// plz make main method static ...
	public void main(String[] args) {
		server = new NetworkServer();
		server.start(port, 1000, this);
		// Tracker Server unfinished
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
				// Warning: performance issue.
				// create multiple instances of process class may slow down
				// program,
				// do one for each thread is enough.
				process ProcessA = new process();
				ProcessA.Push((IncomingPacket) p);// Process
				Postman.postPacket(ProcessA.GetData());// Send back the Packet
			} catch (Exception e) {
			}
		}
	}

}
