/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.broadcastreceivers.BatteryReceiver;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName="battery_triggers")
public class BatteryTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "BatteryTrigger";
	
	/**
	 * Holds the trigger wanted ac power state.
	 */
	@DatabaseField(canBeNull=false)
	private int wantedPowerState = -1;
	

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
	public BatteryTrigger(Context context, Rule rule, String name, String description, int wantedPowerState) {
		super(context, rule, BatteryTrigger.class.getSimpleName(), name, description);
		this.wantedPowerState = wantedPowerState;
	}

	/**
	 * @return the wantedPowerState
	 */
	public int getWantedPowerState() {
		return wantedPowerState;
	}

	/**
	 * @param wantedPowerState the wantedPowerState to set
	 */
	public void setWantedPowerState(int wantedPowerState) {
		this.wantedPowerState = wantedPowerState;
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
		context.registerReceiver(new BatteryReceiver(BatteryTrigger.this), intentFilter);
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
