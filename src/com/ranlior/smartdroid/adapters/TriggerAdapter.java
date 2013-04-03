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
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerAdapter extends ArrayAdapter<Trigger> {

	private static final String TAG = "TriggerAdapter"; 
	
	private static TriggerAdapter instance = null;

	private List<Trigger> triggers;
	
	Context context;

	private TriggerAdapter(Context context, int trigger_layout,  List<Trigger> triggers) {
		super(context, trigger_layout, triggers);
		
		Log.d(TAG, "constructor");
		
		this.context = context;
		this.triggers = triggers;
	}

	public static TriggerAdapter getInstance(Context context, int trigger_layout,  List<Trigger> triggers) {
		
		Log.d(TAG, "getInstance(Context context, int trigger_layout, List<Trigger> triggers)");
		
		if (instance == null) {
			instance = new TriggerAdapter(context, trigger_layout, triggers);
		}
		return instance;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Trigger trigger = triggers.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.trigger_item, null);

			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.description);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvTitle.setText(trigger.getName());
		holder.tvDesc.setText(trigger.getDescription());

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}

	
}
