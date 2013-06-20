/**
 * 
 */
package org.geo2tag.geochat;


import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;

import javax.microedition.lcdui.TextField;
import javax.microedition.location.QualifiedCoordinates;

import org.geo2tag.geochat.utils.DateUtil;
import org.geo2tag.geochat.utils.GCState;
import org.geo2tag.geochat.utils.LocationUtil;
import org.geo2tag.geochat.utils.Settings;

import com.nokia.mid.ui.IconCommand;

import ru.spb.osll.json.Errno;
import ru.spb.osll.json.JsonApplyMarkRequest;
import ru.spb.osll.json.JsonApplyMarkResponse;
import ru.spb.osll.json.JsonAvailableChannelsRequest;
import ru.spb.osll.json.JsonAvailableChannelsResponse;
import ru.spb.osll.json.JsonRequestException;
import ru.spb.osll.json.JsonSubscribeRequest;
import ru.spb.osll.json.JsonSubscribeResponse;
import ru.spb.osll.json.RequestSender;
import ru.spb.osll.objects.Channel;


/**
 * @author Mark Zaslavskiy
 *
 */
public class MessageForm extends Form implements ItemStateListener{
	
	private ChoiceGroup m_channelsSelector = new ChoiceGroup("Channels", Choice.EXCLUSIVE);
	private TextField m_tagEdit = new TextField("Message", "", 120, TextField.ANY);
	
	private IconCommand m_cancelCommand = new IconCommand("Back", Command.BACK, 0, IconCommand.ICON_BACK );
	private Command m_okCommand = new Command("Send", Command.OK, 1);
	
	private Vector m_subscribedChannels = new Vector();
	
	public MessageForm(){
		super("Chat");
		addCommand(m_cancelCommand);		
		addCommand(m_okCommand);	

		append(m_tagEdit);
		append(m_channelsSelector);
		
		setItemStateListener(this);
	}
	

	private void setChannels(Vector channels){
		m_channelsSelector.deleteAll();
		for (int i = 0 ; i < channels.size(); i++){
			Channel channel = (Channel) channels.elementAt(i);
			m_channelsSelector.append(channel.getName(), null);
		}
	}
	
	public void retrieveChannels(){
		
		
		JsonAvailableChannelsRequest req = 
				new JsonAvailableChannelsRequest(GCState.getAuthToken(), Settings.getServerUrl());
		JsonAvailableChannelsResponse res = new JsonAvailableChannelsResponse();
		
		int[] errnos = {Errno.SUCCESS.intValue()};
		
		try {
			RequestSender.performRequest(req, res, errnos);
			System.out.println("AvailableChannels was recieved succesfuly!");
			
			setChannels(res.getChannels());
			
		} catch (JsonRequestException e) {
			// TODO Auto-generated catch block
			
			System.out.println("AvailableChannels failed!");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void sendMessage() {
		// TODO Auto-generated method stub
		System.out.println("Sending tag");
		
		String authToken = GCState.getAuthToken();
		String channel = m_channelsSelector.getString(m_channelsSelector.getSelectedIndex());
		String description = m_tagEdit.getString();
		
		QualifiedCoordinates qc = LocationUtil.getCoordinates();
		
		if (qc == null) {
			System.out.print("GPS is not supported!!!");
			return;
		}
		
		double latitude = qc.getLatitude();
		double longitude = qc.getLongitude();
		double altitude = qc.getAltitude();
		
		String time = DateUtil.getCurrentTime();		
		
		try{
			JsonApplyMarkRequest req = new JsonApplyMarkRequest(authToken, 
					channel, "", "", description, latitude, longitude, altitude, time, Settings.getServerUrl());
			JsonApplyMarkResponse res = new JsonApplyMarkResponse();
			int[] errnos = {Errno.SUCCESS.intValue()};
			
			RequestSender.performRequest(req, res, errnos);
			System.out.println("Tag was sent succesfuly!");
		}catch (JsonRequestException e){
			System.out.println("Error during sending tag: "+e);
		}
	}


	public void itemStateChanged(Item arg0) {
		// TODO Auto-generated method stub
		// Do subscribe actions for selected channel
		
		
		String channel = m_channelsSelector.getString(m_channelsSelector.getSelectedIndex());
		
		if (m_subscribedChannels.contains(channel)) return;
		
		JsonSubscribeRequest req = 
				new JsonSubscribeRequest(GCState.getAuthToken(), channel, Settings.getServerUrl());
		JsonSubscribeResponse res = new JsonSubscribeResponse();
		
		int[] errnos = {Errno.SUCCESS.intValue()};
		
		try {
			RequestSender.performRequest(req, res, errnos);
			System.out.println("SubscribeChannel was done succesfuly!");
			 m_subscribedChannels.addElement(channel);
			
		} catch (JsonRequestException e) {
			// TODO Auto-generated catch block
			
			System.out.println("SubscribeChannel failed!");
			e.printStackTrace();
		}
		
	}


}
