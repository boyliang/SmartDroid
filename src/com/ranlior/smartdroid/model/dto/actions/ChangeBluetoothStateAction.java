/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.ChangeBluetoothStateActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeBluetoothStateAction extends Action {

	private static final String TAG = ChangeBluetoothStateAction.class.getSimpleName();

	private static final String NAME = "Change bluetooth state";

	private static final String DESCRIPTION = "Changes bluetooth state (enabled / disabled)";

	private static final int ICON = R.drawable.ic_list_bluetooth;

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
	private int wantedBluetoothState = BluetoothAdapter.STATE_OFF;

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
		this.wantedBluetoothState = bluetoothState;
	}

	/**
	 * @return the wantedBluetoothState
	 */
	public int getWantedBluetoothState() {
		return wantedBluetoothState;
	}

	/**
	 * @param wantedBluetoothState
	 *            the wantedBluetoothState to set
	 */
	public void setWantedBluetoothState(int wantedBluetoothState) {
		this.wantedBluetoothState = wantedBluetoothState;
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

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Changes bluetooth state
		if (bluetoothAdapter != null) {
			if (wantedBluetoothState == BluetoothAdapter.STATE_ON && !bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.enable();
			} else if (wantedBluetoothState == BluetoothAdapter.STATE_OFF && bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.disable();
			}
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putInt("wantedBluetoothState", wantedBluetoothState);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setWantedBluetoothState(extras.getInt("wantedBluetoothState"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return ChangeBluetoothStateActionEditorActivity.class;
	}

}
