/**
 * 
 */
package com.ranlior.smartdroid.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R;
import android.provider.BaseColumns;

import com.ranlior.smartdroid.model.dto.actions.ChangeBluetoothStateAction;
import com.ranlior.smartdroid.model.dto.actions.ChangeWIFIStateAction;
import com.ranlior.smartdroid.model.dto.actions.ModifyRingerModeAction;
import com.ranlior.smartdroid.model.dto.actions.ModifyVolumeAction;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;
import com.ranlior.smartdroid.model.dto.actions.SetWallpaperAction;
import com.ranlior.smartdroid.model.dto.actions.StartAppAction;
import com.ranlior.smartdroid.model.dto.triggers.BatteryLevelTrigger;
import com.ranlior.smartdroid.model.dto.triggers.BatteryPluggedTrigger;
import com.ranlior.smartdroid.model.dto.triggers.BootCompletedTrigger;
import com.ranlior.smartdroid.model.dto.triggers.LocationProximityTrigger;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;
import com.ranlior.smartdroid.model.dto.triggers.WiredHeadsetPluggedTrigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
// FIXME: clean this file code
public final class SmartDroid {

	// This class cannot be instantiated
	private SmartDroid() {
	}

	/**
	 * App's package.
	 */
	public static final String APP_PACKAGE = "com.ranlior.smartdroid";

