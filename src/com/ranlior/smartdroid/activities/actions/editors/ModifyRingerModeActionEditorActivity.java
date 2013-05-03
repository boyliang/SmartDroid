package com.ranlior.smartdroid.activities.actions.editors;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.actions.ModifyRingerModeAction;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ModifyRingerModeActionEditorActivity extends Activity {

	private static final String TAG = ModifyRingerModeActionEditorActivity.class.getSimpleName();

	private RadioGroup rgRingerMode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_modify_ringer_mode_action_editor);

		rgRingerMode = (RadioGroup) findViewById(R.id.rgRingerMode);
		rgRingerMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int wantedRingerMode = AudioManager.RINGER_MODE_NORMAL;
				switch (checkedId) {
				case R.id.rbSilent:
					wantedRingerMode = AudioManager.RINGER_MODE_SILENT;
					break;
				case R.id.rbVibrate:
					wantedRingerMode = AudioManager.RINGER_MODE_VIBRATE;
					break;
				case R.id.rbNormal:
					wantedRingerMode = AudioManager.RINGER_MODE_NORMAL;
					break;
				}
				Intent resIntent = new Intent();
				resIntent.putExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME, ModifyRingerModeAction.class.getSimpleName());
				// FIXME: search goole not position
				resIntent.putExtra("position", getIntent().getIntExtra("position", -1));
				resIntent.putExtra("wantedRingerMode", wantedRingerMode);
				setResult(RESULT_OK, resIntent);
				finish();
			}
		});

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// If editing trigger, renders trigger's state
		if (extras != null) {
			switch (extras.getInt("wantedRingerMode")) {
			case AudioManager.RINGER_MODE_SILENT:
				rgRingerMode.check(R.id.rbSilent);
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				rgRingerMode.check(R.id.rbVibrate);
				break;
			case AudioManager.RINGER_MODE_NORMAL:
				rgRingerMode.check(R.id.rbNormal);
				break;
			}
		}
	}

}
