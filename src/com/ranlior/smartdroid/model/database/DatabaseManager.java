/**
 * 
 */
package com.ranlior.smartdroid.model.database;

import android.content.Context;

/**
 * @author Ran Haveshush
 * Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class DatabaseManager {

	private static DatabaseManager instance = null;
	
	private DatabaseHelper databaseHelper = null;

	private DatabaseManager(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}

	public static DatabaseManager getInstance(Context context) {
		if (instance == null) {
			// FIXME: check if synchronized is needed
			synchronized (DatabaseManager.class) {
				if (instance == null) {
					instance = new DatabaseManager(context);
				}
			}
		}
		return instance;
	}

	/**
	 * @return the databaseHelper
	 */
	public DatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}
	
}
