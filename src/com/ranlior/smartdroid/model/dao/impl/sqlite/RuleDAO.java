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
	private final Context context;
	
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
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#list()
	 */
	@Override
	public List<Rule> list() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#get(long)
	 */
	@Override
	public Rule get(long ruleId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#Insert(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public Rule Insert(Rule rule) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#Update(com.ranlior.smartdroid.model.dto.rules.Rule)
	 */
	@Override
	public void Update(Rule rule) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IRuleDAO#Delete(long)
	 */
	@Override
	public void Delete(long ruleId) {
		// TODO Auto-generated method stub
		
	}

}
