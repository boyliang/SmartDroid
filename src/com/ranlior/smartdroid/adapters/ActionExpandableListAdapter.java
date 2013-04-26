package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.List;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.ModifyRingerModeAction;

public class ActionExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = ActionExpandableListAdapter.class.getSimpleName();

	private Context context = null;
	
	private Fragment fragment = null;

	private List<Action> actions = null;
	
	private List<Action> selectedActions = null;

	public ActionExpandableListAdapter(Context context, Fragment fragment, List<Action> actions) {
		Log.d(TAG, "Constructor");

		this.context = context;
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
		String actionClassName = actions.get(groupPosition).getClass().getSimpleName();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if ("ModifyRingerModeAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_ringer_action, null);
			final RadioGroup radioGroup = (RadioGroup)convertView.findViewById(R.id.rgRingerMode);
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
					Toast.makeText(context, "Saving action of type ModifyRingerModeAction with value of: " + ringerMode, Toast.LENGTH_SHORT).show();
					//TODO collapse the item after save(lior)
					//TODO update or set the action's ringerMode 
					ModifyRingerModeAction modifyRingerModeAction = (ModifyRingerModeAction) actions.get(groupPosition);
					modifyRingerModeAction.setRingerMode(ringerMode);
				}
			});
		} else if ("SetWallpaperAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_wallpaper_action, null);

			final TextView filePath = (TextView) convertView.findViewById(R.id.tvFilePath);
			
			filePath.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent pickIntent = new Intent();
					pickIntent.setType("image/*");
					pickIntent.setAction(Intent.ACTION_GET_CONTENT);

					Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
					Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
					chooserIntent.putExtra ( Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent } );

					fragment.startActivityForResult(chooserIntent, 12);
					
				}
			});
			
			
			
			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
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
		View view;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.action_list_item, null);
		view.setBackgroundResource(R.drawable.top_round_shadow_expand);
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
	}
	
	public List<Action> getSelected() {
		return selectedActions;
	}
	
	public void clearSelected() {
		selectedActions = null;
	}

}
