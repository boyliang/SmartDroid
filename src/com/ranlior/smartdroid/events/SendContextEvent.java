/**
 * 
 */
package com.ranlior.smartdroid.events;

import android.content.Context;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class SendContextEvent {
	
	private Context context = null;

	/**
	 * 
	 */
	public SendContextEvent(Context context) {
		super();
		
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

}
