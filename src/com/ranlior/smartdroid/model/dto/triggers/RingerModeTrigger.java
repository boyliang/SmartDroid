/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.triggers.editors.RingerModeTriggerEditorActivity;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RingerModeTrigger extends Trigger {

	private static final String TAG = RingerModeTrigger.class.getSimpleName();

	private static final String NAME = "Ringer mode changed";

	private static final String DESCRIPTION = "Trigged when the ringer mode changes (normal/silent/vibrate)";

	private static final int ICON = R.drawable.ic_list_volume;

	/**
	 * Holds the trigger wanted ringer mode.
	 */
	private int wantedRingerMode = 0;

	public RingerModeTrigger() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param wantedRingerMode
	 *            Integer constant representing the wanted ringer mode.
	 */
	public RingerModeTrigger(int wantedRingerMode) {
		super(NAME, DESCRIPTION);
		this.wantedRingerMode = wantedRingerMode;
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

	public static void handle(Context appCtx, Bundle stateExtras) {
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");

		int ringerMode = stateExtras.getInt(AudioManager.EXTRA_RINGER_MODE, -1);
		if (ringerMode == -1) {
			return;
		}

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<RingerModeTrigger> triggers = db.query(RingerModeTrigger.class);

			for (RingerModeTrigger trigger : triggers) {
				if (trigger.getWantedRingerMode() == ringerMode) {
					trigger.setSatisfied(true);
				} else {
					trigger.setSatisfied(false);
				}
				db.store(trigger);
			}

			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return rule.isSatisfied();
				}
			});

			for (Rule rule : rules) {
				rule.perform(appCtx);
			}
		} catch (Db4oIOException e) {
			e.printStackTrace();
		} catch (DatabaseClosedException e) {
			e.printStackTrace();
		} catch (DatabaseReadOnlyException e) {
			e.printStackTrace();
		} finally {
			db.commit();
			db.close();
		}
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
	public Class<? extends Activity> getTriggerEditor() {
		return RingerModeTriggerEditorActivity.class;
	}

}
