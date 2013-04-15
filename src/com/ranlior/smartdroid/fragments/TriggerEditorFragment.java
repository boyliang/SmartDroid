/**
 * 
 */
package com.ranlior.smartdroid.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.TriggerSelectActivity;
import com.ranlior.smartdroid.adapters.TriggerExpandableListAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class TriggerEditorFragment extends SherlockFragment {

	private static final String TAG = TriggerEditorFragment.class.getSimpleName();

	public static final int SELECT_TRIGGER_REQUEST_CODE = 1001;

	private ObjectContainer db = null;

	private long ruleId = -1;

	private List<Trigger> triggers = null;

	private TriggerExpandableListAdapter expandableTriggerAdaper = null;

	private ExpandableListView elvTriggers = null;

	private Listener listener = null;

	private Activity hostingActivity = null;

	/**
	 * Listener interface for the fragment. Container Activity must implement
	 * this interface.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public interface Listener {
		/**
		 * @param triggers
		 */
		public void setTriggers(List<Trigger> triggers);
	}

	/**
	 * Create a new instance of the fargment, initialized to show the triggers
	 * of the rule by given rule id.
	 */
	public static TriggerEditorFragment newInstance(long ruleId) {
		TriggerEditorFragment fragment = new TriggerEditorFragment();

		// Supply rule id input as an argument.
		Bundle args = new Bundle();
		args.putLong(SmartDroid.Extra.EXTRA_RULE_ID, ruleId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach(Activity activity)");

		// Ensures hosting activity implements the Listener interface
		try {
			listener = (Listener) activity;
			// Gets fragment hosting activity
			hostingActivity = activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement " + Listener.class.getSimpleName());
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState(Bundle outState)");

		// Saves the rule id
		outState.putLong(SmartDroid.Extra.EXTRA_RULE_ID, ruleId);

		// Calls the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setHasOptionsMenu(true);
		
		db = Db4oHelper.db(hostingActivity);

		// During creation, if arguments have been supplied to the fragment
		// then parse those out
		Bundle args = getArguments();
		if (args != null) {
			ruleId = args.getLong(SmartDroid.Extra.EXTRA_RULE_ID);
		}

		// If recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			ruleId = savedInstanceState.getLong(SmartDroid.Extra.EXTRA_RULE_ID);
		}

		// Gets the rule
		List<Rule> rules = db.query(new Predicate<Rule>() {
			public boolean match(Rule rule) {
				return rule.getId() == ruleId;
			}
		});

		// Gets rule's triggers
		if (!rules.isEmpty()) {
			triggers = rules.get(0).getTriggers();
		} else {
			triggers = new ArrayList<Trigger>();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");

		inflater.inflate(R.menu.activity_trigger_select, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.addTrigger:
			Intent intent = new Intent(hostingActivity, TriggerSelectActivity.class);
			startActivityForResult(intent, SELECT_TRIGGER_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		expandableTriggerAdaper = new TriggerExpandableListAdapter(hostingActivity, triggers);
		View view = inflater.inflate(R.layout.fragment_expandable_list_triggers, null);
		elvTriggers = (ExpandableListView) view.findViewById(R.id.expandableListView);
		elvTriggers.setAdapter(expandableTriggerAdaper);

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");

		if (resultCode == hostingActivity.RESULT_OK) {
			if (requestCode == SELECT_TRIGGER_REQUEST_CODE) {
				String triggerClassName = data.getStringExtra(SmartDroid.Extra.EXTRA_TRIGGER_CLASS_NAME);
				Log.d(TAG, "triggerClassName: " + triggerClassName);
				Trigger trigger = null;

				try {
					trigger = (Trigger) Class.forName(SmartDroid.Triggers.PACKAGE + "." + triggerClassName).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				}

				triggers.add(trigger);
				// elvTriggers.postDelayed(new Runnable() {
				// @Override
				// public void run() {
				// elvTriggers.setSelection(expandableTriggerAdaper.getGroupCount()
				// - 1);
				// }
				// }, 100L);
				// elvTriggers.expandGroup(expandableTriggerAdaper.getGroupCount()
				// - 1);
				expandableTriggerAdaper.notifyDataSetChanged();
				
				listener.setTriggers(triggers);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		Log.d(TAG, "onResume()");

		db = Db4oHelper.db(hostingActivity);
	}

	@Override
	public void onPause() {
		super.onPause();

		Log.d(TAG, "onPause()");

		db.close();
	}

}
