package com.ranlior.smartdroid.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleEditorFragmentAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.fragments.ActionEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity implements TriggerEditorFragment.Listener, ActionEditorFragment.Listener {

	private static final String TAG = RuleEditorActivity.class.getSimpleName();

	private Context appCtx;

	private RuleEditorFragmentAdapter mAdapter;

	private ViewPager mPager;

	private PageIndicator mIndicator;

	/**
	 * Task Editor states enum.
	 * Inner class representing all the possiable state the
	 * task editor may be in.
	 * 
	 * @author ran
	 *
	 */
	public enum State {
		ADD_RULE,
		EDIT_RULE
	}

	private State state;

	private IRuleDAO ruleDAO;
	
	private ITriggerDAO triggerDAO;
	
	private IActionDAO actionDAO;

	// FIXME: change visiability to private
	static Rule rule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate(Bundle savedInstanceState) ");

		setContentView(R.layout.activity_rule_editor);

		appCtx = getApplicationContext();

		ruleDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getRuleDAO(appCtx);

		// Gets the action from the intent
		Intent intent = getIntent();
		String action = intent.getAction();
		Log.d(TAG, "action: " + action);

		// Sets the state of the activity by the invoked action
		if (SmartDroid.Action.ACTION_ADD_RULE.equals(action)) {
			state = State.ADD_RULE;
		} else if (SmartDroid.Action.ACTION_EDIT_RULE.equals(action)) {
			state = State.EDIT_RULE;
		}
		
		switch (state) {
		case ADD_RULE:
			rule = new Rule(appCtx, "Name", "Desc");
			ruleDAO.insert(rule);
			break;
		case EDIT_RULE:
			long ruleId = intent.getLongExtra(SmartDroid.Extra.EXTRA_RULE_ID, -1);
			rule = ruleDAO.get(ruleId);
			break;
		default:
			throw new IllegalStateException(TAG + " caused by invalid action");
		}

		mAdapter = new RuleEditorFragmentAdapter(getSupportFragmentManager(), rule.getId());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");

		getSupportMenuInflater().inflate(R.menu.activity_rule_editor, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
		Log.d(TAG, "item selected: " + item.getTitle());

		switch (item.getItemId()) {
		case R.id.saveRule:
			// Validates add rule workflow
			List<Trigger> triggers = (List<Trigger>) rule.getTriggers();
			List<Action> actions = (List<Action>) rule.getActions();
			if (triggers != null && actions != null) {
				setResult(RESULT_OK);
				finish();
			} else {
				Toast.makeText(appCtx, "Your triggers or actions list is empty.", Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void setTriggers(List<Trigger> triggers) {
		triggerDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);
		// Persists all triggers
		for (Trigger trigger : triggers) {
			triggerDAO.insert(trigger);
		}
		
	}
	
	@Override
	public void setActions(List<Action> actions) {
		actionDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getActionDAO(appCtx);
		// Persists all actions
		for (Action action : actions) {
			actionDAO.insert(action);
		}
	}

}
