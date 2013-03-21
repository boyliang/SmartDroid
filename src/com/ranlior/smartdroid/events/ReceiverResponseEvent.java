/**
 * 
 */
package com.ranlior.smartdroid.events;

import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class ReceiverResponseEvent {
	
	/**
	 * 
	 */
	private Trigger trigger;

	/**
	 * @param trigger
	 */
	public ReceiverResponseEvent(Trigger trigger) {
		super();
		this.trigger = trigger;
	}

	/**
	 * @return the trigger
	 */
	public Trigger getTrigger() {
		return trigger;
	}

	/**
	 * @param trigger the trigger to set
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	
}
