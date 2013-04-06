/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
// FIXME: Fix this trigger can't diff between plugged states (ac, usb, wireless)
// only diff between connected and disconnected.
@DatabaseTable(tableName = "battery_plugged_triggers")
public class BatteryPluggedTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = BatteryPluggedTrigger.class.getSimpleName();
	
	/**
	 * The trigger's name. 
	 */
	private static final String NAME = "Battery plug state changed";
	
	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when the battery plug state changes (not pluged / ac plugged / usb plugged / wireless plugged)";
	
	/*
     * Table definition.
     */
	
	/**
	 * The table name.
	 */
	public static final String TABLE_NAME = "battery_plugged_triggers";
	
	/*
     * Column definitions.
     */
	
	/**
	 * Column name wanted plugged state.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	public static final String COLUMN_NAME_WANTED_PLUGGED_STATE = "wanted_plugged_state";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the trigger wanted pluged state.<BR/><BR/>
	 * 
	 * Default: 0 means it is on battery<BR/>
	 * BatteryManager.BATTERY_PLUGGED_AC<BR/>
	 * BatteryManager.BATTERY_PLUGGED_USB<BR/>
	 * BatteryManager.BATTERY_PLUGGED_WIRELESS<BR/>
	 * 
	 * @see android.os.BatteryManager
	 */
	@DatabaseField(columnName = BatteryPluggedTrigger.COLUMN_NAME_WANTED_PLUGGED_STATE, canBeNull = false)
	private int wantedPluggedState = 0;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public BatteryPluggedTrigger() {
		super(BatteryPluggedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Minimal constructor.
	 * 
	 * @param context				Context the context instantiating this action
	 * @param rule					Rule represents trigger's rule
	 * @param wanedPluggedState		Integer contant represents the wanted battery plugged state
	 */
	public BatteryPluggedTrigger(Context context, Rule rule, int wantedPluggedState) {
		super(context, rule, BatteryPluggedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
		this.wantedPluggedState = wantedPluggedState;
	}

	/**
	 * @return the wantedPluggedState
	 */
	public int getWantedPluggedState() {
		return wantedPluggedState;
	}

	/**
	 * @param wantedPluggedState the wantedPluggedState to set
	 */
	public void setWantedPluggedState(int wantedPluggedState) {
		this.wantedPluggedState = wantedPluggedState;
	}
	
	public static void handle(Context appCtx, Bundle stateExtras) {
		// Loggers
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		List<Trigger> triggers = null;
		
		int chargePlug = stateExtras.getInt(BatteryManager.EXTRA_PLUGGED, -1);
		if (chargePlug == -1) {
			return;
		}
		
		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(appCtx);

		QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(BatteryPluggedTrigger.class);
		
		try {
	       triggers = queryBuilder.query();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		for (Trigger trigger : triggers) {
			BatteryPluggedTrigger batteryPluggedTrigger = (BatteryPluggedTrigger) trigger;
			if (batteryPluggedTrigger.getWantedPluggedState() == chargePlug) {
				batteryPluggedTrigger.setSatisfied(true);
			} else {
				batteryPluggedTrigger.setSatisfied(false);
			}
			triggerDAO.update(batteryPluggedTrigger);
			Rule rule = batteryPluggedTrigger.getRule();
			if (rule.isSatisfied()) {
				rule.setContext(appCtx);
				rule.perform();
			}
		}
	}

}
