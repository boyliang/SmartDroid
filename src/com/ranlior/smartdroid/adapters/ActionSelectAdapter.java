package com.ranlior.smartdroid.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;

public class ActionSelectAdapter extends ArrayAdapter<Action> {

	private static final String TAG = ActionSelectAdapter.class.getSimpleName();

	private int layoutResourceId = -1;

	private List<Action> actions;

	private LayoutInflater inflater;

	public ActionSelectAdapter(Context context, int layoutResourceId, List<Action> actions) {
		super(context, layoutResourceId, actions);

		Log.d(TAG, "Constructor");

		this.inflater = LayoutInflater.from(context);
		this.layoutResourceId = layoutResourceId;
		this.actions = actions;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Action action = actions.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(layoutResourceId, null);
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

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		ImageView ivIcon;
	}

}
