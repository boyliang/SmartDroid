package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.TriggerSelectAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerSelectActivity extends SherlockListActivity {

	private static final String TAG = TriggerSelectActivity.class.getSimpleName();

	private static final int ADD_TRIGGER_REQUEST_CODE = 1001;

	List<Trigger> triggers = new ArrayList<Trigger>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
		
		setContentView(R.layout.activity_trigger_select);

		TriggerSelectAdapter triggerAdapter = new TriggerSelectAdapter(this, R.layout.trigger_list_item, SmartDroid.Triggers.LIST);
		setListAdapter(triggerAdapter);
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		Log.d(TAG, "onListItemClick(ListView list, View view, int position, long id)");

		Trigger trigger = SmartDroid.Triggers.LIST.get(position);
		Class<? extends Activity> triggerEditorClass = trigger.getTriggerEditor();
		Intent intent = new Intent(TriggerSelectActivity.this, triggerEditorClass);
		startActivityForResult(intent, ADD_TRIGGER_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");

		if (resultCode == RESULT_OK) {
			if (requestCode == ADD_TRIGGER_REQUEST_CODE) {
				Intent resIntent = new Intent();
				resIntent.putExtras(data.getExtras());
				setResult(RESULT_OK, resIntent);
				finish();
			}
		}
	}

}
