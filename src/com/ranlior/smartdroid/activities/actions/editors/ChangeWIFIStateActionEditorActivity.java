package com.ranlior.smartdroid.activities.actions.editors;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.actions.ChangeWIFIStateAction;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeWIFIStateActionEditorActivity extends Activity {

	private static final String TAG = ChangeWIFIStateActionEditorActivity.class.getSimpleName();

	private RadioGroup rgWifiState = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_change_wifi_state_action_editor);

		rgWifiState = (RadioGroup) findViewById(R.id.rgWifiState);
		rgWifiState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int wantedWifiState = BluetoothAdapter.STATE_OFF;
				switch (checkedId) {
				case R.id.rbWifiOn:
					wantedWifiState = WifiManager.WIFI_STATE_ENABLED;
					break;
				case R.id.rbWifiOff:
					wantedWifiState = WifiManager.WIFI_STATE_DISABLED;
					break;
				}
				Intent resIntent = new Intent();
				resIntent.putExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME, ChangeWIFIStateAction.class.getSimpleName());
				// FIXME: search goole not position
				resIntent.putExtra("position", getIntent().getIntExtra("position", -1));
				resIntent.putExtra("wantedWifiState", wantedWifiState);
				setResult(RESULT_OK, resIntent);
				finish();
			}
		});

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// If editing trigger, renders trigger's state
		if (extras != null) {
			switch (extras.getInt("wantedWifiState")) {
			case BluetoothAdapter.STATE_ON:
				rgWifiState.check(R.id.rbWifiOn);
				break;
			case BluetoothAdapter.STATE_OFF:
				rgWifiState.check(R.id.rbWifiOff);
				break;
			}
		}
	}

}
