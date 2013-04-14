package com.ranlior.smartdroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ranlior.smartdroid.fragments.ActionEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;

public class RuleEditorFragmentAdapter extends FragmentPagerAdapter {

	private static final String TAG = RuleEditorFragmentAdapter.class.getSimpleName();

	protected static final String[] CONTENT = new String[] { "Triggers", "Actions" };
	
	private long ruleId = -1;

	public RuleEditorFragmentAdapter(FragmentManager fm, long ruleId) {
		super(fm);
		Log.d(TAG, "Constructor");
		
		this.ruleId = ruleId;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "getItem(int position)");

		switch (position) {
		case 0:
			return TriggerEditorFragment.newInstance(ruleId);
		case 1:
			return ActionEditorFragment.newInstance(ruleId);
		default:
			return null;
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