/**
 * 
 */
package org.geo2tag.geochat;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import org.geo2tag.geochat.utils.GCState;
import org.geo2tag.geochat.utils.Settings;
import org.geo2tag.geochat.utils.StringConstants;

import ru.spb.osll.json.Errno;
import ru.spb.osll.json.JsonLoginRequest;
import ru.spb.osll.json.JsonLoginResponse;
import ru.spb.osll.json.JsonRequestException;
import ru.spb.osll.json.RequestSender;

/**
 * @author Mark Zaslavskiy
 *
 */
public class LoginForm extends Form {


	
	private TextField m_loginText = new TextField(StringConstants.USER_NAME_TEXT_FIELD,
			StringConstants.DEFAULT_USER_NAME, 20, TextField.ANY);
	private TextField m_passwordText = new TextField(StringConstants.PASSWORD_TEXT_FIELD, 
			StringConstants.DEFAULT_PASSWORD, 20, TextField.PASSWORD);
	private Command m_okCommand = new Command(StringConstants.OK_COMMAND, Command.OK, 1);
	private Command m_exitCommand = new Command(StringConstants.EXIT_COMMAND, Command.EXIT, 1);
	
	private Command m_aboutCommand = new Command(StringConstants.ABOUT_COMMAND, Command.HELP, 3);
	private Command m_helpCommand = new Command(StringConstants.HELP_COMMAND, Command.HELP, 4);
	
	public LoginForm(){
		super("Login");
		addCommand(m_okCommand);
		addCommand(m_exitCommand);
		addCommand(m_aboutCommand);
		addCommand(m_helpCommand);
		
		append(m_loginText);
		append(m_passwordText);
	}
	
	public String getLogin(){
		return m_loginText.getString();
	}
	
	public String getPassword(){
		return m_passwordText.getString();
	}
	
	public String login(){
		try{
			String userName = getLogin();
			String password = getPassword();
			
			JsonLoginRequest req = new JsonLoginRequest(userName, password, Settings.getServerUrl()) ;
			JsonLoginResponse res = new JsonLoginResponse();
			int[] errnos = {Errno.SUCCESS.intValue()};
			
			RequestSender.performRequest(req, res, errnos);
			
			System.out.println("Login request success "+res.getAuthString());
			return res.getAuthString();
		}catch (JsonRequestException e){
			System.out.println("Login request failed "+e.toString());
			
			GCState.getMidlet().showAlert("Error during Login request");
			
			return null;
		}
	}
}


