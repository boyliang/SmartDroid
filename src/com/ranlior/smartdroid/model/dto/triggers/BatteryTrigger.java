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
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@Override
	public void register() {
		// Loggers
		Log.d(TAG, "register()");
		
		// Registering a battery broadcast receiver
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = mContext.registerReceiver(null, intentFilter);
	}
	
	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#unregister()
	 */
	public void unregister() {
		// Loggers
		Log.d(TAG, "unregister()");
		
		// FIXME: implement
	}

}
