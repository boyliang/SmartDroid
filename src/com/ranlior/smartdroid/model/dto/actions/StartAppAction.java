/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class StartAppAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "StartAppAction";
	
	/**
	 * Holds the app's name.
	 */
	private String appName = null;
	

	/**
	 * Minimal constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 */
	public StartAppAction(Context context, String name, String description) {
		super(context, name, description);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 * @param appName		String represents app's name
	 */
	public StartAppAction(Context context, String name, String description,
			String appName) {
		super(context, name, description);
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
		
		// Launches the choosen application
		Intent intent = new Intent(appName + ".intent.action.Launch");
		mContext.startActivity(intent);
	}

}
