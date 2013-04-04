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
import com.ranlior.smartdroid.adapters.TriggerAdapter;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerSelectActivity extends ListActivity {

	private static final String TAG = "TriggerSelectActivity";
	
	//TODO make the list dynamic according to device capabilities
	private static final String[] TRIGGERS = {"Battary Level", "Power Source", "Boot", "Location", "Rinnger Mode", "Sensor"};
	private static final String[] DESCRIPTIONS = {"Options: LOW, FULL, OK",
												  "Options: AC, USB, Battery",
												  "Add Trigger to detect when device is done booting",
												  "Have no idea what that is", 
												  "Triggers when Ringer mode change: NORMAL, SILENT, VIBERATE",
												  "Information is un available"};
	
	
	List<Trigger> triggers = new ArrayList<Trigger>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trigger_select);
		
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
	
		for (int i = 0; i < TRIGGERS.length; i++) {
			triggers.add(new Trigger(null, null, null, TRIGGERS[i], DESCRIPTIONS[i]));
		}
		
		setListAdapter(new TriggerAdapter(this, R.layout.trigger_item, triggers));
	}
	
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		
		Intent resIntent = new Intent();
		resIntent.putExtra("triggerName", TRIGGERS[position]);
		
		setResult(RESULT_OK, resIntent);
		finish();
	}
}
