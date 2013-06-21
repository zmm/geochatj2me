/**
 * 
 */
package org.geo2tag.geochat;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;

import org.geo2tag.geochat.utils.Settings;

import com.nokia.mid.ui.IconCommand;

/**
 * @author user
 *
 */
public class SettingsForm extends Form {

	private static final String SETTINGS = "Settings";

	private static final String RADIUS = "Radius";
	private static final String UPDATE_INTERVAL = "Refresh period(s)";
	private static final String ENABLE_PERIODICAL_REFRESH = "Enbale periodic refresh";
	
	private static final String[] RADIUS_ARRAY = new String[]{"1", "5", "10"} ;
	private static final String[] UPDATE_INTERVAL_ARRAY = new String[]{"30", "60", "120"} ;
	private static final String[] ENABLE_PERIODIC_REFRESH_ARRAY = new String[]{"Enbale periodic refresh"};
	
	private  ChoiceGroup m_radiuses = new ChoiceGroup(RADIUS,  Choice.EXCLUSIVE, 
				RADIUS_ARRAY, null);
	private  ChoiceGroup m_updateIntervals = new ChoiceGroup(UPDATE_INTERVAL, 
				Choice.EXCLUSIVE, UPDATE_INTERVAL_ARRAY, null);
	private  ChoiceGroup m_enablePeriodicalRefresh = new ChoiceGroup(ENABLE_PERIODICAL_REFRESH,  
				Choice.MULTIPLE, ENABLE_PERIODIC_REFRESH_ARRAY, null);
	
	private IconCommand m_backCommand = new IconCommand("Back", Command.BACK, 0, IconCommand.ICON_BACK);
	private IconCommand m_okCommand = new IconCommand("Ok", Command.OK, 1, IconCommand.ICON_OK);
	
	public SettingsForm() {
		super(SETTINGS);
		addCommand(m_backCommand);
		addCommand(m_okCommand);
		append(m_radiuses);
		append(m_updateIntervals);
		append(m_enablePeriodicalRefresh);
	}
	
	public SettingsForm(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	private void setupChoiceGroup(ChoiceGroup group, String value, String[] array){
		for (int i=0; i<array.length; i++){
			if (array[i].equals(value)){
				group.setSelectedIndex(i, true);
				break;
			}
		}
	}
	
	
	public void initForm(){
		setupChoiceGroup(m_radiuses, Integer.toString((int)Settings.getRadius()), RADIUS_ARRAY);
		setupChoiceGroup(m_updateIntervals, Integer.toString(Settings.getUpdateInterval()), UPDATE_INTERVAL_ARRAY);
		
		m_enablePeriodicalRefresh.setSelectedIndex(0, Settings.isPeriodicalUpdateEnabled());
	}
	
	public void saveSettings(){
		
		String radius = m_radiuses.getString(m_radiuses.getSelectedIndex());
		String updateInterval = m_updateIntervals.getString(m_updateIntervals.getSelectedIndex());
		boolean isPeriodicalUpdateEnabled = m_enablePeriodicalRefresh.isSelected(0);
		
		Settings.setRadius(Integer.parseInt(radius));
		Settings.setUpdateInterval(Integer.parseInt(updateInterval));
		Settings.setPeriodicalUpdateEnabled(isPeriodicalUpdateEnabled);
	}

}
