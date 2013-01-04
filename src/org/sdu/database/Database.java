package org.sdu.database;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 * Access database.
 * 
 * @version 0.1 rev 8006 Jan. 4, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Database {
	private final String mainDatabase = "hypercube";
	private final String infoTable = "info";
	private Statement statement;
	private Connection conn;
	public String webserverAddress;

	public Database() {
		// Read configuration file
		String line, databaseAddress = "", user = "", password = "";
		try {
			FileReader in = new FileReader("database.conf");
			Scanner conf = new Scanner(in);
			while (conf.hasNextLine()) {
				line = conf.nextLine();
				if (line.equals("[database_address]"))
					databaseAddress = conf.next();
				if (line.equals("[webserver_address]"))
					webserverAddress = conf.next();
				if (line.equals("[user]"))
					user = conf.next();
				if (line.equals("[password]"))
					password = conf.next();
			}
			conf.close();
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "启动失败",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		// Connect to database
		try {
			String url = "jdbc:mysql://" + databaseAddress + "/" + mainDatabase;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			statement = conn.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数据库连接错误", "启动失败",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	public void close() {
		try {
			statement.close();
			conn.close();
		} catch (Exception e) {
		}
	}

	public int getCount() throws Exception {
		int num = 0;
		ResultSet count = statement.executeQuery("select count(*) from "
				+ infoTable);
		count.next();
		num = count.getInt(1);
		count.close();
		return num;
	}

	public void deleteUser(String id) throws Exception {
		statement
				.execute("delete from " + infoTable + " where id='" + id + "'");
	}

	public boolean checkExist(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select * from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next())
			flag = true;
		return flag;
	}

	public boolean checkPassword(String id, String password) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select * from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next() && (rs.getString("password").equals(password)))
			flag = true;
		return flag;
	}

	public void setOnline(String id, boolean visible) throws Exception {
		int flag;
		if (visible)
			flag = 1;
		else
			flag = 0;
		statement.executeUpdate("update " + infoTable
				+ " set online=1, visible=" + flag + " where id='" + id + "'");
	}

	public void setOffline(String id) throws Exception {
		statement.executeUpdate("update " + infoTable
				+ " set online=0 where id='" + id + "'");
	}

	public void setVisible(String id, boolean visible) throws Exception {
		int flag;
		if (visible)
			flag = 1;
		else
			flag = 0;
		statement.executeUpdate("update " + infoTable + " visible=" + flag
				+ " where id='" + id + "'");
	}

	public void setNickname(String id, String nickname) throws Exception {
		statement.executeUpdate("update " + infoTable + " nickname='"
				+ nickname + "' where id='" + id + "'");
	}

	public boolean getOnline(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select * from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next() && (rs.getInt("online") == 1))
			flag = true;
		return flag;
	}

	public boolean getVisible(String id) throws Exception {
		boolean flag = false;
		ResultSet rs = statement.executeQuery("select * from " + infoTable
				+ " where id='" + id + "'");
		if (rs.next() && (rs.getInt("visible") == 1))
			flag = true;
		return flag;
	}

	public String getNickname(String id) throws Exception {
		ResultSet rs = statement.executeQuery("select * from " + infoTable
				+ " where id='" + id + "'");
		rs.next();
		return rs.getString("nickname");
	}
}
