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
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerSelectAdapter extends ArrayAdapter<Trigger> {

	private static final String TAG = TriggerSelectAdapter.class.getSimpleName();

	private int layoutResourceId = -1;

	private List<Trigger> triggers;

	private LayoutInflater inflater;

	public TriggerSelectAdapter(Context context, int layoutResourceId, List<Trigger> triggers) {
		super(context, layoutResourceId, triggers);

		Log.d(TAG, "Constructor");

		this.inflater = LayoutInflater.from(context);
		this.layoutResourceId = layoutResourceId;
		this.triggers = triggers;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Trigger trigger = triggers.get(position);
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

		holder.tvTitle.setText(trigger.getName());
		holder.tvDesc.setText(trigger.getDescription());
		holder.ivIcon.setImageResource(trigger.getIconId());

		return convertView;
	}

	private static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		ImageView ivIcon;
	}

}
