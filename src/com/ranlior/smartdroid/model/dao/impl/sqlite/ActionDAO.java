/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ActionDAO implements IActionDAO {

	private static final String TAG = ActionDAO.class.getSimpleName();
	
	private Context context = null;

	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public ActionDAO(Context context) {
		super();

		Log.d(TAG, "ActionDAO(Context context)");
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#list(long)
	 */
	@Override
	public List<Action> list(long ruleId) {
		Log.d(TAG, "list(long ruleId)");
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#get(long)
	 */
	@Override
	public Action get(long actionId) {
		Log.d(TAG, "get(long actionId: " + actionId + ")");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IActionDAO#insert(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public Action insert(Action action) {
		Log.d(TAG, "insert(Action action)");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IActionDAO#update(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public void update(Action action) {
		Log.d(TAG, "update(Action action)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .IActionDAO#delete(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public void delete(Action action) {
		Log.d(TAG, "delete(Action action)");

	}

}
