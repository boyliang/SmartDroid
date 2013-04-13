/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class RuleDAO implements IRuleDAO {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "RuleDAO";
	
	/**
	 * Holds the invoking context.
	 */
	private Context context = null;
	
	/**
	 * Holds the rule dao.
	 */
	private Dao<Rule, Long> ruleDao = null;

	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public RuleDAO(Context context) {
		super();
		
		// Logger
		Log.d(TAG, "RuleDAO(Context context)");
		
		this.context = context;
		
		// Creates the requested action derived class dao
		DatabaseManager databaseManager = DatabaseManager.getInstance(context);
		try {
			ruleDao = databaseManager.getDatabaseHelper().getDao(Rule.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#list()
	 */
	@Override
	public List<Rule> list() {
		// Logger
		Log.d(TAG, "list()");
		
		List<Rule> ruleList = null;
		
		try {
			ruleList = ruleDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ruleList;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#get(long)
	 */
	@Override
	public Rule get(long ruleId) {
		// Logger
		Log.d(TAG, "get(long ruleId: "+ ruleId +")");
		
		Rule rule = null;
		
		try {
			rule = ruleDao.queryForId(ruleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rule;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#insert(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public Rule insert(Rule rule) {
		// Logger
		Log.d(TAG, "insert(Rule rule)");
		
		try {
			ruleDao.create(rule);
			List<Trigger> triggers = (List<Trigger>) rule.getTriggers();
			if (!triggers.isEmpty()) {
				ITriggerDAO triggerDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getTriggerDAO(context);
				for (Trigger trigger : triggers) {
					triggerDAO.insert(trigger);
				}
			}
			List<Action> actions = (List<Action>) rule.getActions();
			if (!actions.isEmpty()) {
				IActionDAO actionDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getActionDAO(context);
				for (Action action : actions) {
					actionDAO.insert(action);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rule;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#update(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public void update(Rule rule) {
		// Logger
		Log.d(TAG, "update(Rule rule)");
		
		try {
			ruleDao.update(rule);
			List<Trigger> triggers = (List<Trigger>) rule.getTriggers();
			if (!triggers.isEmpty()) {
				ITriggerDAO triggerDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getTriggerDAO(context);
				for (Trigger trigger : triggers) {
					triggerDAO.update(trigger);
				}
			}
			List<Action> actions = (List<Action>) rule.getActions();
			if (!actions.isEmpty()) {
				IActionDAO actionDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getActionDAO(context);
				for (Action action : actions) {
					actionDAO.update(action);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#delete(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public void delete(Rule rule) {
		// Logger
		Log.d(TAG, "delete(Rule rule)");
		
		try {
			ruleDao.delete(rule);
			List<Trigger> triggers = (List<Trigger>) rule.getTriggers();
			if (!triggers.isEmpty()) {
				ITriggerDAO triggerDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getTriggerDAO(context);
				for (Trigger trigger : triggers) {
					triggerDAO.delete(trigger);
				}
			}
			List<Action> actions = (List<Action>) rule.getActions();
			if (!actions.isEmpty()) {
				IActionDAO actionDAO = SmartDAOFactory
						.getFactory(SmartDAOFactory.SQLITE)
						.getActionDAO(context);
				for (Action action : actions) {
					actionDAO.delete(action);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
