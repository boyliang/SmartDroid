package com.ranlior.smartdroid.activities;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.swipetodissmis.SwipeDismissListViewTouchListener;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleAdapter;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.utilities.RuleGenerator;


public class RuleActivity extends SherlockListActivity {

	private final static String TAG = "RuleActivity";  
	
	private RuleAdapter ruleAdapter;

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	   
		 Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
		 
	        menu.add("Add Rule")
	            .setIcon(R.drawable.ic_action_new).
	           setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
	    	
	        //This uses the imported MenuItem from ActionBarSherlock
	        Log.d(TAG, "Got click: " + item.getTitle().toString());
	        
	        if(item.getTitle().toString().equals("Add Rule")) {
	        	Intent intent = new Intent(this, AddRuleActivity.class);
	        	startActivity(intent);
	        }
	        
	        return true;
	    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
		
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);
		
		//TODO change the Rules source to the actual data
		List<Rule> rules = RuleGenerator.getRules(200);
	
		ruleAdapter = RuleAdapter.getInstance(this, R.layout.rule_card_layout, rules);
		setListAdapter(ruleAdapter);

		//set swipe to dismiss gesture and remove from the adapter the item
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(getListView(),
				new SwipeDismissListViewTouchListener.OnDismissCallback() {
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							ruleAdapter.remove(ruleAdapter.getItem(position));
						}
						ruleAdapter.notifyDataSetChanged();
					}
				});
		getListView().setOnTouchListener(touchListener);
		
		//this is a special listener that preventing from swiping to dismiss while scrolling
		getListView().setOnScrollListener(touchListener.makeScrollListener());

	}

}
