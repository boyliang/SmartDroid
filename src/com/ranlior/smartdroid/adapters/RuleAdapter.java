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

	private static final String TAG = RuleAdapter.class.getSimpleName();

	private List<Rule> rules;

	public RuleAdapter(Context context, int layoutResourceId,  List<Rule> rules) {
		super(context, layoutResourceId, rules);
		
		Log.d(TAG, "Constructor");
		
		this.rules = rules;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Rule rule = rules.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}
}
	

