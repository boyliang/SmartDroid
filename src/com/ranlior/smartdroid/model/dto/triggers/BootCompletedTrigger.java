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
import com.ranlior.smartdroid.activities.triggers.editors.BootCompletedTriggerEditorActivity;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class BootCompletedTrigger extends Trigger {

	private static final String TAG = BootCompletedTrigger.class.getSimpleName();

	private static final String NAME = "Boot completed";

	private static final String DESCRIPTION = "Trigged when the device boot completes";

	private static final int ICON = R.drawable.ic_list_boot;

	/**
	 * Full constructor.
	 * 
	 */
	public BootCompletedTrigger() {
		super(NAME, DESCRIPTION);
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<BootCompletedTrigger> triggers = db.query(BootCompletedTrigger.class);

			for (BootCompletedTrigger trigger : triggers) {
				trigger.setSatisfied(true);
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

			for (BootCompletedTrigger trigger : triggers) {
				trigger.setSatisfied(false);
				db.store(trigger);
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
		return null;
	}

	@Override
	public void setExtras(Bundle extras) {
	}

	@Override
	public Class<? extends Activity> getTriggerEditor() {
		return BootCompletedTriggerEditorActivity.class;
	}

}
