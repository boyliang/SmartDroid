/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "modify_volume_actions")
public class ModifyVolumeAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "ModifyVolumeAction";
	
	/**
	 * The action's name. 
	 */
	private static final String NAME = "Modify audio stream volume";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Modifies audio volume of audio streams like alarm, DTMF, music, notification, ring, system and voice call";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "modify_volume_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name audio stream.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_AUDIO_STREAM = "audio_stream";

	/**
	 * Column name volume.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_VOLUME = "volume";
	
	/**
	 * Column name flags.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_FLAGS = "flags";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the audio stream.
	 * 
	 * <P>
	 * AudioManager.STREAM_ALARM:<BR/>
	 * The audio stream for alarms.<BR/><BR/>
	 * AudioManager.STREAM_DTMF:<BR/>
	 * The audio stream for DTMF Tones.<BR/><BR/>
	 * AudioManager.STREAM_MUSIC:<BR/>
	 * The audio stream for music playback.<BR/><BR/>
	 * AudioManager.STREAM_NOTIFICATION:<BR/>
	 * The audio stream for notifications.<BR/><BR/>
	 * AudioManager.STREAM_RING:<BR/>
	 * The audio stream for the phone ring.<BR/><BR/>
	 * AudioManager.STREAM_SYSTEM:<BR/>
	 * The audio stream for system sounds.<BR/><BR/>
	 * AudioManager.STREAM_VOICE_CALL:<BR/>
	 * The audio stream for phone calls.
	 * </P>
	 * 
	 * For more info:
	 * @see android.media.AudioManager
	 */
	@DatabaseField(columnName = ModifyVolumeAction.COLUMN_NAME_AUDIO_STREAM, canBeNull = false)
	private int audioStream = AudioManager.USE_DEFAULT_STREAM_TYPE;
	
	/**
	 * <P>
	 * Holds the wanted audio volume.<BR/>
	 * Notice: the max volume for every stream is different.
	 * </P>
	 * 
	 * For more info:
	 * @see android.media.AudioManager#getStreamMaxVolume(int streamType)
	 */
	@DatabaseField(columnName = ModifyVolumeAction.COLUMN_NAME_VOLUME, canBeNull = false)
	private int volume = 0;
	
	/**
	 * Holds the audio flags.
	 * 
	 * <P>
	 * AudioManager.FLAG_ALLOW_RINGER_MODES:<BR/> 
	 * Whether to include ringer modes as possible options when changing volume.<BR/><BR/>
	 * AudioManager.FLAG_PLAY_SOUND:<BR/>
	 * Whether to play a sound when changing the volume.<BR/><BR/>
	 * AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE:<BR/>
	 * Removes any sounds/vibrate that may be in the queue, or are playing (related to changing volume).<BR/><BR/>
	 * AudioManager.FLAG_SHOW_UI:<BR/>
	 * Show a toast containing the current volume.<BR/><BR/>
	 * AudioManager.FLAG_VIBRATE:<BR/>
	 * Whether to vibrate if going into the vibrate ringer mode.
	 * </P>
	 * 
	 * For more info:
	 * @see android.media.AudioManager
	 */
	@DatabaseField(columnName = ModifyVolumeAction.COLUMN_NAME_FLAGS, canBeNull = false)
	private int flags = 0;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public ModifyVolumeAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param audioStrem	Integer represents audio stream
	 */
	public ModifyVolumeAction(Context context, Rule rule, int audioStream, int volume, int flags) {
		super(context, rule, ModifyVolumeAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.audioStream = audioStream;
	}

	/**
	 * @return the audioStream
	 */
	public int getAudioStream() {
		return audioStream;
	}

	/**
	 * @param audioStream the audioStream to set
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
	 * @param volume the volume to set
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
	 * @param flags the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
		// Changes audio stream volume settings
		int maxVolume = audioManager.getStreamMaxVolume(audioStream);
		if (volume <= maxVolume) {
			audioManager.setStreamVolume(audioStream, volume, flags);
		}
	}

}
