/**
 * 
 */
package com.ranlior.smartdroid.config;

import android.net.Uri;
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
        
        /*
         * Column positions.
         */
        
        /**
         * Column position for rule's id.
         */
        public static final int COLUMN_POSITION_ID = 0;
        
        /**
         * Column position for rule's name.
         */
        public static final int COLUMN_POSITION_NAME = 1;
        
        /**
         * Column position for rule's description.
         */
        public static final int COLUMN_POSITION_DESCRIPTION = 2;
        
        /**
         * Column position for rule's satisfaction status.
         */
        public static final int COLUMN_POSITION_IS_SATISFIED = 3;
        
        /*
         * Column defaults content.
         */
		
		/*
		 * URI definitions.
		 */
		
		/**
		 * The scheme part for this provider's URI.
		 */
		public static final String SCHEME = "content://";
		
		/*
		 * Path parts for the URIs.
		 */
		
		/**
		 * Path part for the rule lists URI.
		 */
		public static final String PATH_RULES = "/" + TABLE_NAME_RULES;
		
		/**
		 * Path part for the rule list ID URI.
		 */
		public static final String PATH_RULE_ID = "/" + TABLE_NAME_RULES + "/";
		
		/**
         * 0-relative position of a rule ID segment in the path part of a RULE ID URI.
         */
        public static final int RULE_ID_PATH_POSITION = 1;
		
		/**
		 * The content:// style URL for this table.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_RULES);
		
		/**
		 * The content URI base for a single rule. Callers must
         * append a numeric rule id to this Uri to retrieve a rule.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_RULE_ID);
		
		/**
		 * The content URI match pattern for a single rule, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
		 */
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_RULE_ID + "/#");
		
		/*
         * MIME type definitions.
         */
		
        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of rules.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/List<"+PACKAGE+".model.dto.rules.Rule>";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single rule.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"+PACKAGE+".model.dto.rules.Rule>";
        
        /*
         * Sort Order definitions.
         */
        
        /**
         * Order by rule list's name.
         */
        public static final String SORT_BY_NAME = COLUMN_NAME_NAME + " ASC";

        /**
         * The default sort order for this table.
         */
        public static final String DEFAULT_SORT_ORDER = SORT_BY_NAME;
        
        /*
         * Projections.
         */
        
        /**
         * Creates a projection that returns the rule contents.
         */
        public static final String[] PROJECTION =
            new String[] {
                SmartDroid.Rules.COLUMN_NAME_ID,
                SmartDroid.Rules.COLUMN_NAME_NAME,
                SmartDroid.Rules.COLUMN_NAME_DESCRIPTION,
                SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED
        	};
        
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
        
        /*
         * Column positions.
         */
        
        /**
         * Column position for trigger's id.
         */
        public static final int COLUMN_POSITION_ID = 0;
        
        /**
         * Column position for trigger's name.
         */
        public static final int COLUMN_POSITION_NAME = 1;
        
        /**
         * Column position for trigger's description.
         */
        public static final int COLUMN_POSITION_DESCRIPTION = 2;
        
        /**
         * Column position for trigger's satisfaction status.
         */
        public static final int COLUMN_POSITION_IS_SATISFIED = 3;
        
        /**
         * Column position for trigger's rule id.
         */
        public static final int COLUMN_POSITION_RULE_ID = 4;
        
        /*
         * Column defaults content.
         */
		
		/*
		 * URI definitions.
		 */
		
		/**
		 * The scheme part for this provider's URI.
		 */
		public static final String SCHEME = "content://";
		
		/*
		 * Path parts for the URIs.
		 */
		
		/**
		 * Path part for the trigger lists URI.
		 */
		public static final String PATH_TRIGGERS = "/" + TABLE_NAME_TRIGGERS;
		
		/**
		 * Path part for the trigger list ID URI.
		 */
		public static final String PATH_TRIGGER_ID = "/" + TABLE_NAME_TRIGGERS + "/";
		
		/**
         * 0-relative position of a trigger ID segment in the path part of a TRIGGER ID URI.
         */
        public static final int TRIGGER_ID_PATH_POSITION = 1;
		
		/**
		 * The content:// style URL for this table.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_TRIGGERS);
		
		/**
		 * The content URI base for a single trigger. Callers must
         * append a numeric trigger id to this Uri to retrieve a trigger.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_TRIGGER_ID);
		
		/**
		 * The content URI match pattern for a single trigger, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
		 */
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_TRIGGER_ID + "/#");
		
		/*
         * MIME type definitions.
         */
		
        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of triggers.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/List<"+PACKAGE+".model.dto.triggers.Trigger>";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single trigger.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"+PACKAGE+".model.dto.triggers.Trigger>";
        
        /*
         * Sort Order definitions.
         */
        
        /**
         * Order by trigger list's name.
         */
        public static final String SORT_BY_NAME = COLUMN_NAME_NAME + " ASC";

        /**
         * The default sort order for this table.
         */
        public static final String DEFAULT_SORT_ORDER = SORT_BY_NAME;
        
        /*
         * Projections.
         */
        
        /**
         * Creates a projection that returns the trigger contents.
         */
        public static final String[] PROJECTION =
            new String[] {
                SmartDroid.Triggers.COLUMN_NAME_ID,
                SmartDroid.Triggers.COLUMN_NAME_NAME,
                SmartDroid.Triggers.COLUMN_NAME_DESCRIPTION,
                SmartDroid.Triggers.COLUMN_NAME_IS_SATISFIED,
                SmartDroid.Triggers.COLUMN_NAME_RULE_ID
        	};
        
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
		 * Column name action's rule id.
		 * 
		 * <P>Type: INTEGER</P>
		 * <P>Constraint: FOREIGN KEY</p>
		 * <P>Constraint: NOT NULL</p>
		 */
		public static final String COLUMN_NAME_RULE_ID = "rule_id";
		
		/*
		 * Column values.
		 */
        
        /*
         * Column positions.
         */
        
        /**
         * Column position for action's id.
         */
        public static final int COLUMN_POSITION_ID = 0;
        
        /**
         * Column position for action's name.
         */
        public static final int COLUMN_POSITION_NAME = 1;
        
        /**
         * Column position for action's description.
         */
        public static final int COLUMN_POSITION_DESCRIPTION = 2;
        
        /**
         * Column position for action's rule id.
         */
        public static final int COLUMN_POSITION_RULE_ID = 3;
        
        /*
         * Column defaults content.
         */
		
		/*
		 * URI definitions.
		 */
		
		/**
		 * The scheme part for this provider's URI.
		 */
		public static final String SCHEME = "content://";
		
		/*
		 * Path parts for the URIs.
		 */
		
		/**
		 * Path part for the action lists URI.
		 */
		public static final String PATH_ACTIONS = "/" + TABLE_NAME_ACTIONS;
		
		/**
		 * Path part for the action list ID URI.
		 */
		public static final String PATH_ACTION_ID = "/" + TABLE_NAME_ACTIONS + "/";
		
		/**
         * 0-relative position of a action ID segment in the path part of a ACTION ID URI.
         */
        public static final int ACTION_ID_PATH_POSITION = 1;
		
		/**
		 * The content:// style URL for this table.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_ACTIONS);
		
		/**
		 * The content URI base for a single action. Callers must
         * append a numeric action id to this Uri to retrieve a action.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_ACTION_ID);
		
		/**
		 * The content URI match pattern for a single action, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
		 */
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + SmartProvider.AUTHORITY + PATH_ACTION_ID + "/#");
		
		/*
         * MIME type definitions.
         */
		
        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of actions.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/List<"+PACKAGE+".model.dto.actions.Action>";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single action.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"+PACKAGE+".model.dto.actions.Action>";
        
        /*
         * Sort Order definitions.
         */
        
        /**
         * Order by action list's name.
         */
        public static final String SORT_BY_NAME = COLUMN_NAME_NAME + " ASC";

        /**
         * The default sort order for this table.
         */
        public static final String DEFAULT_SORT_ORDER = SORT_BY_NAME;
        
        /*
         * Projections.
         */
        
        /**
         * Creates a projection that returns the action contents.
         */
        public static final String[] PROJECTION =
            new String[] {
                SmartDroid.Actions.COLUMN_NAME_ID,
                SmartDroid.Actions.COLUMN_NAME_NAME,
                SmartDroid.Actions.COLUMN_NAME_DESCRIPTION,
                SmartDroid.Actions.COLUMN_NAME_RULE_ID
        	};
        
	}

}
