package com.ranlior.smartdroid.adapters;

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

public class RuleAdapter extends ArrayAdapter<Rule> {

	private static final String TAG = "RuleAdapter";
	
	private static RuleAdapter instance = null;

	private List<Rule> rules;
	
	Context context;


	private RuleAdapter(Context context, int card_layout,  List<Rule> ruleList) {
		super(context, card_layout, ruleList);
		
		Log.d(TAG, "constructor");
		
		this.context = context;
		this.rules = ruleList;
		
	}

	public static RuleAdapter getInstance(Context context, int card_layout, List<Rule> ruleList) {
		Log.d(TAG, "getInstance(Context context, int card_layout, List<Rule> ruleList)");
		
		if (instance == null) {
			instance = new RuleAdapter(context, card_layout, ruleList);
		}
		return instance;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		final Rule rule = rules.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.rule_card_layout, null);

			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvTitle.setText(rule.getName());
		holder.tvDesc.setText(rule.getDescription());

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}
	
}
