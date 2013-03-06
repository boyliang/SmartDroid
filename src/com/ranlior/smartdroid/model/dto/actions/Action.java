/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public abstract class Action {
	
	/**
	 * Holds the context that instantiate this action.
	 */
	protected final Context mContext;
	
	/**
	 * Holds the action's identifier.
	 */
	private long id = -1L;
	
	/**
	 * Holds the action's name.
	 */
	private String name = null;
	
	/**
	 * Holds the action's description.
	 */
	private String description = null;
	

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param name
	 * @param description
	 */
	public Action(Context context, String name, String description) {
		super();
		this.mContext = context;
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
	 * @param id the identifier to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Performs the action.
	 * Every derived class should implemenet this method.
	 * This is where the action logic implemeneted.
	 */
	public abstract void perform();

}
