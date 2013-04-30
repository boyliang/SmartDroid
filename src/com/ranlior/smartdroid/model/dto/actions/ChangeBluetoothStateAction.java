/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.ranlior.smartdroid.R;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeBluetoothStateAction extends Action {

	private static final String TAG = ChangeBluetoothStateAction.class.getSimpleName();

	private static final String NAME = "Change bluetooth state";

	private static final String DESCRIPTION = "Changes bluetooth state (enabled / disabled)";

	private final String ICON = "ic_list_bluetooth";

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

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Changes bluetooth state
		if (bluetoothAdapter != null) {
			if (bluetoothState == BluetoothAdapter.STATE_ON && !bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.enable();
			} else if (bluetoothState == BluetoothAdapter.STATE_OFF && bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.disable();
			}
		}
	}

	@Override
	public String getIconName() {
		return ICON;
	}

	@Override
	public View getChildView(Context context, View convertView) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.expand_bluetooth_action, null);
		final RadioGroup rgBluetoothState = (RadioGroup) convertView.findViewById(R.id.rgBluetoothState);
		convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int bluetoothState = BluetoothAdapter.STATE_OFF;
				switch (rgBluetoothState.getCheckedRadioButtonId()) {
				case R.id.rbBluetoothOn:
					bluetoothState = BluetoothAdapter.STATE_ON;
					break;
				case R.id.rbBluetoothOff:
					bluetoothState = BluetoothAdapter.STATE_OFF;
					break;
				}
				setBluetoothState(bluetoothState);
			}
		});
		return convertView;
	}

}
