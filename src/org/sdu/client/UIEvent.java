package org.sdu.client;

/**
 * UIEvent class defines the event id posted by ClientUI class.
 * 
 * @version 0.1 rev 8001 Dec. 28, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class UIEvent
{
	public static final byte CheckVersion = 0x01;
	public static final byte Login = 0x02;
	
	private int eventId;
	
	public UIEvent(int eventId)
	{
		this.eventId = eventId;
	}

	/**
	 * @return the eventId
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
}