/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeBluetoothStateAction extends Action {

	private static final String TAG = ChangeBluetoothStateAction.class.getSimpleName();

	private static final String NAME = "Change bluetooth state";

	private static final String DESCRIPTION = "Changes bluetooth state (enabled / disabled)";

	/**
	 * Holds the wanted bluetooth state.
	 * 
	 * <P>
	 * BluetoothAdapter.STATE_OFF:<BR/>
	 * Indicates the local Bluetooth adapter is off.<BR/>
	 * BluetoothAdapter.STATE_ON<BR/>
	 * Indicates the local Bluetooth adapter is on, and ready for use.<BR/>
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.bluetooth.BluetoothAdapter
	 */
	private int bluetoothState = BluetoothAdapter.STATE_OFF;

	public ChangeBluetoothStateAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param bluetoothState
	 *            Integer represents the wanted bluetooth state
	 */
	public ChangeBluetoothStateAction(int bluetoothState) {
		super(NAME, DESCRIPTION);
		this.bluetoothState = bluetoothState;
	}

	/**
	 * @return the bluetoothState
	 */
	public int getBluetoothState() {
		return bluetoothState;
	}

	/**
	 * @param bluetoothState
	 *            the bluetoothState to set
	 */
	public void setBluetoothState(int bluetoothState) {
		this.bluetoothState = bluetoothState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dto.actions.Action#perform(android.content
	 * .Context)
	 */
	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		//XXX
		
		// Changes bluetooth state
		if (mBluetoothAdapter != null) {
			if (bluetoothState == BluetoothAdapter.STATE_ON && !mBluetoothAdapter.isEnabled()) {
				mBluetoothAdapter.enable();
			} else if (bluetoothState == BluetoothAdapter.STATE_OFF) {
				mBluetoothAdapter.disable();
			}
		}
	}

}
