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
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "wired_headset_plugged_triggers")
public class WiredHeadsetPluggedTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = WiredHeadsetPluggedTrigger.class.getSimpleName();
	
	/**
	 * The trigger's name. 
	 */
	private static final String NAME = "Wired headset plug state";
	
	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when wired headset plug state changes (plugged / unplugged)";
	
	/**
	 * Constant represents wired headset is plugged.
	 */
	public static final int HEADSET_PLUGGED = 1;
	
	/**
	 * Constant represents wired headset is unplugged.
	 */
	public static final int HEADSET_UNPLUGGED = 0;
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	public static final String TABLE_NAME = "wired_headset_plugged_triggers";
	
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
	 * @see android.media.AudioManager
	 */
	@DatabaseField(columnName = WiredHeadsetPluggedTrigger.COLUMN_NAME_WANTED_PLUGGED_STATE, canBeNull = false)
	private int wantedPluggedState = HEADSET_PLUGGED;
	
	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public WiredHeadsetPluggedTrigger() {
		super(WiredHeadsetPluggedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param context				Context the context instantiating this action
	 * @param rule					Rule represents trigger's rule
	 * @param wantedPluggedState	Integer represents wired headset wanted plug state
	 */
	public WiredHeadsetPluggedTrigger(Context context, Rule rule, int wantedPluggedState) {
		super(context, rule, WiredHeadsetPluggedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
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
		// Logger
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		List<Trigger> triggers = null;
		
		int isPlugged = stateExtras.getInt("state");
		String headsetName = stateExtras.getString("name");
		int hasMicrophone = stateExtras.getInt("microphone");

		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(appCtx);

		QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(WiredHeadsetPluggedTrigger.class);
		
		try {
	       triggers = queryBuilder.query();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		for (Trigger trigger : triggers) {
			WiredHeadsetPluggedTrigger wiredHeadsetPluggedTrigger = (WiredHeadsetPluggedTrigger) trigger;
			if (isPlugged ==  wiredHeadsetPluggedTrigger.getWantedPluggedState()) {
				wiredHeadsetPluggedTrigger.setSatisfied(true);
			} else {
				wiredHeadsetPluggedTrigger.setSatisfied(false);
			}
			triggerDAO.update(wiredHeadsetPluggedTrigger);
			Rule rule = wiredHeadsetPluggedTrigger.getRule();
			if (rule.isSatisfied()) {
				rule.setContext(appCtx);
				rule.perform();
			}
		}
	}
}
