/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.StartAppActionEditorAcivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class StartAppAction extends Action {

	private static final String TAG = StartAppAction.class.getSimpleName();

	private static final String NAME = "Start Application";

	private static final String DESCRIPTION = "Starts an application";

	private static final int ICON = R.drawable.ic_list_application;

	/**
	 * Holds the application to start package.
	 */
	private String appPackage = null;

	public StartAppAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param appPackage
	 *            String represents app's package
	 */
	public StartAppAction(String appPackage) {
		super(NAME, DESCRIPTION);
		this.appPackage = appPackage;
	}

	/**
	 * @return the appPackage
	 */
	public String getAppPackage() {
		return appPackage;
	}

	/**
	 * @param appPackage
	 *            the appPackage to set
	 */
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dto.actions.Action#perform(android.content
	 * .Context)
	 */
	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		try {
			// Launches the choosen application
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage(appPackage);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putString("appPackage", appPackage);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setAppPackage(extras.getString("appPackage"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return StartAppActionEditorAcivity.class;
	}

}
