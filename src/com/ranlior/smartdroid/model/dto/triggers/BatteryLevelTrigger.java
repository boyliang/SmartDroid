/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "battery_level_triggers")
public class BatteryLevelTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "BatteryLevelTrigger";
	
	/**
	 * The trigger's name. 
	 */
	private static final String NAME = "Battery level state changed";
	
	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when the battery level state changes (low / okay)";
	
	/**
	 * The contant representing battary level low.
	 */
	public static final int BATTERY_LEVEL_LOW = 0;
	
	/**
	 * The contant representing battary level okay.
	 */
	public static final int BATTERY_LEVEL_OKAY = 1;
	
	/*
     * Table definition.
     */
	
	/**
	 * The table name.
	 */
	public static final String TABLE_NAME = "battery_level_triggers";
	
	/*
     * Column definitions.
     */
	
	/**
	 * Column name wanted state.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	public static final String COLUMN_NAME_WANTED_LEVEL_STATE = "wanted_level_state";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the trigger wanted state.<BR/><BR/>
	 * 
	 * @see android.os.BatteryManager
	 */
	@DatabaseField(columnName = BatteryLevelTrigger.COLUMN_NAME_WANTED_LEVEL_STATE, canBeNull = false)
	private int wantedLevelState = 0;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected BatteryLevelTrigger() {
		super();
	}

	/**
	 * Minimal constructor.
	 * 
	 * @param context				Context the context instantiating this action
	 * @param rule					Rule represents trigger's rule
	 * @param wantedLevelState		Integer contant represents the wanted battery level state
	 */
	public BatteryLevelTrigger(Context context, Rule rule, int wantedLevelState) {
		super(context, rule, BatteryLevelTrigger.class.getSimpleName(), NAME, DESCRIPTION);
		this.wantedLevelState = wantedLevelState;
	}
	
	/**
	 * @return the wantedLevelState
	 */
	public int getWantedLevelState() {
		return wantedLevelState;
	}

	/**
	 * @param wantedLevelState the wantedLevelState to set
	 */
	public void setWantedLevelState(int wantedLevelState) {
		this.wantedLevelState = wantedLevelState;
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		// Loggers
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		List<Trigger> triggers = null;
		
		int batteryLevel = stateExtras.getInt(SmartDroid.Extra.EXTRA_BATTERY_LEVEL, -1);
		
		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(appCtx);

		QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(BatteryLevelTrigger.class);
		
		try {
	       triggers = queryBuilder.query();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		for (Trigger trigger : triggers) {
			BatteryLevelTrigger batteryLevelTrigger = (BatteryLevelTrigger) trigger;
			if (batteryLevelTrigger.getWantedLevelState() == batteryLevel) {
				batteryLevelTrigger.setSatisfied(true);
			} else {
				batteryLevelTrigger.setSatisfied(false);
			}
			triggerDAO.update(batteryLevelTrigger);
			Rule rule = batteryLevelTrigger.getRule();
			if (rule.isSatisfied()) {
				rule.setContext(appCtx);
				rule.perform();
			}
		}
	}

}
