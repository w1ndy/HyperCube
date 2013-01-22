package org.sdu.test;

import org.sdu.database.*;

/**
 * Test database components.
 * 
 * @version 0.1 rev 8001 Jan. 22, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class DatabaseTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Database db = new Database();
		Filter f = new Filter(db);
		f.setVisible(true);
		db.close();
	}
}
