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
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.actions.Action;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class ActionDAO implements IActionDAO {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "ActionDAO";
	
	/**
	 * Holds the map to the action derived daos.
	 * Single instance per class lazzy initialized map.
	 */
	private static Map<String, Dao<Action, Long>> actionDerivedDAOsMap =
			new HashMap<String, Dao<Action, Long>>();
	
	/**
	 * Holds the action derived class name.
	 */
	private final String actionDerivedClassName;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public ActionDAO(Context context,  Class<? extends Action> actionDerivedClass) {
		super();
		
		// Logger
		Log.d(TAG, "ActionDAO(Context context)");
		
		// Gets the action derived class name from the class obj
		actionDerivedClassName = actionDerivedClass.getSimpleName();
		// Gets the action derived class dao from the trigger daos map by key class name
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		// If the action derived class dao not exists
		if (actionDao == null) {
			// Creates the requested action derived class dao
			DatabaseManager databaseManager = DatabaseManager.getInstance(context);
			try {
				actionDao = databaseManager.getDatabaseHelper().getDao(actionDerivedClass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Inserts the just created dao to the action derived class daos map
			actionDerivedDAOsMap.put(actionDerivedClassName, actionDao);
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#list()
	 */
	@Override
	public List<Action> list() {
		// Logger
		Log.d(TAG, "list()");
		
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		List<Action> actionList = null;
		
		try {
			actionList = actionDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actionList;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#get(long)
	 */
	@Override
	public Action get(long actionId) {
		// Logger
		Log.d(TAG, "get(long actionId: "+ actionId +")");
		
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		Action action = null;
		
		try {
			action = actionDao.queryForId(actionId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return action;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Insert(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public Action Insert(Action action) {
		// Logger
		Log.d(TAG, "Insert(Action action)");
		
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		
		try {
			action.setId( actionDao.create(action) );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return action;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Update(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public void Update(Action action) {
		// Logger
		Log.d(TAG, "Update(Action action)");
		
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		
		try {
			actionDao.update(action);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Delete(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public void Delete(Action action) {
		// Logger
		Log.d(TAG, "Delete(Action action)");
		
		Dao<Action, Long> actionDao = actionDerivedDAOsMap.get(actionDerivedClassName);
		
		try {
			actionDao.delete(action);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
