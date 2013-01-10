package org.sdu.server;

import java.net.InetAddress;

public interface DatabaseInterface {
	/**
	 * Get the number of all user
	 * @return Number
	 * @throws Exception
	 */
	public int getCount() throws Exception;
	/**
	 * Delete exist user
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(String id) throws Exception;
	/**
	 * Check the user exist or not
	 * @param id
	 * @return Flag
	 * @throws Exception
	 */
	public boolean checkExist(String id) throws Exception;
	/**
	 * Check the user and password
	 * @param id
	 * @param password
	 * @return Flag
	 * @throws Exception
	 */
	public boolean checkPassword(String id, String password) throws Exception;
	
	/**
	 * Set user Online
	 * @param id
	 * @param visible
	 * @throws Exception
	 */
	public void setOnline(String id, boolean visible,InetAddress IP) throws Exception;
	/**
	 * Set user Offline
	 * @param id
	 * @throws Exception
	 */
	public void setOffline(String id) throws Exception;
	/**
	 * Set user Nickname
	 * @param id
	 * @param nickname
	 * @throws Exception
	 */
	public void setNickname(String id, String nickname) throws Exception;
	/**
	 * Get user Online or not
	 * @param id
	 * @return Flag
	 * @throws Exception
	 */
	public boolean getOnline(String id) throws Exception;
	/**
	 * Get user Visible or not
	 * @param id
	 * @return Flag
	 * @throws Exception
	 */
	public boolean getVisible(String id) throws Exception;
	/**
	 * Get user Nickname 
	 * @param id
	 * @return	Nickname
	 * @throws Exception
	 */
	public String getNickname(String id) throws Exception;
	/**
	 * Send a Notification to user 
	 * @param Notification
	 * @param id
	 * @throws Exception
	 */
	public void setNotification(String Notification,String id) throws Exception;
	/**
	 * 
	 * @param SQL_Query
	 * @return The User who fit the condition
	 * @throws Exception
	 */
	public String[] getUserGroup(String SQL_Query) throws Exception;
	/**
	 * Send a Message to user
	 * @param Message
	 * @param id
	 * @throws Exception
	 */
	public void setMessage(String Message,String id) throws Exception;
	/**
	 * Set a user to Visible
	 * @param id
	 * @throws Exception
	 */
	public void setVisible(String id) throws Exception;
	/**
	 * Set a user to Invisible
	 * @param id
	 * @throws Exception
	 */
	public void setInvisible(String id) throws Exception;
	/**
	 * Backup for ChatData
	 * @param id
	 * @param Data
	 * @throws Exception
	 */
	public void chatdatabackup(String id,String Data) throws Exception;
	/**
	 * 
	 * @param id
	 * @param timestamp
	 * @return The Notification whose time bigger than the given time
	 * @throws Exception
	 */
	public String[] getNotification(String id,long timestamp) throws Exception;
	/**
	 * Set the account to Freeze
	 * @param id
	 * @throws Exception
	 */
	public void setFreeze(String id) throws Exception;
	/**
	 * return the account is freeze or not
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean Freeze(String id) throws Exception;
	/**
	 * Close the connection to database
	 */
	public void close();
}
