package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Menu;

import com.j256.ormlite.dao.ForeignCollection;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.actions.StartAppAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.BatteryTrigger;
import com.ranlior.smartdroid.model.dto.triggers.SensorTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class MainActivity extends Activity {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//startService(new Intent(this, SmartService.class));
		
		Rule rule = new Rule(this, "rule", "bsdf");
		
		// Gets the rules dao
		IRuleDAO ruleDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(this);
		
		rule = ruleDAO.Insert(rule);
		
		// Gets the battery triggers dao
		ITriggerDAO triggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, Trigger.class);
		
		Trigger batteryTrigger = new BatteryTrigger(this, rule, "ACDC", "Notify when plug or unplag device to power");
		
		// Gets the battery triggers dao
		ITriggerDAO batteryTriggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, BatteryTrigger.class);
		
		triggerDAO.Insert(batteryTrigger);
		batteryTrigger = batteryTriggerDAO.Insert(batteryTrigger);
		
		Trigger sensorTrigger = new SensorTrigger(this, rule, "Accelerometer", "Bla Bla", Sensor.TYPE_ACCELEROMETER, new float[] {10.0f, 5.5f});

		// Gets the sensors triggers dao
		ITriggerDAO sensorTriggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, SensorTrigger.class);
		
		triggerDAO.Insert(sensorTrigger);
		sensorTrigger = sensorTriggerDAO.Insert(sensorTrigger);
		
		Action notificationAction = new NotificationAction(this, rule, "Notifiy", "Bal Bla", "Notified", "Na Na", 0, 0);
		
		// Gets the notification actions dao
		IActionDAO actionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, NotificationAction.class);
		
		// Gets the notification actions dao
		IActionDAO notificationActionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, NotificationAction.class);
		
		actionDAO.Insert(notificationAction);
		notificationActionDAO.Insert(notificationAction);
		
		Action startAppAction = new StartAppAction(this, rule, "start app action", "na", "facebook");
		
		// Gets the notification actions dao
		IActionDAO startAppActionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, StartAppAction.class);
		
		actionDAO.Insert(startAppAction);
		startAppActionDAO.Insert(startAppAction);
		
		// Gets the rules list from db
		long ruleId = rule.getId();
		rule = ruleDAO.get(ruleId);
		
		ForeignCollection<Action> actions = rule.getActions();
		ForeignCollection<Trigger> triggers = rule.getTriggers();
//		CloseableIterator<Action> actionsIt = actions.closeableIterator();
//		CloseableIterator<Trigger> triggersIt = triggers.closeableIterator();
//		
//		Log.d(TAG, "ACTIONS:");
//		if (actionsIt.hasNext()) {
//			Action action = actionsIt.next();
//			Log.d(TAG , "Action Name: " + action.getName());
//		}
//		
//		Log.d(TAG, "TRIGGERS:");
//		if (triggersIt.hasNext()) {
//			Trigger trigger = triggersIt.next();
//			Log.d(TAG , "Trigger Name: " + trigger.getName());
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
