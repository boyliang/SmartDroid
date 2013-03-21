/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class TriggerDAO implements ITriggerDAO {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "TriggerDAO";
	
	/**
	 * Holds the trigger base class name.
	 */
	private static final String TRIGGER_CLASS_NAME = "Trigger";
	
	/**
	 * Holds the map to the trigger derived daos.
	 * Single instance per class lazzy initialized map.
	 */
	private static Map<String, Dao<Trigger, Long>> triggerDerivedDAOsMap =
			new HashMap<String, Dao<Trigger, Long>>();
	
	/**
	 * Holds the invoking context.
	 */
	private final Context context;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public TriggerDAO(Context context) {
		super();
		
		// Logger
		Log.d(TAG, "TriggerDAO(Context context)");
		
		this.context = context;
		mapTriggerDao(context, Trigger.class);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#list(long)
	 */
	@Override
	public Collection<Trigger> list(long ruleId) {
		// Logger
		Log.d(TAG, "list(long ruleId)");
		
		List<Trigger> baseTriggerList = null;
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		
		try {
			Map<String, Object> filedValues = new HashMap<String, Object>();
			filedValues.put(SmartDroid.Triggers.COLUMN_NAME_RULE_ID, ruleId);
			baseTriggerList = baseTriggerDao.queryForFieldValues(filedValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return baseTriggerList;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#get(long)
	 */
	@Override
	public Trigger get(long triggerId) {
		// Logger
		Log.d(TAG, "get(long triggerId: "+ triggerId +")");
		
		Trigger trigger = null;
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		
		try {
			Trigger baseTrigger = (Trigger) baseTriggerDao.queryForId(triggerId);
			if (baseTrigger != null) {
				Dao<Trigger, Long> derivedTriggerDao = triggerDerivedDAOsMap.get(baseTrigger.getClassName());
				trigger = derivedTriggerDao.queryForId(triggerId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trigger;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Insert(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public Trigger Insert(Trigger trigger) {
		// Logger
		Log.d(TAG, "Insert(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		try {
			trigger.setId( baseTriggerDao.create(trigger) );
			derivedTriggerDao.create(trigger);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trigger;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Update(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public void Update(Trigger trigger) {
		// Logger
		Log.d(TAG, "Update(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		try {
			baseTriggerDao.update(trigger);
			derivedTriggerDao.update(trigger);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Delete(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public void Delete(Trigger trigger) {
		// Logger
		Log.d(TAG, "Delete(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = triggerDerivedDAOsMap.get(trigger.getClassName());
		
		try {
			baseTriggerDao.delete(trigger);
			derivedTriggerDao.delete(trigger);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param context
	 * @param triggerDerivedClass
	 */
	private Dao<Trigger, Long> mapTriggerDao(Context context, Class<? extends Trigger> triggerDerivedClass) {
		// Gets the trigger derived class dao from the trigger daos map by key class name
		Dao<Trigger, Long> derivedTriggerDao = triggerDerivedDAOsMap.get(triggerDerivedClass.getSimpleName());
		// If the trigger derived class dao not exists
		if (derivedTriggerDao == null) {
			// Creates the requested trigger derived class dao
			DatabaseManager databaseManager = DatabaseManager.getInstance(context);
			try {
				derivedTriggerDao = databaseManager.getDatabaseHelper().getDao(triggerDerivedClass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Inserts the just created dao to the trigger derived class daos map
			triggerDerivedDAOsMap.put(triggerDerivedClass.getSimpleName(), derivedTriggerDao);
		}
		return derivedTriggerDao;
	}

}
