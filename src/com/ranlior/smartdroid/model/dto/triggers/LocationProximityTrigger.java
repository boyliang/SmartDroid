/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email: ran.haveshush.shenkar@gmail.com
 * 
 */
@DatabaseTable(tableName = "location_proximity_triggers")
public class LocationProximityTrigger extends Trigger {

	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = LocationProximityTrigger.class.getSimpleName();

	/**
	 * The trigger's name.
	 */
	private static final String NAME = "Location proximity";

	/**
	 * The trigger's description.
	 */
	private static final String DESCRIPTION = "Trigged when entering and exiting a location";

	/*
	 * Table definition.
	 */

	/**
	 * The table name.
	 */
	public static final String TABLE_NAME = "location_proximity_triggers";

	/*
	 * Column definitions.
	 */

	/**
	 * Column name latitude.
	 * 
	 * <P>Type: REAL</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	public static final String COLUMN_NAME_LATITUDE = "latitude";
	
	/**
	 * Column name longitude.
	 * 
	 * <P>Type: REAL</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	public static final String COLUMN_NAME_LONGITUDE = "longitude";

	/**
	 * Column name radius.
	 * 
	 * <P>Type: REAL</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_RADIUS = "radius";

	/**
	 * Column name expiration.
	 * 
	 * <P>Type: INTEGER</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_EXPIRATION = "expiration";

	/*
	 * Instance variables.
	 */

	/**
	 * Holds the latitude of the central point of the alert region.
	 */
	@DatabaseField(columnName = LocationProximityTrigger.COLUMN_NAME_LATITUDE, canBeNull = false)
	private double latitude;
	
	/**
	 * Holds the longitude of the central point of the alert region.
	 */
	@DatabaseField(columnName = LocationProximityTrigger.COLUMN_NAME_LONGITUDE, canBeNull = false)
	private double longitude;
	
	/**
	 * Holds the radius of the central point of the alert region, in meters.
	 */
	@DatabaseField(columnName = LocationProximityTrigger.COLUMN_NAME_RADIUS, canBeNull = false)
	private float radius = 500.0F;
	
	/**
	 * Holds time for this proximity alert, in milliseconds, or -1 to indicate no expiration.
	 * Default: -1 no expiration.
	 */
	@DatabaseField(columnName = LocationProximityTrigger.COLUMN_NAME_EXPIRATION, canBeNull = false)
	private long expiration = -1;

	/**
	 * Default constructor. ORMLite needs a no-arg constructor.
	 */
	public LocationProximityTrigger() {
		super(LocationProximityTrigger.class.getSimpleName(), NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param context	Context the context instantiating this action
	 * @param rule		Rule represents trigger's rule
	 */
	public LocationProximityTrigger(Context context, Rule rule, double lat, double lng, float radius, long expiration) {
		super(context, rule, LocationProximityTrigger.class.getSimpleName(), NAME, DESCRIPTION);
		this.latitude = lat;
		this.longitude = lng;
		this.radius = radius;
		this.expiration = expiration;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * @return the expiration
	 */
	public long getExpiration() {
		return expiration;
	}

	/**
	 * @param expiration the expiration to set
	 */
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#register()
	 */
	@Override
	public void register() {
		// Logger
		Log.d(TAG, "register()");
		
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(SmartDroid.Action.ACTION_LOCATION_PROXIMITY);
		intent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_ID, getId());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.addProximityAlert(latitude, longitude, radius,
				expiration, pendingIntent);
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.triggers.Trigger#unregister()
	 */
	@Override
	public void unregister() {
		// Logger
		Log.d(TAG, "unregister()");
		
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(SmartDroid.Action.ACTION_LOCATION_PROXIMITY);
		intent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_ID, getId());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.removeProximityAlert(pendingIntent);
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		// Loggers
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");
		
		Trigger trigger = null;

		long triggerId = stateExtras.getLong(SmartDroid.Extra.EXTRA_TRIGGER_ID);
		boolean isProximityEntering = stateExtras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING);

		ITriggerDAO triggerDAO = SmartDAOFactory.getFactory(
				SmartDAOFactory.SQLITE).getTriggerDAO(appCtx);
		
		trigger = triggerDAO.get(triggerId);
		
		if (isProximityEntering) {
			trigger.setSatisfied(true);
		} else {
			trigger.setSatisfied(false);
		}
		
		triggerDAO.update(trigger);
		Rule rule = trigger.getRule();
		if (rule.isSatisfied()) {
			rule.setContext(appCtx);
			rule.perform();
		}
	}

}
