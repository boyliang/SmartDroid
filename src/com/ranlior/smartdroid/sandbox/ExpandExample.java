package com.ranlior.smartdroid.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;

public class ExpandExample extends Activity {


	
	private ExpandableListView list;
	private MyExpandableListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand_example);
		
		list = (ExpandableListView)findViewById(R.id.expandableListView1);
		adapter = new MyExpandableListAdapter(this, SmartDroid.Triggers.LIST);
		list.setAdapter(adapter);
		list.setGroupIndicator(null);
		
	}


}
