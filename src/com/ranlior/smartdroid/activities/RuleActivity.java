package com.ranlior.smartdroid.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
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

	private ListView lvRules = null;

	private ActionMode actionMode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setContentView(R.layout.activity_rule);

		// FIXME: move this code xml
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DDDDDD")));
		getSupportActionBar().setTitle(Html.fromHtml("<font color='#555555'>Rule Editor</font>"));
		
		appCtx = getApplicationContext();

		db = Db4oHelper.db(appCtx);

		List<Rule> rules = new ArrayList<Rule>(db.query(Rule.class));

		rulesAdapter = new RulesAdapter(this, R.layout.rule_list_item, rules);
		lvRules = (ListView) findViewById(R.id.lvRules);
		lvRules.setAdapter(rulesAdapter);
		lvRules.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Rule rule = rulesAdapter.getItem(position);
				Intent intent = new Intent(RuleActivity.this, RuleEditorActivity.class);
				intent.setAction(SmartDroid.Action.ACTION_EDIT_RULE);
				intent.putExtra(SmartDroid.Extra.EXTRA_RULE_ID, rule.getId());
				startActivityForResult(intent, EDIT_RULE_REQUEST_CODE);
			}
		});
		lvRules.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (actionMode == null) {
					actionMode = RuleActivity.this.startActionMode(actionModeCallback);
				}
				rulesAdapter.toggleSelected(position);
				return true;
			}
		});

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View emptyView = inflater.inflate(R.layout.empty_rule_list, null);
		((ViewGroup) lvRules.getParent()).addView(emptyView);
		lvRules.setEmptyView(emptyView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
		
		getSupportMenuInflater().inflate(R.menu.activity_rule_menu, menu);
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
				rulesAdapter.add(rule);
				break;
			case EDIT_RULE_REQUEST_CODE:
				rulesAdapter.remove(rule);
				rulesAdapter.add(rule);
				break;
			default:
				throw new IllegalStateException(TAG + " caused by invalid request code");
			}
		}
	}

	private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			Log.d(TAG, "onCreateActionMode(ActionMode mode, Menu menu)");
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.activity_rule_action_mode, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			Log.d(TAG, "onPrepareActionMode(ActionMode mode, Menu menu)");
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Log.d(TAG, "onActionItemClicked(ActionMode mode, MenuItem item)");
			switch (item.getItemId()) {
			case R.id.deleteRule:
				db = Db4oHelper.db(appCtx);
				List<Rule> selectedRules = rulesAdapter.getSelected();
				for (Rule rule : selectedRules) {
					db.delete(rule);
					db.commit();
					rulesAdapter.remove(rule);
				}
				mode.finish();
				return true;
			default:
				return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			Log.d(TAG, "onDestroyActionMode(ActionMode mode)");
			actionMode = null;
			rulesAdapter.clearSelected();
		}
	};

}
