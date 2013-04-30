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
import com.ranlior.smartdroid.activities.triggers.editors.WiredHeadsetPluggedTriggerEditorActivity;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class WiredHeadsetPluggedTrigger extends Trigger {

	private static final String TAG = WiredHeadsetPluggedTrigger.class.getSimpleName();

	private static final String NAME = "Wired headset plug state";

	private static final String DESCRIPTION = "Trigged when wired headset plug state changes (plugged / unplugged)";

	private final int ICON = R.drawable.ic_list_headphones;

	public static final int HEADSET_PLUGGED = 1;

	public static final int HEADSET_UNPLUGGED = 0;

	/**
	 * Holds the trigger wanted pluged state.<BR/>
	 * <BR/>
	 * 
	 * @see android.media.AudioManager
	 */
	private int wantedPluggedState = HEADSET_PLUGGED;

	public WiredHeadsetPluggedTrigger() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param wantedPluggedState
	 *            Integer represents wired headset wanted plug state
	 */
	public WiredHeadsetPluggedTrigger(int wantedPluggedState) {
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

		int isPlugged = stateExtras.getInt("state");
		String headsetName = stateExtras.getString("name");
		int hasMicrophone = stateExtras.getInt("microphone");

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<WiredHeadsetPluggedTrigger> triggers = db.query(WiredHeadsetPluggedTrigger.class);

			for (WiredHeadsetPluggedTrigger trigger : triggers) {
				if (trigger.getWantedPluggedState() == isPlugged) {
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
		return WiredHeadsetPluggedTriggerEditorActivity.class;
	}

}
