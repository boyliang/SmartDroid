package com.ranlior.smartdroid.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ActionAdapter extends ArrayAdapter<Action> {

	private static final String TAG = "ActionAdapter";
	
	private static ActionAdapter instance = null;

	private List<Action> actions;
	
	Context context;

	private ActionAdapter(Context context, int trigger_layout,  List<Action> actions) {
		super(context, trigger_layout, actions);
		
		Log.d(TAG, "constructor");
		
		this.context = context;
		this.actions = actions;
		
	}

	public static ActionAdapter getInstance(Context context, int action_layout,  List<Action> actions) {
		
		Log.d(TAG, "getInstance(Context context, int action_layout, List<Rule> actions)");
		
		if (instance == null) {
			instance = new ActionAdapter(context, action_layout, actions);
		}
		return instance;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Action action = actions.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.trigger_item, null);

			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvTitle.setText(action.getName());
		holder.tvDesc.setText(action.getDescription());

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}

	
}
