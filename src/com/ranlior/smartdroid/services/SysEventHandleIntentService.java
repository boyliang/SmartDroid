/**
 * 
 */
package com.ranlior.smartdroid.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.triggers.BatteryLevelTrigger;
import com.ranlior.smartdroid.model.dto.triggers.BatteryPluggedTrigger;
import com.ranlior.smartdroid.model.dto.triggers.BootCompletedTrigger;
import com.ranlior.smartdroid.model.dto.triggers.LocationProximityTrigger;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.WiredHeadsetPluggedTrigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SysEventHandleIntentService extends IntentService {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "BroadcastReceivedIntentService";

	/**
	 * Default constructor.
	 */
	public SysEventHandleIntentService() {
		super(SysEventHandleIntentService.class.getSimpleName());
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Logger
		Log.d(TAG, "onHandleIntent(Intent intent)");
		
		Context appCtx = getApplicationContext();
		String action = intent.getStringExtra(SmartDroid.Extra.EXTRA_ACTION);
		Bundle stateExtras = intent.getExtras();
		
		/*
		 * TODO: map action string to concrette trigger static handle method.
		 */
		if ("android.media.RINGER_MODE_CHANGED".equals(action)) {
			RingerModeTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
			BootCompletedTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.ACTION_BATTERY_LOW".equals(action)) {
			stateExtras.putInt(SmartDroid.Extra.EXTRA_BATTERY_LEVEL, BatteryLevelTrigger.BATTERY_LEVEL_LOW);
			BatteryLevelTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.ACTION_BATTERY_OKAY".equals(action)) {
			stateExtras.putInt(SmartDroid.Extra.EXTRA_BATTERY_LEVEL, BatteryLevelTrigger.BATTERY_LEVEL_OKAY);
			BatteryLevelTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
			BatteryPluggedTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
			BatteryPluggedTrigger.handle(appCtx, stateExtras);
		} else if ("com.ranlior.smartdroid.ACTION_LOCATION_PROXIMITY".equals(action)) {
			LocationProximityTrigger.handle(appCtx, stateExtras);
		} else if ("android.intent.action.HEADSET_PLUG".equals(action)) {
			WiredHeadsetPluggedTrigger.handle(appCtx, stateExtras);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		// Logger
		Log.d(TAG, "onCreate()");
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Logger
		Log.d(TAG, "onDestroy()");
	}

	/* (non-Javadoc)
	 * @see android.app
	 * .IntentService#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Logger
		Log.d(TAG, "onStartCommand(Intent intent, int flags, int startId)");
		
		return super.onStartCommand(intent, flags, startId);
	}

}