	/**
	 * Preferences.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public static final class Pref {

		// This class cannot be instantiated
		private Pref() {
		}

		/**
		 * Preferences authentication info file name.
		 */
		// FIXME: Example only.
		public static final String PREFS_FILE_AUTH = "prefsFileAuth";

	}

	/**
	 * Intent actions contact.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public static final class Action {

		// This class cannot be instantiated
		private Action() {
		}

		/**
		 * Action to get a location proximity alert broadcast.
		 */
		public static final String ACTION_LOCATION_PROXIMITY = APP_PACKAGE + ".ACTION_LOCATION_PROXIMITY";

		/**
		 * Action to add new rule.
		 */
		public static final String ACTION_ADD_RULE = APP_PACKAGE + ".ACTION_ADD_RULE";

		/**
		 * Action to edit existing rule.
		 */
		public static final String ACTION_EDIT_RULE = APP_PACKAGE + ".ACTION_EDIT_RULE";
	}

	/**
	 * Intent Extras contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public static final class Extra {

		// This class cannot be instantiated
		private Extra() {
		}

		/**
		 * Extra key for intent action string.
		 * 
		 * <P>
		 * Type: String
		 * </P>
		 */
		public static final String EXTRA_ACTION = APP_PACKAGE + ".EXTRA_ACTION";

		/**
		 * Extra key for intent rule id.
		 * 
		 * <P>
		 * Type: UUID
		 * </P>
		 * 
		 * @see java.util.UUID
		 */
		public static final String EXTRA_RULE_ID = APP_PACKAGE + ".EXTRA_RULE_ID";

		/**
		 * Extra key for intent trigger id long.
		 * 
		 * <P>
		 * Type: UUID
		 * </P>
		 * 
		 * @see java.util.UUID
		 */
		public static final String EXTRA_TRIGGER_ID = APP_PACKAGE + ".EXTRA_TRIGGER_ID";

		/**
		 * Extra key for intent state enum.
		 * 
		 * <P>
		 * Type: State
		 * </P>
		 */
		public static final String EXTRA_STATE = APP_PACKAGE + ".EXTRA_STATE";

		/**
		 * Extra key for intent battery level integer.
		 * 
		 * <P>
		 * Type: Integer
		 * </P>
		 */
		public static final String EXTRA_BATTERY_LEVEL = APP_PACKAGE + ".EXTRA_BATTERY_LEVEL";

		/**
		 * Extra key for trigger class name.
		 * 
		 * <P>
		 * Type: String
		 * </P>
		 */
		public static final String EXTRA_TRIGGER_CLASS_NAME = "triggerClassName";

		/**
		 * Extra key for action class name.
		 * 
		 * <P>
		 * Type: String
		 * </P>
		 */
		public static final String EXTRA_ACTION_CLASS_NAME = "actionClassName";

	}

	/**
	 * Fragment Arguments contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public static final class Arg {

		// This class cannot be instantiated
		private Arg() {
		}

		/**
		 * Argument key for the account picker dialog title.
		 * 
		 * <P>
		 * Type: int
		 * </P>
		 */
		// FIXME: Example only.
		public static final String ARG_ACCOUNT_PICKER_DIALOG_TITLE = APP_PACKAGE + ".ARG_ACCOUT_PICKER_DIALOG_TITLE";
	}

	/*
	 * 
	 * CONTENT PROVIDER: Tables contracts.
	 */

	/**
	 * Provider contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	public static final class SmartProvider {

		// This class cannot be instantiated
		private SmartProvider() {
		}

		/**
		 * The authority.
		 */
		public static final String AUTHORITY = APP_PACKAGE + ".contentproviders.SmartProvider";

	}

	/**
	 * Rules table contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 */
	public static final class Rules implements BaseColumns {

		// This class cannot be instantiated
		private Rules() {
		}

		/**
		 * The rules table name offered by this provider.
		 */
		public static final String TABLE_NAME_RULES = "rules";

		/**
		 * Holds the rules package.
		 */
		public static final String PACKAGE = SmartDroid.APP_PACKAGE + ".model.dto.rules";

		/*
		 * Column definitions.
		 */

		/**
		 * Column name rule's id. Rule identifier.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_ID = "_id";

		/**
		 * Column name rule's name.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_NAME = "name";

		/**
		 * Column name rule's description.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";

		/**
		 * Column name rule's satisfaction status. If satisfied then 1 (=true),
		 * else 0 (=false).
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_IS_SATISFIED = "isSatisfied";

		/**
		 * Column name rule's not satisfied triggers count.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_NOT_SATISFIED_TRIGGERS_COUNT = "notSatisfiedTriggersCount";

		/*
		 * Column values.
		 */

		/**
		 * Column value false as integer 0.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final int COLUMN_VALUE_FALSE = 0;

		/**
		 * Column value true as integer 1.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final int COLUMN_VALUE_TRUE = 1;

	}

	/**
	 * Triggers table contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 */
	public static final class Triggers implements BaseColumns {

		// This class cannot be instantiated
		private Triggers() {
		}

		/**
		 * The triggers table name offered by this provider.
		 */
		public static final String TABLE_NAME_TRIGGERS = "triggers";

		/**
		 * Holds the triggers package.
		 */
		public static final String PACKAGE = SmartDroid.APP_PACKAGE + ".model.dto.triggers";

		/*
		 * Column definitions.
		 */

		/**
		 * Column name trigger's id. Trigger identifier.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_ID = "_id";

		/**
		 * Column name trigger's name.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_NAME = "name";

		/**
		 * Column name trigger's description.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";

		/**
		 * Column name trigger's satisfaction status. If satisfied then 1
		 * (=true), else 0 (=false).
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_IS_SATISFIED = "isSatisfied";

		/**
		 * Column trigger's class name.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_CLASS_NAME = "className";

		/**
		 * Column name trigger's rule id.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: FOREIGN KEY
		 * </p>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_RULE_ID = "rule_id";

		/*
		 * Column values.
		 */

		/**
		 * Column value false as integer 0.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final int COLUMN_VALUE_FALSE = 0;

		/**
		 * Column value true as integer 1.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 */
		public static final int COLUMN_VALUE_TRUE = 1;

		/**
		 * Maps all concrette derived triggers.
		 */
		// TODO: list new concrette derived trigger's classes here
		public static final List<Trigger> LIST = new ArrayList<Trigger>(Arrays.asList(new BatteryLevelTrigger(),
				new BatteryPluggedTrigger(), new BootCompletedTrigger(), new LocationProximityTrigger(), new RingerModeTrigger(),
				new WiredHeadsetPluggedTrigger()));
		

	}

	/**
	 * Actions table contract.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 */
	public static final class Actions implements BaseColumns {

		// This class cannot be instantiated
		private Actions() {
		}

		/**
		 * The actions table name offered by this provider.
		 */
		public static final String TABLE_NAME_ACTIONS = "actions";

		/**
		 * Holds the actions package.
		 */
		public static final String PACKAGE = SmartDroid.APP_PACKAGE + ".model.dto.actions";

		/*
		 * Column definitions.
		 */

		/**
		 * Column name action's id. Action identifier.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_ID = "_id";

		/**
		 * Column name action's name.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_NAME = "name";

		/**
		 * Column name action's description.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_DESCRIPTION = "description";

		/**
		 * Column action's class name.
		 * 
		 * <P>
		 * Type: TEXT
		 * </P>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_CLASS_NAME = "className";

		/**
		 * Column name action's rule id.
		 * 
		 * <P>
		 * Type: INTEGER
		 * </P>
		 * <P>
		 * Constraint: FOREIGN KEY
		 * </p>
		 * <P>
		 * Constraint: NOT NULL
		 * </p>
		 */
		public static final String COLUMN_NAME_RULE_ID = "rule_id";

		/**
		 * Maps all concrette derived actions.
		 */
		// TODO: list new concrette derived action's classes here
		public static final List<com.ranlior.smartdroid.model.dto.actions.Action> LIST = new ArrayList<com.ranlior.smartdroid.model.dto.actions.Action>(
				Arrays.asList(new ChangeBluetoothStateAction(), new ChangeWIFIStateAction(), new ModifyRingerModeAction(),
						new ModifyVolumeAction(), new NotificationAction(), new SetWallpaperAction(), new StartAppAction()));
		
	}

}
