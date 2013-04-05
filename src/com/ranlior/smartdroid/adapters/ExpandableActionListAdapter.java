package com.ranlior.smartdroid.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ExpandableActionListAdapter extends BaseExpandableListAdapter {

	private Context context = null;

	List<Action> actions;

	public ExpandableActionListAdapter(Context context, List<Action> actions) {
		this.context = context;
		this.actions = actions;
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

		
			String triggerClassName = actions.get(groupPosition).getClassName();
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//TODO inflate actionView for action type 
//			if ("RingerModeTrigger".equals(triggerClassName)) {
//				convertView = inflater.inflate(R.layout.expand_ringer_trigger, null);
//			}else if ("BatteryLevelTrigger".equals(triggerClassName)) {
//				convertView = inflater.inflate(R.layout.expand_battery_trigger, null);
//			}else if("BattryPluggedTrigger".equals(triggerClassName)) {
//				convertView = inflater.inflate(R.layout.expand_power_trigger, null);
//			}
		
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
		return actions.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	//TODO create holder for the view recycling 
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.trigger_item, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.description);

		title.setText(actions.get(groupPosition).getName());
		desc.setText(actions.get(groupPosition).getDescription());

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
