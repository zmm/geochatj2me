/**
 * 
 */
package org.geo2tag.geochat.utils;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 * @author Mark Zaslavskiy
 *
 */
public class Settings {
	
	
	private static final String MAGIC_NUMBER_KEY = "magic_string";
	private static final long MAGIC_NUMBER = 2701;

	private static String SERVER_URL = "http://demo64.geo2tag.org/service";
	
	private static String RADIUS_KEY = "radius";
	private static int DEFAULT_RADIUS = 1;
	
	private static String UPDATE_INTERVAL_KEY = "update_interval";
	private static int DEFAULT_UPDATE_INTERVAL = 30;
	
	private static String ENABLE_PERIODICAL_UPDATE_KEY = "enable_periodical_update";
	private static boolean DEFAULT_ENABLE_PREIODICAL_UPDATE = true;
	
	public static String getServerUrl() {
		return SERVER_URL;
	}
	
	public static double getRadius() {
		double result = DEFAULT_RADIUS;
		byte[] bytes = getRecord(RADIUS_KEY);
		
		if (bytes != null)  result = ((int)bytes[0]);
		return result;
	}
	public static int getUpdateInterval() {
		int result = DEFAULT_UPDATE_INTERVAL;
		byte[] bytes = getRecord(UPDATE_INTERVAL_KEY);
		if (bytes != null) result = (int)bytes[0];
		return result;
	}
	
	public static boolean isPeriodicalUpdateEnabled(){
		byte[] bytes = getRecord(ENABLE_PERIODICAL_UPDATE_KEY);
		boolean result = DEFAULT_ENABLE_PREIODICAL_UPDATE;
			if (bytes!=null) result = (bytes[0] == 1  ?  true : false);
		return result;
	}
	
	public static void setRadius(int data) {
		byte[] bytes = new byte[]{(byte)data};
		setRecord(RADIUS_KEY, bytes);
	}
	
	public static void setUpdateInterval(int data) {
		byte[] bytes = new byte[]{(byte)data};
		setRecord(UPDATE_INTERVAL_KEY, bytes);
	}
	
	public static void setPeriodicalUpdateEnabled(boolean data){
		byte[] bytes = new byte[] {(byte) (data?1:0)};
		setRecord(ENABLE_PERIODICAL_UPDATE_KEY, bytes);
	}
	
	
	private static void createLongRecord(String key, long field){
		byte[] bytes = new byte[]{(byte)field};
		createRecord(key, bytes);
	}
	
	private static void createBooleanRecord(String key, boolean field) {
		// TODO Auto-generated method stub
		
		byte[] bytes = new byte[] {(byte) (field?1:0)};
		createRecord(key, bytes);
	}

	private static void createRecord(String key,  byte[] data){
		try {
			RecordStore recordStore = RecordStore.openRecordStore(key, true);
			recordStore.addRecord(data, 0, data.length);		
			recordStore.closeRecordStore();
			
			System.out.println("Record for "+ key+ " was created successfuly");
		}  catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Record for "+ key+ " was not created");
		}
	}
	
	private static void setRecord(String key,  byte[] data){
		try {	
			RecordStore recordStore = RecordStore.openRecordStore(key, true);
			
			RecordEnumeration recEnum = recordStore.enumerateRecords( null, null, false );
			
			recordStore.setRecord(recEnum.nextRecordId(),data, 0, data.length);
			recEnum.destroy();
			
			recordStore.closeRecordStore();
			
			System.out.println("Succsesful write of record for key = "+key);
		}  catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("Errors during write of record for key = "+key);

		}
	}
	
	
	private static byte[] getRecord(String key){
		try {	
			RecordStore recordStore = RecordStore.openRecordStore(key, false);	
			RecordEnumeration recEnum = recordStore.enumerateRecords( null, null, false );
			
			byte[] bytes = recEnum.nextRecord();
			recEnum.destroy();
			
			recordStore.closeRecordStore();
			System.out.println("Succsesful retrival of key = "+key+" from RMS");
			return bytes;
		}  catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erorrs during retrival of key = "+key+" from RMS");
			return null;
		}
	}
	
	
	public static void initSettings(){
		System.out.println("initSettings()");
		if (! doesSettingsExist()){
				createLongRecord(MAGIC_NUMBER_KEY, MAGIC_NUMBER);
				createLongRecord(UPDATE_INTERVAL_KEY, DEFAULT_UPDATE_INTERVAL);
				createLongRecord(RADIUS_KEY, DEFAULT_RADIUS);
				createBooleanRecord(ENABLE_PERIODICAL_UPDATE_KEY, DEFAULT_ENABLE_PREIODICAL_UPDATE);
		}/*else{
			try {
				RecordStore.deleteRecordStore(UPDATE_INTERVAL_KEY);
				RecordStore.deleteRecordStore(RADIUS_KEY);
				RecordStore.deleteRecordStore(ENABLE_PERIODICAL_UPDATE_KEY);
				RecordStore.deleteRecordStore(MAGIC_NUMBER_KEY);
			} catch (RecordStoreNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RecordStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}


	private static boolean doesSettingsExist() {
		// TODO Auto-generated method stub
		try {
			RecordStore rs = RecordStore.openRecordStore(MAGIC_NUMBER_KEY, false);
			rs.closeRecordStore();
		} catch (RecordStoreFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}
	
	
}
