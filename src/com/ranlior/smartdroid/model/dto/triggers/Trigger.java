/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class Trigger {

	private UUID id = null;

	private String name = null;

	private String description = null;

	private boolean isSatisfied = false;

	/**
	 * Minimal constructor.
	 * 
	 * @param name
	 *            String represents trigger's name
	 * @param description
	 *            String represents trigger's description
	 */
	public Trigger(String name, String description) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the isSatisfied
	 */
	public boolean isSatisfied() {
		return isSatisfied;
	}

	/**
	 * @param isSatisfied
	 *            the isSatisfied to set
	 */
	public void setSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	/**
	 * Registers the trigger to the system. Derived class may implemenet this
	 * method. This is where the trigger registration logic implementation.
	 * 
	 * @param context
	 */
	public void register(Context context) {
	}

	/**
	 * Unregisters the trigger to the system. Derived class may implemenet this
	 * method. This is where the trigger registration logic implementation.
	 * 
	 * @param context
	 */
	public void unregister(Context context) {
	}
	
	public abstract int getIconId();

	public abstract Bundle getExtras();
	
	public abstract void setExtras(Bundle extras);
	
	public abstract Class<? extends Activity> getTriggerEditor();

	
}
