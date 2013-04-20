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
import com.db4o.ext.Db4oUUID;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.ActionSelectActivity;
import com.ranlior.smartdroid.activities.RuleEditorActivity.State;
import com.ranlior.smartdroid.adapters.ActionExpandableListAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ActionEditorFragment extends SherlockFragment {

	private static final String TAG = ActionEditorFragment.class.getSimpleName();

	public static final int SELECT_ACTION_REQUEST_CODE = 1002;

	private ObjectContainer db = null;

	private State state;

	private long ruleUuIdLong = -1L;

	private byte[] ruleUuidSignatire = null;

	private static List<Action> actions = null;

	private ActionExpandableListAdapter expandableActionAdaper = null;

	private ExpandableListView elvActions = null;

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
		 * @param actions
		 */
		public void setActions(List<Action> actions);
	}

	/**
	 * Create a new instance of the fargment, initialized to show the actions of
	 * the rule by given rule id.
	 */
	public static ActionEditorFragment newInstance(State state, Db4oUUID ruleUuid) {
		Log.d(TAG, "newInstance(State state, Db4oUUID ruleUuid)");
		ActionEditorFragment fragment = new ActionEditorFragment();

		// Supply rule id input as an argument.
		Bundle args = new Bundle();
		args.putSerializable("state", state);
		if (ruleUuid != null) {
			args.putLong("long", ruleUuid.getLongPart());
			args.putByteArray("signature", ruleUuid.getSignaturePart());
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

		// Saves the state
		outState.putSerializable("state", state);
		// Saves the rule's uuid
		outState.putLong("long", ruleUuIdLong);
		outState.putByteArray("signature", ruleUuidSignatire);

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
			state = (State) args.getSerializable("state");
			ruleUuIdLong = args.getLong("long");
			ruleUuidSignatire = args.getByteArray("signature");
		}

		// If recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			state = (State) savedInstanceState.getSerializable("state");
			ruleUuIdLong = savedInstanceState.getLong("long");
			ruleUuidSignatire = savedInstanceState.getByteArray("signature");
		}

		// Gets rule's actions
		switch (state) {
		case ADD_RULE:
			actions = new ArrayList<Action>();
			break;
		case EDIT_RULE:
			Db4oUUID ruleUuid = new Db4oUUID(ruleUuIdLong, ruleUuidSignatire);
			Rule rule = db.ext().getByUUID(ruleUuid);
			db.activate(rule, 3);
			actions = rule.getActions();
			break;
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");

		inflater.inflate(R.menu.activity_action_select, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.addAction:
			Intent intent = new Intent(hostingActivity, ActionSelectActivity.class);
			startActivityForResult(intent, SELECT_ACTION_REQUEST_CODE);
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
			expandableActionAdaper = new ActionExpandableListAdapter(hostingActivity, actions);
			View view = inflater.inflate(R.layout.fragment_expandable_list_actions, null);
			elvActions = (ExpandableListView) view.findViewById(R.id.expandableListView);
			elvActions.setAdapter(expandableActionAdaper);
			return view;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");

		if (resultCode == hostingActivity.RESULT_OK) {
			if (requestCode == SELECT_ACTION_REQUEST_CODE) {
				String actionClassName = data.getStringExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME);
				Log.d(TAG, "actionClassName: " + actionClassName);
				Action action = null;

				try {
					action = (Action) Class.forName(SmartDroid.Actions.PACKAGE + "." + actionClassName).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				}

				actions.add(action);
				elvActions.postDelayed(new Runnable() {
					@Override
					public void run() {
						elvActions.setSelection(expandableActionAdaper.getGroupCount() - 1);
					}
				}, 100L);
				elvActions.expandGroup(expandableActionAdaper.getGroupCount() - 1);
				expandableActionAdaper.notifyDataSetChanged();

				listener.setActions(actions);
			}
		}
	}

}
