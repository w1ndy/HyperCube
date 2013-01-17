package org.sdu.client;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AvatarDownloader class download avatars asynchronously.
 * 
 * @version 0.1 rev 8000 Jan. 18, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class AvatarDownloader extends Observable implements Observer
{
	public class AvatarData extends Observable implements Runnable
	{
		private String url;
		private String dest;
		private String id;
		
		public AvatarData(String url, String dest, String id, Observer o)
		{
			this.url = url;
			this.dest = dest;
			this.id = id;
			addObserver(o);
		}
		
		public String getUrl()
		{
			return url;
		}
		
		public String getDest()
		{
			return dest;
		}
		
		public String getId()
		{
			return id;
		}
		
		@Override
		public void run()
		{
			try {
				URL addr = new URL(url);
				InputStream is = addr.openStream();
				OutputStream os = new FileOutputStream(dest);
				byte[] buffer = new byte[2048];
				
				int len;
				while((len = is.read(buffer)) != -1)
					os.write(buffer, 0, len);
				
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setChanged();
			notifyObservers(this);
		}
	}
	
	private ExecutorService executor;
	
	public AvatarDownloader()
	{
		executor = Executors.newFixedThreadPool(1);
	}
	
	public void release()
	{
		executor.shutdown();
	}

	public void download(String url, String dest, String id)
	{
		executor.submit(new AvatarData(url, dest, id, this));
	}

	@Override
	public void update(Observable arg0, Object o) {
		setChanged();
		notifyObservers(o);
	}
}
