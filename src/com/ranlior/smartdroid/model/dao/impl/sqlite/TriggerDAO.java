/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
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
	 * Holds the map to the trigger derived daos.
	 * Single instance per class lazzy initialized map.
	 */
	private static Map<String, Dao<Trigger, Long>> triggerDerivedDAOsMap =
			new HashMap<String, Dao<Trigger, Long>>();

	/**
	 * Holds the trigger derived class name.
	 */
	private final String triggerDerivedClassName;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public TriggerDAO(Context context, Class<? extends Trigger> triggerDerivedClass) {
		super();
		
		// Logger
		Log.d(TAG, "TriggerDAO(Context context, triggerDerivedClass: "
				+ triggerDerivedClass.getSimpleName() + ")");
		
		// Gets the trigger derived class name from the class obj
		triggerDerivedClassName  = triggerDerivedClass.getSimpleName();
		// Gets the trigger derived class dao from the trigger daos map by key class name
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		// If the trigger derived class dao not exists
		if (triggerDao == null) {
			// Creates the requested trigger derived class dao
			DatabaseManager databaseManager = DatabaseManager.getInstance(context);
			try {
				triggerDao = databaseManager.getDatabaseHelper().getDao(triggerDerivedClass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Inserts the just created dao to the trigger derived class daos map
			triggerDerivedDAOsMap.put(triggerDerivedClassName, triggerDao);
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#list()
	 */
	@Override
	public List<Trigger> list() {
		// Logger
		Log.d(TAG, "list()");
		
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		List<Trigger> triggerList = null;
		
		try {
			triggerList = triggerDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return triggerList;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#get(long)
	 */
	@Override
	public Trigger get(long triggerId) {
		// Logger
		Log.d(TAG, "get(long triggerId: "+ triggerId +")");
		
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		Trigger trigger = null;
		
		try {
			trigger = (Trigger) triggerDao.queryForId(triggerId);
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
		
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		
		try {
			trigger.setId( triggerDao.create(trigger) );
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
		
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		
		try {
			triggerDao.update(trigger);
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
		
		Dao<Trigger, Long> triggerDao = triggerDerivedDAOsMap.get(triggerDerivedClassName);
		
		try {
			triggerDao.delete(trigger);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
