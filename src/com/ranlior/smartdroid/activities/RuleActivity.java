package com.ranlior.smartdroid.activities;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleAdapter;
import com.ranlior.smartdroid.loaders.RulesLoader;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IRuleDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

public class RuleActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<List<Rule>> {

	private final static String TAG = "RuleActivity";

	private RuleAdapter rulesAdapter = null;

	private ListView lvRules = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);

		IRuleDAO ruleDAO = SmartDAOFactory.getFactory(SmartDAOFactory.SQLITE).getRuleDAO(getApplicationContext());

		List<Rule> rules = ruleDAO.list();

		rulesAdapter = RuleAdapter.getInstance(this, R.layout.rule_card_layout, rules);
		lvRules = (ListView) findViewById(R.id.lvRules);
		lvRules.setAdapter(rulesAdapter);

		// set swipe to dismiss gesture and remove from the adapter the item
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(lvRules,
				new SwipeDismissListViewTouchListener.OnDismissCallback() {
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							rulesAdapter.remove(rulesAdapter.getItem(position));
						}
						rulesAdapter.notifyDataSetChanged();
					}
				});

		lvRules.setOnTouchListener(touchListener);

		// this is a special listener that preventing from swiping to dismiss
		// while scrolling
		lvRules.setOnScrollListener(touchListener.makeScrollListener());

		// Prepare the loader.
		// Either re-connect with an existing one, or start a new one.
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");

		menu.add("Add Rule").setIcon(R.drawable.ic_action_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		// This uses the imported MenuItem from ActionBarSherlock
		Log.d(TAG, "Got click: " + item.getTitle().toString());

		if (item.getTitle().toString().equals("Add Rule")) {
			Intent intent = new Intent(this, RuleEditorActivity.class);
			startActivity(intent);
		}

		return true;
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
