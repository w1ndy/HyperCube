package org.sdu.database;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Build database connection.
 * 
 * @version 0.1 rev 8001 Dec. 25, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class Database {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//Read configuration file
		FileReader in=new FileReader("database.conf");
		Scanner conf=new Scanner(in);
		String line,address="",database="",table="",user="",password="";
		while (conf.hasNextLine()) {
			line=conf.nextLine();
			if (line.equals("[address]")) address=conf.next();
			if (line.equals("[database]")) database=conf.next();
			if (line.equals("[table]")) table=conf.next();
			if (line.equals("[user]")) user=conf.next();
			if (line.equals("[password]")) password=conf.next();
		}
		conf.close();
		in.close();
		
		//Connect to database
		String url = "jdbc:mysql://"+address+"/"+database;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select * from "+table);
		rs.next();
		System.out.println(rs.getString("id"));
		rs.close();
		statement.close();
		conn.close();
	}
}
