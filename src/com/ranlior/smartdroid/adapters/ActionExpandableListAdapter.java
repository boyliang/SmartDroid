package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ActionExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = ActionExpandableListAdapter.class.getSimpleName();

	private Context context = null;

	private Fragment fragment = null;

	private List<Action> actions = null;

	private List<Action> selectedActions = null;

	private LayoutInflater inflater = null;

	public ActionExpandableListAdapter(Context context, Fragment fragment, List<Action> actions) {
		Log.d(TAG, "Constructor");

		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.fragment = fragment;
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
	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		Action action = actions.get(groupPosition);
		return action.getChildView(context, convertView);
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

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Action action = actions.get(groupPosition);
		boolean isActionSelected = (selectedActions != null) ? selectedActions.contains(action) : false;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.action_list_item, null);
			convertView.findViewById(R.id.content).setBackgroundResource(R.drawable.expandable_list_item_selector);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.contentImage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		int resID = context.getResources().getIdentifier(actions.get(groupPosition).getIconName(), "drawable", context.getPackageName());

		holder.tvTitle.setText(action.getName());
		holder.tvDesc.setText(action.getDescription());
		holder.ivIcon.setImageResource(resID);

		convertView.findViewById(R.id.content).setSelected(isActionSelected);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		ImageView ivIcon;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void add(Action action) {
		if (actions != null) {
			actions.add(action);
			notifyDataSetChanged();
		}
	}

	public void remove(Action action) {
		if (actions != null) {
			actions.remove(action);
			notifyDataSetChanged();
		}
	}

	public List<Action> getActions() {
		return actions;
	}

	private boolean isSelected(int position) {
		if (selectedActions == null || selectedActions.isEmpty()) {
			return false;
		}
		return selectedActions.contains(actions.get(position));
	}

	private void setSelected(int position, boolean toSelect) {
		if (selectedActions == null) {
			selectedActions = new ArrayList<Action>();
		}
		if (toSelect && !isSelected(position)) {
			selectedActions.add(actions.get(position));
		} else if (!toSelect && isSelected(position)) {
			selectedActions.remove(actions.get(position));
		}
	}

	public void toggleSelected(int position) {
		if (isSelected(position)) {
			setSelected(position, false);
		} else {
			setSelected(position, true);
		}
		notifyDataSetChanged();
	}

	public List<Action> getSelected() {
		return selectedActions;
	}

	public void clearSelected() {
		selectedActions = null;
		notifyDataSetChanged();
	}

}
