/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

import java.io.Serializable;
import java.util.Calendar;

public class Message implements Serializable{
	/**
	 * 
	 */
	private Calendar cal;
	private int hour;
	private int minute;
	private String minutes;
	private String am_pm, time;
	
	private static final long serialVersionUID = 1L;
	
	public Message() 
	{
		cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		if(minute < 10)
			minutes = "0" + minute;
		else
			minutes = "" + minute;
		
		if(hour == 0)
			hour = 12;
		
		if(cal.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";
		time = hour + ":" + minutes + " " + am_pm;
	}
	
	public String getMessageTime()
	{
		return time;
	}
}
