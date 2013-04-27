package com.ranlior.smartdroid.activities;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
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
import com.ranlior.smartdroid.fragments.RuleEditorFragment;
import com.ranlior.smartdroid.fragments.TriggerEditorFragment;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class RuleEditorActivity extends SherlockFragmentActivity implements TriggerEditorFragment.Listener, ActionEditorFragment.Listener,
		RuleEditorFragment.Listener {

	private static final String TAG = RuleEditorActivity.class.getSimpleName();

	private Context appCtx = null;

	private ObjectContainer db = null;

	private RuleEditorFragmentAdapter mAdapter = null;

	private ViewPager mPager = null;

	private PageIndicator mIndicator = null;

	private Rule rule = null;

	/**
	 * Task Editor states enum. Inner class representing all the possiable state
	 * the task editor may be in.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
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

		// FIXME: move this code xml
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DDDDDD")));
		getSupportActionBar().setTitle(Html.fromHtml("<font color='#555555'>Rule Editor</font>"));

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
			rule = new Rule(null, null);
			mAdapter = new RuleEditorFragmentAdapter(getSupportFragmentManager(), State.ADD_RULE, null);
			break;
		case EDIT_RULE:
			final UUID ruleUuId = (UUID) intent.getSerializableExtra(SmartDroid.Extra.EXTRA_RULE_ID);
			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return ruleUuId.compareTo(rule.getId()) == 0;
				}
			});
			rule = rules.get(0);
			mAdapter = new RuleEditorFragmentAdapter(getSupportFragmentManager(), State.EDIT_RULE, ruleUuId);
			break;
		default:
			throw new IllegalStateException(TAG + " caused by invalid action");
		}

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		// TODO this might be the place to enable\disable menu items to avoid
		// the click while paging on menu item
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				/*
				 * This method will be invoked when a new page becomes selected.
				 * Animation is not necessarily complete.
				 * 
				 * Parameters position Position index of the new selected page.
				 */
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				/*
				 * This method will be invoked when the current page is
				 * scrolled, either as part of a programmatically initiated
				 * smooth scroll or a user initiated touch scroll.
				 * 
				 * Parameters position Position index of the first page
				 * currently being displayed. Page position+1 will be visible if
				 * positionOffset is nonzero. positionOffset Value from [0, 1)
				 * indicating the offset from the page at position.
				 * positionOffsetPixels Value in pixels indicating the offset
				 * from position.
				 */
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				/*
				 * Called when the scroll state changes.
				 * 
				 * ViewPager.SCROLL_STATE_DRAGGING - Indicates that the pager is
				 * currently being dragged by the user.
				 * ViewPager.SCROLL_STATE_IDLE - Indicates that the pager is in
				 * an idle, settled state. The current page is fully in view and
				 * no animation is in progress. ViewPager.SCROLL_STATE_SETTLING
				 * - Indicates that the pager is in the process of settling to a
				 * final position.
				 */
			}
		});

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");

		getSupportMenuInflater().inflate(R.menu.activity_rule_editor_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
		Log.d(TAG, "item selected: " + item.getTitle());

		db = Db4oHelper.db(appCtx);

		switch (item.getItemId()) {
		case R.id.saveRule:
			// Validates rule add or edit workflow
			if (rule.getTriggers().isEmpty()) {
				Toast.makeText(appCtx, "Rule's triggers list is empty.", Toast.LENGTH_SHORT).show();
				mPager.setCurrentItem(0, true);
			} else if (rule.getActions().isEmpty()) {
				Toast.makeText(appCtx, "Rule's actions list is empty.", Toast.LENGTH_SHORT).show();
				mPager.setCurrentItem(1, true);
			} else if (rule.getName() == null || "".equals(rule.getName())) {
				Toast.makeText(appCtx, "Rule's name is empty.", Toast.LENGTH_SHORT).show();
				mPager.setCurrentItem(2, true);
			} else if (rule.getDescription() == null || "".equals(rule.getDescription())) {
				Toast.makeText(appCtx, "Rule's description is empty.", Toast.LENGTH_SHORT).show();
				mPager.setCurrentItem(2, true);
				// If rule add or edit workflow valid
			} else {
				// Saves the rule to the db
				db.store(rule);
				db.commit();
				// Returns result ok to the invoking rule activity
				Intent resIntent = new Intent();
				resIntent.putExtra(SmartDroid.Extra.EXTRA_RULE_ID, rule.getId());
				setResult(RESULT_OK, resIntent);
				finish();
				Toast.makeText(appCtx, "Rule Saved", Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void setTriggers(List<Trigger> triggers) {
		rule.setTriggers(triggers);
	}

	@Override
	public void setActions(List<Action> actions) {
		rule.setActions(actions);
	}

	@Override
	public void setName(String name) {
		rule.setName(name);
	}

	@Override
	public void setDescription(String description) {
		rule.setDescription(description);
	}

}
