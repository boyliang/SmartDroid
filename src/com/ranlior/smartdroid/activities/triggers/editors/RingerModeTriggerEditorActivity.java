/**
 * 
 */
package com.ranlior.smartdroid.activities.triggers.editors;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RingerModeTriggerEditorActivity extends SherlockActivity {

	private static final String TAG = RingerModeTriggerEditorActivity.class.getSimpleName();

	private RadioGroup rgRingerMode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_ringer_mode_trigger_editor);

		rgRingerMode = (RadioGroup) findViewById(R.id.rgRingerMode);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(menu)");
		getSupportMenuInflater().inflate(R.menu.activity_trigger_editor_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		int wantedRingerMode = -1;
		switch (item.getItemId()) {
		case R.id.saveTrigger:
			switch (rgRingerMode.getCheckedRadioButtonId()) {
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
			Intent resInent = new Intent();
			resInent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_CLASS_NAME, RingerModeTrigger.class.getSimpleName());
			// FIXME: search goole not position
			resInent.putExtra("position", getIntent().getIntExtra("position", -1));
			resInent.putExtra("wantedRingerMode", wantedRingerMode);
			setResult(RESULT_OK, resInent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
