package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.StartAppAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
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

		Rule rule = new Rule(appCtx, "name", "desc");

		// Gets the rules dao
		IRuleDAO ruleDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE)
				.getRuleDAO(appCtx);

		rule = ruleDAO.insert(rule);

		// Gets the triggers dao
		ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);

		Trigger ringerModeTrigger = new RingerModeTrigger(appCtx, rule, "name",
				"desc", AudioManager.RINGER_MODE_NORMAL);

		ringerModeTrigger = triggerDAO.insert(ringerModeTrigger);

		// Gets the notification actions dao
		IActionDAO actionDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getActionDAO(appCtx);

		Action startAppAction = new StartAppAction(appCtx, rule, "name",
				"desc", "com.android.contacts");

		actionDAO.insert(startAppAction);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
