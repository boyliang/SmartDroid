/**
 * 
 */
package com.ranlior.smartdroid.loaders;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class RulesLoader extends AsyncTaskLoader<List<Rule>> {

	private static final String TAG = "RulesLoader";

	private ObjectContainer db = null;

	private List<Rule> rules = null;

	public RulesLoader(Context context) {
		super(context);

		Log.d(TAG, "RulesLoader(Context context)");

		db = Db4oHelper.db(context);
	}

	/**
	 * This is where the bulk of our work is done. This function is called in a
	 * background thread and should generate a new set of data to be published
	 * by the loader.
	 */
	@Override
	public List<Rule> loadInBackground() {
		Log.d(TAG, "loadInBackground()");

		rules = db.query(new Predicate<Rule>() {
			public boolean match(Rule rule) {
				return true;
			}
		});

		return rules;
	}

	/**
	 * Handles a request to start the Loader.
	 */
	@Override
	protected void onStartLoading() {
		if (rules != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(rules);
		}

		if (takeContentChanged() || rules == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
	}

	/**
	 * Handles a request to stop the Loader.
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	/**
	 * Handles a request to cancel a load.
	 */
	@Override
	public void onCanceled(List<Rule> rules) {
		super.onCanceled(rules);
	}

	/**
	 * Handles a request to completely reset the Loader.
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();

		// At this point we can release the resources associated with 'rules'
		// if needed.
		if (rules != null) {
			rules = null;
		}
	}

}
