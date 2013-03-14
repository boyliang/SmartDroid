/**
 * 
 */
package com.ranlior.smartdroid.model.dao;

import android.content.Context;

import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public abstract class SmartDAOFactory {
	
	/*
	 *	List of data storage types supported. 
	 */
	
	public static final int SQLITE = 1;
	
	/*
	 *  List of DAO types supported by the factory
	 *  There will be a method for each DAO that can be created.
	 *  The conrete factories will have to implement there methods.
	 */
	
	public abstract IRuleDAO getRulesDAO();
	public abstract ITriggerDAO getTriggerDAO();
	public abstract IActionDAO getActionDAO();
	
	public static SmartDAOFactory getFactory(Context context, int whichFactory) {
		switch (whichFactory) {
		case SQLITE:
			return new SmartSqliteDAOFactory(context);
		default:
			return null;
		}
	}

}
