/**
 * 
 */
package com.ranlior.smartdroid.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SmartService extends Service {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "SmartService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Logger
		Log.d(TAG, "onCreate()");
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Logger
		Log.d(TAG, "onStartCommand(Intent intent, int flags, int startId)");
		
		return START_STICKY;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
