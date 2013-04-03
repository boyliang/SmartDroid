package com.ranlior.smartdroid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.TriggerAdapter;
import com.ranlior.smartdroid.config.SmartDroid;

public final class FragmentGenerator extends SherlockFragment {

	private static final String TAG = "FragmentGenerator";

	private String content;

	public static FragmentGenerator newInstance(String content) {
		// Logger
		Log.d(TAG, "newInstance(String content)");

		FragmentGenerator fragment = new FragmentGenerator();
		fragment.content = content;

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Logger
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		LinearLayout linearLayout = null;

		if ("Triggers".equals(content)) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.list_fragment_page, null);
			ListView lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
			lvSimple.setAdapter(TriggerAdapter.getInstance(getActivity(), R.layout.trigger_item, SmartDroid.Triggers.LIST));
		} else if ("Actions".equals(content)) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.list_fragment_page, null);
			ListView lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
			// lvSimple.setAdapter(ActionAdapter.getInstance(getActivity(),
			// R.layout.action_item, RuleGenerator.getActions(2)));
		} else if ("Rule".equals(content)) {
			linearLayout = (LinearLayout) inflater.inflate(R.layout.rule_fragment_details, null);
		}

		return linearLayout;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		// Logger
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");

		inflater.inflate(R.menu.rule_editor_menu, menu);
	}

}
