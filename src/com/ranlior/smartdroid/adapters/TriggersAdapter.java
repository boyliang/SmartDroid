package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
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

public class TriggersAdapter extends ArrayAdapter<Trigger> {

	private static final String TAG = TriggersAdapter.class.getSimpleName();

	private List<Trigger> triggers = null;

	private List<Trigger> selectedTriggers = null;

	private LayoutInflater inflater;

	public TriggersAdapter(Context context, int layoutResourceId, List<Trigger> triggers) {
		super(context, layoutResourceId, triggers);

		Log.d(TAG, "Constructor");

		this.triggers = triggers;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Trigger trigger = triggers.get(position);
		boolean isTriggerSelected = (selectedTriggers != null) ? selectedTriggers.contains(trigger) : false;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.trigger_list_item, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.contentImage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(triggers.get(position).getName());
		holder.tvDesc.setText(triggers.get(position).getDescription());
		holder.ivIcon.setImageResource(trigger.getIconId());

		convertView.findViewById(R.id.content).setSelected(isTriggerSelected);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		ImageView ivIcon;
	}

	public Trigger getTrigger(int position) {
		return triggers.get(position);
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	private boolean isSelected(int position) {
		if (selectedTriggers == null || selectedTriggers.isEmpty()) {
			return false;
		}
		return selectedTriggers.contains(triggers.get(position));
	}

	private void setSelected(int position, boolean toSelect) {
		if (selectedTriggers == null) {
			selectedTriggers = new ArrayList<Trigger>();
		}
		if (toSelect && !isSelected(position)) {
			selectedTriggers.add(triggers.get(position));
		} else if (!toSelect && isSelected(position)) {
			selectedTriggers.remove(triggers.get(position));
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

	public List<Trigger> getSelected() {
		return selectedTriggers;
	}

	public void clearSelected() {
		selectedTriggers = null;
		notifyDataSetChanged();
	}

}
