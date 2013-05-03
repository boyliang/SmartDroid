package com.ranlior.smartdroid.activities.actions.editors;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ranlior.smartdroid.R;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class NotificationActionEditorActivity extends Activity {

	private static final String TAG = NotificationActionEditorActivity.class.getSimpleName();
	
	private EditText etNotificationTitle = null;
	
	private RadioGroup rgNotificationDefaults = null;
	
	private EditText etNotificationText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_notification_action_editor);
		
		etNotificationTitle = (EditText) findViewById(R.id.etNotificationTitle);
		etNotificationText = (EditText) findViewById(R.id.etNotificationText);
		rgNotificationDefaults = (RadioGroup) findViewById(R.id.rgNotificationDefaults);
		
		
	}

}
