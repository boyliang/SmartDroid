package com.ranlior.smartdroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ranlior.smartdroid.fragments.ActionEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;

public class RuleEditorFragmentAdapter extends FragmentPagerAdapter {

	private static final String TAG = RuleEditorFragmentAdapter.class.getSimpleName();

	protected static final String[] CONTENT = new String[] { "Triggers", "Actions", "Rule" };

	private int mCount = CONTENT.length;

	public RuleEditorFragmentAdapter(FragmentManager fm) {
		super(fm);
		Log.d(TAG, "RuleEditorFragmentAdapter(FragmentManager fm)");
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "getItem(int position)");

		switch (position) {
		case 0:
			return new TriggerEditorFragment();
		case 1:
			return new ActionEditorFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		Log.d(TAG, "getCount()");
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.d(TAG, "getPageTitle(int position)");
		return RuleEditorFragmentAdapter.CONTENT[position % CONTENT.length];
	}

}