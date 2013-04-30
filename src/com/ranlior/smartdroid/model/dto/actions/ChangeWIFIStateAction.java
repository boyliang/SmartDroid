/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.ranlior.smartdroid.R;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class ChangeWIFIStateAction extends Action {

	private static final String TAG = ChangeWIFIStateAction.class.getSimpleName();

	private static final String NAME = "Change wifi state";

	private static final String DESCRIPTION = "Changes wifi state (enabled / disabled)";

	private final String ICON = "ic_list_wifi";

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
	private int wifiState = WifiManager.WIFI_STATE_ENABLED;

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
		this.wifiState = wifiState;
	}

	/**
	 * @return the wifiState
	 */
	public int getWifiState() {
		return wifiState;
	}

	/**
	 * @param wifiState
	 *            the wifiState to set
	 */
	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
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
		if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
			wifiManager.setWifiEnabled(true);
		} else {
			wifiManager.setWifiEnabled(false);
		}
	}

	@Override
	public String getIconName() {
		return ICON;
	}

	@Override
	public View getChildView(Context context, View convertView) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.expand_wifi_action, null);
		final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.rgWiFiSate);
		convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int wifiState = WifiManager.WIFI_STATE_ENABLED;
				int wifiSelectedSate = radioGroup.getCheckedRadioButtonId();
				switch (wifiSelectedSate) {
				case R.id.rbWifiOn:
					wifiState = WifiManager.WIFI_STATE_ENABLED;
					break;
				case R.id.rbVibrate:
					wifiState = WifiManager.WIFI_STATE_DISABLED;
					break;
				}
				setWifiState(wifiState);
			}
		});
		return convertView;
	}

}
