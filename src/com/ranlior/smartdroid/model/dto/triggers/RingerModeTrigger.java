/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
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
@DatabaseTable(tableName = "ringer_mode_triggers")
public class RingerModeTrigger extends Trigger {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = RingerModeTrigger.class.getSimpleName();
	
	/**
	 * The trigger's name. 
	 */
	private static final String NAME = "Ringer mode changed";
	
	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when the ringer mode changes (normal/silent/vibrate)";
	
	/*
     * Table definition.
     */
	
	/**
	 * The table name.
	 */
	public static final String TABLE_NAME = "ringer_mode_triggers";
	
	/*
     * Columns definitions.
     */
	
	/**
	 * Column name wanted ringer mode.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	public static final String COLUMN_NAME_WANTED_RINGER_MODE = "wantedRingerMode";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the trigger wanted ringer mode.
	 */
	@DatabaseField(columnName = RingerModeTrigger.COLUMN_NAME_WANTED_RINGER_MODE, canBeNull = false)
	private int wantedRingerMode = 0;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public RingerModeTrigger() {
		super(RingerModeTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param context			Context the context instantiating this action
	 * @param rule				Rule represents trigger's rule
	 * @param wantedRingerMode	Integer constant representing the wanted ringer mode.
	 */
	public RingerModeTrigger(Context context, Rule rule, int wantedRingerMode) {
		super(context, rule, RingerModeTrigger.class.getSimpleName(), NAME, DESCRIPTION);
		this.wantedRingerMode = wantedRingerMode;
	}

	/**
	 * @return the wantedRingerMode
	 */
	public int getWantedRingerMode() {
		return wantedRingerMode;
	}

	/**
	 * @param wantedRingerMode the wantedRingerMode to set
	 */
	public void setWantedRingerMode(int wantedRingerMode) {
		this.wantedRingerMode = wantedRingerMode;
	}
	
	public static void handle(Context appCtx, Bundle stateExtras) {
		// Loggers
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		List<Trigger> triggers = null;

		int ringerMode = stateExtras.getInt(AudioManager.EXTRA_RINGER_MODE, -1);
		if (ringerMode == -1) {
			return;
		}

		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(appCtx);

		QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(RingerModeTrigger.class);
		
		try {
	       triggers = queryBuilder.query();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		for (Trigger trigger : triggers) {
			RingerModeTrigger ringerModeTrigger = (RingerModeTrigger) trigger;
			if (ringerModeTrigger.getWantedRingerMode() == ringerMode) {
				ringerModeTrigger.setSatisfied(true);
			} else {
				ringerModeTrigger.setSatisfied(false);
			}
			triggerDAO.update(ringerModeTrigger);
			Rule rule = ringerModeTrigger.getRule();
			if (rule.isSatisfied()) {
				rule.setContext(appCtx);
				rule.perform();
			}
		}
	}

}
