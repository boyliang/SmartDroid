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
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.TriggerSelectActivity;
import com.ranlior.smartdroid.adapters.TriggerExpandableListAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class TriggerEditorFragment extends SherlockFragment {

	private static final String TAG = TriggerEditorFragment.class.getSimpleName();

	public static final int SELECT_TRIGGER_REQUEST_CODE = 1001;

	public static List<Trigger> triggers;

	private TriggerExpandableListAdapter expandableTriggerAdaper;

	private ExpandableListView elvTriggers;

	private ITriggerEditorListener listener;

	private Activity hostingActivity;

	/**
	 * Listener interface for the fragment. Container Activity must implement
	 * this interface.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public interface ITriggerEditorListener {
		/**
		 * Gets the rule to edit id.
		 */
		public long getRuleId();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach(Activity activity)");

		// Ensures hosting activity implements the Listener interface
		try {
			listener = (ITriggerEditorListener) activity;
			// Gets fragment hosting activity
			hostingActivity = activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement " + ITriggerEditorListener.class.getSimpleName());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setHasOptionsMenu(true);

		// Gets triggr's rule id, if any
		long ruleId = listener.getRuleId();
		
		// If new rule added there isn't any rule id
		if (ruleId == 0) {
			// create empty new triggers list
			triggers = new ArrayList<Trigger>();
		// If existing rule edited there is rule id
		} else {
			// Gets rule's triggers
			ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getTriggerDAO(hostingActivity);
			triggers = (List<Trigger>) triggerDAO.list(ruleId);
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
		Log.d(TAG, "item selected: " + item.getTitle());

		switch (item.getItemId()) {
		case R.id.addTrigger:
			Intent intent = new Intent(hostingActivity, TriggerSelectActivity.class);
			startActivityForResult(intent, SELECT_TRIGGER_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// FIXME: check if needed or use onCreate only
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		expandableTriggerAdaper = new TriggerExpandableListAdapter(hostingActivity, triggers);
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_expandable_list_triggers, null);
		elvTriggers = (ExpandableListView) linearLayout.findViewById(R.id.expandableListView);
		elvTriggers.setAdapter(expandableTriggerAdaper);

		return linearLayout;
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
				expandableTriggerAdaper.notifyDataSetChanged();
			}
		}
	}

}
