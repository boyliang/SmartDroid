/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName="SensorTriggers")
public class SensorTrigger extends Trigger {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "SensorTrigger";
	
	/**
	 * Holds the sensor manager.
	 */
	private SensorManager sensorManager = null;
	
	/**
	 * Holds the sensor.
	 */
	private Sensor sensor = null;

	/**
	 * Holds the sensor event listener.
	 */
	private SensorEventListener listener = null;
	
	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected SensorTrigger() {
		super();
	}

	/**
	 * Minimal constructor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents trigger's rule
	 * @param name			String represents trigger's name
	 * @param description	String represents trigger's description
	 */
	public SensorTrigger(Context context, Rule rule, String name, String description, int sensorType, float... sensorValues) {
		super(context, rule, name, description);
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensorType);
		listener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				float[] values = event.values;
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@SuppressLint("NewApi")
	@Override
	public void register() {
		// Logger
		Log.d(TAG, "register()");
		
		sensorManager.registerListener(listener, sensor, sensor.getMinDelay());
	}
	
	/**
	 * Unregisters the trigger to the system.
	 * Every derived class should implemenet this method.
	 * This is where the trigger unregistration logic implementation.
	 */
	public void unregister() {
		// Logger
		Log.d(TAG, "register()");
		
		sensorManager.unregisterListener(listener);
	}

}
