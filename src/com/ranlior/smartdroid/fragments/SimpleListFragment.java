package com.ranlior.smartdroid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.ActionAdapter;
import com.ranlior.smartdroid.adapters.TriggerAdapter;
import com.ranlior.smartdroid.utilities.RuleGenerator;

public final class SimpleListFragment extends SherlockFragment {
   
	private static final String TAG = "SimpleListFragment";
	
	private String content;
	
    public static SimpleListFragment newInstance(String content) {
    	
    	Log.d(TAG, "newInstance(String content)");
    	
    	SimpleListFragment fragment = new SimpleListFragment();
    	fragment.content = content;
    	
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle savedInstanceState)");
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
    	
    	LinearLayout linearLayout = null;
        
        if(content.equals("Triggers")) {
        	linearLayout = (LinearLayout)inflater.inflate(R.layout.list_fragment_page, null);

            ListView lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
        	lvSimple.setAdapter(TriggerAdapter.getInstance(getActivity(), R.layout.trigger_item, RuleGenerator.getTriggers(3)));
        
        } else if(content.equals("Actions")) {
        	linearLayout = (LinearLayout)inflater.inflate(R.layout.list_fragment_page, null);

            ListView lvSimple = (ListView) linearLayout.findViewById(R.id.lvSimple);
        	lvSimple.setAdapter(ActionAdapter.getInstance(getActivity(), R.layout.action_item, RuleGenerator.getActions(2)));
        	
        } else if(content.equals("Rule")) {
        	linearLayout = (LinearLayout)inflater.inflate(R.layout.rule_fragment_details, null);
        	
        }
        		
        return linearLayout;
    }

}
