/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName="start_app_actions")
public class StartAppAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "StartAppAction";
	
	/**
	 * Holds the app's name.
	 */
	@DatabaseField
	private String appName = null;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected StartAppAction() {
		super();
	}

	/**
	 * Minimal constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 */
	public StartAppAction(Context context, Rule rule, String name, String description) {
		super(context, rule, StartAppAction.class.getSimpleName(), name, description);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 * @param appName		String represents app's name
	 */
	public StartAppAction(Context context, Rule rule, String name, String description,
			String appName) {
		super(context, rule, StartAppAction.class.getSimpleName(), name, description);
		this.appName = appName;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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
			Intent intent = packageManager.getLaunchIntentForPackage("com.android.contacts");
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

}
