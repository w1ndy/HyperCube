package org.sdu.database;

/**
 * Message template.
 * 
 * @version 0.1 rev 8001 Jan. 18, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Message {
	public long time;
	public String message;
	public boolean isNotification;
	public String from;

	public Message(long time, String message, boolean isNotification,
			String from) {
		this.time = time;
		this.message = message;
		this.isNotification = isNotification;
		this.from = from;
	}
}
