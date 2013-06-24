/**
 * 
 */
package org.geo2tag.geochat;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;

import com.nokia.mid.ui.IconCommand;

/**
 * @author user
 *
 */
public class TextForm extends Form{

	private StringItem m_text = new StringItem("", "");
		
	private IconCommand m_backCommand = new IconCommand("Back", Command.BACK, 0, IconCommand.ICON_BACK);

	
	public TextForm(String header, String text){
		super(header);
		m_text.setLabel(text);
		addCommand(m_backCommand);
		append(m_text);
	}
	
	
	


}
