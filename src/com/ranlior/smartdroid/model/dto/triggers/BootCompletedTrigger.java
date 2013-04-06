/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

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
@DatabaseTable(tableName = "boot_completed_triggers")
public class BootCompletedTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = BootCompletedTrigger.class.getSimpleName();
	
	/**
	 * The trigger's name. 
	 */
	private static final String NAME = "Boot completed";
	
	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when the device boot completes";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The ringer mode triggers table name offered by this provider.
	 */
	public static final String TABLE_NAME = "boot_completed_triggers";
	
	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public BootCompletedTrigger() {
		super(BootCompletedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents trigger's rule
	 */
	public BootCompletedTrigger(Context context, Rule rule) {
		super(context, rule, BootCompletedTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		// Logger
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		List<Trigger> triggers = null;

		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(appCtx);

		QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(BootCompletedTrigger.class);
		
		try {
	       triggers = queryBuilder.query();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		for (Trigger trigger : triggers) {
			BootCompletedTrigger bootCompletedTrigger = (BootCompletedTrigger) trigger;
			bootCompletedTrigger.setSatisfied(true);
			triggerDAO.update(bootCompletedTrigger);
			Rule rule = bootCompletedTrigger.getRule();
			if (rule.isSatisfied()) {
				rule.setContext(appCtx);
				rule.perform();
			}
		}
		
		for (Trigger trigger : triggers) {
			BootCompletedTrigger bootCompletedTrigger = (BootCompletedTrigger) trigger;
			bootCompletedTrigger.setSatisfied(false);
			triggerDAO.update(bootCompletedTrigger);
		}
	}
}
