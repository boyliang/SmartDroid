/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.SplashScreen;
import com.ranlior.smartdroid.activities.actions.editors.NotificationActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class NotificationAction extends Action {

	private static final String TAG = NotificationAction.class.getSimpleName();

	private static final String NAME = "Notification";

	private static final String DESCRIPTION = "Fires a notification";

	private static final int ICON = R.drawable.ic_list_notification;

	/**
	 * Holds the notification's title.
	 */
	private String title = null;

	/**
	 * Holds the notification's text.
	 */
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
	 * 
	 * @see android.app.Notification
	 * 
	 */
	private int defaults;

	/**
	 * Integer represents the notification flags.
	 * 
	 * For notification flags info:
	 * 
	 * @see android.app.Notification
	 * 
	 */
	private int flags;

	public NotificationAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param title
	 *            String represents notification's title
	 * @param text
	 *            String represents notification's text
	 * @param flags
	 *            Integer represents notification's flags
	 */
	public NotificationAction(String title, String text, int defaults, int flags) {
		super(NAME, DESCRIPTION);
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
	 * @param title
	 *            the title to set
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
	 * @param text
	 *            the text to set
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
	 * @param defaults
	 *            the defaults to set
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
	 * @param flags
	 *            the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dto.actions.Action#perform(android.content
	 * .Context)
	 */
	@Override
	// FIXME: create notification by API level
	// for supporting older and newer notification styles.
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		Intent intent = new Intent(context, SplashScreen.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Constructs the notification
		Notification notification = new Notification(R.drawable.ic_launcher, title, System.currentTimeMillis());
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notification.defaults |= defaults;
		notification.flags |= flags;

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// FIXME: check if the id need to be changed.
		notificationManager.notify(0, notification);
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putString("title", title);
		extras.putString("text", text);
		extras.putInt("defaults", defaults);
		extras.putInt("flags", flags);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setTitle(extras.getString("title"));
		setText(extras.getString("text"));
		setDefaults(extras.getInt("defaults"));
		setFlags(extras.getInt("flags"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return NotificationActionEditorActivity.class;
	}

}
