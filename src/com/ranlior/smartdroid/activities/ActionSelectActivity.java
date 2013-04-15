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
import com.ranlior.smartdroid.adapters.ActionSelectAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ActionSelectActivity extends ListActivity {

	private static final String TAG = ActionSelectActivity.class.getSimpleName();

	List<Action> actions = new ArrayList<Action>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_action_select);

		ActionSelectAdapter actionAdapter = new ActionSelectAdapter(this, R.layout.action_list_item, SmartDroid.Actions.LIST);
		setListAdapter(actionAdapter);
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		Log.d(TAG, "onListItemClick(ListView list, View view, int position, long id)");

		Intent resIntent = new Intent();
		resIntent.putExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME,
				SmartDroid.Actions.LIST.get(position).getClass().getSimpleName());
		setResult(RESULT_OK, resIntent);

		finish();
	}
}
