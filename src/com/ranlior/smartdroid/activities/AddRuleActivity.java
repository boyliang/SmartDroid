package com.ranlior.smartdroid.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.AddRuleFragmentAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class AddRuleActivity extends SherlockFragmentActivity {

	private static final String TAG = "AddRuleActivity";
	
	/**
	 * Holds the adapter that create pages of lists
	 */
	AddRuleFragmentAdapter mAdapter;
	
	/**
	 * holds the ViewPgaer
	 */
	ViewPager mPager;
	
	PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate(Bundle savedInstanceState) ");
		
		setContentView(R.layout.activity_add_rule);

		mAdapter = new AddRuleFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

}
