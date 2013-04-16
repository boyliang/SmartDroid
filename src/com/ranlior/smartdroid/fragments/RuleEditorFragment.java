/**
 * 
 */
package com.ranlior.smartdroid.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RuleEditorFragment extends SherlockFragment {

	private static final String TAG = RuleEditorFragment.class.getSimpleName();

	private long ruleId = -1;

	private Listener listener = null;

	private Activity hostingActivity = null;

	/**
	 * Listener interface for the fragment. Container Activity must implement
	 * this interface.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public interface Listener {
		/**
		 * @param name
		 */
		public void setName(String name);
		/**
		 * @param description
		 */
		public void setDescription(String description);
	}

	/**
	 * Create a new instance of the fargment, initialized to show the actions of
	 * the rule by given rule id.
	 */
	public static RuleEditorFragment newInstance(long ruleId) {
		RuleEditorFragment fragment = new RuleEditorFragment();

		// Supply rule id input as an argument.
		Bundle args = new Bundle();
		args.putLong(SmartDroid.Extra.EXTRA_RULE_ID, ruleId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach(Activity activity)");

		// Ensures hosting activity implements the Listener interface
		try {
			listener = (Listener) activity;
			// Gets fragment hosting activity
			hostingActivity = activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement " + Listener.class.getSimpleName());
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState(Bundle outState)");

		// Saves the rule id
		outState.putLong(SmartDroid.Extra.EXTRA_RULE_ID, ruleId);

		// Calls the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

		// If the container view is null,
		// There is no need for us to create the fragment view
		if (container == null) {
			return null;
		// If the container view isn't null,
		// There is need for us to create the fragment view
		} else {
			View view = inflater.inflate(R.layout.rule_fragment_details, null);
			EditText tvRuleName = (EditText) view.findViewById(R.id.etRuleName);
			tvRuleName.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}
				@Override
				public void afterTextChanged(Editable s) {
					listener.setName(s.toString());
				}
			});
			EditText tvRuleDescription = (EditText) view.findViewById(R.id.etRuleDescription);
			tvRuleDescription.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}
				@Override
				public void afterTextChanged(Editable s) {
					listener.setDescription(s.toString());
				}
			});
			return view;
		}
	}

}
