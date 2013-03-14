/**
 * 
 */
package com.ranlior.smartdroid.model.contentproviders;

import java.util.HashMap;

import com.ranlior.smartdroid.config.SmartDroid;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SmartProvider extends ContentProvider {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "SmartProvider";

	/*
	 * Constants used by the Uri matcher to choose an action based on the
	 * pattern of the incoming URI
	 */
	
	/**
	 * The incoming URI matches the Rules URI pattern.
	 */
	private static final int RULES = 1;

	/**
	 * The incoming URI matches the Rule ID URI pattern.
	 */
	private static final int RULE_ID = 2;

	/**
	 * The incoming URI matches the Triggers URI pattern.
	 */
	private static final int TRIGGERS = 3;

	/**
	 * The incoming URI matches the Trigger ID URI pattern.
	 */
	private static final int TRIGGER_ID = 4;
	
	/**
	 * The incoming URI matches the Actions URI pattern.
	 */
	private static final int ACTIONS = 5;

	/**
	 * The incoming URI matches the Action ID URI pattern.
	 */
	private static final int ACTION_ID = 6;

	/**
	 * A UriMatcher instance.
	 */
	private static final UriMatcher uriMatcher;
	
	/**
	 * A projection map used to select columns from the database.
	 */
	private static HashMap<String, String> rulesProjectionMap;

	/**
	 * A projection map used to select columns from the database.
	 */
	private static HashMap<String, String> triggersProjectionMap;
	
	/**
	 * A projection map used to select columns from the database.
	 */
	private static HashMap<String, String> actionsProjectionMap;

	/**
	 * A block that instantiates and sets static objects.
	 */
	static {
		/*
		 * Creates and initializes the URI matcher.
		 */

		// Creates a new instance
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		// Adds patterns that routes URIs.
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Rules.TABLE_NAME_RULES , RULES);
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Rules.TABLE_NAME_RULES + "/#", RULE_ID);
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Triggers.TABLE_NAME_TRIGGERS , TRIGGERS);
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Triggers.TABLE_NAME_TRIGGERS + "/#", TRIGGER_ID);
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Actions.TABLE_NAME_ACTIONS , ACTIONS);
		uriMatcher.addURI(SmartDroid.SmartProvider.AUTHORITY, SmartDroid.Actions.TABLE_NAME_ACTIONS + "/#", ACTION_ID);

		/*
		 * Creates and initializes a projection map that returns all columns.
		 */
		
		// Creates a new projection map instance. The map returns a column name
		// given a string. The two are usually equal.
		rulesProjectionMap = new HashMap<String, String>();

		// Maps all rules table columns
		rulesProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_ID, SmartDroid.Rules.COLUMN_NAME_ID);
		rulesProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_NAME, SmartDroid.Rules.COLUMN_NAME_NAME);
		rulesProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_DESCRIPTION, SmartDroid.Rules.COLUMN_NAME_DESCRIPTION);
		rulesProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED, SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED);

		// Creates a new projection map instance. The map returns a column name
		// given a string. The two are usually equal.
		triggersProjectionMap = new HashMap<String, String>();
		
		// Maps all triggers table columns
		triggersProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_ID, SmartDroid.Rules.COLUMN_NAME_ID);
		triggersProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_NAME, SmartDroid.Rules.COLUMN_NAME_NAME);
		triggersProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_DESCRIPTION, SmartDroid.Rules.COLUMN_NAME_DESCRIPTION);
		triggersProjectionMap.put(SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED, SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED);
		
		// Creates a new projection map instance. The map returns a column name
		// given a string. The two are usually equal.
		actionsProjectionMap = new HashMap<String, String>();
		
		// Maps all actions table columns
		actionsProjectionMap.put(SmartDroid.Actions.COLUMN_NAME_ID, SmartDroid.Actions.COLUMN_NAME_ID);
		actionsProjectionMap.put(SmartDroid.Actions.COLUMN_NAME_NAME, SmartDroid.Actions.COLUMN_NAME_NAME);
		actionsProjectionMap.put(SmartDroid.Actions.COLUMN_NAME_DESCRIPTION, SmartDroid.Actions.COLUMN_NAME_DESCRIPTION);
	}

	/**
	 * Handle a new DatabaseHelper.
	 */
	private DatabaseHelper databaseHelper = null;
	
	/**
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		/**
		 * The database that the provider uses as its underlying data store.
		 */
		public static final String DATABASE_NAME = "smart.db";

		/**
		 * The database version.
		 */
		public static final int DATABASE_VERSION = 1;
		
		/**
		 * Creates table rules SQL.
		 */
		private static final String CREATE_TABLE_RULES =
				"CREATE TABLE " + SmartDroid.Rules.TABLE_NAME_RULES + "("
				+ SmartDroid.Rules.COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, "
				+ SmartDroid.Rules.COLUMN_NAME_NAME + " TEXT NOT NULL, "
				+ SmartDroid.Rules.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, "
				+ SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED + " INTEGER NOT NULL);";
		
		/**
		 * Creates table triggers SQL.
		 */
		private static final String CREATE_TABLE_TRIGGERS = 
				"CREATE TABLE " + SmartDroid.Triggers.TABLE_NAME_TRIGGERS + "("
				+ SmartDroid.Triggers.COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, "
				+ SmartDroid.Triggers.COLUMN_NAME_NAME + " TEXT NOT NULL, "
				+ SmartDroid.Triggers.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, "
				+ SmartDroid.Triggers.COLUMN_NAME_IS_SATISFIED + " INTEGER NOT NULL, "
				+ SmartDroid.Triggers.COLUMN_NAME_RULE_ID + " INTEGER NOT NULL, "
				+ "FOREIGN KEY(" + SmartDroid.Triggers.COLUMN_NAME_RULE_ID + ") "
				+ "REFERENCES " + SmartDroid.Rules.TABLE_NAME_RULES + "(" + SmartDroid.Rules.COLUMN_NAME_ID + "));";
		
		/**
		 * Creates table actions SQL.
		 */
		private static final String CREATE_TABLE_ACTIONS = 
				"CREATE TABLE " + SmartDroid.Actions.TABLE_NAME_ACTIONS + "("
				+ SmartDroid.Actions.COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, "
				+ SmartDroid.Actions.COLUMN_NAME_NAME + " TEXT NOT NULL, "
				+ SmartDroid.Actions.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, "
				+ SmartDroid.Actions.COLUMN_NAME_RULE_ID + " INTEGER NOT NULL, "
				+ "FOREIGN KEY(" + SmartDroid.Actions.COLUMN_NAME_RULE_ID + ") "
				+ "REFERENCES " + SmartDroid.Rules.TABLE_NAME_RULES + "(" + SmartDroid.Rules.COLUMN_NAME_ID + "));";
				
		
		/**
		 * Drops table rules SQL.
		 */
		private static final String DROP_TABLE_RULES = 
				"DROP TABLE IF EXISTS " + SmartDroid.Rules.TABLE_NAME_RULES;
		
		/**
		 * Drops table triggers SQL.
		 */
		private static final String DROP_TABLE_TRIGGERS = 
				"DROP TABLE IF EXISTS " + SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
		
		/**
		 * Drops table actions SQL.
		 */
		private static final String DROP_TABLE_ACTIONS = 
				"DROP TABLE IF EXISTS " + SmartDroid.Actions.TABLE_NAME_ACTIONS;


		/**
		 * Full constructor.
		 * 
		 * @param context
		 */
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			// Creates the rules table
			db.execSQL(CREATE_TABLE_RULES);
			// Creates the triggers table
			db.execSQL(CREATE_TABLE_TRIGGERS);
			// Creates the actions table
			db.execSQL(CREATE_TABLE_ACTIONS);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Logs that the database is being upgraded
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			// Drops the table actions and existing data
			db.execSQL(DROP_TABLE_ACTIONS);
			
			// Drops the table triggers and existing data
			db.execSQL(DROP_TABLE_TRIGGERS);

			// Drops the table rules and existing data
			db.execSQL(DROP_TABLE_RULES);

			// Recreates the database with a new version
			onCreate(db);
		}

	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		// Creates a new helper object. Note that the database itself isn't opened until
		// something tries to access it, and it's only created if it doesn't already exist.
		databaseHelper = new DatabaseHelper(getContext());

		// Assumes that any failures will be reported by a thrown exception.
		return true;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Logger
		Log.d(TAG, "query(" + uri + ",...");
		
		// Constructs a new query builder and sets its table name and projection
		// Based on the incoming URI pattern
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy;
		
		switch (uriMatcher.match(uri)) {
		case RULES:
			qb.setTables(SmartDroid.Rules.TABLE_NAME_RULES);
			qb.setProjectionMap(rulesProjectionMap);
			orderBy = SmartDroid.Rules.DEFAULT_SORT_ORDER;
			break;
		case TRIGGERS:
			qb.setTables(SmartDroid.Triggers.TABLE_NAME_TRIGGERS);
			qb.setProjectionMap(triggersProjectionMap);
			orderBy = SmartDroid.Triggers.DEFAULT_SORT_ORDER;
			break;
		case ACTIONS:
			qb.setTables(SmartDroid.Actions.TABLE_NAME_ACTIONS);
			qb.setProjectionMap(actionsProjectionMap);
			orderBy = SmartDroid.Actions.DEFAULT_SORT_ORDER;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// If sort order is specified, uses the incoming sort order
		if (!TextUtils.isEmpty(sortOrder)) {
			orderBy = sortOrder;
		}

		// Opens the database object in "read" mode,
		// since no writes need to be done.
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		/*
		 * Performs the query. If no problems occur trying to read the database,
		 * then a Cursor object is returned; otherwise, the cursor variable
		 * contains null. If no records were selected, then the Cursor object is
		 * empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(db, // The database to query
				projection, // The columns to return from the query
				selection, // The columns for the where clause
				selectionArgs, // The values for the where clause
				null, // don't group the rows
				null, // don't filter by row groups
				orderBy // The sort order
				);

		// Tells the Cursor what URI to watch,
		// so it knows when its source data changes.
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		// Logger
		Log.d(TAG, "getType(" + uri + ",...");
		
		// Chooses the MIME type based on the incoming URI pattern
		switch (uriMatcher.match(uri)) {
		case RULES:
			return SmartDroid.Rules.CONTENT_TYPE;
		case RULE_ID:
			return SmartDroid.Rules.CONTENT_ITEM_TYPE;
		case TRIGGERS:
			return SmartDroid.Triggers.CONTENT_TYPE;
		case TRIGGER_ID:
			return SmartDroid.Triggers.CONTENT_ITEM_TYPE;
		case ACTIONS:
			return SmartDroid.Actions.CONTENT_TYPE;
		case ACTION_ID:
			return SmartDroid.Actions.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Logger
		Log.d(TAG, "insert(" + uri + ",...)");
		
		String table = null;
		Uri baseUri = null;
		
		// Chooses the table and base uri based on the incoming URI pattern
		switch (uriMatcher.match(uri)) {
		case RULES:
			table = SmartDroid.Rules.TABLE_NAME_RULES;
			baseUri = SmartDroid.Rules.CONTENT_ID_URI_BASE;
			break;
		case TRIGGERS:
			table = SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
			baseUri = SmartDroid.Triggers.CONTENT_ID_URI_BASE;
			break;
		case ACTIONS:
			table = SmartDroid.Actions.TABLE_NAME_ACTIONS;
			baseUri = SmartDroid.Actions.CONTENT_ID_URI_BASE;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Validates the content values
		if (initialValues == null) {
			return null;
		}

		// Opens the database object in "read/write" mode, 
		// since writes need to be done.
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		long insertedRowId = db.insert(table, null, initialValues);

		// If insert failed, throw exception
		if (insertedRowId == -1) {
			throw new SQLException("Failed insert row into " + uri);
		}

		// If insert succedded return the new URI
		Uri insertedUri = ContentUris.withAppendedId(baseUri, insertedRowId);

		getContext().getContentResolver().notifyChange(insertedUri, null);

		return insertedUri;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String initialWhereClause, String[] whereArgs) {
		// Logger
		Log.d(TAG, "delete(" + uri + ",...)");

		String table = null;
		String finalWhereClause = null;

		// Edits the where clause based on the incoming URI pattern
		switch (uriMatcher.match(uri)) {
		case RULES:
			table = SmartDroid.Rules.TABLE_NAME_RULES;
			finalWhereClause = initialWhereClause;
			break;
		case RULE_ID:
			table = SmartDroid.Rules.TABLE_NAME_RULES;
			finalWhereClause = SmartDroid.Rules.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		case TRIGGERS:
			table = SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
			finalWhereClause = initialWhereClause;
			break;
		case TRIGGER_ID:
			table = SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
			finalWhereClause = SmartDroid.Triggers.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		case ACTIONS:
			table = SmartDroid.Actions.TABLE_NAME_ACTIONS;
			finalWhereClause = initialWhereClause;
			break;
		case ACTION_ID:
			table = SmartDroid.Actions.TABLE_NAME_ACTIONS;
			finalWhereClause = SmartDroid.Actions.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Opens the database object in "read/write" mode, 
		// since writes need to be done.
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		int numOfRowsAffected = db.delete(table, finalWhereClause, whereArgs);

		getContext().getContentResolver().notifyChange(uri, null);

		return numOfRowsAffected;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String initialWhereClause, String[] whereArgs) {
		Log.d(TAG, "update(" + uri + ",...)");
		
		String table = null;
		String finalWhereClause = null;
		
		// Edits the where clause based on the incoming URI pattern
		switch (uriMatcher.match(uri)) {
		case RULES:
			table = SmartDroid.Rules.TABLE_NAME_RULES;
			finalWhereClause = initialWhereClause;
			break;
		case RULE_ID:
			table = SmartDroid.Rules.TABLE_NAME_RULES;
			finalWhereClause = SmartDroid.Rules.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		case TRIGGERS:
			table = SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
			finalWhereClause = initialWhereClause;
			break;
		case TRIGGER_ID:
			table = SmartDroid.Triggers.TABLE_NAME_TRIGGERS;
			finalWhereClause = SmartDroid.Triggers.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		case ACTIONS:
			table = SmartDroid.Actions.TABLE_NAME_ACTIONS;
			finalWhereClause = initialWhereClause;
			break;
		case ACTION_ID:
			table = SmartDroid.Actions.TABLE_NAME_ACTIONS;
			finalWhereClause = SmartDroid.Actions.COLUMN_NAME_ID + "=" + ContentUris.parseId(uri);
			if (initialWhereClause != null) {
				finalWhereClause = finalWhereClause + " AND " + initialWhereClause;
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		// Opens the database object in "read/write" mode, 
		// since writes need to be done.
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		
		int numOfRowsAffected = db.update(table, values, finalWhereClause, whereArgs);
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return numOfRowsAffected;
	}

}
