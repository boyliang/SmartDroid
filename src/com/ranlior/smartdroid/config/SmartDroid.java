/**
 * 
 */
package com.ranlior.smartdroid.config;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public final class SmartDroid {
	
	// This class cannot be instantiated
	private SmartDroid() {}
	
	/**
	 * App's package.
	 */
	public static final String PACKAGE = "il.ac.shenkar.todo";
	
	/**
	 * Preferences.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Prefs {
		
		// This class cannot be instantiated
		private Prefs() {}
		
		/**
		 * Preferences authentication info file name.
		 */
		public static final String PREFS_FILE_AUTH = "prefsFileAuth";
		
		/**
		 * Preference for the google account name to sync Google Tasks with.
		 */
		public static final String PREF_ACCOUNT_NAME = "prefAccountName";
		
		/**
		 * Preference for the auth token to Google Tasks Service.
		 */
		public static final String PREF_AUTH_TOKEN = "prefAuthToken";
		
	}
	
	/**
	 * Intent actions contact.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Actions {
		
		// This class cannot be instantiated
		private Actions() {}
		
		/**
		 * Action for the user to select Google account to sync with.
		 */
		public static final String ACTION_SELECT_ACCOUNT = "il.ac.shenkar.todo.ACTION_SELECT_ACCOUNT";
		
		public static final String ACTION_VIEW_TASK = "il.ac.shenkar.todo.ACTION_VIEW_TASK";
		public static final String ACTION_EDIT_TASK = "il.ac.shenkar.todo.ACTION_EDIT_TASK";
		
		/**
		 * Action to get a datetime reminder broadcast.
		 */
		public static final String ACTION_DATETIME_REMINDER_BROADCAST = "il.ac.shenkar.todo.ACTION_DATETIME_REMINDER_BROADCAST";
		
		/**
		 * Action to get a location reminder broadcast.
		 */
		public static final String ACTION_LOCATION_REMINDER_BROADCAST = "il.ac.shenkar.todo.ACTION_LOCATION_REMINDER_BROADCAST";
		
	}
	
	/**
	 * Intent Extras contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Extras {
		
		// This class cannot be instantiated
		private Extras() {}
		
		/**
		 * Extra key for task's client id.
		 * <P>Type: long</P>
		 */
		public static final String EXTRA_TASK_CLIENT_ID = "il.ac.shenkar.todo.EXTRA_TASK_CLIENT_ID";
		
		/**
		 * Extra key for task list client id.
		 * <P>Type: long</P>
		 */
		public static final String EXTRA_TASK_LIST_CLIENT_ID = "il.ac.shenkar.todo.EXTRA_TASK_LIST_CLIENT_ID";
		
		/**
		 * Extra key for task's title.
		 * <P>Type: String</P>
		 */
		public static final String EXTRA_TASK_TITLE = "il.ac.shenkar.todo.EXTRA_TASK_TITLE";
		
		/**
		 * Extra key for task's notes.
		 * <P>Type: String</P>
		 */
		public static final String EXTRA_TASK_NOTES = "il.ac.shenkar.todo.EXTRA_TASK_NOTES";
		
		/**
		 * Extra key for reminder's id.
		 * <P>Type: int</P>
		 */
		public static final String EXTRA_REMINDER_ID = "il.ac.shenkar.todo.EXTRA_REMINDER_ID";
		
		/**
		 * Extra key for task's location reminder address.
		 * <P>Type: String</P>
		 */
		public static final String EXTRA_TASK_LOCATION_REMINDER_ADDRESS = "il.ac.shenkar.todo.EXTRA_TASK_LOCATION_REMINDER_ADDRESS";
		
		/**
		 * Extra key for indicating if the geo-fancing notification fires when
		 * Entering the proximity (true) or exiting the proximity (false).
		 * <P>Type: boolean</P>
		 */
		public static final String EXTRA_IS_PROXIMITY_ENTERING = "il.ac.shenkar.todo.EXTRA_IS_PROXIMITY_ENTERING";
		
	}
	
	/**
	 * Fragment Arguments contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Args {
		
		// This class cannot be instantiated
		private Args() {}
		
		/**
		 * Argument key for the account picker dialog title.
		 * <P>Type: int</P>
		 */
		public static final String ARG_ACCOUNT_PICKER_DIALOG_TITLE = "il.ac.shenkar.todo.ARG_ACCOUT_PICKER_DIALOG_TITLE";
		
		/**
		 * Argument key for the list client identifier.
		 * <P>Type: String</P>
		 */
		public static final String ARG_TASK_LIST_CLIENT_ID = "il.ac.shenkar.todo.ARG_TASK_LIST_CLIENT_ID";
	}

}
