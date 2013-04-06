package com.ranlior.smartdroid.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ExpandableActionListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = ExpandableActionListAdapter.class.getSimpleName();

	private Context context = null;

	List<Action> actions;

	public ExpandableActionListAdapter(Context context, List<Action> actions) {
		Log.d(TAG, "ExpandableActionListAdapter(Context context, List<Action> actions)");

		this.context = context;
		this.actions = actions;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		Log.d(TAG, "getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)");

		String triggerClassName = actions.get(groupPosition).getClassName();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if ("RingerModeTrigger".equals(triggerClassName)) {
			convertView = inflater.inflate(R.layout.expand_ringer_trigger, null);
		} else if ("BatteryLevelTrigger".equals(triggerClassName)) {
			convertView = inflater.inflate(R.layout.expand_battery_trigger, null);
		} else if ("BattryPluggedTrigger".equals(triggerClassName)) {
			convertView = inflater.inflate(R.layout.expand_power_trigger, null);
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public int getGroupCount() {
		return actions.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// TODO create holder for the view recycling
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		Log.d(TAG, "getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)");

		View view;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.action_item, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.description);

		title.setText(actions.get(groupPosition).getName());
		desc.setText(actions.get(groupPosition).getDescription());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
