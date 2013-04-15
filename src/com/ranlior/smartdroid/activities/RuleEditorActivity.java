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
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleEditorFragmentAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.fragments.ActionEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity implements TriggerEditorFragment.Listener, ActionEditorFragment.Listener {

	private static final String TAG = RuleEditorActivity.class.getSimpleName();

	private Context appCtx = null;

	private ObjectContainer db = null;

	private RuleEditorFragmentAdapter mAdapter = null;

	private ViewPager mPager = null;

	private PageIndicator mIndicator = null;

	// FIXME: change visiability to private
	static Rule rule = null;

	/**
	 * Task Editor states enum. Inner class representing all the possiable state
	 * the task editor may be in.
	 * 
	 * @author ran
	 * 
	 */
	public enum State {
		ADD_RULE, EDIT_RULE
	}

	private State state = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate(Bundle savedInstanceState) ");

		setContentView(R.layout.activity_rule_editor);

		appCtx = getApplicationContext();

		db = Db4oHelper.db(appCtx);

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
			rule = new Rule("Name", "Desc");
			break;
		case EDIT_RULE:
			final long ruleId = intent.getLongExtra(SmartDroid.Extra.EXTRA_RULE_ID, -1);
			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return rule.getId() == ruleId;
				}
			});
			rule = rules.get(0);
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
			if (!rule.getTriggers().isEmpty() && !rule.getActions().isEmpty()) {
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
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
		db = Db4oHelper.db(appCtx);
	}

	@Override
	public void setTriggers(List<Trigger> triggers) {
		rule.getTriggers().addAll(triggers);
	}

	@Override
	public void setActions(List<Action> actions) {
		rule.getActions().addAll(actions);
	}

}
