/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.ModifyRingerModeActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ModifyRingerModeAction extends Action {

	private static final String TAG = ModifyRingerModeAction.class.getSimpleName();

	private static final String NAME = "Modify ringer mode";

	private static final String DESCRIPTION = "Modifies ringer mode (Normal / Silent / Vibrate)";

	private static final int ICON = R.drawable.ic_list_volume;

	/**
	 * Holds the wanted ringer mode.
	 * 
	 * <P>
	 * AudioManager.RINGER_MODE_NORMAL:<BR/>
	 * Ringer mode that may be audible and may vibrate.<BR/>
	 * <BR/>
	 * AudioManager.RINGER_MODE_SILENT:<BR/>
	 * Ringer mode that will be silent and will not vibrate.<BR/>
	 * <BR/>
	 * AudioManager.RINGER_MODE_VIBRATE:<BR/>
	 * Ringer mode that will be silent and will vibrate.<BR/>
	 * <BR/>
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.media.AudioManager
	 */
	private int wantedRingerMode = AudioManager.RINGER_MODE_NORMAL;

	public ModifyRingerModeAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context
	 *            Context the context instantiating this action
	 * @param audioStrem
	 *            Integer represents audio stream
	 */
	public ModifyRingerModeAction(int ringerMode) {
		super(NAME, DESCRIPTION);
		this.wantedRingerMode = ringerMode;
	}

	/**
	 * @return the wantedRingerMode
	 */
	public int getWantedRingerMode() {
		return wantedRingerMode;
	}

	/**
	 * @param wantedRingerMode
	 *            the wantedRingerMode to set
	 */
	public void setWantedRingerMode(int wantedRingerMode) {
		this.wantedRingerMode = wantedRingerMode;
	}

	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// Changes ringer mode settings
		audioManager.setRingerMode(wantedRingerMode);
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putInt("wantedRingerMode", wantedRingerMode);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setWantedRingerMode(extras.getInt("wantedRingerMode"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return ModifyRingerModeActionEditorActivity.class;
	}

	public View getChildView(Context context, View convertView) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.expand_ringer_action, null);
		final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgRingerMode);
		convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int ringerMode = AudioManager.RINGER_MODE_NORMAL;
				int ringerModeChecked = radioGroup.getCheckedRadioButtonId();
				switch (ringerModeChecked) {
				case R.id.rbSilent:
					ringerMode = AudioManager.RINGER_MODE_SILENT;
					break;
				case R.id.rbVibrate:
					ringerMode = AudioManager.RINGER_MODE_VIBRATE;
					break;
				case R.id.rbNormal:
					ringerMode = AudioManager.RINGER_MODE_NORMAL;
					break;
				}
				setWantedRingerMode(ringerMode);
			}
		});
		return convertView;
	}

}
