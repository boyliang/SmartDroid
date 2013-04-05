package com.ranlior.smartdroid.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.ExpandableTrigerListAdapter;
import com.ranlior.smartdroid.utilities.RuleGenerator;

public class ExpandExample extends Activity {


	
	private ExpandableListView list;
	private ExpandableTrigerListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand_example);
		
		list = (ExpandableListView)findViewById(R.id.expandableListView1);
		adapter = new ExpandableTrigerListAdapter(this, RuleGenerator.getTriggers(3));
		list.setAdapter(adapter);
		list.setGroupIndicator(null);
		
		
	}


}
