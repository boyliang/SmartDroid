/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import java.util.UUID;

import android.content.Context;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class Action {

	private UUID id = null;

	private String name = null;

	private String description = null;
	
	private String icon = null;

	/**
	 * Full constructor.
	 * 
	 * @param name
	 * @param description
	 */
	public Action(String name, String description) {
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
	 * Performs the action. Every derived class should implemenet this method.
	 * This is where the action logic implemeneted.
	 * 
	 * @param context
	 */
	public abstract void perform(Context context);
	
	public abstract String getIconName();

}
