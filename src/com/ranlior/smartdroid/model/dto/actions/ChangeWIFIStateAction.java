/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.ChangeWIFIStateActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeWIFIStateAction extends Action {

	private static final String TAG = ChangeWIFIStateAction.class.getSimpleName();

	private static final String NAME = "Change wifi state";

	private static final String DESCRIPTION = "Changes wifi state (enabled / disabled)";

	private static final int ICON = R.drawable.ic_list_wifi;

	/**
	 * Holds the wanted wifi state.
	 * 
	 * <P>
	 * WifiManager.WIFI_STATE_ENABLED:<BR/>
	 * Wi-Fi is enabled.<BR/>
	 * WifiManager.WIFI_STATE_DISABLED<BR/>
	 * Wi-Fi is disabled.<BR/>
	 * </P>
	 * 
	 * For more info:
	 * 
	 * @see android.net.wifi.WifiManager
	 */
	private int wantedWifiState = WifiManager.WIFI_STATE_ENABLED;

	public ChangeWIFIStateAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param wifiState
	 *            Integer represents the wanted wifi state
	 */
	public ChangeWIFIStateAction(int wifiState) {
		super(NAME, DESCRIPTION);
		this.wantedWifiState = wifiState;
	}

	/**
	 * @return the wantedWifiState
	 */
	public int getWantedWifiState() {
		return wantedWifiState;
	}

	/**
	 * @param wantedWifiState
	 *            the wantedWifiState to set
	 */
	public void setWantedWifiState(int wantedWifiState) {
		this.wantedWifiState = wantedWifiState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		// Changes wifi state
		if (wantedWifiState == WifiManager.WIFI_STATE_ENABLED) {
			wifiManager.setWifiEnabled(true);
		} else {
			wifiManager.setWifiEnabled(false);
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putInt("wantedWifiState", wantedWifiState);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setWantedWifiState(extras.getInt("wantedWifiState"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return ChangeWIFIStateActionEditorActivity.class;
	}

}
