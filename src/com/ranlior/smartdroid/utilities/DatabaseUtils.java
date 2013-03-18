/**
 * 
 */
package com.ranlior.smartdroid.utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.ranlior.smartdroid.model.database.DatabaseHelper;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public abstract class DatabaseUtils {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "DatabaseUtils";
	
	/**
	 * @param context
	 * @param tablesSuffix
	 * @return
	 */
	public static List<String> getTableNames(Context context, String tablesSuffix) {
		List<String> tableNames = new ArrayList<String>();
		
		DatabaseManager databaseManager = DatabaseManager.getInstance(context);
		DatabaseHelper databaseHelper = databaseManager.getDatabaseHelper();
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		db.beginTransaction();
		
		try {
			String sql = "SELECT name FROM sqlite_master WHERE name like '%_?'";
			String[] selectionArgs = new String[] {tablesSuffix};
			Cursor cursor = db.rawQuery(sql, selectionArgs);
			while (cursor.moveToNext()) {
				tableNames.add(cursor.getString(1));
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		
		for (String tableName : tableNames) {
			Log.d(TAG, "Table Name: " + tableName);
		}
		
		return tableNames;
	}
	
//	public static ForeignCollection<Trigger> getTriggers(Rule rule) {
//		ForeignCollection<Trigger> triggers = null;
//		
//		DatabaseManager databaseManager = DatabaseManager.getInstance(rule.getContext());
//		DatabaseHelper databaseHelper = databaseManager.getDatabaseHelper();
//		SQLiteDatabase db = databaseHelper.getReadableDatabase();
//		db.beginTransaction();
//		
//		triggers = rule.getTriggers();
//		CloseableIterator<Trigger> triggersIt = triggers.closeableIterator();
//		try {
//			Log.d(TAG, "TRIGGERS:");
//			while (triggersIt.hasNext()) {
//				Trigger trigger = triggersIt.next();
//				Log.d(TAG , "Trigger Name: " + trigger.getName());
//			}
//			triggersIt.close();
//			db.setTransactionSuccessful();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			db.endTransaction();
//		}
//	}

}
