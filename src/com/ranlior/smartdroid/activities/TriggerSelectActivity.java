package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.SelectTriggerAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerSelectActivity extends ListActivity {

	private static final String TAG = "TriggerSelectActivity";

	List<Trigger> triggers = new ArrayList<Trigger>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trigger_select);

		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		SelectTriggerAdapter triggerAdapter = new SelectTriggerAdapter(this, R.layout.trigger_item, SmartDroid.Triggers.LIST);
		setListAdapter(triggerAdapter);
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {

		Intent resIntent = new Intent();

		resIntent.putExtra("triggerName", SmartDroid.Triggers.LIST.get(position).getClassName());

		setResult(RESULT_OK, resIntent);
		finish();
	}
}
