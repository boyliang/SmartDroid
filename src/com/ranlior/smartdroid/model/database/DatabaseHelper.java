/**
 * 
 */
package com.ranlior.smartdroid.model.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	/**
	 * The logger's tag.
	 */
	public static final String TAG = "DatabaseHelper";
	
	/**
	 * The database that the provider uses as its underlying data store.
	 */
	public static final String DATABASE_NAME = "smart.db";

	/**
	 * The database version.
	 */
	public static final int DATABASE_VERSION = 1;

	/**
	 * Minimal constructor.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/* (non-Javadoc)
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
	 * #onCreate(android.database.sqlite.SQLiteDatabase, com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		// Logs that the database is being creating
		Log.d(TAG, "Creating database name:" + DATABASE_NAME + "version: " + DATABASE_VERSION);
		
		try {
			// Creates actions tables
			TableUtils.createTable(connectionSource, NotificationAction.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
	 * #onUpgrade(android.database.sqlite.SQLiteDatabase, com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// Logs that the database is being upgraded
		Log.w(TAG, "Upgrading database name: " + DATABASE_NAME + " from version "
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		
		try {
			// Drops actions tables
			TableUtils.dropTable(connectionSource, NotificationAction.class, true);
			// Recreates database
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
