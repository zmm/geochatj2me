package org.geo2tag.geochat;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.geo2tag.geochat.utils.GCState;

public class MainMidlet extends MIDlet  {

	
	private LoginForm m_loginForm = new LoginForm();
	private MessageForm m_messageForm = new MessageForm();
	private TagsList m_tagsList = new TagsList();
	private Display m_display = Display.getDisplay(this);
	
	
	private CommandListener m_commandListener = new CommandListener() {
		
		public void commandAction(Command arg0, Displayable arg1) {
			// TODO Auto-generated method stub
			System.out.println(arg0.toString()+" "+arg1.toString());
			
			if (arg0.getCommandType() == Command.OK && arg1.getClass() == LoginForm.class){
				// Do login stuff and go to chat form in case of success
				System.out.println("OK action pressed!!!!");
				String token = m_loginForm.login();
				if (token != null){
					GCState.setAuthToken(token);
					m_tagsList.startTagsListUpdate();
					m_display.setCurrent(m_tagsList);
				}
			}else if (arg0.getCommandType() == Command.BACK && arg1.getClass() == TagsList.class){
				
				// Do things for stopping receiving marks and go back to login form
				System.out.println("BACK action from TagsList pressed!!!!");
				m_tagsList.stopTagsListUpdate();
				m_display.setCurrent(m_loginForm);
				
			}else if (arg0.getLabel() == TagsList.WRITE_MESSAGE && arg1.getClass() == TagsList.class){
				// Going to MessageForm
				System.out.println("ITEM action from TagsList pressed!!!!");
				m_tagsList.stopTagsListUpdate();
				m_messageForm.retrieveChannels();
				m_display.setCurrent(m_messageForm);
				
			}else if (arg0.getLabel() == TagsList.REFRESH && arg1.getClass() == TagsList.class){
				
				m_tagsList.refreshData();
				
			}else if (arg0.getCommandType() == Command.BACK && arg1.getClass() == MessageForm.class){
				
				System.out.println("CANCEL action from MessageForm pressed!!!!");
				m_tagsList.startTagsListUpdate();
				m_display.setCurrent(m_tagsList);
				
			}else if (arg0.getCommandType() == Command.OK && arg1.getClass() == MessageForm.class){
				
				System.out.println("SEND action from MessageForm pressed!!!!");
				m_messageForm.sendMessage();
				m_display.setCurrent(m_tagsList);
				
			}
		}
	}; 
	
	
	public MainMidlet() {
		// TODO Auto-generated constructor stub
		setupCommandListeners();
	}

	private void setupCommandListeners(){
		m_loginForm.setCommandListener(m_commandListener);
		m_tagsList.setCommandListener(m_commandListener);
		m_messageForm.setCommandListener(m_commandListener);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
		m_display.setCurrent(m_loginForm);
	}


	


	public void showAlert(String text){
		Alert message = new Alert(null);
		message.setString(text);
		message.setTimeout(5000);
		Displayable currentDisplayable = m_display.getCurrent();
		m_display.setCurrent(message, currentDisplayable);
	}
}
