/**
 * 
 */
package com.ranlior.smartdroid.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.services.SysEventHandleIntentService;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SysEventReceiver extends BroadcastReceiver {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "SysEventReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Logger
		Log.d(TAG, "onReceive(Context context, Intent intent)");
		
		Context appCtx = context.getApplicationContext();

		Intent serviceIntent = new Intent(appCtx, SysEventHandleIntentService.class);
		Log.d(TAG, "ACTION: " + intent.getAction());
		Toast.makeText(appCtx, intent.getAction(), Toast.LENGTH_SHORT).show();
		serviceIntent.putExtra(SmartDroid.Extra.EXTRA_ACTION, intent.getAction());
		if (intent.getExtras() != null) {
			Log.d(TAG, "Extras: not null");
			Toast.makeText(appCtx, "Extras: not null", Toast.LENGTH_SHORT).show();
			serviceIntent.putExtras(intent.getExtras());
		} else {
			Log.d(TAG, "Extras: null");
			Toast.makeText(appCtx, "Extras: null", Toast.LENGTH_SHORT).show();
		}

		appCtx.startService(serviceIntent);
	}
}
