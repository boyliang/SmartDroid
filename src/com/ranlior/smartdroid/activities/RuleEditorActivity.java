package com.ranlior.smartdroid.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleEditorFragmentAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity {

	private static final String TAG = RuleEditorActivity.class.getSimpleName();

	private RuleEditorFragmentAdapter mAdapter;

	private ViewPager mPager;

	private PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate(Bundle savedInstanceState) ");

		setContentView(R.layout.activity_rule_editor);

		mAdapter = new RuleEditorFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

}
