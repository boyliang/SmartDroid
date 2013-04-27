package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = TriggerExpandableListAdapter.class.getSimpleName();

	private Context context = null;

	private List<Trigger> triggers = null;

	private List<Trigger> selectedTriggers = null;

	public TriggerExpandableListAdapter(Context context, List<Trigger> triggers) {
		Log.d(TAG, "Constructor");

		this.context = context;
		this.triggers = triggers;
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
		String triggerClassName = triggers.get(groupPosition).getClass().getSimpleName();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if ("RingerModeTrigger".equals(triggerClassName)) {
			final RingerModeTrigger ringerModeTrigger = (RingerModeTrigger) triggers.get(groupPosition);
			convertView = inflater.inflate(R.layout.expand_ringer_trigger, null);
			final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgRingerMode);
			radioGroup.check(ringerModeTrigger.getWantedRingerMode());
			TextView tvSave = (TextView) convertView.findViewById(R.id.save);
			tvSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int what = radioGroup.getCheckedRadioButtonId();
					switch (what) {
					case R.id.rbSilent:
						ringerModeTrigger.setWantedRingerMode(AudioManager.RINGER_MODE_SILENT);
						break;
					case R.id.rbVibrate:
						ringerModeTrigger.setWantedRingerMode(AudioManager.RINGER_MODE_VIBRATE);
						break;
					case R.id.rbNormal:
						ringerModeTrigger.setWantedRingerMode(AudioManager.RINGER_MODE_NORMAL);
						break;
					default:
						ringerModeTrigger.setWantedRingerMode(AudioManager.RINGER_MODE_NORMAL);
						break;
					}
				}
			});
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public int getGroupCount() {
		return triggers.size();
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
		View view;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.trigger_list_item, null);
		view.setBackgroundResource(R.drawable.top_round_shadow_expand);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.description);

		title.setText(triggers.get(groupPosition).getName());
		desc.setText(triggers.get(groupPosition).getDescription());

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

	public void add(Trigger trigger) {
		if (triggers != null) {
			triggers.add(trigger);
			notifyDataSetChanged();
		}
	}

	public void remove(Trigger trigger) {
		if (triggers != null) {
			triggers.remove(trigger);
			notifyDataSetChanged();
		}
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
	}

	public List<Trigger> getSelected() {
		return selectedTriggers;
	}

	public void clearSelected() {
		selectedTriggers = null;
	}

}
