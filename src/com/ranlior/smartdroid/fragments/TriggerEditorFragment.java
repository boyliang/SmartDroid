/**
 * 
 */
package com.ranlior.smartdroid.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.RuleEditorActivity.State;
import com.ranlior.smartdroid.activities.TriggerSelectActivity;
import com.ranlior.smartdroid.adapters.TriggersAdapter;
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

	public static final int ADD_TRIGGER_REQUEST_CODE = 1001;

	public static final int EDIT_TRIGGER_REQUEST_CODE = 1002;

	private ObjectContainer db = null;

	private State state;

	private UUID ruleUuId = null;

	private TriggersAdapter triggersAdapter = null;

	private ListView lvTriggers = null;

	private Listener listener = null;

	private Activity hostingActivity = null;

	private ActionMode actionMode = null;

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
	public static TriggerEditorFragment newInstance(State state, UUID ruleUuid) {
		Log.d(TAG, "newInstance(State state, UUID ruleUuid)");
		TriggerEditorFragment fragment = new TriggerEditorFragment();

		// Supply rule id input as an argument.
		Bundle args = new Bundle();
		args.putSerializable(SmartDroid.Extra.EXTRA_STATE, state);
		if (ruleUuid != null) {
			args.putSerializable(SmartDroid.Extra.EXTRA_RULE_ID, ruleUuid);
		}
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

		outState.putSerializable(SmartDroid.Extra.EXTRA_STATE, state);
		outState.putSerializable(SmartDroid.Extra.EXTRA_RULE_ID, ruleUuId);

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
			state = (State) args.getSerializable(SmartDroid.Extra.EXTRA_STATE);
			ruleUuId = (UUID) args.getSerializable(SmartDroid.Extra.EXTRA_RULE_ID);
		}

		// If recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			state = (State) savedInstanceState.getSerializable(SmartDroid.Extra.EXTRA_STATE);
			ruleUuId = (UUID) savedInstanceState.getSerializable(SmartDroid.Extra.EXTRA_RULE_ID);
		}

		List<Trigger> triggers = null;

		// Gets rule's triggers
		switch (state) {
		case ADD_RULE:
			triggers = new ArrayList<Trigger>();
			break;
		case EDIT_RULE:
			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return ruleUuId.compareTo(rule.getId()) == 0;
				}
			});
			triggers = rules.get(0).getTriggers();
			break;
		}

		triggersAdapter = new TriggersAdapter(hostingActivity, R.layout.trigger_list_item, triggers);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");

		inflater.inflate(R.menu.fragment_trigger_editor_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.addTrigger:
			Intent intent = new Intent(hostingActivity, TriggerSelectActivity.class);
			startActivityForResult(intent, ADD_TRIGGER_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		// If the container view is null,
		// There is no need for us to create the fragment view
		if (container == null) {
			return null;
			// If the container view isn't null,
			// There is need for us to create the fragment view
		} else {
			View view = inflater.inflate(R.layout.fragment_list, null);
			lvTriggers = (ListView) view.findViewById(R.id.listView);
			lvTriggers.setAdapter(triggersAdapter);
			lvTriggers.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Trigger trigger = triggersAdapter.getTrigger(position);
					Log.d(TAG, "Trigger clicked position: " + position);
					Class<? extends Activity> triggerEditorClass = trigger.getTriggerEditor();
					Intent intent = new Intent(hostingActivity, triggerEditorClass);
					// FIXME: search google not position
					intent.putExtra("position", position);
					intent.putExtras(trigger.getExtras());
					startActivityForResult(intent, EDIT_TRIGGER_REQUEST_CODE);
				}
			});
			lvTriggers.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					if (actionMode == null) {
						actionMode = getSherlockActivity().startActionMode(actionModeCallback);
					}
					triggersAdapter.toggleSelected(position);
					return true;
				}
			});

			View emptyView = inflater.inflate(R.layout.empty_trigger_list, null);
			((ViewGroup) lvTriggers.getParent()).addView(emptyView);
			lvTriggers.setEmptyView(emptyView);

			return view;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");

		Trigger trigger = null;

		if (resultCode == hostingActivity.RESULT_OK) {
			switch (requestCode) {
			case ADD_TRIGGER_REQUEST_CODE:
				String triggerClassName = data.getStringExtra(SmartDroid.Extra.EXTRA_TRIGGER_CLASS_NAME);
				Log.d(TAG, "Added Trigger: " + triggerClassName);

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

				triggersAdapter.add(trigger);

				lvTriggers.postDelayed(new Runnable() {
					@Override
					public void run() {
						lvTriggers.setSelection(triggersAdapter.getCount() - 1);
					}
				}, 100L);

				listener.setTriggers(triggersAdapter.getTriggers());
				break;
			case EDIT_TRIGGER_REQUEST_CODE:
				final int position = data.getIntExtra("position", -1);
				Log.d(TAG, "Edited Trigger in position: " + position);
				trigger = triggersAdapter.getTrigger(position);
				trigger.setExtras(data.getExtras());
				lvTriggers.postDelayed(new Runnable() {
					@Override
					public void run() {
						lvTriggers.setSelection(position);
					}
				}, 100L);
				break;
			}
		}
	}

	private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			Log.d(TAG, "onCreateActionMode(ActionMode mode, Menu menu)");
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.fragment_trigger_editor_action_mode, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			Log.d(TAG, "onPrepareActionMode(ActionMode mode, Menu menu)");
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Log.d(TAG, "onActionItemClicked(ActionMode mode, MenuItem item)");
			switch (item.getItemId()) {
			case R.id.deleteTrigger:
				db = Db4oHelper.db(hostingActivity);
				List<Trigger> selectedTriggers = triggersAdapter.getSelected();
				for (Trigger trigger : selectedTriggers) {
					db.delete(trigger);
					db.commit();
					triggersAdapter.remove(trigger);
				}
				mode.finish();
				return true;
			default:
				return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			Log.d(TAG, "onDestroyActionMode(ActionMode mode)");
			actionMode = null;
			triggersAdapter.clearSelected();
		}
	};

}
