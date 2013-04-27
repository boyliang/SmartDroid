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
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.R.id;
import com.ranlior.smartdroid.adapters.ActionExpandableListAdapter.ViewHolder;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = TriggerExpandableListAdapter.class.getSimpleName();

	private Context context = null;

	private List<Trigger> triggers = null;

	private List<Trigger> selectedTriggers = null;

	private LayoutInflater inflater;

	public TriggerExpandableListAdapter(Context context, List<Trigger> triggers) {
		Log.d(TAG, "Constructor");

		this.context = context;
		this.inflater = LayoutInflater.from(context);
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

		if ("RingerModeTrigger".equals(triggerClassName)) {
			convertView = inflater.inflate(R.layout.expand_ringer_trigger, null);
			boolean isParentSelected = parent.isSelected();
			convertView.setSelected(parent.isSelected());
			final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgRingerMode);
			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int ringerMode = -1;
					int what = radioGroup.getCheckedRadioButtonId();
					switch (what) {
					case R.id.rbSilent:
						ringerMode = AudioManager.RINGER_MODE_SILENT;
						break;
					case R.id.rbVibrate:
						ringerMode = AudioManager.RINGER_MODE_VIBRATE;
						break;
					case R.id.rbNormal:
						ringerMode = AudioManager.RINGER_MODE_NORMAL;
						break;
					default:
						ringerMode = AudioManager.RINGER_MODE_NORMAL;
						break;
					}
					Toast.makeText(context, "Saving trigger of type RingerModeTrigger with value of: " + ringerMode, Toast.LENGTH_SHORT)
							.show();
					// TODO: collapse the item after save (lior)
					// TODO: update or set the trigger's ringerMode
					RingerModeTrigger ringerModeTrigger = (RingerModeTrigger) triggers.get(groupPosition);
					ringerModeTrigger.setWantedRingerMode(ringerMode);
				}
			});
		} else if ("LocationProximityTrigger".equals(triggerClassName)) {
			convertView = inflater.inflate(R.layout.expand_location_trigger, null);
			final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group_notify_when);
			final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id.auto_complete_text_view_location);
			final SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.sbRadius);
			final TextView tvRadiusValue = (TextView) convertView.findViewById(R.id.sbValue);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					tvRadiusValue.setText(String.valueOf(progress) + " m");
				}
			});
			
			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int inOut = -1;
					int what = radioGroup.getCheckedRadioButtonId();
					switch (what) {
					case R.id.radio_button_entering:
						inOut = 0; //TODO set static final
						break;
					case R.id.rbVibrate:
						inOut = 1; //TODO set static final
						break;
					default:
						inOut = 0; //TODO set static final
						break;
					}
					
					int radiusValue = seekBar.getProgress();
					
					
					
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
		final ViewHolder holder;
		final Trigger trigger = triggers.get(groupPosition);

		boolean isTriggerSelected = false;
		if (selectedTriggers != null) {
			isTriggerSelected = selectedTriggers.contains(trigger);
		}
		
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

		int resID = context.getResources().getIdentifier(triggers.get(groupPosition).getIconName(), "drawable", context.getPackageName());

		holder.tvTitle.setText(triggers.get(groupPosition).getName());
		holder.tvDesc.setText(triggers.get(groupPosition).getDescription());
		holder.ivIcon.setImageResource(resID);
		
		View view  = convertView.findViewById(R.id.content);
		view.setSelected(isTriggerSelected);
		
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
