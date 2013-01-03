package org.sdu.database;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db=new Database("info");
		System.out.println(db.checkPassword("test", "test"));
		db.close();

	}

}
