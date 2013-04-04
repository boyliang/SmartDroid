package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;

public class ActionSelectActivity extends Activity {

	private static final String TAG = "ActionSelectActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_select);
		
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
		
		setResult(RESULT_OK);
		finish();
	}
}
