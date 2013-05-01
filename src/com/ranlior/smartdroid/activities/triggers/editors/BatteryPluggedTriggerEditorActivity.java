package com.ranlior.smartdroid.activities.triggers.editors;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.ranlior.smartdroid.R;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class BatteryPluggedTriggerEditorActivity extends SherlockActivity {

	private static final String TAG = BatteryPluggedTriggerEditorActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_battery_plugged_trigger_editor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
		getSupportMenuInflater().inflate(R.menu.activity_trigger_editor_menu, menu);
		return true;
	}

}
