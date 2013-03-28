/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "modify_ringer_mode_actions")
public class ModifyRingerModeAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "ModifyRingerModeAction";
	
	/**
	 * The action's name. 
	 */
	private static final String NAME = "Modify ringer mode";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Modifies ringer mode (Normal / Silent / Vibrate)";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "modify_ringer_mode_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name ringer mode.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_RINGER_MODE = "ringer_mode";

	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the wanted ringer mode.
	 * 
	 * <P>
	 * AudioManager.RINGER_MODE_NORMAL:<BR/>
	 * Ringer mode that may be audible and may vibrate.<BR/><BR/>
	 * AudioManager.RINGER_MODE_SILENT:<BR/>
	 * Ringer mode that will be silent and will not vibrate.<BR/><BR/>
	 * AudioManager.RINGER_MODE_VIBRATE:<BR/>
	 * Ringer mode that will be silent and will vibrate.<BR/><BR/>
	 * </P>
	 * 
	 * For more info:
	 * @see android.media.AudioManager
	 */
	@DatabaseField(columnName = ModifyRingerModeAction.COLUMN_NAME_RINGER_MODE, canBeNull = false)
	private int ringerMode = AudioManager.RINGER_MODE_NORMAL;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected ModifyRingerModeAction() {
		super();
	}

	/**
	 * Minimal constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 */
	public ModifyRingerModeAction(Context context, Rule rule) {
		super(context, rule, ModifyRingerModeAction.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param audioStrem	Integer represents audio stream
	 */
	public ModifyRingerModeAction(Context context, Rule rule, int ringerMode) {
		super(context, rule, ModifyRingerModeAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.ringerMode = ringerMode;
	}

	/**
	 * @return the ringerMode
	 */
	public int getRingerMode() {
		return ringerMode;
	}

	/**
	 * @param ringerMode the ringerMode to set
	 */
	public void setRingerMode(int ringerMode) {
		this.ringerMode = ringerMode;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
		// Changes ringer mode settings
		audioManager.setRingerMode(ringerMode);
	}

}
