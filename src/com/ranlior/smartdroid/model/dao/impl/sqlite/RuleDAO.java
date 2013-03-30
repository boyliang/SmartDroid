/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.rules.Rule;

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
