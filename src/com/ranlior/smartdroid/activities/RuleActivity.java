package com.ranlior.smartdroid.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.nineoldandroids.swipetodissmis.SwipeDismissListViewTouchListener;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.adapters.RuleAdapter;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.utilities.RuleGenerator;

public class RuleActivity extends Activity {

	private ListView lvRuleCardsList;
	private RuleAdapter ruleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);
		List<Rule> rules = RuleGenerator.getRules(200);
		lvRuleCardsList = (ListView) findViewById(R.id.lvRuleCardsList);
		ruleAdapter = RuleAdapter.getInstance(this, R.layout.rule_card_layout, rules);
		lvRuleCardsList.setAdapter(ruleAdapter);

		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(lvRuleCardsList,
				new SwipeDismissListViewTouchListener.OnDismissCallback() {
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							ruleAdapter.remove(ruleAdapter.getItem(position));
						}
						ruleAdapter.notifyDataSetChanged();
					}
				});
		lvRuleCardsList.setOnTouchListener(touchListener);
		lvRuleCardsList.setOnScrollListener(touchListener.makeScrollListener());

	}

}
