/**
 * 
 */
package com.ranlior.smartdroid.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.ranlior.smartdroid.services.BroadcastReceivedIntentService;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class RingerModeReceiver extends BroadcastReceiver {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "RingerModeReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Logger
		Log.d(TAG, "onReceive(Context context, Intent intent)");
		
		Context appCtx = context.getApplicationContext();

		Intent serviceIntent = new Intent(appCtx, BroadcastReceivedIntentService.class);
		serviceIntent.putExtra("triggerName", "RingerModeTrigger");

		int ringerMode = intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, -1);
		serviceIntent.putExtra("ringerMode", ringerMode);

		appCtx.startService(serviceIntent);
	}
}
