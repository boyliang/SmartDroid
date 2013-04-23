package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.db4o.ObjectContainer;
import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RulesAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.loaders.RulesLoader;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

public class RuleActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<List<Rule>> {

	private final static String TAG = RuleActivity.class.getSimpleName();

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
				startActivity(intent);
			}
		});

		// set swipe to dismiss gesture and remove from the adapter the item
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(lvRules,
				new SwipeDismissListViewTouchListener.OnDismissCallback() {
					@Override
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						Rule rule = null;
						for (int position : reverseSortedPositions) {
							rule = rulesAdapter.getItem(position);
							rulesAdapter.remove(rule);
							db.delete(rule);
						}
						rulesAdapter.notifyDataSetChanged();
					}
				});

		lvRules.setOnTouchListener(touchListener);

		// this is a special listener that preventing from swiping to dismiss to
		// trigger while scrolling
		lvRules.setOnScrollListener(touchListener.makeScrollListener());

		// Prepare the loader.
		// Either re-connect with an existing one, or start a new one.
		getSupportLoaderManager().initLoader(0, null, this);
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
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public Loader<List<Rule>> onCreateLoader(int id, Bundle args) {
		Log.d(TAG, "onCreateLoader(int id, Bundle args)");
		return new RulesLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<Rule>> loader, List<Rule> data) {
		Log.d(TAG, "onLoadFinished(Loader<List<Rule>> loader, List<Rule> rules)");

		// Set the new data in the adapter.
		if (rulesAdapter != null) {
			rulesAdapter.clear();
			// FIXME: no such method
			rulesAdapter.addAll(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Rule>> loader) {
		Log.d(TAG, "onLoaderReset(Loader<List<Rule>> loader)");
		if (rulesAdapter != null) {
			rulesAdapter.clear();
		}
	}

}
