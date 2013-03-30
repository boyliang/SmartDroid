/**
 * 
 */
package com.ranlior.smartdroid.utilities;

import com.squareup.otto.Bus;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public final class BusProvider {
	private static final Bus BUS = new Bus();
	
	public static Bus getInstance() {
		return BUS;
	}
	
	private BusProvider() {}
}
