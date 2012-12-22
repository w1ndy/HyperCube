package org.sdu.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;

import org.sdu.network.Session;

/**
 * Test performance on an echo socket server.
 * 
 * @version 0.1 rev 8002 Dec. 19, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class NativeClientTest implements Runnable
{
	private static final String host = "211.87.218.74";
	private static final int 	port = EchoServerTest.port;
	
	private static final int max_thread = 1000;
	private static final int post_times = 1;
	private static final byte delimiter = Session.delimiter;
	private static final byte[] data = {delimiter, 0x00, 0x05,
		'H', 'e', 'l', 'l', 'o'};
	private static final int packet_len = data.length;
	private static final int expected_result_len = packet_len * post_times;
	
	private static int tot_conn_time;
	private static int tot_send_time;
	private static int tot_recv_time;
	
	private static int tot_send;
	private static int tot_recv;
	
	private static final Object timeLock = new Object();
	
	public static void main(String[] args)
	{
		ExecutorService service = Executors.newFixedThreadPool(max_thread);
		List<Callable<Object>> todo = new ArrayList<Callable<Object>>(max_thread);
		for(int i = 0; i < max_thread; i++) {
			todo.add(Executors.callable(new NativeClientTest()));
		}
		try {
			service.invokeAll(todo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("Total Connection Time: " + tot_conn_time + "ms");
		System.out.println("Total Send Time: " + tot_send_time + "ms");
		System.out.println("Total Recv Time: " + tot_recv_time + "ms");
		System.out.println();
		System.out.println("Thread packet size: " + (post_times * packet_len));
		System.out.println("Total Byte Sent: " + tot_send);
		System.out.println("Total Byte Received: " + tot_recv);
		System.out.println();
		System.out.println("Average Connection Time: " + 
				((float)tot_conn_time / (float)(max_thread)) + "ms");
		System.out.println("Average Send Time: " +
				((float)tot_send_time / (float)(max_thread)) + "ms");
		System.out.println("Average Recv Time: " +
				((float)tot_recv_time / (float)(max_thread)) + "ms");
		service.shutdownNow();
	}
	
	/**
	 * Initialize the test.
	 */
	public NativeClientTest()
	{
		//for(int i = 0; i < max_thread; i++) {
		//	(new Thread(this)).start();
		//}
	}
	
	/**
	 * Test conn / post / recv performance.
	 */
	@Override
	public void run() {
		Socket s;
		long start_time, cost_time, data_size;
		InputStream istream;
		OutputStream ostream;
		
		try {
			start_time = System.currentTimeMillis();
			s = new Socket();
			s.connect(new InetSocketAddress(host, port), 5000);
			s.setTcpNoDelay(true);
			cost_time = System.currentTimeMillis() - start_time;
			System.out.println("Conn time: " + cost_time + "ms");
			synchronized(timeLock) {
				tot_conn_time += cost_time;
			}
			
			ostream = s.getOutputStream();
			istream = s.getInputStream();
			data_size = 0;
			start_time = System.currentTimeMillis();
			for(int i = 0; i < post_times; i++) {
				ostream.write(data);
				data_size += data.length;
			}
			cost_time = System.currentTimeMillis() - start_time;
			System.out.println("Post time: " + cost_time + "ms");
			synchronized(timeLock) {
				tot_send_time += cost_time;
				tot_send += data_size;
			}
			
			byte[] recv = new byte[expected_result_len];
			start_time = System.currentTimeMillis();
			data_size = 0;
			while(data_size != expected_result_len) data_size += istream.read(recv, 0, expected_result_len);
			cost_time = System.currentTimeMillis() - start_time;
			System.out.println("Recv time: " + cost_time + "ms");
			synchronized(timeLock) {
				tot_recv_time += cost_time;
				tot_recv += data_size;
			}
			s.close();
		} catch (Throwable t) {
			System.out.println("Error while running test.");
		}
	}
}
