/**
 * 
 */
package com.ranlior.smartdroid.model.dao;

import android.content.Context;

import com.ranlior.smartdroid.model.dao.impl.sqlite.ActionDAO;
import com.ranlior.smartdroid.model.dao.impl.sqlite.RuleDAO;
import com.ranlior.smartdroid.model.dao.impl.sqlite.TriggerDAO;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class SmartDb4oDAOFactory extends SmartDAOFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dao.SmartDAOFactory#getRuleDAO(android.content
	 * .Context)
	 */
	@Override
	public IRuleDAO getRuleDAO(Context context) {
		return new RuleDAO(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dao.SmartDAOFactory#getTriggerDAO(android
	 * .content.Context, java.lang.Class)
	 */
	@Override
	public ITriggerDAO getTriggerDAO(Context context) {
		return new TriggerDAO(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dao.SmartDAOFactory#getActionDAO(android
	 * .content.Context, java.lang.Class)
	 */
	@Override
	public IActionDAO getActionDAO(Context context) {
		return new ActionDAO(context);
	}

}
