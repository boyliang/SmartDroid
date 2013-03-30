package com.ranlior.smartdroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.fragments.SimpleListFragment;

public class AddRuleFragmentAdapter extends FragmentPagerAdapter {
	
	private static final String TAG = "AddRuleFragmentAdapter";
	
    protected static final String[] CONTENT = new String[] { "Triggers", "Actions", "Rule" };
    

    private int mCount = CONTENT.length;

    public AddRuleFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleListFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return AddRuleFragmentAdapter.CONTENT[position % CONTENT.length];
    }

   

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}