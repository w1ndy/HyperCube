package org.sdu.test;

import org.sdu.database.Database;

/**
 * Test database components.
 * 
 * @version 0.1 rev 8000 Jan. 4, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class DatabaseTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Database db = new Database();
		System.out.println(db.checkPassword("test", "test"));
		db.close();
	}
}
