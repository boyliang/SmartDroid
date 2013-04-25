package com.ranlior.smartdroid.adapters;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ranlior.smartdroid.activities.RuleEditorActivity.State;
import com.ranlior.smartdroid.fragments.ActionEditorFragment;
import com.ranlior.smartdroid.fragments.RuleEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;

public class RuleEditorFragmentAdapter extends FragmentPagerAdapter {

	private static final String TAG = RuleEditorFragmentAdapter.class.getSimpleName();

	protected static final String[] CONTENT = new String[] { "TRIGGERS", "ACTIONS", "RULE" };

	private final State state;

	private final UUID ruleUuid;

	public RuleEditorFragmentAdapter(FragmentManager fm, State state, UUID ruleUuid) {
		super(fm);
		Log.d(TAG, "Constructor");

		this.state = state;
		this.ruleUuid = ruleUuid;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "getItem(int position: " + position + ")");

		switch (position) {
		case 0:
			return TriggerEditorFragment.newInstance(state, ruleUuid);
		case 1:
			return ActionEditorFragment.newInstance(state, ruleUuid);
		case 2:
			return RuleEditorFragment.newInstance(state, ruleUuid);
		default:
			throw new IllegalArgumentException(TAG + "#getItem(int position: " + position + ") illegal position.");
		}
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return RuleEditorFragmentAdapter.CONTENT[position % CONTENT.length];
	}

}