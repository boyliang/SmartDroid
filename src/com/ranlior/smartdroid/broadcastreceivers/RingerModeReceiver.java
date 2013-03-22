/**
 * 
 */
package com.ranlior.smartdroid.broadcastreceivers;

import com.ranlior.smartdroid.events.ReceiverResponseEvent;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.utilities.BusProvider;
import com.squareup.otto.Produce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

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
	
	/**
	 * Holds the broadcast reciver register trigger.
	 */
	private RingerModeTrigger ringerModeTrigger = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Logger
		Log.d(TAG, "onReceive(Context context, Intent intent)");

		int ringerMode = intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, -1);
		int wantedRingerMode = ringerModeTrigger.getWantedRingerMode();
		
		if (ringerMode == wantedRingerMode) {
			ringerModeTrigger.setSatisfied(true);
			BusProvider.getInstance().post(produceReceiverResponseEvent());
		}
	}

	/**
	 * Default constructor.
	 */
	public RingerModeReceiver() {
		super();
	}

	/**
	 * Full constructor.
	 * 
	 * @param ringerModeTrigger
	 */
	public RingerModeReceiver(RingerModeTrigger ringerModeTrigger) {
		super();
		this.ringerModeTrigger = ringerModeTrigger;
	}
	
	@Produce
	public ReceiverResponseEvent produceReceiverResponseEvent() {
		return new ReceiverResponseEvent(ringerModeTrigger);
	}
}
