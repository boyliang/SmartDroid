/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "change_wifi_state_actions")
public class ChangeWIFIStateAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "ChangeWIFIStateAction";
	
	/**
	 * The action's name.
	 */
	private static final String NAME = "Change wifi state";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Changes wifi state (enabled / disabled)";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "change_wifi_state_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name wifi state.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_WIFI_STATE = "wifi_state";

	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the wanted wifi state.
	 * 
	 * <P>
	 * WifiManager.WIFI_STATE_ENABLED:<BR/>
	 * Wi-Fi is enabled.<BR/>
	 * WifiManager.WIFI_STATE_DISABLED<BR/>
	 * Wi-Fi is disabled.<BR/>
	 * </P>
	 * 
	 * For more info:
	 * @see android.net.wifi.WifiManager
	 */
	@DatabaseField(columnName = ChangeWIFIStateAction.COLUMN_NAME_WIFI_STATE, canBeNull = false)
	private int wifiState = WifiManager.WIFI_STATE_ENABLED;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public ChangeWIFIStateAction() {
		super();
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param wifiState		Integer represents the wanted wifi state
	 */
	public ChangeWIFIStateAction(Context context, Rule rule, int wifiState) {
		super(context, rule, ChangeWIFIStateAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.wifiState = wifiState;
	}

	/**
	 * @return the wifiState
	 */
	public int getWifiState() {
		return wifiState;
	}

	/**
	 * @param wifiState the wifiState to set
	 */
	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		
		// Changes wifi state
		if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
			wifiManager.setWifiEnabled(true);
		} else {
			wifiManager.setWifiEnabled(false);
		}
	}

}
