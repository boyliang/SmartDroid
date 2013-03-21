/**
 * 
 */
package com.ranlior.smartdroid.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.ranlior.smartdroid.events.ReceiverResponseEvent;
import com.ranlior.smartdroid.model.dto.triggers.BatteryTrigger;
import com.ranlior.smartdroid.utilities.BusProvider;
import com.squareup.otto.Produce;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class BatteryReceiver extends BroadcastReceiver{
	
	/**
	 * Holds the broadcast reciver register trigger.
	 */
	private BatteryTrigger batteryTrigger = null;

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
				|| status == BatteryManager.BATTERY_STATUS_FULL;

		int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
		
		int wantedPowerState = batteryTrigger.getWantedPowerState();
		if (BatteryManager.BATTERY_PLUGGED_AC == wantedPowerState) {
			// FIXME: later
			batteryTrigger.setSatisfied(true);
			BusProvider.getInstance().post(produceReceiverResponseEvent());
		}
	}

	/**
	 * Full constructor.s
	 * 
	 * @param trigger
	 */
	public BatteryReceiver(BatteryTrigger batteryTrigger) {
		super();
		this.batteryTrigger = batteryTrigger;
	}
	
	@Produce
	public ReceiverResponseEvent produceReceiverResponseEvent() {
		return new ReceiverResponseEvent(batteryTrigger);
	}
	
}
