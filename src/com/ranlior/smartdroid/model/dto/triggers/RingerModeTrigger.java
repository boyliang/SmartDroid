/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.broadcastreceivers.SysEventReceiver;
import com.ranlior.smartdroid.config.SmartDroid;
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
	private static final String TAG = "RingerModeTrigger";
	
	/**
	 * Holds the trigger wanted ringer mode.
	 */
	@DatabaseField(columnName = SmartDroid.RingerModeTriggers.COLUMN_NAME_WANTED_RINGER_MODE, canBeNull = false)
	private int wantedRingerMode = 0;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected RingerModeTrigger() {
		super();
	}

	/**
	 * Minimal constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents trigger's rule
	 * @param name			String represents trigger's name
	 * @param description	String represents trigger's description
	 */
	public RingerModeTrigger(Context context, Rule rule, String name, String description, int wantedRingerMode) {
		super(context, rule, RingerModeTrigger.class.getSimpleName(), name, description);
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

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@Override
	public void register() {
		// Loggers
		Log.d(TAG, "register()");
		
		// Registering a battery broadcast receiver
		IntentFilter intentFilter = new IntentFilter("android.media.RINGER_MODE_CHANGED");
		context.registerReceiver(new SysEventReceiver(), intentFilter);
	}
	
	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#unregister()
	 */
	@Override
	public void unregister() {
		// Loggers
		Log.d(TAG, "unregister()");
		
		// FIXME: check if implementation is right
		context.unregisterReceiver(new SysEventReceiver());
	}
	
	public static void handle(Context appCtx, Bundle stateExtras) {
		// Loggers
		Log.d(TAG, "handle()");
		
		List<Trigger> triggers = null;

		int ringerMode = stateExtras.getInt(AudioManager.EXTRA_RINGER_MODE, -1);

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
