package com.ranlior.smartdroid.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.model.dto.rules.Rule;

public class RulesAdapter extends ArrayAdapter<Rule> {

	private static final String TAG = RulesAdapter.class.getSimpleName();

	private List<Rule> rules = null;

	private List<Rule> selectedRules = null;

	private LayoutInflater inflater = null;

	public RulesAdapter(Context context, int layoutResourceId, List<Rule> rules) {
		super(context, layoutResourceId, rules);

		Log.d(TAG, "Constructor");

		this.rules = rules;
		this.inflater  = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Rule rule = rules.get(position);
		boolean isRuleSelected = (selectedRules != null) ? selectedRules.contains(rule) : false;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.rule_list_item, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(rule.getName());
		holder.tvDesc.setText(rule.getDescription());
		
		convertView.findViewById(R.id.content).setSelected(isRuleSelected);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}

	@Override
	public void addAll(Collection<? extends Rule> collection) {
		Iterator<? extends Rule> iterator = collection.iterator();
		while (iterator.hasNext()) {
			add(iterator.next());
		}
	}

	public boolean isSelected(int position) {
		if (selectedRules == null || selectedRules.isEmpty()) {
			return false;
		}
		return selectedRules.contains(rules.get(position));
	}

	public void setSelected(int position, boolean toSelect) {
		if (selectedRules == null) {
			selectedRules = new ArrayList<Rule>();
		}
		if (toSelect && !isSelected(position)) {
			selectedRules.add(rules.get(position));
		} else if (!toSelect && isSelected(position)) {
			selectedRules.remove(rules.get(position));
		}
	}

	public void toggleSelected(int position) {
		if (isSelected(position)) {
			setSelected(position, false);
		} else {
			setSelected(position, true);
		}
		notifyDataSetChanged();
	}

	public List<Rule> getSelected() {
		return selectedRules;
	}

	public void clearSelected() {
		selectedRules = null;
		notifyDataSetChanged();
	}

}
