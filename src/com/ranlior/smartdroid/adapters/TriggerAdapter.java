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
	
	private Context context;
	
	private LayoutInflater inflater;

	private List<Trigger> triggers;
	

	/**
	 * Full constractor.
	 * 
	 * @param context
	 * @param trigger_layout
	 * @param triggers
	 */
	public TriggerAdapter(Context context, int trigger_layout,  List<Trigger> triggers) {
		super(context, trigger_layout, triggers);
		
		Log.d(TAG, "TriggerAdapter(Context context, int trigger_layout,  List<Trigger> triggers)");
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.triggers = triggers;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Trigger trigger = triggers.get(position);
		if (convertView == null) {
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

	private static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
	}

	
}
