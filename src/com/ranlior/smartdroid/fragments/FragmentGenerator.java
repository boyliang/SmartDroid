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
import com.ranlior.smartdroid.adapters.ExpandableTrigerListAdapter;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.utilities.RuleGenerator;

public final class FragmentGenerator extends SherlockFragment {

	private static final String TAG = "SimpleListFragment";

	private String content;

	private ExpandableListView lvSimple;

	private ExpandableTrigerListAdapter adaper;

	private static List<Trigger> triggers;

	public static FragmentGenerator newInstance(String content) {

		if (triggers == null) {
			triggers = RuleGenerator.getTriggers(3);
		}

		Log.d(TAG, "newInstance(String content)");

		FragmentGenerator fragment = new FragmentGenerator();

		fragment.content = content;

		return fragment;
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

		if ("Triggers".equals(content)) {
			inflater.inflate(R.menu.trigger_list_menu, menu);
		} else if ("Actions".equals(content)) {
			inflater.inflate(R.menu.action_list_menu, menu);
		} else if ("Rule".equals(content)) {
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

		if (content.equals("Triggers")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_expand_example, null);

			lvSimple = (ExpandableListView) linearLayout.findViewById(R.id.expandableListView1);
			adaper = new ExpandableTrigerListAdapter(getActivity(), triggers);
			lvSimple.setAdapter(adaper);
			lvSimple.setGroupIndicator(null);
			lvSimple.setChildDivider(null);
			lvSimple.setChildIndicator(null);

		} else if (content.equals("Actions")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.list_fragment_page, null);

			// lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
			// lvSimple.setAdapter(ActionAdapter.getInstance(getActivity(),
			// R.layout.action_item, RuleGenerator.getActions(2)));

		} else if (content.equals("Rule")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.rule_fragment_details, null);

		}

		return linearLayout;
	}

	// the activity call it after onActivityResult
	public void addTrigger(String stringExtra) {

		triggers.addAll(RuleGenerator.getTriggers(3));

		adaper.notifyDataSetChanged();
		lvSimple.postDelayed(new Runnable() {
			@Override
			public void run() {
				lvSimple.setSelection(adaper.getGroupCount() - 1);
			}
		}, 100L);
		lvSimple.expandGroup(adaper.getGroupCount() - 1);

	}

}
