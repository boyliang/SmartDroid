/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName="BatteryTriggers")
public class BatteryTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "BatteryTrigger";

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected BatteryTrigger() {
		super();
	}

	/**
	 * Minimal constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents trigger's rule
	 * @param name			String represents trigger's name
	 * @param description	String represents trigger's description
	 */
	public BatteryTrigger(Context context, Rule rule, String name, String description) {
		super(context, rule, name, description);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@Override
	public void register() {
		// Loggers
		Log.d(TAG, "register()");
		
		// FIXME: Implment persist the current state of the trigger.
		
		// Registering a battery broadcast receiver
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		mContext.registerReceiver(null, intentFilter);
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
