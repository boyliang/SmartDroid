/**
 * 
 */
package com.ranlior.smartdroid.model.dao;

import android.content.Context;

import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

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
	
	public abstract IRuleDAO getRuleDAO(Context context);
	
	/**
	 * @param context
	 * @param triggerDerivedClass
	 * @return
	 */
	public abstract ITriggerDAO getTriggerDAO(Context context, Class<? extends Trigger> triggerDerivedClass);
	
	/**
	 * @param context
	 * @param actionDerivedClass
	 * @return
	 */
	public abstract IActionDAO getActionDAO(Context context, Class<? extends Action> actionDerivedClass);
	
	/**
	 * Returns the requested dao factory by given constant representing
	 * the factory.
	 * 
	 * @param context
	 * @param whichFactory
	 * @return
	 */
	public static SmartDAOFactory getFactory(int whichFactory) {
		switch (whichFactory) {
		case SQLITE:
			return new SmartSqliteDAOFactory();
		default:
			throw new IllegalArgumentException("Invalid factory type argument");
		}
	}

}
