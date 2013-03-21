/**
 * 
 */
package com.ranlior.smartdroid.config;

import android.provider.BaseColumns;

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
	public static final String PACKAGE = "com.ranlior.smartdroid";
	
	/**
	 * Preferences.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Pref {
		
		// This class cannot be instantiated
		private Pref() {}
		
		/**
		 * Preferences authentication info file name.
		 */
		// FIXME: Example only.
		public static final String PREFS_FILE_AUTH = "prefsFileAuth";
		
	}
	
	/**
	 * Intent actions contact.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Action {
		
		// This class cannot be instantiated
		private Action() {}
		
		/**
		 * Action to get a location reminder broadcast.
		 */
		// FIXME: Example only.
		public static final String ACTION_LOCATION_REMINDER_BROADCAST = PACKAGE + ".ACTION_LOCATION_REMINDER_BROADCAST";
		
	}
	
	/**
	 * Intent Extras contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Extra {
		
		// This class cannot be instantiated
		private Extra() {}
		
		/**
		 * Extra key for task's client id.
		 * <P>Type: long</P>
		 */
		// FIXME: Example only.
		public static final String EXTRA_TASK_CLIENT_ID = PACKAGE + ".EXTRA_TASK_CLIENT_ID";
		
	}
	
	/**
	 * Fragment Arguments contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class Arg {
		
		// This class cannot be instantiated
		private Arg() {}
		
		/**
		 * Argument key for the account picker dialog title.
		 * <P>Type: int</P>
		 */
		// FIXME: Example only.
		public static final String ARG_ACCOUNT_PICKER_DIALOG_TITLE = PACKAGE + ".ARG_ACCOUT_PICKER_DIALOG_TITLE";
	}
	
	/*
	 * 
	 *	CONTENT PROVIDER: Tables contracts.
	 * 
	 */
	
	/**
	 * Provider contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 *
	 */
	public static final class SmartProvider {
		
		// This class cannot be instantiated
		private SmartProvider() {}
		
		/**
		 * The authority.
		 */
		public static final String AUTHORITY = PACKAGE + ".contentproviders.SmartProvider";
		
	}
	
	/**
	 * Rules table contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 */
	public static final class Rules implements BaseColumns {
		
		// This class cannot be instantiated
		private Rules() {}
		
		/**
		 * The rules table name offered by this provider.
		 */
		public static final String TABLE_NAME_RULES = "rules";
		
		/*
         * Column definitions.
         */
		
		/**
		 * Column name rule's id.
		 * Rule identifier.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_ID = "_id";
		
		/**
		 * Column name rule's name.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_NAME = "name";
		
		/**
		 * Column name rule's description.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		
		/**
		 * Column name rule's satisfaction status.
		 * If satisfied then 1 (=true), else 0 (=false).
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_IS_SATISFIED = "isSatisfied";
		
		/**
		 * Column name rule's not satisfied triggers count.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_NOT_SATISFIED_TRIGGERS_COUNT = "notSatisfiedTriggersCount";
		
		/*
		 * Column values.
		 */
		
		/**
		 * Column value false as integer 0.
		 * 
		 * <P>Type: INTEGER</P>
		 */
		public static final int COLUMN_VALUE_FALSE = 0;
		
		/**
		 * Column value true as integer 1.
		 * 
		 * <P>Type: INTEGER</P>
		 */
		public static final int COLUMN_VALUE_TRUE = 1;
        
	}
	
	/**
	 * Triggers table contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 */
	public static final class Triggers implements BaseColumns {
		
		// This class cannot be instantiated
		private Triggers() {}
		
		/**
		 * The triggers table name offered by this provider.
		 */
		public static final String TABLE_NAME_TRIGGERS = "triggers";
		
		/*
         * Column definitions.
         */
		
		/**
		 * Column name trigger's id.
		 * Trigger identifier.
		 * 
		 * <P>Type: INTEGER</P>
 		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_ID = "_id";
		
		/**
		 * Column name trigger's name.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_NAME = "name";
		
		/**
		 * Column name trigger's description.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		
		/**
		 * Column name trigger's satisfaction status.
		 * If satisfied then 1 (=true), else 0 (=false).
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_IS_SATISFIED = "isSatisfied";
		
		/**
		 * Column trigger's class name.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_CLASS_NAME = "className";
		
		/**
		 * Column name trigger's rule id.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: FOREIGN KEY</p>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_RULE_ID = "rule_id";		
		
		/*
		 * Column values.
		 */
		
		/**
		 * Column value false as integer 0.
		 * 
		 * <P>Type: INTEGER</P>
		 */
		public static final int COLUMN_VALUE_FALSE = 0;
		
		/**
		 * Column value true as integer 1.
		 * 
		 * <P>Type: INTEGER</P>
		 */
		public static final int COLUMN_VALUE_TRUE = 1;
        
	}
	
	/**
	 * Actions table contract.
	 * 
	 * @author Ran Haveshush
	 * Email:  ran.haveshush.shenkar@gmail.com
	 */
	public static final class Actions implements BaseColumns {
		
		// This class cannot be instantiated
		private Actions() {}
		
		/**
		 * The actions table name offered by this provider.
		 */
		public static final String TABLE_NAME_ACTIONS = "actions";
		
		/*
         * Column definitions.
         */
		
		/**
		 * Column name action's id.
		 * Action identifier.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_ID = "_id";
		
		/**
		 * Column name action's name.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_NAME = "name";
		
		/**
		 * Column name action's description.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		
		/**
		 * Column action's class name.
		 * 
		 * <P>Type: TEXT</P>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_CLASS_NAME = "className";
		
		/**
		 * Column name action's rule id.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: FOREIGN KEY</p>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_RULE_ID = "rule_id";
        
	}

}
