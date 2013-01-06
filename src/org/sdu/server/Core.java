/**
 * 
 */
package org.sdu.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.sdu.database.Database;
import org.sdu.net.Packet;
import org.sdu.net.Session;
import org.sdu.net.SessionHandler;
import org.sdu.net.NetworkServer;
import org.sdu.util.DebugFramework;

/**
 * @author Celr
 *
 */
public class Core extends SessionHandler {
	public static final int port = 21071;
	public static final DebugFramework debugger = DebugFramework.getFramework();
	static Database db;
	
	private NetworkServer server;
	
	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onAcceptingSession(java.nio.channels.SocketChannel)
	 */
	@Override
	public boolean onAcceptingSession(SocketChannel c) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onSessionAccepted(org.sdu.net.Session)
	 */
	@Override
	public void onSessionAccepted(Session s) {
		try {
			debugger.print("Session from " + s.getChannel().getRemoteAddress() + " accepted.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onSessionClosed(org.sdu.net.Session)
	 */
	@Override
	public void onSessionClosed(Session s) {
		try {
			debugger.print("Session from " + s.getChannel().getRemoteAddress() + " closed.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onUnregisteredSession(java.nio.channels.SocketChannel)
	 */
	@Override
	public void onUnregisteredSession(SocketChannel c) {
		try {
			debugger.print("Session from " + c.getRemoteAddress() + " failed to register.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onPacketReceived(org.sdu.net.Session, org.sdu.net.Packet)
	 */
	@Override
	public void onPacketReceived(Session s, Packet p) {
		process.Push(p.getData(),db);// Process
		s.post(process.GetData());// Send back the Packet

	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onConnected(org.sdu.net.Session)
	 */
	@Override
	public void onConnected(Session s) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sdu.net.SessionHandler#onConnectFailure(java.nio.channels.SocketChannel)
	 */
	@Override
	public void onConnectFailure(SocketChannel c) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		debugger.setLogFileName("EchoServerTest.log");
		new Core();
		db.close();
	}
	public Core()
	{
		try {
			db = new Database();
		} catch (Exception e) {
			debugger.print("Failed to connect with database");
		}
		server = new NetworkServer(this, port);
		server.start(true);
		
	}
}
