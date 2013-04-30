/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.triggers.editors.BatteryLevelTriggerEditorActivity;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class BatteryLevelTrigger extends Trigger {

	private static final String TAG = BatteryLevelTrigger.class.getSimpleName();

	public static final String NAME = "Battery level state changed";

	public static final String DESCRIPTION = "Trigged when the battery level state changes (low / okay)";

	private static final int ICON = R.drawable.ic_list_battery;

	/**
	 * The contant representing battary level low.
	 */
	public static final int BATTERY_LEVEL_LOW = 0;

	/**
	 * The contant representing battary level okay.
	 */
	public static final int BATTERY_LEVEL_OKAY = 1;

	/**
	 * Holds the trigger wanted state.<BR/>
	 * <BR/>
	 * 
	 * @see android.os.BatteryManager
	 */
	private int wantedLevelState = 0;

	public BatteryLevelTrigger() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param wantedLevelState
	 *            Integer contant represents the wanted battery level state
	 */
	public BatteryLevelTrigger(int wantedLevelState) {
		super(NAME, DESCRIPTION);
		this.wantedLevelState = wantedLevelState;
	}

	/**
	 * @return the wantedLevelState
	 */
	public int getWantedLevelState() {
		return wantedLevelState;
	}

	/**
	 * @param wantedLevelState
	 *            the wantedLevelState to set
	 */
	public void setWantedLevelState(int wantedLevelState) {
		this.wantedLevelState = wantedLevelState;
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");

		int batteryLevel = stateExtras.getInt(SmartDroid.Extra.EXTRA_BATTERY_LEVEL, -1);
		if (batteryLevel == -1) {
			return;
		}

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<BatteryLevelTrigger> triggers = db.query(BatteryLevelTrigger.class);

			for (BatteryLevelTrigger trigger : triggers) {
				if (trigger.getWantedLevelState() == batteryLevel) {
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
		extras.putInt("wantedLevelState", wantedLevelState);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setWantedLevelState(extras.getInt("wantedLevelState", -1));
	}

	@Override
	public Class<? extends Activity> getTriggerEditor() {
		return BatteryLevelTriggerEditorActivity.class;
	}

}
