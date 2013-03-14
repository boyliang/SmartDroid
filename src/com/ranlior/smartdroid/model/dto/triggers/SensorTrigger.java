/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

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
public class SensorTrigger extends Trigger {

	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "SensorTrigger";
	
	/**
	 * Holds the sensor manager.
	 */
	private final SensorManager sensorManager;
	
	/**
	 * Holds the sensor.
	 */
	private final Sensor sensor;

	/**
	 * Holds the sensor event listener.
	 */
	private final SensorEventListener listener;
	
	/**
	 * Holds the satisfaction flag.
	 */
	private boolean isSetisfied = false;

	/**
	 * Minimal constructor.
	 * 
	 * @param context
	 * @param name
	 * @param description
	 */
	public SensorTrigger(Context context, String name, String description, int sensorType, float... sensorValues) {
		super(context, name, description);
		
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
