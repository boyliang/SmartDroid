package com.ranlior.smartdroid.sandbox;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.animations.ExpandAnimation;
import com.ranlior.smartdroid.config.SmartDroid.Triggers;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.utilities.DisplayHelper;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context = null;

	List<Trigger> triggers;

	public MyExpandableListAdapter(Context context, List<Trigger> triggers) {
		this.context = context;
		this.triggers = triggers;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		
			String triggerClassName = triggers.get(groupPosition).getClassName();
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if ("RingerModeTrigger".equals(triggerClassName)) {
				convertView = inflater.inflate(R.layout.expand_ringer_trigger, null);
			}else if ("BatteryLevelTrigger".equals(triggerClassName)) {
				convertView = inflater.inflate(R.layout.expand_battery_trigger, null);
			}else if("BattryPluggedTrigger".equals(triggerClassName)) {
				convertView = inflater.inflate(R.layout.expand_power_trigger, null);
			}
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return triggers.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.trigger_item, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.description);

		title.setText(triggers.get(groupPosition).getName());
		desc.setText(triggers.get(groupPosition).getDescription());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
