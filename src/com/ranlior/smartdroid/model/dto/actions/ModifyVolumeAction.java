/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.ModifyVolumeActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ModifyVolumeAction extends Action {

	private static final String TAG = ModifyVolumeAction.class.getSimpleName();

	private static final String NAME = "Modify audio stream volume";

	private static final String DESCRIPTION = "Modifies audio volume of audio streams like alarm, DTMF, music, notification, ring, system and voice call";

	private static final int ICON = R.drawable.ic_list_volume;

	/**
	 * Holds the audio stream.
	 * 
	 * <P>
	 * AudioManager.STREAM_ALARM:<BR/>
	 * The audio stream for alarms.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_DTMF:<BR/>
	 * The audio stream for DTMF Tones.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_MUSIC:<BR/>
	 * The audio stream for music playback.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_NOTIFICATION:<BR/>
	 * The audio stream for notifications.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_RING:<BR/>
	 * The audio stream for the phone ring.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_SYSTEM:<BR/>
	 * The audio stream for system sounds.<BR/>
	 * <BR/>
	 * AudioManager.STREAM_VOICE_CALL:<BR/>
	 * The audio stream for phone calls.
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.media.AudioManager
	 */
	private int audioStream = AudioManager.USE_DEFAULT_STREAM_TYPE;

	/**
	 * <P>
	 * Holds the wanted audio volume.<BR/>
	 * Notice: the max volume for every stream is different.
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.media.AudioManager#getStreamMaxVolume(int streamType)
	 */
	private int volume = 0;

	/**
	 * Holds the audio flags.
	 * 
	 * <P>
	 * AudioManager.FLAG_ALLOW_RINGER_MODES:<BR/>
	 * Whether to include ringer modes as possible options when changing volume.
	 * <BR/>
	 * <BR/>
	 * AudioManager.FLAG_PLAY_SOUND:<BR/>
	 * Whether to play a sound when changing the volume.<BR/>
	 * <BR/>
	 * AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE:<BR/>
	 * Removes any sounds/vibrate that may be in the queue, or are playing
	 * (related to changing volume).<BR/>
	 * <BR/>
	 * AudioManager.FLAG_SHOW_UI:<BR/>
	 * Show a toast containing the current volume.<BR/>
	 * <BR/>
	 * AudioManager.FLAG_VIBRATE:<BR/>
	 * Whether to vibrate if going into the vibrate ringer mode.
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.media.AudioManager
	 */
	private int flags = 0;

	public ModifyVolumeAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param audioStrem
	 *            Integer represents audio stream
	 * @param volume
	 *            Integer represents the volume
	 * @param volume
	 *            Integer represents the audio flags
	 */
	public ModifyVolumeAction(int audioStream, int volume, int flags) {
		super(NAME, DESCRIPTION);
		this.audioStream = audioStream;
		this.volume = volume;
		this.flags = flags;
	}

	/**
	 * @return the audioStream
	 */
	public int getAudioStream() {
		return audioStream;
	}

	/**
	 * @param audioStream
	 *            the audioStream to set
	 */
	public void setAudioStream(int audioStream) {
		this.audioStream = audioStream;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// Changes audio stream volume settings
		int maxVolume = audioManager.getStreamMaxVolume(audioStream);
		if (volume <= maxVolume) {
			audioManager.setStreamVolume(audioStream, volume, flags);
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putInt("audioStream", audioStream);
		extras.putInt("volume", volume);
		extras.putInt("flags", flags);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setAudioStream(extras.getInt("audioStream"));
		setVolume(extras.getInt("volume"));
		setFlags(extras.getInt("flags"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return ModifyVolumeActionEditorActivity.class;
	}

}
