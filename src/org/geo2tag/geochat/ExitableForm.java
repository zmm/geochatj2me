/**
 * 
 */
package org.geo2tag.geochat;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;

/**
 * @author user
 *
 */
public class ExitableForm extends Form {

	
	public static final String EXIT_COMMAND = "Exit";
	private Command m_exitCommand = new Command(EXIT_COMMAND, Command.EXIT, 1);

	
	public ExitableForm(String arg0){
		super(arg0);
		addCommand(m_exitCommand);
	}
}
