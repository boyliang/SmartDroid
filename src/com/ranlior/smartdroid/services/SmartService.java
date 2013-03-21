/**
 * 
 */
package com.ranlior.smartdroid.services;

import com.ranlior.smartdroid.events.ReceiverResponseEvent;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.utilities.BusProvider;
import com.squareup.otto.Subscribe;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SmartService extends Service {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "SmartService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Logger
		Log.d(TAG, "onCreate()");
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Logger
		Log.d(TAG, "onStartCommand(Intent intent, int flags, int startId)");
		BusProvider.getInstance().register(this);
		
		return START_STICKY;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Subscribe
	public void receiverResponseEventHandler(ReceiverResponseEvent event) {
		// Logger
		Log.d(TAG, "receiverResponseEventHandler(ReceiverResponseEvent event)");

		IRuleDAO ruleDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(getApplicationContext());
		
		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(getApplicationContext());
		
		Trigger trigger = event.getTrigger();
		triggerDAO.Update(trigger);
		
		Rule rule = trigger.getRule();
		rule.decNotSatisfiedTriggerCount();		
		
		if (rule.getNotSatisfiedTriggersCount() == 0) {
			// Logger
			Log.d(TAG, "THE RULE IS SATISFIED");
			rule.setSatisfied(true);
			rule.perform();
		}
		
		ruleDAO.update(rule);
	}
	
}
