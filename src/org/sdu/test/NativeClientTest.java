package org.sdu.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.sdu.network.Session;
import org.sdu.util.DebugFramework;

/**
 * Test performance on an echo socket server.
 * 
 * @version 0.1 rev 8000 Dec. 17, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NativeClientTest implements Runnable
{
	private static final String host = "127.0.0.1";
	private static final int 	port = EchoServerTest.port;
	
	private static final int max_thread = 1;
	private static final int post_times = 10;
	private static final byte delimiter = Session.delimiter;
	private static final int data_len = 5;
	private static final int packet_len = data_len + 3;
	private static final int expected_result_len = packet_len * post_times;
	private static final byte[] data = {delimiter, (byte)((data_len >> 4) & 0xff), (byte)(data_len & 0xff),
		'H', 'e', 'l', 'l', 'o'};
	
	public static void main(String[] args)
	{
		DebugFramework.getFramework().setLogFileName("NativeClientTest.log");
		new NativeClientTest();
	}
	
	/**
	 * Initialize the test.
	 */
	public NativeClientTest()
	{
		for(int i = 0; i < max_thread; i++) {
			(new Thread(this)).start();
		}
	}
	
	/**
	 * Test conn / post / recv performance.
	 */
	@Override
	public void run() {
		Socket s;
		long start_time;
		InputStream istream;
		OutputStream ostream;
		
		try {
			start_time = System.currentTimeMillis();
			s = new Socket(host, port);
			System.out.println("Conn time: " + (System.currentTimeMillis() - start_time));
			s.setTcpNoDelay(true);
			
			ostream = s.getOutputStream();
			istream = s.getInputStream();
			start_time = System.currentTimeMillis();
			for(int i = 0; i < post_times; i++)	ostream.write(data);
			ostream.flush();
			System.out.println("Post avg time: " + ((float)(System.currentTimeMillis() - start_time) / (float)post_times));
			
			byte[] recv = new byte[expected_result_len];
			start_time = System.currentTimeMillis();
			istream.read(recv, 0, expected_result_len);
			System.out.println("Recv avg time: " + ((float)(System.currentTimeMillis() - start_time) / (float)post_times));
		} catch (Throwable t) {
			System.out.println("Error while running test.");
		}
	}
}