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
@DatabaseTable(tableName="notification_actions")
public class NotificationAction extends Action {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "NotificationAction";
	
	/**
	 * Holds the notification's title.
	 */
	@DatabaseField
	private String title = null;
	
	/**
	 * Holds the notification's text.
	 */
	@DatabaseField
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
	@DatabaseField
	private int defaults;

	/**
	 * Integer represents the notification flags.
	 * 
	 * For notification flags info:
	 * @see	android.app.Notification
	 * 
	 */
	@DatabaseField
	private int flags;

	
	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected NotificationAction() {
		super();
	}
	
	/**
	 * Minimal constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param name			String represents action's name
	 * @param description	String represents action's description
	 */
	public NotificationAction(Context context, Rule rule, String name, String description) {
		super(context, rule, name, description);
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
	public NotificationAction(Context context, Rule rule, String name, String description,
			String title, String text, int defaults, int flags) {
		super(context, rule, name, description);
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

		Intent intent = new Intent(mContext, MainActivity.class);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(
				mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Constructs the notification
		Notification notification = new Notification(R.drawable.ic_launcher, title, System.currentTimeMillis());
		notification.setLatestEventInfo(mContext, title, text, pendingIntent);
		notification.defaults |= defaults;
		notification.flags |= flags;
		
		NotificationManager notificationManager = 
				(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify((int)getId(), notification);
	}

}
