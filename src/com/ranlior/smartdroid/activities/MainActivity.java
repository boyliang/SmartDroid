package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.ChangeWIFIStateAction;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.LocationProximityTrigger;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
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

		Context appCtx = getApplicationContext();

		// Gets the rules dao
		IRuleDAO ruleDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(appCtx);

		Rule rule1 = new Rule(appCtx, "name1", "desc1");
		ruleDAO.insert(rule1);
		Rule rule2 = new Rule(appCtx, "name2", "desc2");
		ruleDAO.insert(rule2);
		Rule rule3 = new Rule(appCtx, "name3", "desc3");
		ruleDAO.insert(rule3);

		// Gets the triggers dao
		ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);

		Trigger ringerModeTrigger1 = new RingerModeTrigger(appCtx, rule1, AudioManager.RINGER_MODE_SILENT);
		triggerDAO.insert(ringerModeTrigger1);
		Trigger ringerModeTrigger2 = new RingerModeTrigger(appCtx, rule2, AudioManager.RINGER_MODE_VIBRATE);
		triggerDAO.insert(ringerModeTrigger2);
		Trigger locationProximityTrigger = new LocationProximityTrigger(appCtx, rule3, 32.089759, 34.848705, 100.0F, -1L);
		triggerDAO.insert(locationProximityTrigger);
		locationProximityTrigger.register();

		// Gets the notification actions dao
		IActionDAO actionDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getActionDAO(appCtx);

		Action changeWifiState1 = new ChangeWIFIStateAction(appCtx, rule1, WifiManager.WIFI_STATE_ENABLED);
		actionDAO.insert(changeWifiState1);
		Action changeWifiState2 = new ChangeWIFIStateAction(appCtx, rule2, WifiManager.WIFI_STATE_DISABLED);
		actionDAO.insert(changeWifiState2);
		Action notifyAcPlugged = new NotificationAction(
				appCtx, rule3, "Location Proximity", "Danielle's home",
				Notification.DEFAULT_ALL, Notification.FLAG_ONLY_ALERT_ONCE);
		actionDAO.insert(notifyAcPlugged);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
