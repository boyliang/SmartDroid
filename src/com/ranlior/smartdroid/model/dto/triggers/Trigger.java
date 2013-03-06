/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.content.Context;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public abstract class Trigger {
	
	/**
	 * Holds the context that instantiate this action.
	 */
	protected final Context mContext;
	
	/**
	 * Holds the trigger's identifier.
	 */
	private long mId = -1L;
	
	/**
	 * Holds the trigger's name.
	 */
	private String name = null;
	
	/**
	 * Holds the trigger's description.
	 */
	private String description = null;
	

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param name
	 * @param description
	 */
	public Trigger(Context context, String name, String description) {
		super();
		this.mContext = context;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the mId
	 */
	public long getmId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setmId(long mId) {
		this.mId = mId;
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
	 * Registers the trigger to the system.
	 * Every derived class should implemenet this method.
	 * This is where the trigger registration logic implementation.
	 */
	public abstract void register();

	/**
	 * Checks the trigger satisfactory.
	 * Every derived class should implemenet this method.
	 * This is where the trigger stisfactory logic implementation.
	 */
	public abstract boolean isSatisfaied();
}
