package com.ranlior.smartdroid.activities.actions.editors;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ranlior.smartdroid.R;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ModifyVolumeActionEditorActivity extends Activity {

	private static final String TAG = ModifyVolumeActionEditorActivity.class.getSimpleName();

	private TextView tvAlarmVolume = null;
	private TextView tvDTMFVolume = null;
	private TextView tvMusicVolume = null;
	private TextView tvNotificationVolume = null;
	private TextView tvRingVolume = null;
	private TextView tvSystemVolume = null;
	private TextView tvVoiceVolume = null;

	private SeekBar sbAlarmVolume = null;
	private SeekBar sbDTMFVolume = null;
	private SeekBar sbMusicVolume = null;
	private SeekBar sbNotificationVolume = null;
	private SeekBar sbRingVolume = null;
	private SeekBar sbSystemVolume = null;
	private SeekBar sbVoiceVolume = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_modify_volume_action_editor);

		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// If editing trigger, renders trigger's state
		if (extras != null) {
			// XXX: continue from here.
		} else {
			int maxVolume;
	
			tvAlarmVolume = (TextView) findViewById(R.id.tvAlarmVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			tvAlarmVolume.setText(maxVolume);
			sbAlarmVolume = (SeekBar) findViewById(R.id.sbAlarmVolume);
			sbAlarmVolume.setMax(maxVolume);
	
			tvDTMFVolume = (TextView) findViewById(R.id.tvDTMFVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF);
			tvDTMFVolume.setText(maxVolume);
			sbDTMFVolume = (SeekBar) findViewById(R.id.sbDTMFVolume);
			sbDTMFVolume.setMax(maxVolume);
	
			tvMusicVolume = (TextView) findViewById(R.id.tvMusicVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			tvMusicVolume.setText(maxVolume);
			sbMusicVolume = (SeekBar) findViewById(R.id.sbMusicVolume);
			sbMusicVolume.setMax(maxVolume);
	
			tvNotificationVolume = (TextView) findViewById(R.id.tvNotificationVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
			tvNotificationVolume.setText(maxVolume);
			sbNotificationVolume = (SeekBar) findViewById(R.id.sbNotificationVolume);
			sbNotificationVolume.setMax(maxVolume);
	
			tvRingVolume = (TextView) findViewById(R.id.tvRingVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
			tvRingVolume.setText(maxVolume);
			sbRingVolume = (SeekBar) findViewById(R.id.sbRingVolume);
			sbRingVolume.setMax(maxVolume);
	
			tvSystemVolume = (TextView) findViewById(R.id.tvSystemVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			tvSystemVolume.setText(maxVolume);
			sbSystemVolume = (SeekBar) findViewById(R.id.sbSystemVolume);
			sbSystemVolume.setMax(maxVolume);
	
			tvVoiceVolume = (TextView) findViewById(R.id.tvVoiceCallVolume);
			maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
			tvVoiceVolume.setText(maxVolume);
			sbVoiceVolume = (SeekBar) findViewById(R.id.sbVoiceCallVolume);
			sbVoiceVolume.setMax(maxVolume);
		}

	}

}
