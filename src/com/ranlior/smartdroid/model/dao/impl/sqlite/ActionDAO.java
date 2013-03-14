/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.database.DatabaseHelper;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.actions.Action;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class ActionDAO implements IActionDAO {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "ActionDAO";
	
	/**
	 * Holds the invoking context.
	 */
	private final Context context;
	
	private final DatabaseHelper databaseHelper;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public ActionDAO(Context context) {
		super();
		
		// Logger
		Log.d(TAG, "ActionDAO(Context context)");
		
		this.context = context;
		this.databaseHelper = DatabaseManager.getInstance(context).getDatabaseHelper();
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#list()
	 */
	@Override
	public Cursor list() {
		// Logger
		Log.d(TAG, "list()");
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#get(long)
	 */
	@Override
	public Action get(long actionId) {
		// Logger
		Log.d(TAG, "get(long actionId)");
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Insert(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public Action Insert(Action action) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Update(com.ranlior.smartdroid.model.dto.actions.Action)
	 */
	@Override
	public void Update(Action action) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dao.logic.IActionDAO#Delete(long)
	 */
	@Override
	public void Delete(long actionId) {
		// TODO Auto-generated method stub
		
	}

}
