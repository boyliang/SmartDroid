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
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SmartSqliteDAOFactory extends SmartDAOFactory {
	
	/**
	 * Holds the context which invoked this dao.
	 */
	private Context context = null;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public SmartSqliteDAOFactory(Context context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.SmartDAOFactory#getRulesDAO()
	 */
	@Override
	public IRuleDAO getRulesDAO() {
		return new RuleDAO(context);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.SmartDAOFactory#getTriggerDAO()
	 */
	@Override
	public ITriggerDAO getTriggerDAO() {
		return new TriggerDAO(context);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.SmartDAOFactory#getActionDAO()
	 */
	@Override
	public IActionDAO getActionDAO() {
		return new ActionDAO(context);
	}

}
