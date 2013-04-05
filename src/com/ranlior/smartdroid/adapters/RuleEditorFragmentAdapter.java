package com.ranlior.smartdroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Watson.OnCreateOptionsMenuListener;

import com.ranlior.smartdroid.fragments.FragmentGenerator;

public class RuleEditorFragmentAdapter extends FragmentPagerAdapter {
	
	private static final String TAG = "RuleEditorFragmentAdapter";
	
    protected static final String[] CONTENT = new String[] { "Triggers", "Actions", "Rule" };

    private int mCount = CONTENT.length;

    public RuleEditorFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentGenerator.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return RuleEditorFragmentAdapter.CONTENT[position % CONTENT.length];
    }
    
    
}