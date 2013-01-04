package org.sdu.database;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 * Build database connection.
 * 
 * @version 0.1 rev 8004 Jan. 5, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Connect {
	private Statement statement;
	private Connection conn;
	private String table;
	public String webserverAddress;

	public Connect() {
		// Read configuration file
		String line, databaseAddress = "", database = "", user = "", password = "";
		try {
			FileReader in = new FileReader("database.conf");
			Scanner conf = new Scanner(in);
			while (conf.hasNextLine()) {
				line = conf.nextLine();
				if (line.equals("[database_address]"))
					databaseAddress = conf.next();
				if (line.equals("[webserver_address]"))
					webserverAddress = conf.next();
				if (line.equals("[database]"))
					database = conf.next();
				if (line.equals("[table]"))
					table = conf.next();
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
			String url = "jdbc:mysql://" + databaseAddress + "/" + database;
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

	ResultSet get(String query) throws Exception {
		ResultSet rs;
		statement.setFetchSize(1001);
		if ((query == null) || query.equals(""))
			rs = statement.executeQuery("select * from " + table);
		else
			rs = statement.executeQuery("select * from " + table + " where "
					+ query);
		return rs;
	}

	int getCount(String query) throws Exception {
		ResultSet rs;
		if ((query == null) || query.equals(""))
			rs = statement.executeQuery("select count(*) from " + table);
		else
			rs = statement.executeQuery("select count(*) from " + table
					+ " where " + query);
		rs.next();
		int countNum = rs.getInt(1);
		rs.close();
		return countNum;
	}

	void delete(String id) throws Exception {
		statement.execute("delete from " + table + " where id='" + id + "'");
	}

	void setPic() throws Exception {
		FileReader fi = new FileReader("1.txt");
		Scanner scan = new Scanner(fi);
		String s, s1, s2;
		int i;
		Random rand = new Random();
		File pic, pic1;
		while (scan.hasNext()) {
			s = scan.next();
			pic = new File("/Users/cc941201/Desktop/Database/wget/pic/" + s);
			do {
				s1 = "";
				for (i = 0; i < 27; i++)
					s1 += (char) (rand.nextInt(26) + 97);
				s2 = "";
				for (i = 0; i < 5; i++)
					s2 += (char) (rand.nextInt(26) + 97);
				pic1 = new File("/Library/Server/Web/Data/Sites/Default/pic/"
						+ s1 + "/" + s2 + ".jpg");
			} while (pic1.exists());
			File path = new File("/Library/Server/Web/Data/Sites/Default/pic/"
					+ s1 + "/");
			if (!path.exists())
				path.mkdirs();
			pic.renameTo(pic1);
			statement.execute("update stu set pic='" + s1 + s2 + "' where id='"
					+ s.substring(0, s.length() - 4) + "'");
		}
	}

	String[] getEnumList(int x, int y) {
		String[] list;
		try {
			ResultSet rs = statement.executeQuery("show columns from " + table
					+ " like '" + List.columnName[x][y] + "'");
			rs.next();
			String enums = rs.getString("Type");
			int position = 0, count = 0;
			while ((position = enums.indexOf("'", position)) > 0) {
				position = enums.indexOf("'", position + 1) + 1;
				count++;
			}
			position = 0;
			list = new String[count + 1];
			list[0] = "";
			for (int i = 1; i <= count; i++) {
				position = enums.indexOf("'", position);
				int secondPosition = enums.indexOf("'", position + 1);
				list[i] = enums.substring(position + 1, secondPosition);
				position = secondPosition + 1;
			}
		} catch (Exception e) {
			list = new String[0];
		}
		return list;
	}

	ResultSet getOne(String id) throws Exception {
		return statement.executeQuery("select * from " + table + " where id='"
				+ id + "'");
	}
}
