package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleEditorFragmentAdapter;
import com.ranlior.smartdroid.fragments.FragmentGenerator;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity {

	/**
	 * The logger's tag.
	 */
	private static final String TAG = "AddRuleActivity";

	/**menu
	 * 
	 * Holds the adapter that create pages of lists
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
	
	public static final int SELECT_TRIGGER_REQUEST_CODE = 1001;
	public static final int SELECT_ACTION_REQUEST_CODE = 1002;
	
	//ONLY MOCKUP
	public static List<String> triggerStrings = new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate(Bundle savedInstanceState) ");

		setContentView(R.layout.activity_add_rule);

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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Log.d(TAG,  "onActivityResult(int requestCode, int resultCode, Intent data)");
		
		if(resultCode == RESULT_OK) {
			if(requestCode == SELECT_TRIGGER_REQUEST_CODE) {
				Log.i(TAG, "the user selected: " + data.getStringExtra("triggerName"));
				triggerStrings.add( data.getStringExtra("triggerName"));
				FragmentGenerator triggerPage = (FragmentGenerator)getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.pager+":0");
				triggerPage.addTrigger(data.getStringExtra("triggerName"));
				
			}
		} else {
			Log.e(TAG, "there is a problem returning data from " + (requestCode == SELECT_TRIGGER_REQUEST_CODE ?  "SelectTriggerActivity" : "SelectActionActivity"));
		}
	}

}
