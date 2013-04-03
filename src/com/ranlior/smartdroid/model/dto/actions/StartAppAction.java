/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "start_app_actions")
public class StartAppAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "StartAppAction";
	
	/**
	 * The action's name. 
	 */
	private static final String NAME = "Start Application";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Starts an application";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "start_app_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name application package.
	 * 
	 * <P>Type: STRING</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_APP_PACKAGE = "app_package";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the application to start package.
	 */
	@DatabaseField(columnName = StartAppAction.COLUMN_NAME_APP_PACKAGE, canBeNull = false)
	private String appPackage = null;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public StartAppAction() {
		super();
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 * @param appPackage	String represents app's package
	 */
	public StartAppAction(Context context, Rule rule, String appPackage) {
		super(context, rule, StartAppAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.appPackage = appPackage;
	}

	/**
	 * @return the appPackage
	 */
	public String getAppPackage() {
		return appPackage;
	}

	/**
	 * @param appPackage the appPackage to set
	 */
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		try {
			// Launches the choosen application
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage(appPackage);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

}
