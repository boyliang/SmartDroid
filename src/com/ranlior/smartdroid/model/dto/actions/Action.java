/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class Action {

	private Long id = null;

	private String name = null;

	private String description = null;

	/**
	 * Full constructor.
	 * 
	 * @param name
	 * @param description
	 */
	public Action(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the identifier to set
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
	 * Performs the action. Every derived class should implemenet this method.
	 * This is where the action logic implemeneted.
	 * 
	 * @param context
	 */
	public abstract void perform(Context context);

}
