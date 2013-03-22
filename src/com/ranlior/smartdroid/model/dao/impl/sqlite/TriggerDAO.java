/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.rules.Rule;
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
	 * Holds the increment constant.
	 */
	private static final int INC_NOT_SATISFIED_TRIGGERS_COUNT = 0;
	
	/**
	 * Holds the decrement constant.
	 */
	private static final int DEC_NOT_SATISFIED_TRIGGERS_COUNT = 1;
	
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
		
		Trigger derivedTrigger = null;
		List<Trigger> derivedTriggerList = new ArrayList<Trigger>();
		Dao<Trigger, Long> derivedTriggerDao = null;
		
		try {
			Map<String, Object> filedValues = new HashMap<String, Object>();
			filedValues.put(SmartDroid.Triggers.COLUMN_NAME_RULE_ID, ruleId);
			baseTriggerList = baseTriggerDao.queryForFieldValues(filedValues);
			// Gets the concrette derived triggers
			for (Trigger trigger : baseTriggerList) {
				derivedTriggerDao = triggerDerivedDAOsMap.get(trigger.getClassName());
				derivedTrigger = derivedTriggerDao.queryForId(trigger.getId());
				derivedTrigger.setContext(context);
				derivedTriggerList.add(derivedTrigger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return derivedTriggerList;
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
			// Gets the concrette derived trigger
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
	public Trigger insert(Trigger trigger) {
		// Logger
		Log.d(TAG, "insert(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		try {
			trigger.setId( baseTriggerDao.create(trigger) );
			derivedTriggerDao.create(trigger);
			updateRuleNotSatisfiedTriggersCount(trigger, INC_NOT_SATISFIED_TRIGGERS_COUNT);
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
	public void update(Trigger trigger) {
		// Logger
		Log.d(TAG, "update(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		try {
			Trigger persistedTrigger = get(trigger.getId());
			boolean isPersistedTriggerSatisfied = persistedTrigger.isSatisfied();
			boolean isUpdatedTriggerSatisfied = trigger.isSatisfied();
			if (isPersistedTriggerSatisfied != isUpdatedTriggerSatisfied) {
				// If updated trigger become satisfied, update rule
				if (isUpdatedTriggerSatisfied) {
					updateRuleNotSatisfiedTriggersCount(trigger, DEC_NOT_SATISFIED_TRIGGERS_COUNT);
				// If updated trigger become not satisfied, update rule
				} else {
					updateRuleNotSatisfiedTriggersCount(trigger, INC_NOT_SATISFIED_TRIGGERS_COUNT);
				}
			}
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
	public void delete(Trigger trigger) {
		// Logger
		Log.d(TAG, "delete(Trigger trigger)");
		
		Dao<Trigger, Long> baseTriggerDao = triggerDerivedDAOsMap.get(TRIGGER_CLASS_NAME);
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		try {
			baseTriggerDao.delete(trigger);
			derivedTriggerDao.delete(trigger);
			updateRuleNotSatisfiedTriggersCount(trigger, DEC_NOT_SATISFIED_TRIGGERS_COUNT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#query(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public QueryBuilder<Trigger, Long> query(Trigger trigger) {
		// Logger
		Log.d(TAG, "query(Trigger trigger)");
		
		Dao<Trigger, Long> derivedTriggerDao = mapTriggerDao(context, trigger.getClass());
		
		return derivedTriggerDao.queryBuilder();
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
	
	/**
	 * Increments or decrements rule's not satisfied triggers counter.
	 * 
	 * @param trigger
	 */
	private void updateRuleNotSatisfiedTriggersCount(Trigger trigger, int op) {
		IRuleDAO ruleDao = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(context);
		
		Rule rule = trigger.getRule();
		switch (op) {
		case INC_NOT_SATISFIED_TRIGGERS_COUNT:
			rule.incNotSatisfiedTriggerCount();
			break;
		case DEC_NOT_SATISFIED_TRIGGERS_COUNT:
			rule.decNotSatisfiedTriggerCount();
			break;
		default:
			break;
		}
		ruleDao.update(rule);
	}

}
