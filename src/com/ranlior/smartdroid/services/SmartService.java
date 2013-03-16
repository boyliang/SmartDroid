/**
 * 
 */
package com.ranlior.smartdroid.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ranlior.smartdroid.events.RequestContextEvent;
import com.ranlior.smartdroid.events.SendContextEvent;
import com.ranlior.smartdroid.utilities.BusProvider;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

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
		
		BusProvider.getInstance().register(this);
		
		return START_STICKY;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Logger
		Log.d(TAG, "onDestroy()");
		
		BusProvider.getInstance().unregister(this);
	}
	
	@Produce
	public SendContextEvent sendContextEvent(Context context) {
		// Logger
		Log.d(TAG, "sendContextEvent(Context context)");
		
		return new SendContextEvent(context);
	}
	
	@Subscribe
	public void subContextEvent(RequestContextEvent event) {
		// Logger
		Log.d(TAG, "subContextEvent(RequestContextEvent event)");
		
		BusProvider.getInstance().post(sendContextEvent(this));
	}
	
}