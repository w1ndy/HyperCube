/**
 * 
 */
package org.sdu.server;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Celr
 *
 */
public class ID_Manager {
	public static HashMap<String, String> IdMap = new HashMap<String, String>();
	public static String setID(String username){
		IdMap.put(username,""+(username.codePointAt(username.length()-1)+username.codePointAt(username.length()-2)+(new Date()).getTime()));    
		return IdMap.get(username);
	}
	public static String getID(String username){
		return IdMap.get(username);
	}
}
