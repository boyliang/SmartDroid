/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ModifyRingerModeAction extends Action {

	private static final String TAG = ModifyRingerModeAction.class.getSimpleName();

	private static final String NAME = "Modify ringer mode";

	private static final String DESCRIPTION = "Modifies ringer mode (Normal / Silent / Vibrate)";

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
	private int ringerMode = AudioManager.RINGER_MODE_NORMAL;

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
		this.ringerMode = ringerMode;
	}

	/**
	 * @return the ringerMode
	 */
	public int getRingerMode() {
		return ringerMode;
	}

	/**
	 * @param ringerMode
	 *            the ringerMode to set
	 */
	public void setRingerMode(int ringerMode) {
		this.ringerMode = ringerMode;
	}

	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// Changes ringer mode settings
		audioManager.setRingerMode(ringerMode);
	}

}
