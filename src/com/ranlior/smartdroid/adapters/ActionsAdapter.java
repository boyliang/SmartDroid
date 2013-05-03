package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ActionsAdapter extends ArrayAdapter<Action> {

	private static final String TAG = ActionsAdapter.class.getSimpleName();

	private List<Action> actions = null;

	private List<Action> selectedActions = null;

	private LayoutInflater inflater = null;

	public ActionsAdapter(Context context, int layoutResourceId, List<Action> actions) {
		super(context, layoutResourceId, actions);
		
		Log.d(TAG, "Constructor");

		this.actions = actions;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Action action = actions.get(position);
		boolean isActionSelected = (selectedActions != null) ? selectedActions.contains(action) : false;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.action_list_item, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.contentImage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(action.getName());
		holder.tvDesc.setText(action.getDescription());
		holder.ivIcon.setImageResource(action.getIconId());

		convertView.findViewById(R.id.content).setSelected(isActionSelected);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		ImageView ivIcon;
	}
	
	public Action getAction(int position) {
		return actions.get(position);
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
