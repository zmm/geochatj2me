/**
 * 
 */
package org.geo2tag.geochat.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Mark Zaslavskiy
 *
 */
public class DateUtil {

	private static int decPow(int power){
		int result = 1;
		for (int i=1; i<=power; i++){
			result *= 10;
		}
		return result;
	}
	
	private static String identNumberWithZeros(int number, int digitsNumber){
		String zeros = "";
		
		for (int i=digitsNumber-1; i>0; i--){
			if (decPow(i) > number)
				zeros = zeros + "0";
		}
		
		return zeros+Integer.toString(number);
	}

	
	public static String getCurrentTime(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int millisecond = cal.get(Calendar.MILLISECOND);
		
//	"dd MM yyyy HH:mm:ss.SSS";		
		
		String currentTime = 
				identNumberWithZeros(day,2) + " " +
				identNumberWithZeros(month,2) + " " +
				Integer.toString(year) + " " +
				identNumberWithZeros(hour,2) + ":" + 
				identNumberWithZeros(minute,2) + ":" + 
				identNumberWithZeros(second,2) + "." + 
				identNumberWithZeros(millisecond,3);
		
		
		return currentTime;
	}
	
}
