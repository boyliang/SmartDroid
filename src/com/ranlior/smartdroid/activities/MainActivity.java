package com.ranlior.smartdroid.activities;

import java.util.Collection;

import android.app.Activity;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.BatteryTrigger;
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

		// startService(new Intent(this, SmartService.class));

		Rule rule = new Rule(this, "rule", "bsdf");

		// Gets the rules dao
		IRuleDAO ruleDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(this);

		rule = ruleDAO.insert(rule);

		// Gets the triggers dao
		ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getTriggerDAO(this);

		Trigger batteryTrigger = new BatteryTrigger(this, rule, "ACDC",
				"Notify when plug or unplag device to power",
				BatteryManager.BATTERY_PLUGGED_AC);

		batteryTrigger = triggerDAO.Insert(batteryTrigger);

		Action notificationAction = new NotificationAction(this, rule,
				"Notifiy", "Bal Bla", "Notified", "Na Na", 0, 0);

		// Gets the notification actions dao
		IActionDAO actionDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getActionDAO(this);

		actionDAO.Insert(notificationAction);

		Collection<Trigger> triggers = rule.getTriggers();
		Collection<Action> actions = rule.getActions();
		
		ruleDAO.update(rule);
		
		rule.register();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
