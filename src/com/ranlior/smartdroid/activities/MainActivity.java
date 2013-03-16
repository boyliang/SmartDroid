package com.ranlior.smartdroid.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.events.RequestContextEvent;
import com.ranlior.smartdroid.events.SendContextEvent;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.BatteryTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.services.SmartService;
import com.ranlior.smartdroid.utilities.BusProvider;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private Trigger batteryTrigger = null;
	private Context mServiceContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(this, SmartService.class));
		
		Rule rule = new Rule(this, "rule", "bsdf");
		
		// Gets the rules dao
		IRuleDAO ruleDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(this);
		
		rule = ruleDAO.Insert(rule);
		
		BusProvider.getInstance().post(requestServiceContext());
		
		batteryTrigger  = new BatteryTrigger(mServiceContext, rule, "isCharged", "Battery charged notification");
		rule.getTriggers().add(batteryTrigger);

		// Gets the battery triggers dao
		ITriggerDAO batteryTriggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, BatteryTrigger.class);
		
		batteryTriggerDAO.Insert(batteryTrigger);

		Action notificationAction = new NotificationAction(this, rule, "isCharged", "Battery charged notification", "Charged", "Text", 0, 0);
//		Action startAppAction = new StartAppAction(this, rule, "start app action", "na", "facebook");
		
		// Gets the notification actions dao
		IActionDAO notificationActionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, NotificationAction.class);
		
		notificationActionDAO.Insert(notificationAction);
		rule.getActions().add(notificationAction);
		
//		// Gets the notification actions dao
//		IActionDAO startAppActionDAO = SmartDAOFactory
//				.getFactory(SmartDAOFactory.SQLITE)
//				.getActionDAO(this, StartAppAction.class);
//		
//		startAppActionDAO.Insert(startAppAction);
		
		rule.register();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		// Logger
		Log.d(TAG , "onResume()");
		
		BusProvider.getInstance().register(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		// Logger
		Log.d(TAG, "onPause()");
		
		BusProvider.getInstance().unregister(this);
	}

	@Produce
	public RequestContextEvent requestServiceContext() {
		// Logger
		Log.d(TAG, "requestServiceContext()");
		
		return new RequestContextEvent();
	}
	
	@Subscribe
	public void getContextEvent(SendContextEvent event) {
		// Logger
		Log.d(TAG, "getContextEvent(SendContextEvent event)");
		
		mServiceContext  = event.getContext();
	}
}
