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
import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oUUID;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.RuleEditorActivity.State;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RuleEditorFragment extends SherlockFragment {

	private static final String TAG = RuleEditorFragment.class.getSimpleName();

	private ObjectContainer db = null;

	private State state;

	private long ruleUuIdLong = -1L;

	private byte[] ruleUuidSignatire = null;

	private Listener listener = null;

	private Activity hostingActivity = null;

	private String ruleName = null;

	private String ruleDescription = null;

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
	 * Create a new instance of the fargment, initialized to show the name and
	 * description of the rule by given rule id, if exists.
	 */
	public static RuleEditorFragment newInstance(State state, Db4oUUID ruleUuid) {
		Log.d(TAG, "newInstance(State state, Db4oUUID ruleUuid)");
		RuleEditorFragment fragment = new RuleEditorFragment();

		// Supply rule id input as an argument.
		Bundle args = new Bundle();
		args.putSerializable("state", state);
		if (ruleUuid != null) {
			args.putLong("long", ruleUuid.getLongPart());
			args.putByteArray("signature", ruleUuid.getSignaturePart());
		}
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

		// Saves the state
		outState.putSerializable("state", state);
		// Saves the rule's uuid
		outState.putLong("long", ruleUuIdLong);
		outState.putByteArray("signature", ruleUuidSignatire);

		// Calls the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		db = Db4oHelper.db(hostingActivity);

		// During creation, if arguments have been supplied to the fragment
		// then parse those out
		Bundle args = getArguments();
		if (args != null) {
			state = (State) args.getSerializable("state");
			ruleUuIdLong = args.getLong("long");
			ruleUuidSignatire = args.getByteArray("signature");
		}

		// If recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			state = (State) savedInstanceState.getSerializable("state");
			ruleUuIdLong = savedInstanceState.getLong("long");
			ruleUuidSignatire = savedInstanceState.getByteArray("signature");
		}

		// Gets rule's name and description
		switch (state) {
		case ADD_RULE:
			break;
		case EDIT_RULE:
			Db4oUUID ruleUuid = new Db4oUUID(ruleUuIdLong, ruleUuidSignatire);
			Rule rule = db.ext().getByUUID(ruleUuid);
			db.activate(rule, 1);
			ruleName = rule.getName();
			ruleDescription = rule.getDescription();
			break;
		}
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
			tvRuleName.setText(ruleName);
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
			tvRuleDescription.setText(ruleDescription);
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
