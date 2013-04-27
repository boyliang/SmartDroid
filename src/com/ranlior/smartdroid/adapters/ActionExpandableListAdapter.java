package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.ActionSelectAdapter.ViewHolder;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.ChangeBluetoothStateAction;
import com.ranlior.smartdroid.model.dto.actions.ChangeWIFIStateAction;
import com.ranlior.smartdroid.model.dto.actions.ModifyRingerModeAction;

public class ActionExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = ActionExpandableListAdapter.class.getSimpleName();

	private Context context = null;

	private Fragment fragment = null;

	private List<Action> actions = null;

	private List<Action> selectedActions = null;

	private LayoutInflater inflater;

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
		String actionClassName = actions.get(groupPosition).getClass().getSimpleName();

		if ("ModifyRingerModeAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_ringer_action, null);
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
					Toast.makeText(context, "Saving action of type ModifyRingerModeAction with value of: " + ringerMode, Toast.LENGTH_SHORT)
							.show();
					// TODO collapse the item after save(lior)
					// TODO update or set the action's ringerMode
					ModifyRingerModeAction modifyRingerModeAction = (ModifyRingerModeAction) actions.get(groupPosition);
					modifyRingerModeAction.setRingerMode(ringerMode);
				}
			});
		} else if ("SetWallpaperAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_wallpaper_action, null);

			// TODO make it work

			final TextView filePath = (TextView) convertView.findViewById(R.id.tvFilePath);

			filePath.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent pickIntent = new Intent();
					pickIntent.setType("image/*");
					pickIntent.setAction(Intent.ACTION_GET_CONTENT);

					Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					String pickTitle = "Select or take a new Picture"; // Or get
																		// from
																		// strings.xml
					Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
					chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });

					fragment.startActivityForResult(chooserIntent, 12);

				}
			});

			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO take the path and store it
				}
			});

		} else if ("ChangeWIFIStateAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_wifi_action, null);

			final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgWiFiSate);
			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int wifiState = -1;
					int wifiSelectedSate = radioGroup.getCheckedRadioButtonId();
					switch (wifiSelectedSate) {
					case R.id.rbWifiOn:
						wifiState = WifiManager.WIFI_STATE_ENABLED;
						break;
					case R.id.rbVibrate:
						wifiState = WifiManager.WIFI_STATE_DISABLED;
						break;
					default:
						wifiState = WifiManager.WIFI_STATE_DISABLED;
						break;
					}
					Toast.makeText(context, "Saving action of type ChangeWifiStaetAction with value of: " + wifiState, Toast.LENGTH_SHORT)
							.show();
					// TODO collapse the item after save(lior)
					ChangeWIFIStateAction changeWIFIStateAction = (ChangeWIFIStateAction) actions.get(groupPosition);
					changeWIFIStateAction.setWifiState(wifiState);
				}
			});

		} else if ("ChangeBluetoothStateAction".equals(actionClassName)) {
			convertView = inflater.inflate(R.layout.expand_bluetooth_action, null);

			final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgBluetoothState);
			convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int bluetoothState = -1;
					int bluetoothSelectedSate = radioGroup.getCheckedRadioButtonId();
					switch (bluetoothSelectedSate) {
					case R.id.rbBluetoothOn:
						bluetoothState = BluetoothAdapter.STATE_ON;
						break;
					case R.id.rbBluetoothOff:
						bluetoothState = BluetoothAdapter.STATE_OFF;
						break;
					default:
						bluetoothState = BluetoothAdapter.STATE_OFF;
						break;
					}
					Toast.makeText(context, "Saving action of type ChangeWifiStaetAction with value of: " + bluetoothState,
							Toast.LENGTH_SHORT).show();
					// TODO collapse the item after save(lior)
					ChangeBluetoothStateAction changeBluetoothStateAction = (ChangeBluetoothStateAction) actions.get(groupPosition);
					changeBluetoothStateAction.setBluetoothState(bluetoothState);
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

	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final ViewHolder holder;

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

		holder.tvTitle.setText(actions.get(groupPosition).getName());
		holder.tvDesc.setText(actions.get(groupPosition).getDescription());
		holder.ivIcon.setImageResource(resID);
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
	}

	public List<Action> getSelected() {
		return selectedActions;
	}

	public void clearSelected() {
		selectedActions = null;
	}

}
