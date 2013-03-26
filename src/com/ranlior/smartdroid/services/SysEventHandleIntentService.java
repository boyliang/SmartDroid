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
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;

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