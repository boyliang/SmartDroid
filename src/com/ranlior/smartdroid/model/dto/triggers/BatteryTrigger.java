/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class BatteryTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "BatteryTrigger";

	/**
	 * Minimal constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param name			String represents trigger's name
	 * @param description	String represents trigger's description
	 */
	public BatteryTrigger(Context context, String name, String description) {
		super(context, name, description);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#isSatisfaied()
	 */
	@Override
	// FIXME: needs to be implemented
	public boolean isSatisfaied() {
		// Loggers
		Log.d(TAG, "isSatisfaied()");
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@Override
	public void register() {
		// Loggers
		Log.d(TAG, "register()");
		
		// Registering a battery broadcast receiver
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = mContext.registerReceiver(null, ifilter);
	}

}
