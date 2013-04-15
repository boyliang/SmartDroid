/**
 * 
 */
package com.ranlior.smartdroid.model.dao;

import android.content.Context;

import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class SmartDAOFactory {

	/*
	 * List of data storage types supported.
	 */

	public static final int DB4O = 1;

	/*
	 * List of DAO types supported by the factory There will be a method for
	 * each DAO that can be created. The conrete factories will have to
	 * implement there methods.
	 */

	/**
	 * @param context
	 * @return
	 */
	public abstract IRuleDAO getRuleDAO(Context context);

	/**
	 * @param context
	 * @return
	 */
	public abstract ITriggerDAO getTriggerDAO(Context context);

	/**
	 * @param context
	 * @return
	 */
	public abstract IActionDAO getActionDAO(Context context);

	/**
	 * Returns the requested dao factory by given constant representing the
	 * factory.
	 * 
	 * @param context
	 * @param whichFactory
	 * @return
	 */
	public static SmartDAOFactory getFactory(int whichFactory) {
		switch (whichFactory) {
		case DB4O:
			return new SmartDb4oDAOFactory();
		default:
			throw new IllegalArgumentException("Invalid factory type argument");
		}
	}

}
