/**
 * 
 */
package com.ranlior.smartdroid.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.ActionSelectActivity;
import com.ranlior.smartdroid.adapters.ExpandableActionListAdapter;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ActionEditorFragment extends SherlockFragment {

	private static final String TAG = ActionEditorFragment.class.getSimpleName();
	
	public static final int SELECT_ACTION_REQUEST_CODE = 1002;
	
	private static List<Action> actions;

	private ExpandableActionListAdapter expandableActionAdaper;
	
	private ExpandableListView elvActions;

	private Activity hostingActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");
		
		setHasOptionsMenu(true);
		
		hostingActivity = getActivity();
		
		IActionDAO actionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(hostingActivity);
		
		// FIXME: get the real rule id
		actions = (List<Action>) actionDAO.list(0);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater)");
		
		inflater.inflate(R.menu.action_list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.addAction:
			Intent intent = new Intent(hostingActivity, ActionSelectActivity.class);
			startActivityForResult(intent, SELECT_ACTION_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
		
		expandableActionAdaper = new ExpandableActionListAdapter(hostingActivity, actions);
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_expandable_list_actions, null);
		// TODO: change the list id
		elvActions = (ExpandableListView) linearLayout.findViewById(R.id.expandableListView);
		elvActions.setAdapter(expandableActionAdaper);

		return linearLayout;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data)");
		
		if (resultCode == hostingActivity.RESULT_OK) {
			if (requestCode == SELECT_ACTION_REQUEST_CODE) {
				
				String actionClassName = data.getStringExtra(SmartDroid.Extra.EXTRA_ACTION_CLASS_NAME);
				Action action = null;
				
				try {
					action = (Action) Class.forName(SmartDroid.Actions.PACKAGE + "." + actionClassName).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				}
				
				actions.add(action);
			}
		}
	}

}
