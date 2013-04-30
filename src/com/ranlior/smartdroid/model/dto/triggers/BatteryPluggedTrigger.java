/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.triggers.editors.BatteryPluggedTriggerEditorActivity;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
// FIXME: Fix this trigger can't diff between plugged states (ac, usb, wireless)
// only diff between connected and disconnected.
public class BatteryPluggedTrigger extends Trigger {

	private static final String TAG = BatteryPluggedTrigger.class.getSimpleName();

	private static final String NAME = "Battery plug state changed";

	private static final String DESCRIPTION = "Trigged when the battery plug state changes (not pluged / ac plugged / usb plugged / wireless plugged)";

	private static final int ICON = R.drawable.ic_list_plugged;

	/**
	 * Holds the trigger wanted pluged state.<BR/>
	 * <BR/>
	 * 
	 * Default: 0 means it is on battery<BR/>
	 * BatteryManager.BATTERY_PLUGGED_AC<BR/>
	 * BatteryManager.BATTERY_PLUGGED_USB<BR/>
	 * BatteryManager.BATTERY_PLUGGED_WIRELESS<BR/>
	 * 
	 * @see android.os.BatteryManager
	 */
	private int wantedPluggedState = 0;

	public BatteryPluggedTrigger() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param wanedPluggedState
	 *            Integer contant represents the wanted battery plugged state
	 */
	public BatteryPluggedTrigger(int wantedPluggedState) {
		super(NAME, DESCRIPTION);
		this.wantedPluggedState = wantedPluggedState;
	}

	/**
	 * @return the wantedPluggedState
	 */
	public int getWantedPluggedState() {
		return wantedPluggedState;
	}

	/**
	 * @param wantedPluggedState
	 *            the wantedPluggedState to set
	 */
	public void setWantedPluggedState(int wantedPluggedState) {
		this.wantedPluggedState = wantedPluggedState;
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");

		int chargePlug = stateExtras.getInt(BatteryManager.EXTRA_PLUGGED, -1);
		if (chargePlug == -1) {
			return;
		}

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<BatteryPluggedTrigger> triggers = db.query(BatteryPluggedTrigger.class);

			for (BatteryPluggedTrigger trigger : triggers) {
				if (trigger.getWantedPluggedState() == chargePlug) {
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
		extras.putInt("wantedPluggedState", wantedPluggedState);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setWantedPluggedState(extras.getInt("wantedPluggedState", -1));
	}

	@Override
	public Class<? extends Activity> getTriggerEditor() {
		return BatteryPluggedTriggerEditorActivity.class;
	}

}
