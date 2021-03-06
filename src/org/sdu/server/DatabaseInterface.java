package org.sdu.server;

import org.sdu.database.Message;

public interface DatabaseInterface {
	/**
	 * Set user Online
	 * @param id
	 * @param visible
	 * @throws Exception
	 */
	public void setOnline(String id, boolean visible) throws Exception;
	/**
	 * Set user Offline
	 * @param id
	 * @throws Exception
	 */
	public void setOffline(String id) throws Exception;
	/**
	 * Get user Online or not
	 * @param id
	 * @return Flag
	 * @throws Exception
	 */
	public boolean getOnline(String id) throws Exception;
	/**
	 * Set a user to Visible
	 * @param id
	 * @throws Exception
	 */
	public void setVisible(String id,boolean visible) throws Exception;
	/**
	 * Get user Visible or not
	 * @param id
	 * @return Flag
	 * @throws Exception
	 */
	public boolean getVisible(String id) throws Exception;
	/**
	 * Set the account to Freeze
	 * @param id
	 * @throws Exception
	 */
	public void setFreeze(String id,boolean freeze) throws Exception;
	/**
	 * Get  Messages for user
	 * @param Message
	 * @param id
	 * @throws Exception
	 */
	public void setMessage(String Message,String from,String receiver,boolean notification,boolean read) throws Exception;
	/**
	 * Get  Messages for user
	 * @param Message
	 * @param id
	 * @throws Exception
	 */
	public Message[] getMessage(String id) throws Exception;
	
	/**
	 * return the account is freeze or not
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean getFreeze(String id) throws Exception;
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
	 * Set user Nickname
	 * @param id
	 * @param nickname
	 * @throws Exception
	 */
	public void setNickname(String id, String nickname) throws Exception;
	/**
	 * Get user Nickname 
	 * @param id
	 * @return	Nickname
	 * @throws Exception
	 */
	public String getNickname(String id) throws Exception;
	/**
	 * Check the user and password
	 * @param id
	 * @param password
	 * @return Flag
	 * @throws Exception
	 */
	public boolean checkPassword(String id, String password) throws Exception;
	/**
	 * Get the number of all user
	 * @return Number
	 * @throws Exception
	 */
	public int getCount() throws Exception;
	/**
	 * 
	 * @param id
	 * @return The User's friends list
	 * @throws Exception
	 */
	public String[] getFriendList(String id) throws Exception;
	/**
	 * 
	 * @param SQL_Query
	 * @return The User who fit the condition
	 * @throws Exception
	 */
	public String[] getUserGroup(String SQL_Query) throws Exception;
	/**
	 * 
	 * @param id
	 * @param Data
	 * @throws Exception
	 */
	public void setStatus(String id,String Data) throws Exception;
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public String getStatus(String id) throws Exception;
	/**
	 * 
	 * @param id
	 * @param URL
	 * @throws Exception
	 */
	public void setHeadImage(String id,String URL) throws Exception;
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public String getHeadImage(String id) throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getRealName(String id) throws Exception;
	/**
	 * Close the connection to database
	 */
	public void close();
}

