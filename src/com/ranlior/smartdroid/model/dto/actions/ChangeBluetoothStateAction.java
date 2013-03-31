/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "change_bluetooth_state_actions")
public class ChangeBluetoothStateAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "ChangeBluetoothStateAction";
	
	/**
	 * The action's name.
	 */
	private static final String NAME = "Change bluetooth state";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Changes bluetooth state (enabled / disabled)";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "change_bluetooth_state_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name bluetooth state.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_BLUETOOTH_STATE = "bluetooth_state";

	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the wanted bluetooth state.
	 * 
	 * <P>
	 * BluetoothAdapter.STATE_OFF:<BR/>
	 * Indicates the local Bluetooth adapter is off.<BR/>
	 * BluetoothAdapter.STATE_ON<BR/>
	 * Indicates the local Bluetooth adapter is on, and ready for use.<BR/>
	 * </P>
	 * 
	 * For more info:
	 * @see android.bluetooth.BluetoothAdapter
	 */
	@DatabaseField(columnName = ChangeBluetoothStateAction.COLUMN_NAME_BLUETOOTH_STATE, canBeNull = false)
	private int bluetoothState = BluetoothAdapter.STATE_OFF;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected ChangeBluetoothStateAction() {
		super();
	}

	/**
	 * Full constractor.
	 * 
	 * @param context			Context the context instantiating this action
	 * @param rule				Rule represents action's rule
	 * @param bluetoothState	Integer represents the wanted bluetooth state
	 */
	public ChangeBluetoothStateAction(Context context, Rule rule, int bluetoothState) {
		super(context, rule, ChangeBluetoothStateAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.bluetoothState = bluetoothState;
	}

	/**
	 * @return the bluetoothState
	 */
	public int getBluetoothState() {
		return bluetoothState;
	}

	/**
	 * @param bluetoothState the bluetoothState to set
	 */
	public void setBluetoothState(int bluetoothState) {
		this.bluetoothState = bluetoothState;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// Changes bluetooth state
		if (mBluetoothAdapter != null) {
			if (bluetoothState == BluetoothAdapter.STATE_ON && !mBluetoothAdapter.isEnabled()) {
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				context.startActivity(intent);
			} else if (bluetoothState == BluetoothAdapter.STATE_OFF) {
				mBluetoothAdapter.disable();
			}
		}
	}

}
