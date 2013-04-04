/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.MainActivity;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "notification_actions")
public class NotificationAction extends Action {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "NotificationAction";
	
	/**
	 * The action's name. 
	 */
	private static final String NAME = "Notification";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Fires a notification";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "notification_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name notification title.
	 * 
	 * <P>Type: STRING</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_TITLE = "title";
	
	/**
	 * Column name notification text.
	 * 
	 * <P>Type: STRING</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_TEXT = "text";
	
	/**
	 * Column name notification defaults.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_DEFAULTS = "defaults";
	
	/**
	 * Column name notification flags.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_FLAGS = "flags";
	
	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the notification's title.
	 */
	@DatabaseField(columnName = NotificationAction.COLUMN_NAME_TITLE, canBeNull = false)
	private String title = null;
	
	/**
	 * Holds the notification's text.
	 */
	@DatabaseField(columnName = NotificationAction.COLUMN_NAME_TEXT, canBeNull = false)
	private String text = null;
	
	/**
	 * Integer represents the notification use defaults.
	 * 
	 * Use all: Notification.DEFAULT_ALL
	 * Use lights: Notification.DEFAULT_LIGHTS
	 * Use sound: Notification.DEFAULT_SOUND
	 * Use vibrate: Notification.DEFAULT_VIBRATE
	 * 
	 * For notification defaults info:
	 * @see	android.app.Notification
	 * 
	 */
	@DatabaseField(columnName = NotificationAction.COLUMN_NAME_DEFAULTS, canBeNull = false)
	private int defaults;

	/**
	 * Integer represents the notification flags.
	 * 
	 * For notification flags info:
	 * @see	android.app.Notification
	 * 
	 */
	@DatabaseField(columnName = NotificationAction.COLUMN_NAME_FLAGS, canBeNull = false)
	private int flags;

	
	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	public NotificationAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 * @param title			String represents notification's title
	 * @param text			String represents notification's text
	 * @param flags			Integer represents notification's flags
	 */
	public NotificationAction(Context context, Rule rule, String title, String text, int defaults, int flags) {
		super(context, rule, NotificationAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.title = title;
		this.text = text;
		this.defaults = defaults;
		this.flags = flags;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the defaults
	 */
	public int getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(int defaults) {
		this.defaults = defaults;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	// FIXME: create notification by API level 
	// for supporting older and newer notification styles.
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");

		Intent intent = new Intent(context, MainActivity.class);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(
				context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Constructs the notification
		Notification notification = new Notification(R.drawable.ic_launcher, title, System.currentTimeMillis());
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notification.defaults |= defaults;
		notification.flags |= flags;
		
		NotificationManager notificationManager = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify((int)getId(), notification);
	}

}
