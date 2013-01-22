package org.sdu.database;

import java.io.FileReader;
import java.util.Scanner;

/**
 * Read the configuration file.
 * 
 * @version 0.1 rev 8002 Jan. 22, 2013
 * Copyright (c) HyperCube Dev Team
 */
class Configure {
	static String databaseAddress, webserverAddress, user, password;

	static void read() throws Exception {
		FileReader in = new FileReader("database.conf");
		Scanner conf = new Scanner(in);
		while (conf.hasNextLine()) {
			String line = conf.nextLine();
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
	}
}
