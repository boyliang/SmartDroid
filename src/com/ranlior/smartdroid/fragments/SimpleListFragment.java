package com.ranlior.smartdroid.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.ActionSelectActivity;
import com.ranlior.smartdroid.activities.AddRuleActivity;
import com.ranlior.smartdroid.activities.RuleActivity;
import com.ranlior.smartdroid.activities.TriggerSelectActivity;
import com.ranlior.smartdroid.adapters.ActionAdapter;
import com.ranlior.smartdroid.adapters.TriggerAdapter;
import com.ranlior.smartdroid.utilities.RuleGenerator;

public final class SimpleListFragment extends SherlockFragment {

	private static final String TAG = "SimpleListFragment";
	
	private String content;

	private ListView lvSimple;

	public static SimpleListFragment newInstance(String content) {

		Log.d(TAG, "newInstance(String content)");

		SimpleListFragment fragment = new SimpleListFragment();
	
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

		if ("Triggers".equals(content) || "Actions".equals(content)) {
			menu.add("Add Item").setIcon(R.drawable.ic_action_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else if ("Rule".equals(content)) {
			menu.clear();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		if ("Add Item".equals(item.getTitle().toString())) {

			Intent intent = null;
			int requestCode = -1;
			if ("Triggers".equals(content)) {
				intent = new Intent(getActivity(), TriggerSelectActivity.class);
				requestCode = AddRuleActivity.SELECT_TRIGGER_REQUEST_CODE;
				Log.i(TAG, "clicked on the '+' action for triggers");
			} else if ("Actions".equals(content)) {
				Log.i(TAG, "clicked on the '+' action for actions");
				intent = new Intent(getActivity(), ActionSelectActivity.class);
				requestCode = AddRuleActivity.SELECT_ACTION_REQUEST_CODE;
			}

			getActivity().startActivityForResult(intent, requestCode);
		}

		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		LinearLayout linearLayout = null;

		if (content.equals("Triggers")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.list_fragment_page, null);

			lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
			lvSimple.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, AddRuleActivity.triggerStrings));

		} else if (content.equals("Actions")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.list_fragment_page, null);

			lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
			lvSimple.setAdapter(ActionAdapter.getInstance(getActivity(), R.layout.action_item, RuleGenerator.getActions(2)));

		} else if (content.equals("Rule")) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.rule_fragment_details, null);

		}

		return linearLayout;
	}
	


}
