/**
 * 
 */
package org.geo2tag.geochat.utils;

import org.geo2tag.geochat.MainMidlet;


/**
 * @author Mark Zaslavskiy
 *
 */
public  class GCState {
	private static String m_authToken = null;
	private static MainMidlet m_midlet = null;
	
	
	public static String getAuthToken() {
		return m_authToken;
	}


	public static void setAuthToken(String authToken) {
		GCState.m_authToken = authToken;
	}

	public static MainMidlet getMidlet() {
		return m_midlet;
	}

	public static void setMidlet(MainMidlet midlet) {
		GCState.m_midlet = midlet;
	}
	
	
	
}
