package com.ranlior.smartdroid.activities.actions.editors;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.actions.ChangeBluetoothStateAction;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeBluetoothStateActionEditorActivity extends SherlockActivity {

	private static final String TAG = ChangeBluetoothStateActionEditorActivity.class.getSimpleName();

	private RadioGroup rgBluetoothState = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_change_bluetooth_state_action_editor);

		rgBluetoothState = (RadioGroup) findViewById(R.id.rgBluetoothState);
		rgBluetoothState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int wantedBluetoothState = BluetoothAdapter.STATE_OFF;
				switch (checkedId) {
				case R.id.rbBluetoothOn:
					wantedBluetoothState = BluetoothAdapter.STATE_ON;
					break;
				case R.id.rbBluetoothOff:
					wantedBluetoothState = BluetoothAdapter.STATE_OFF;
					break;
				}
				Intent resIntent = new Intent();
				resIntent.putExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME, ChangeBluetoothStateAction.class.getSimpleName());
				// FIXME: search goole not position
				resIntent.putExtra("position", getIntent().getIntExtra("position", -1));
				resIntent.putExtra("wantedBluetoothState", wantedBluetoothState);
				setResult(RESULT_OK, resIntent);
				finish();
			}
		});

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// If editing trigger, renders trigger's state
		if (extras != null) {
			switch (extras.getInt("wantedBluetoothState")) {
			case BluetoothAdapter.STATE_ON:
				rgBluetoothState.check(R.id.rbBluetoothOn);
				break;
			case BluetoothAdapter.STATE_OFF:
				rgBluetoothState.check(R.id.rbBluetoothOff);
				break;
			}
		}
	}

}
