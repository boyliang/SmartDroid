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
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerSelectActivity extends ListActivity {

	private static final String TAG = "TriggerSelectActivity";

	// TODO make the list dynamic according to device capabilities
	private static final String[] TRIGGERS_NAMES = { "Battary Level", "Power Source", "Rinnger Mode",};
	private static final String[] TRIGGER_DESCRIPTIONS = { "Options: LOW, FULL, OK", "Options: AC, USB, Battery",
	"Triggers when Ringer mode change: NORMAL, SILENT, VIBERATE" };

	private static final String[] TRIGGERS_CLASS_NAMES = { "BatteryLevelTrigger", "BatteryPluggedTrigger", "RinngerModeTrigger" };

	List<Trigger> triggers = new ArrayList<Trigger>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trigger_select);

		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		for (int i = 0; i < TRIGGERS_NAMES.length; i++) {
			triggers.add(new Trigger(null, null, null, TRIGGERS_NAMES[i], TRIGGER_DESCRIPTIONS[i]));
		}

		setListAdapter(new SelectTriggerAdapter(this, R.layout.trigger_item, triggers));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {

		Intent resIntent = new Intent();
		resIntent.putExtra("triggerName", TRIGGERS_CLASS_NAMES[position]);

		setResult(RESULT_OK, resIntent);
		finish();
	}
}
