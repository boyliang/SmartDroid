/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.triggers.editors.LocationProximityTriggerEditorActivity;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.database.Db4oHelper;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class LocationProximityTrigger extends Trigger {

	private static final String TAG = LocationProximityTrigger.class.getSimpleName();

	private static final String NAME = "Location proximity";

	private static final String DESCRIPTION = "Trigged when entering and exiting a location";

	private static final int ICON = R.drawable.ic_list_location;

	/**
	 * Holds the latitude of the central point of the alert region.
	 */
	private double latitude;

	/**
	 * Holds the longitude of the central point of the alert region.
	 */
	private double longitude;

	/**
	 * Holds the proximity entering or exiting wanted state.
	 */
	private boolean isProximityEntering = true;

	/**
	 * Holds the radius of the central point of the alert region, in meters.
	 */
	private float radius = 200.0F;

	/**
	 * Holds time for this proximity alert, in milliseconds, or -1 to indicate
	 * no expiration. Default: -1 no expiration.
	 */
	private long expiration = -1;

	public LocationProximityTrigger() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constructor.
	 * 
	 * @param lat
	 * @param lng
	 * @param isProximityEntering
	 * @param radius
	 * @param expiration
	 */
	public LocationProximityTrigger(double lat, double lng, boolean isProximityEntering, float radius, long expiration) {
		super(NAME, DESCRIPTION);
		this.latitude = lat;
		this.longitude = lng;
		this.isProximityEntering = isProximityEntering;
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
	 * @param latitude
	 *            the latitude to set
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
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the isProximityEntering
	 */
	public boolean isProximityEntering() {
		return isProximityEntering;
	}

	/**
	 * @param isProximityEntering
	 *            the isProximityEntering to set
	 */
	public void setProximityEntering(boolean isProximityEntering) {
		this.isProximityEntering = isProximityEntering;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
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
	 * @param expiration
	 *            the expiration to set
	 */
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dto.triggers.Trigger#register(android.content
	 * .Context)
	 */
	@Override
	public void register(Context context) {
		Log.d(TAG, "register(Context context)");

		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(SmartDroid.Action.ACTION_LOCATION_PROXIMITY);
		intent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_ID, getId());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.addProximityAlert(latitude, longitude, radius, expiration, pendingIntent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ranlior.smartdroid.model.dto.triggers.Trigger#unregister(android.
	 * content.Context)
	 */
	@Override
	public void unregister(Context context) {
		Log.d(TAG, "unregister(Context context)");

		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(SmartDroid.Action.ACTION_LOCATION_PROXIMITY);
		intent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_ID, getId());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.removeProximityAlert(pendingIntent);
	}

	public static void handle(Context appCtx, Bundle stateExtras) {
		Log.d(TAG, "handle(Context appCtx, Bundle stateExtras)");

		Trigger trigger = null;

		final UUID triggerUuid = (UUID) stateExtras.getSerializable(SmartDroid.Extra.EXTRA_TRIGGER_ID);
		boolean isProximityEntering = stateExtras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING);

		ObjectContainer db = Db4oHelper.db(appCtx);

		try {
			List<LocationProximityTrigger> triggers = db.query(new Predicate<LocationProximityTrigger>() {
				public boolean match(LocationProximityTrigger trigger) {
					return triggerUuid.compareTo(trigger.getId()) == 0;
				}
			});

			trigger = triggers.get(0);

			if (isProximityEntering) {
				trigger.setSatisfied(true);
			} else {
				trigger.setSatisfied(false);
			}

			db.store(trigger);

			List<Rule> rules = db.query(new Predicate<Rule>() {
				public boolean match(Rule rule) {
					return rule.isSatisfied();
				}
			});

			for (Rule rule : rules) {
				rule.perform(appCtx);
			}
		} catch (Db4oIOException e) {
			e.printStackTrace();
		} catch (DatabaseClosedException e) {
			e.printStackTrace();
		} catch (DatabaseReadOnlyException e) {
			e.printStackTrace();
		} finally {
			db.commit();
			db.close();
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putDouble("latitude", latitude);
		extras.putDouble("longitude", longitude);
		extras.putBoolean("isProximityEntering", isProximityEntering);
		extras.putFloat("radius", radius);
		extras.putLong("expiration", expiration);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setLatitude(extras.getDouble("latitude"));
		setLongitude(extras.getDouble("longitude"));
		setProximityEntering(extras.getBoolean("isProximityEntering"));
		setRadius(extras.getFloat("radius"));
		setExpiration(extras.getLong("expiration"));
	}

	@Override
	public Class<? extends Activity> getTriggerEditor() {
		return LocationProximityTriggerEditorActivity.class;
	}

}
