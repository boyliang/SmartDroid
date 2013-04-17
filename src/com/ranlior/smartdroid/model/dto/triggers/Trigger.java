/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.content.Context;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class Trigger implements Comparable<Trigger> {

	private Long id = null;

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
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Trigger another) {
		if (another == null) {
			return 1;
		} else {
			return (int) (id - another.getId());
		}
	};
}
