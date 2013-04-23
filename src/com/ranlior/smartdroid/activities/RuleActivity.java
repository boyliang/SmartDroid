package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RulesAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

public class RuleActivity extends SherlockFragmentActivity {

	private final static String TAG = RuleActivity.class.getSimpleName();

	public static final int ADD_RULE_REQUEST_CODE = 1001;

	public static final int EDIT_RULE_REQUEST_CODE = 1002;

	private Context appCtx = null;

	private ObjectContainer db = null;

	private RulesAdapter rulesAdapter = null;

	private List<Rule> rules = null;

	private ListView lvRules = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_rule);

		appCtx = getApplicationContext();

		db = Db4oHelper.db(appCtx);

		rules = new ArrayList<Rule>(db.query(Rule.class));

		rulesAdapter = new RulesAdapter(this, R.layout.rule_list_item, rules);
		lvRules = (ListView) findViewById(R.id.lvRules);
		lvRules.setAdapter(rulesAdapter);
		lvRules.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Gets the rule's uuid
				Rule rule = rules.get(position);
				// Redirects the rule editor activiry with the selected rule id
				Intent intent = new Intent(RuleActivity.this, RuleEditorActivity.class);
				intent.setAction(SmartDroid.Action.ACTION_EDIT_RULE);
				intent.putExtra(SmartDroid.Extra.EXTRA_RULE_ID, rule.getId());
				startActivityForResult(intent, EDIT_RULE_REQUEST_CODE);
			}
		});

		// set swipe to dismiss gesture and remove from the adapter the item
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(lvRules,
				new SwipeDismissListViewTouchListener.OnDismissCallback() {
					@Override
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							Rule rule = rules.get(position);
							db.delete(rule);
							rules.remove(position);
						}
						rulesAdapter.notifyDataSetChanged();
					}
				});

		lvRules.setOnTouchListener(touchListener);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View emptyView = inflater.inflate(R.layout.empty_rule_list, null);
		((ViewGroup) lvRules.getParent()).addView(emptyView);
		lvRules.setEmptyView(emptyView);

		// this is a special listener that preventing from swiping to dismiss to
		// trigger while scrolling
		lvRules.setOnScrollListener(touchListener.makeScrollListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");

		getSupportMenuInflater().inflate(R.menu.activity_rule, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.addRule:
			Intent intent = new Intent(RuleActivity.this, RuleEditorActivity.class);
			intent.setAction(SmartDroid.Action.ACTION_ADD_RULE);
			startActivityForResult(intent, ADD_RULE_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");

		if (resultCode == RESULT_OK) {
			final UUID ruleId = (UUID) data.getSerializableExtra(SmartDroid.Extra.EXTRA_RULE_ID);
			db = Db4oHelper.db(appCtx);
			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return ruleId.compareTo(rule.getId()) == 0;
				}
			});
			Rule rule = rules.get(0);

			switch (requestCode) {
			case ADD_RULE_REQUEST_CODE:
				this.rules.add(rule);
				break;
			case EDIT_RULE_REQUEST_CODE:
				int pos = this.rules.indexOf(rule);
				this.rules.remove(pos);
				this.rules.add(pos, rule);
				break;
			default:
				throw new IllegalStateException(TAG + " caused by invalid request code");
			}

			rulesAdapter.notifyDataSetChanged();
		}
	}

}
