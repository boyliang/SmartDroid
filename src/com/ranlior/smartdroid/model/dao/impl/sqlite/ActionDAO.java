/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.ranlior.smartdroid.config.SmartDroid;
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
	 * Holds the action base class name.
	 */
	private static final String ACTION_CLASS_NAME = "Action";
	
	/**
	 * Holds the map to the action derived daos.
	 * Single instance per class lazzy initialized map.
	 */
	private static Map<String, Dao<Action, Long>> actionDerivedDAOsMap =
			new HashMap<String, Dao<Action, Long>>();
	
	/**
	 * Holds the invoking context.
	 */
	private Context context = null;
	
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public ActionDAO(Context context) {
		super();
		
		// Logger
		Log.d(TAG, "ActionDAO(Context context)");
		
		this.context = context;
		mapActionDao(context, Action.class);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#list(long)
	 */
	@Override
	public List<Action> list(long ruleId) {
		// Logger
		Log.d(TAG, "list(long ruleId)");
		
		List<Action> baseActionList = null;
		Dao<Action, Long> baseActionDao = actionDerivedDAOsMap.get(ACTION_CLASS_NAME);
		
		Action derivedAction = null;
		List<Action> derivedActionList = new ArrayList<Action>();
		Dao<Action, Long> derivedActionDao = null;
		
		try {
			Map<String, Object> filedValues = new HashMap<String, Object>();
			filedValues.put(SmartDroid.Actions.COLUMN_NAME_RULE_ID, ruleId);
			baseActionList = baseActionDao.queryForFieldValues(filedValues);
			// Gets the concrette derived actions
			for (Action action : baseActionList) {
				derivedActionDao = actionDerivedDAOsMap.get(action.getClassName());
				derivedAction = derivedActionDao.queryForId(action.getId());
				derivedAction.setContext(context);
				derivedActionList.add(derivedAction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return derivedActionList;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#get(long)
	 */
	@Override
	public Action get(long actionId) {
		// Logger
		Log.d(TAG, "get(long actionId: "+ actionId +")");
		
		Action action = null;
		Dao<Action, Long> baseActionDao = actionDerivedDAOsMap.get(ACTION_CLASS_NAME);
		
		try {
			Action baseAction = baseActionDao.queryForId(actionId);
			// Gets the concrette derived action
			if (baseAction != null) {
				Dao<Action, Long> derivedActionDao = actionDerivedDAOsMap.get(baseAction.getClassName());
				action = derivedActionDao.queryForId(actionId);
			}
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
		
		Dao<Action, Long> baseActionDao = actionDerivedDAOsMap.get(ACTION_CLASS_NAME);
		Dao<Action, Long> derivedActionDao = mapActionDao(context, action.getClass());
		
		try {
			action.setId( baseActionDao.create(action) );
			derivedActionDao.create(action);
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
		
		Dao<Action, Long> baseActionDao = actionDerivedDAOsMap.get(ACTION_CLASS_NAME);
		Dao<Action, Long> derivedActionDao = mapActionDao(context, action.getClass());
		
		try {
			action.setId( baseActionDao.update(action) );
			derivedActionDao.update(action);
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
		
		Dao<Action, Long> baseActionDao = actionDerivedDAOsMap.get(ACTION_CLASS_NAME);
		Dao<Action, Long> derivedActionDao = actionDerivedDAOsMap.get(action.getClassName());
		
		try {
			baseActionDao.delete(action);
			derivedActionDao.delete(action);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param context
	 * @param actionDerivedClass
	 */
	private Dao<Action, Long> mapActionDao(Context context, Class<? extends Action> actionDerivedClass) {
		// Gets the action derived class name from the action daos map by key class name
		Dao<Action, Long> derivedAactionDao = actionDerivedDAOsMap.get(actionDerivedClass.getSimpleName());
		// If the action derived class dao not exists
		if (derivedAactionDao == null) {
			// Creates the requested action derived class dao
			DatabaseManager databaseManager = DatabaseManager.getInstance(context);
			try {
				derivedAactionDao = databaseManager.getDatabaseHelper().getDao(actionDerivedClass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Inserts the just created dao to the action derived class daos map
			actionDerivedDAOsMap.put(actionDerivedClass.getSimpleName(), derivedAactionDao);
		}
		return derivedAactionDao;
	}

}
