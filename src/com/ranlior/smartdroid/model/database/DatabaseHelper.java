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
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.actions.StartAppAction;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.BatteryTrigger;
import com.ranlior.smartdroid.model.dto.triggers.SensorTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

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
	public static final int DATABASE_VERSION = 19;

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
		Log.w(TAG, "Creating database name:" + DATABASE_NAME + "version: " + DATABASE_VERSION);
		
		createTables();
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

		dropTables();
		// Recreates database
		onCreate(database, connectionSource);
	}
	
	/**
	 * Create tables for Ormlite annotated classes.
	 */
	// TODO: Creates tables for annotated classes
	private void createTables() {
		try {
			// Creates rules table
			TableUtils.createTable(connectionSource, Rule.class);
			// Creates triggers tables
			TableUtils.createTable(connectionSource, Trigger.class);
			TableUtils.createTable(connectionSource, BatteryTrigger.class);
			TableUtils.createTable(connectionSource, SensorTrigger.class);
			// Creates actions tables
			TableUtils.createTable(connectionSource, Action.class);
			TableUtils.createTable(connectionSource, NotificationAction.class);
			TableUtils.createTable(connectionSource, StartAppAction.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Drops tables for Ormlite annotated classes.
	 */
	// TODO: Drops tables for annotated classes
	private void dropTables() {
		try {
			// Drops rules table
			TableUtils.dropTable(connectionSource, Rule.class, true);
			// Drops triggers tables
			TableUtils.dropTable(connectionSource, Trigger.class, true);
			TableUtils.dropTable(connectionSource, BatteryTrigger.class, true);
			TableUtils.dropTable(connectionSource, SensorTrigger.class, true);
			// Drops actions tables
			TableUtils.dropTable(connectionSource, Action.class, true);
			TableUtils.dropTable(connectionSource, NotificationAction.class, true);
			TableUtils.dropTable(connectionSource, StartAppAction.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
