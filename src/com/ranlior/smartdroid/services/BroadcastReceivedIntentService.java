/**
 * 
 */
package com.ranlior.smartdroid.services;

import java.sql.SQLException;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class BroadcastReceivedIntentService extends IntentService {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "BroadcastReceivedIntentService";

	/**
	 * Default constructor.
	 */
	public BroadcastReceivedIntentService() {
		super(BroadcastReceivedIntentService.class.getSimpleName());
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Logger
		Log.d(TAG, "onHandleIntent(Intent intent)");
		
		Context appCtx = getApplicationContext();

		List<Trigger> triggers = null;

		String triggerName = intent.getStringExtra("triggerName");

		if ("RingerModeTrigger".equals(triggerName)) {
			
			Log.d(TAG, "Handle: RingerModeTrigger");

			int ringerMode = intent.getIntExtra("ringerMode", -1);

			ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);

			QueryBuilder<Trigger, Long> queryBuilder = triggerDAO.queryBuilder(RingerModeTrigger.class);
			
			try {
		       triggers = queryBuilder.query();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

			for (Trigger trigger : triggers) {
				RingerModeTrigger ringerModeTrigger = (RingerModeTrigger) trigger;
				if (ringerModeTrigger.getWantedRingerMode() == ringerMode) {
					ringerModeTrigger.setSatisfied(true);
				} else {
					ringerModeTrigger.setSatisfied(false);
				}
				triggerDAO.update(ringerModeTrigger);
				Rule rule = ringerModeTrigger.getRule();
				if (rule.isSatisfied()) {
					rule.setContext(appCtx);
					rule.perform();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		// Logger
		Log.d(TAG, "onCreate()");
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Logger
		Log.d(TAG, "onDestroy()");
	}

	/* (non-Javadoc)
	 * @see android.app
	 * .IntentService#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Logger
		Log.d(TAG, "onStartCommand(Intent intent, int flags, int startId)");
		
		return super.onStartCommand(intent, flags, startId);
	}

}