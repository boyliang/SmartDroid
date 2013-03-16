package com.ranlior.smartdroid.activities;

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Menu;

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
import com.ranlior.smartdroid.services.SmartService;

public class MainActivity extends Activity {

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
		
		Trigger batteryTrigger = new BatteryTrigger(this, rule, "ACDC", "Notify when plug or unplag device to power");
		// FIXME: think of change impl to object fromto
		Trigger sensorTrigger = new SensorTrigger(this, rule, "Accelerometer", "Bla Bla", Sensor.TYPE_ACCELEROMETER, new float[] {10.0f, 5.5f});
		
		// Gets the battery triggers dao
		ITriggerDAO batteryTriggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, BatteryTrigger.class);
		
		batteryTrigger = batteryTriggerDAO.Insert(batteryTrigger);

		// Gets the sensors triggers dao
		ITriggerDAO sensorTriggerDAO = SmartDAOFactory
			.getFactory(SmartDAOFactory.SQLITE)
			.getTriggerDAO(this, SensorTrigger.class);
		
		sensorTrigger = sensorTriggerDAO.Insert(sensorTrigger);
		
		Action notificationAction = new NotificationAction(this, rule, "Notifiy", "Bal Bla", "Notified", "Na Na", 0, 0);
		Action startAppAction = new StartAppAction(this, rule, "start app action", "na", "facebook");
		
		// Gets the notification actions dao
		IActionDAO notificationActionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, NotificationAction.class);
		
		notificationActionDAO.Insert(notificationAction);
		
		// Gets the notification actions dao
		IActionDAO startAppActionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(this, StartAppAction.class);
		
		startAppActionDAO.Insert(startAppAction);
		
		// Gets the rules list from db
		List<Rule> rules = ruleDAO.list();
		Collection<Action> actions = rules.get(0).getActions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
