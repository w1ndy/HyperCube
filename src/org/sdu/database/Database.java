package org.sdu.database;

import java.sql.*;

import org.sdu.server.DatabaseInterface;

/**
 * Access database.
 * 
 * @version 0.1 rev 8010 Jan. 22, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Database implements DatabaseInterface {
	private static final String mainDatabase = "hypercube";
	private static final String messageDatabase = "message";
	private static final String infoTable = "info";
	private static final String stuTable = "stu";
	private Statement statement, statementMessage;
	private Connection conn, connMessage;

	public Database() throws Exception {
		// Read configuration file
		Configure.read();

		// Connect to database
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://" + Configure.databaseAddress + "/"
				+ mainDatabase + "?characterEncoding=utf8";
		conn = DriverManager.getConnection(url, Configure.user,
				Configure.password);
		statement = conn.createStatement();
		url = "jdbc:mysql://" + Configure.databaseAddress + "/"
				+ messageDatabase + "?characterEncoding=utf8";
		connMessage = DriverManager.getConnection(url, Configure.user,
				Configure.password);
		statementMessage = connMessage.createStatement();
	}

	@Override
	public void close() {
		try {
			statementMessage.close();
			connMessage.close();
			statement.close();
			conn.close();
		} catch (Exception e) {
		}
	}

	@Override
	public int getCount() throws Exception {
		int num = 0;
		ResultSet count = statement.executeQuery("select count(*) from "
				+ infoTable);
		count.next();
		num = count.getInt(1);
		count.close();
		return num;
	}

	@Override
	public void deleteUser(String id) throws Exception {
		statement.executeUpdate("delete from " + infoTable + " where id='" + id
				+ "'");
	}

	@Override
	public boolean checkExist(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select id from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next())
			flag = true;
		return flag;
	}

	@Override
	public boolean checkPassword(String id, String password) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select password from "
				+ infoTable + " where id='" + id + "'");
		if (rs.next() && (rs.getString(1).equals(password)))
			flag = true;
		return flag;
	}

	@Override
	public void setOnline(String id, boolean visible) throws Exception {
		statement.executeUpdate("update " + infoTable
				+ " set online=1, visible=" + toInt(visible) + " where id='"
				+ id + "'");
	}

	@Override
	public void setOffline(String id) throws Exception {
		statement.executeUpdate("update " + infoTable
				+ " set online=0 where id='" + id + "'");
	}

	@Override
	public void setVisible(String id, boolean visible) throws Exception {
		statement.executeUpdate("update " + infoTable + " set visible="
				+ toInt(visible) + " where id='" + id + "'");
	}

	@Override
	public void setNickname(String id, String nickname) throws Exception {
		statement.executeUpdate("update " + infoTable + " set nickname='"
				+ nickname + "' where id='" + id + "'");
	}

	@Override
	public boolean getOnline(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select online from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next() && (rs.getInt(1) == 1))
			flag = true;
		return flag;
	}

	@Override
	public boolean getVisible(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select visible from "
				+ infoTable + " where id='" + id + "'");
		if (rs.next() && (rs.getInt(1) == 1))
			flag = true;
		return flag;
	}

	@Override
	public String getNickname(String id) throws Exception {
		ResultSet rs = statement.executeQuery("select nickname from "
				+ infoTable + " where id='" + id + "'");
		rs.next();
		return rs.getString(1);
	}

	@Override
	public void setFreeze(String id, boolean freeze) throws Exception {
		statement.executeUpdate("update " + infoTable + " set freeze="
				+ toInt(freeze) + " where id='" + id + "'");
	}

	@Override
	public void setMessage(String Message, String from, String receiver,
			boolean notification, boolean read) throws Exception {
		statementMessage.executeUpdate("insert into " + receiver
				+ " (content,from,read,notification) values ('" + Message
				+ "','" + from + "'+" + toInt(read) + "," + toInt(notification)
				+ ")");
	}

	@Override
	public Message[] getMessage(String id) throws Exception {
		ResultSet rs = statementMessage.executeQuery("select * from " + id
				+ " where read=0");
		int count = 0;
		while (rs.next())
			count++;
		Message[] message = new Message[count];
		rs.first();
		for (int i = 0; i < count; i++) {
			message[i] = new Message(rs.getLong("time"),
					rs.getString("content"), rs.getBoolean("notification"),
					rs.getString("from"));
			rs.next();
		}
		rs.close();
		statementMessage.executeUpdate("update " + id
				+ " set read=1 where read=0");
		return message;
	}

	@Override
	public boolean getFreeze(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select freeze from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next() && (rs.getInt(1) == 1))
			flag = true;
		return flag;
	}

	@Override
	public String[] getFriendList(String id) throws Exception {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String[] getUserGroup(String SQL_Query) throws Exception {
		ResultSet rs;
		if (SQL_Query == null || SQL_Query.equals(""))
			rs = statement.executeQuery("select id from " + stuTable);
		else
			rs = statement.executeQuery("select id from " + stuTable
					+ " where " + SQL_Query);
		int count = 0;
		while (rs.next())
			count++;
		String[] id = new String[count];
		rs.first();
		for (int i = 0; i < count; i++) {
			id[i] = rs.getString(1);
			rs.next();
		}
		rs.close();
		statementMessage.executeUpdate("update " + id
				+ " set read=1 where read=0");
		return id;
	}

	@Override
	public void setStatus(String id, String Data) throws Exception {
		statement.executeUpdate("update " + infoTable + " set status='" + Data
				+ "' where id='" + id + "'");
	}

	@Override
	public String getStatus(String id) throws Exception {
		ResultSet rs = statement.executeQuery("select status from " + infoTable
				+ " where id='" + id + "'");
		rs.next();
		return rs.getString(1);
	}

	@Override
	public void setHeadImage(String id, String URL) throws Exception {
		statement.executeUpdate("update " + infoTable + " set pic='" + URL
				+ "' where id='" + id + "'");
	}

	@Override
	public String getHeadImage(String id) throws Exception {
		ResultSet rs = statement.executeQuery("select pic from " + infoTable
				+ " where id='" + id + "'");
		rs.next();
		return rs.getString(1);
	}

	@Override
	public String getRealName(String id) throws Exception {
		ResultSet rs = statement.executeQuery("select name from " + stuTable
				+ " where id='" + id + "'");
		rs.next();
		return rs.getString(1);
	}

	private int toInt(boolean t) {
		int flag;
		if (t)
			flag = 1;
		else
			flag = 0;
		return flag;
	}

	public String[] getEnumList(int x, int y) {
		String[] list;
		try {
			ResultSet rs = statement.executeQuery("show columns from "
					+ stuTable + " like '" + List.COLUMN_NAME[x][y] + "'");
			rs.next();
			String enums = rs.getString("Type");
			int position = 0, count = 0;
			while ((position = enums.indexOf('\'', position)) > 0) {
				position = enums.indexOf('\'', position + 1) + 1;
				count++;
			}
			position = 0;
			list = new String[count + 1];
			list[0] = "";
			for (int i = 1; i <= count; i++) {
				position = enums.indexOf('\'', position);
				int secondPosition = enums.indexOf('\'', position + 1);
				list[i] = enums.substring(position + 1, secondPosition);
				position = secondPosition + 1;
			}
		} catch (Exception e) {
			list = new String[0];
		}
		return list;
	}
}
