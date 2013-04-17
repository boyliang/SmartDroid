/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RuleDAO implements IRuleDAO {

	private static final String TAG = RuleDAO.class.getSimpleName();

	private Context context = null;

	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public RuleDAO(Context context) {
		super();

		Log.d(TAG, "RuleDAO(Context context)");

		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#list()
	 */
	@Override
	public List<Rule> list() {
		Log.d(TAG, "list()");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#get(long)
	 */
	@Override
	public Rule get(long ruleId) {
		Log.d(TAG, "get(long ruleId: " + ruleId + ")");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#insert(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public Rule insert(Rule rule) {
		Log.d(TAG, "insert(Rule rule)");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#update(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public void update(Rule rule) {
		Log.d(TAG, "update(Rule rule)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IRuleDAO#delete(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public void delete(Rule rule) {
		Log.d(TAG, "delete(Rule rule)");

	}

}
