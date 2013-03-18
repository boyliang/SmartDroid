/**
 * 
 */
package com.ranlior.smartdroid.model.dto.rules;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.DatabaseHelper;
import com.ranlior.smartdroid.model.database.DatabaseManager;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email: ran.haveshush.shenkar@gmail.com
 * 
 */
@DatabaseTable(tableName = "rules")
public class Rule {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "Rule";
	
	/**
	 * Holds the context that instantiate this rule.
	 */
	protected Context context = null;

	/**
	 * Holds the rule's identifier.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_ID, generatedId = true)
	private long id = -1L;

	/**
	 * Holds the rule's name.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_NAME, canBeNull = false)
	private String name = null;

	/**
	 * Holds the rule's description.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_DESCRIPTION, canBeNull = false)
	private String description = null;

	/**
	 * Holds the rule's satisfaction status.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED, canBeNull = false)
	private boolean isSatisfied = false;

	/**
	 * Holds all the triggers of the rule.
	 */
	@ForeignCollectionField
	private ForeignCollection<Trigger> triggers = null;

	/**
	 * Holds all the actions of the rule.
	 */
	@ForeignCollectionField
	private ForeignCollection<Action> actions = null;

	/**
	 * Default constructor.
	 */
	protected Rule() {
		super();
	}

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param name
	 * @param description
	 */
	public Rule(Context context, String name, String description) {
		super();
		this.context = context;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the mContext
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param mContext the mContext to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the triggers
	 */
	public ForeignCollection<Trigger> getTriggers() {
		DatabaseManager databaseManager = DatabaseManager.getInstance(context);
		DatabaseHelper databaseHelper = databaseManager.getDatabaseHelper();
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		db.beginTransaction();
		
		CloseableIterator<Trigger> triggersIt = triggers.closeableIterator();
		try {
			Log.d(TAG , "TRIGGERS:");
			while (triggersIt.hasNext()) {
				Trigger trigger = triggersIt.next();
				Log.d(TAG , "Trigger Name: " + trigger.getName());
			}
			triggersIt.close();
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return triggers;
	}

	/**
	 * @param triggers
	 *            the triggers to set
	 */
	public void setTriggers(ForeignCollection<Trigger> triggers) {
		this.triggers = triggers;
	}

	/**
	 * @return the actions
	 */
	public ForeignCollection<Action> getActions() {
		DatabaseManager databaseManager = DatabaseManager.getInstance(context);
		DatabaseHelper databaseHelper = databaseManager.getDatabaseHelper();
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		db.beginTransaction();
		
		CloseableIterator<Action> actionsIt = actions.closeableIterator();
		try {
			Log.d(TAG , "ACTIONS:");
			while (actionsIt.hasNext()) {
				Action action = actionsIt.next();
				Log.d(TAG , "Action Name: " + action.getName());
			}
			actionsIt.close();
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return actions;
	}

	/**
	 * @param actions
	 *            the actions to set
	 */
	public void setActions(ForeignCollection<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Checks that all the triggers in this rule satisfied.
	 */
	public boolean isSatisfied() {
		setSatisfied(true);
		for (Trigger trigger : triggers) {
			if (!trigger.isSatisfied()) {
				setSatisfied(false);
				break;
			}
		}
		return isSatisfied;
	}

	/**
	 * @param isSatisfied
	 *            the isSatisfied to set
	 */
	public void setSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	/**
	 * Registers all triggers.
	 */
	public void register() {
		for (Trigger trigger : triggers) {
			trigger.register();
		}
	}

	/**
	 * Performs all the action in this rule.
	 */
	public void perform() {
		for (Action action : actions) {
			action.perform();
		}
	}

}
