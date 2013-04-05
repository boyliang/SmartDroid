package com.ranlior.smartdroid.fragments;

import java.util.List;

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
import com.ranlior.smartdroid.activities.ActionSelectActivity;
import com.ranlior.smartdroid.activities.RuleEditorActivity;
import com.ranlior.smartdroid.activities.TriggerSelectActivity;
import com.ranlior.smartdroid.adapters.ExpandableActionListAdapter;
import com.ranlior.smartdroid.adapters.ExpandableTrigerListAdapter;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.utilities.TriggerFactory;

public final class EditorFragmentFactory extends SherlockFragment {

	private static final String TAG = "SimpleListFragment";
	
	private static final String KEY_CONTENT = "content";

	private ExpandableListView lvSimple;

	private ExpandableTrigerListAdapter expandableTriggerAdaper;
	private ExpandableActionListAdapter expandableActionAdaper;

	private static List<Trigger> triggers;
	
	private ReciveData hostingActiviity = (ReciveData)getActivity();

	public static EditorFragmentFactory newInstance(String content) {

		Log.d(TAG, "newInstance(String content)");

		EditorFragmentFactory fragment = new EditorFragmentFactory();
		
		Bundle args = new Bundle();
		args.putString(KEY_CONTENT, content);
		
		fragment.setArguments(args);

		return fragment;
	}
	
	/**
	 * hosting activity must implement that interface 
	 * 
	 * @author lior ginsberg
	 *
	 */
	public interface ReciveData {
		
		/**
		 * 
		 * @return the list of triggers from hosing activity
		 */
		public List<Trigger> getTriggers();
		
		/**
		 * 
		 * @return the list of actions from hosting activity
		 */
		public List<Action> getActions();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");

		if ("Triggers".equals(getArguments().get(KEY_CONTENT))) {
			inflater.inflate(R.menu.trigger_list_menu, menu);
		} else if ("Actions".equals(getArguments().get(KEY_CONTENT))) {
			inflater.inflate(R.menu.action_list_menu, menu);
		} else if ("Rule".equals(getArguments().get(KEY_CONTENT))) {
			menu.clear();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
		Intent intent = null;
		int requestCode = -1;

		switch (item.getItemId()) {
		case R.id.addTrigger:
			Log.i(TAG, "clicked on the '+' action for triggers");
			intent = new Intent(getActivity(), TriggerSelectActivity.class);
			requestCode = RuleEditorActivity.SELECT_TRIGGER_REQUEST_CODE;

			break;
		case R.id.addAction:
			Log.i(TAG, "clicked on the '+' action for actions");
			intent = new Intent(getActivity(), ActionSelectActivity.class);
			requestCode = RuleEditorActivity.SELECT_ACTION_REQUEST_CODE;
		default:
			break;
		}

		getActivity().startActivityForResult(intent, requestCode);

		return true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		LinearLayout linearLayout = null;

		
		
		if ("Triggers".equals(getArguments().get(KEY_CONTENT))) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_expand_example, null);
			lvSimple = (ExpandableListView) linearLayout.findViewById(R.id.expandableListView1);
			expandableTriggerAdaper = new ExpandableTrigerListAdapter(getActivity(), hostingActiviity.getTriggers());
			lvSimple.setAdapter(expandableTriggerAdaper);
			lvSimple.setGroupIndicator(null);
			lvSimple.setChildDivider(null);
			lvSimple.setChildIndicator(null);

		} else if ("Actions".equals(getArguments().get(KEY_CONTENT))) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_expand_example, null);
			lvSimple = (ExpandableListView) linearLayout.findViewById(R.id.expandableListView1);
			expandableActionAdaper = new ExpandableActionListAdapter(getActivity(), hostingActiviity.getActions());
			lvSimple.setAdapter(expandableActionAdaper);
			lvSimple.setGroupIndicator(null);
			lvSimple.setChildDivider(null);
			lvSimple.setChildIndicator(null);

		} else if ("Rule".equals(getArguments().get(KEY_CONTENT))) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.rule_fragment_details, null);
		}

		return linearLayout;
	}

	// the activity call it after onActivityResult
	public void triggerAddedEvent(String triggerClassName) {

		triggers.add(TriggerFactory.getTriggrByTypeName(triggerClassName));

		expandableTriggerAdaper.notifyDataSetChanged();
		lvSimple.postDelayed(new Runnable() {
			@Override
			public void run() {
				lvSimple.setSelection(expandableTriggerAdaper.getGroupCount() - 1);
			}
		}, 100L);
		lvSimple.expandGroup(expandableTriggerAdaper.getGroupCount() - 1);

	}

}
