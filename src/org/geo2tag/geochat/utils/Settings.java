/**
 * 
 */
package org.geo2tag.geochat.utils;

/**
 * @author Mark Zaslavskiy
 *
 */
public class Settings {
	private static String SERVER_URL = "http://demo64.geo2tag.org/service";
	private static double RADIUS = 300;
	private static long UPDATE_INTERVAL = 30000;
	private static boolean ENABLE_PREIODICAL_UPDATE = true;
	
	public static String getServerUrl() {
		return SERVER_URL;
	}
	
	public static double getRadius() {
		return RADIUS;
	}
	public static long getUpdateInterval() {
		return UPDATE_INTERVAL;
	}
	
	public static boolean isPeriodicalUpdateEnabled(){
		return ENABLE_PREIODICAL_UPDATE;
	}
}
