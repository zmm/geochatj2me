/**
 * 
 */
package org.geo2tag.geochat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.List;
import javax.microedition.location.QualifiedCoordinates;

import org.geo2tag.geochat.utils.GCState;
import org.geo2tag.geochat.utils.LocationUtil;
import org.geo2tag.geochat.utils.Settings;
import org.geo2tag.geochat.utils.StringConstants;

import ru.spb.osll.json.Errno;
import ru.spb.osll.json.JsonLoadTagsRequest;
import ru.spb.osll.json.JsonLoadTagsResponse;
import ru.spb.osll.json.JsonRequestException;
import ru.spb.osll.json.RequestSender;
import ru.spb.osll.objects.Channel;
import ru.spb.osll.objects.Mark;

import com.nokia.mid.ui.IconCommand;

/**
 * @author Mark Zaslavskiy
 *
 */
public class TagsList extends List {

	


	
	private Timer m_timer = new Timer();
	
	private class CustomTimerTask extends TimerTask{
			    public void run() {
	    		refreshData();
	    }
	}
	
	private TimerTask m_timerTask = new CustomTimerTask(); 
	
	
	private IconCommand m_backCommand = new IconCommand(StringConstants.BACK_COMMAND, Command.BACK, 0, IconCommand.ICON_BACK);
	private Command m_writeMessageCommand = new Command(StringConstants.WRITE_MESSAGE_COMMAND, Command.HELP, 1);
	private Command m_refreshCommand = new Command(StringConstants.REFRESH_COMMAND, Command.HELP, 2);
	
	private Command m_aboutCommand = new Command(StringConstants.ABOUT_COMMAND, Command.HELP, 3);
	private Command m_helpCommand = new Command(StringConstants.HELP_COMMAND, Command.HELP, 4);
	private Command m_exitCommand = new Command(StringConstants.EXIT_COMMAND, Command.EXIT, 5);

	
	public TagsList(){
		this("",List.IMPLICIT);
		setFitPolicy(TEXT_WRAP_ON);
		
		addCommand(m_backCommand);
		addCommand(m_writeMessageCommand);
		addCommand(m_refreshCommand);
		addCommand(m_aboutCommand);
		addCommand(m_helpCommand);
		addCommand(m_exitCommand);
	}
	
	public TagsList(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public void startTagsListUpdate(){
		if (Settings.isPeriodicalUpdateEnabled())
			m_timer.schedule(m_timerTask, 0, Settings.getUpdateInterval());
		else 
			refreshData();
	}
	
	public void stopTagsListUpdate(){
		if (Settings.isPeriodicalUpdateEnabled()){
			m_timer.cancel();
			m_timer = new Timer();
			m_timerTask = new CustomTimerTask();
		}
	}
	
	private void setTags(Vector tags){
		deleteAll();
		for (int i=0; i<tags.size(); i++){
			Mark mark = (Mark) tags.elementAt(i);
			String str = mark.getUser() + "(" + mark.getTime() + ")" + ":\n" + mark.getDescription()+"\n";
			
			append(str, null);
		}
	}
	
	public void refreshData(){
    	QualifiedCoordinates q = LocationUtil.getCoordinates();
    	
    	if (q == null) return;
    	
    	double latitude = q.getLatitude();
    	double longitude = q.getLongitude();
    	
    	
        JsonLoadTagsRequest req = 
        		new JsonLoadTagsRequest(GCState.getAuthToken(), latitude, longitude, 
        				Settings.getRadius(), Settings.getServerUrl());
        JsonLoadTagsResponse res = new JsonLoadTagsResponse();
        
        int[] errnos = {Errno.SUCCESS.intValue()};
		
		try {
			RequestSender.performRequest(req, res, errnos);
			System.out.println("LoadTags was done succesfuly!");
			Channel channel = (Channel) res.getChannels().elementAt(0);
			setTags(channel.getMarks());
			
		} catch (JsonRequestException e) {
			// TODO Auto-generated catch block
			
			System.out.println("LoadTags failed!");
			e.printStackTrace();
		}
	}
}
