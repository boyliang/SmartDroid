package com.ranlior.smartdroid.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.Notification;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.ModifyRingerModeAction;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.BootCompletedTrigger;
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

//		Rule rule1 = new Rule(appCtx, "name1", "desc1");
//		ruleDAO.insert(rule1);
//		Rule rule2 = new Rule(appCtx, "name2", "desc2");
//		ruleDAO.insert(rule2);
		Rule rule3 = new Rule(appCtx, "name3", "desc3");
		ruleDAO.insert(rule3);

		// Gets the triggers dao
		ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);

//		Trigger ringerModeTrigger = new RingerModeTrigger(appCtx, rule1, AudioManager.RINGER_MODE_SILENT);
//		triggerDAO.insert(ringerModeTrigger);
//		
//		Trigger bootCompletedTrigger = new BootCompletedTrigger(appCtx, rule2);
//		triggerDAO.insert(bootCompletedTrigger);
		Trigger locationProximityTrigger = new LocationProximityTrigger(appCtx, rule3, 32.062665, 34.862398, 500.0F, -1L);
		triggerDAO.insert(locationProximityTrigger);
		locationProximityTrigger.register();

		// Gets the notification actions dao
		IActionDAO actionDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getActionDAO(appCtx);

//		Action modifyRingerModeAction = new ModifyRingerModeAction(appCtx, rule1, AudioManager.RINGER_MODE_NORMAL);
//		actionDAO.insert(modifyRingerModeAction);
//		
//		Action notifyBootCompleted = new NotificationAction(
//				appCtx, rule2, "Boot Completed", "Boot completed triggered",
//				Notification.DEFAULT_ALL, Notification.FLAG_ONLY_ALERT_ONCE);
//		actionDAO.insert(notifyBootCompleted);
		
		Action notifyAcPlugged = new NotificationAction(
				appCtx, rule3, "Location Proximity", "Entered proximity",
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
