package org.sdu.database;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Build database connection.
 * 
 * @version 0.1 rev 8008 Jan. 6, 2013
 * Copyright (c) HyperCube Dev Team
 */
class Connect {
	private Statement statement;
	private Connection conn;
	int totalNum;
	String[] name, id, idNum, faculty, pic;

	Connect() throws Exception {
		String url = "jdbc:mysql://" + Configure.databaseAddress + "/"
				+ Configure.database;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, Configure.user,
				Configure.password);
		statement = conn.createStatement();
	}

	void close() {
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
			rs = statement.executeQuery("select * from " + Configure.table);
		else
			rs = statement.executeQuery("select * from " + Configure.table
					+ " where " + query);
		return rs;
	}

	int getCount(String query) throws Exception {
		ResultSet rs;
		if ((query == null) || query.equals(""))
			rs = statement.executeQuery("select count(*) from "
					+ Configure.table);
		else
			rs = statement.executeQuery("select count(*) from "
					+ Configure.table + " where " + query);
		rs.next();
		int countNum = rs.getInt(1);
		rs.close();
		return countNum;
	}

	void delete(int index) throws Exception {
		File origin = new File(Configure.siteDirectory + "pic/"
				+ pic[index].substring(0, pic[index].length() - 5) + "/"
				+ pic[index].substring(pic[index].length() - 5) + ".jpg");
		File move = new File(Configure.siteDirectory + "picdeleted/"
				+ pic[index].substring(0, pic[index].length() - 5) + "/"
				+ pic[index].substring(pic[index].length() - 5) + ".jpg");
		File oldPath = new File(Configure.siteDirectory + "pic/"
				+ pic[index].substring(0, pic[index].length() - 5) + "/");
		File newPath = new File(Configure.siteDirectory + "picdeleted/"
				+ pic[index].substring(0, pic[index].length() - 5) + "/");
		if (!newPath.exists())
			newPath.mkdirs();
		origin.renameTo(move);
		File[] oldPathFiles = oldPath.listFiles();
		if (oldPathFiles.length == 0)
			oldPath.delete();
		statement.execute("delete from " + Configure.table + " where id='"
				+ id[index] + "'");
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
			ResultSet rs = statement.executeQuery("show columns from "
					+ Configure.table + " like '" + List.COLUMN_NAME[x][y]
					+ "'");
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
		return statement.executeQuery("select * from " + Configure.table
				+ " where id='" + id + "'");
	}

	void getData(String query) throws Exception {
		totalNum = getCount(query);
		ResultSet rs = get(query);
		int num = (totalNum > 1000) ? 1000 : totalNum;
		name = new String[num];
		id = new String[num];
		idNum = new String[num];
		faculty = new String[num];
		pic = new String[num];
		for (int i = 0; i < num; i++) {
			rs.next();
			name[i] = rs.getString("name");
			id[i] = rs.getString("id");
			idNum[i] = rs.getString("idnum");
			faculty[i] = rs.getString("faculty");
			pic[i] = rs.getString("pic");
		}
		rs.close();
	}

	String[] getID(String query) throws Exception {
		String[] id = new String[totalNum];
		ResultSet rs = get(query);
		for (int i = 0; i < totalNum; i++) {
			rs.next();
			id[i] = rs.getString("id");
		}
		rs.close();
		return id;
	}
}
