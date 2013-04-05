package com.ranlior.smartdroid.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleEditorFragmentAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity {

	/**
	 * The logger's tag.
	 */
	private static final String TAG = "RuleEditorActivity";

	/**
	 * Holds the adapter that create pages of lists.
	 */
	private RuleEditorFragmentAdapter mAdapter;

	/**
	 * holds the ViewPgaer
	 */
	private ViewPager mPager;

	/**
	 * Holds the tabs indicator
	 */
	private PageIndicator mIndicator;

	/**
	 * Holds the current position of the pager
	 */
	private int pagePosition;

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

		pagePosition = 0;

		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Toast.makeText(RuleEditorActivity.this, "Changed to page " + position, Toast.LENGTH_SHORT).show();
				pagePosition = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

}
