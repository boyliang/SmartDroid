package com.ranlior.smartdroid.activities.triggers.editors;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.triggers.LocationProximityTrigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class LocationProximityTriggerEditorActivity extends SherlockActivity {

	private static final String TAG = LocationProximityTriggerEditorActivity.class.getSimpleName();

	private static final int AUTO_COMPLETE_TEXT_VIEW_THRESHOLD = 3;

	private static final Locale LOCALE = new Locale("IW", "IL");

	private ArrayAdapter<String> autoCompleteAdapter = null;

	private AutoCompleteTextView actvLocation = null;

	private RadioGroup rgNotifyWhen = null;

	private TextView tvRadius = null;

	private SeekBar sbRadius = null;

	private double latitude;

	private double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(savedInstanceState)");

		setContentView(R.layout.activity_location_proximity_trigger_editor);

		autoCompleteAdapter = new ArrayAdapter<String>(LocationProximityTriggerEditorActivity.this,
				android.R.layout.simple_dropdown_item_1line);

		actvLocation = (AutoCompleteTextView) findViewById(R.id.actvLocation);
		actvLocation.setThreshold(AUTO_COMPLETE_TEXT_VIEW_THRESHOLD);
		actvLocation.setAdapter(autoCompleteAdapter);
		actvLocation.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.d(TAG, "afterTextChanged(s)");
				String toAutoCompleteAddressStr = s.toString();
				// If the address string isn't empty and longer then threshold,
				// autocomplete
				if (!TextUtils.isEmpty(toAutoCompleteAddressStr) && toAutoCompleteAddressStr.length() >= AUTO_COMPLETE_TEXT_VIEW_THRESHOLD) {
					new AddressesAutoCompleteTask().execute(toAutoCompleteAddressStr);
				}
			}
		});
		actvLocation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(TAG, "onItemClick(parent, view, position, id)");
				String selectedAddressStr = ((TextView) view).getText().toString();
				actvLocation.setText(selectedAddressStr);
				new Geocoding().execute(selectedAddressStr);
			}
		});
		rgNotifyWhen = (RadioGroup) findViewById(R.id.rgNotifyWhen);
		tvRadius = (TextView) findViewById(R.id.tvRadius);
		sbRadius = (SeekBar) findViewById(R.id.sbRadius);
		sbRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tvRadius.setText(String.valueOf(progress));
			}
		});

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		// If editing trigger, renders trigger's state
		if (extras != null) {
			latitude = extras.getDouble("latitude");
			longitude = extras.getDouble("longitude");
			new ReverseGeocoding().execute(latitude, longitude);
			boolean isProximityEntering = extras.getBoolean("isProximityEntering");
			if (isProximityEntering) {
				rgNotifyWhen.check(R.id.rbProximityEntering);
			} else {
				rgNotifyWhen.check(R.id.rbProximityExiting);
			}
			float radius = extras.getFloat("radius");
			tvRadius.setText(String.valueOf(radius));
			sbRadius.setProgress((int) radius);
			// long expiration = extras.getLong("expiration");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu(menu)");
		getSupportMenuInflater().inflate(R.menu.activity_trigger_editor_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem item)");

		switch (item.getItemId()) {
		case R.id.saveTrigger:
			Intent resIntent = new Intent();
			resIntent.putExtra(SmartDroid.Extra.EXTRA_TRIGGER_CLASS_NAME, LocationProximityTrigger.class.getSimpleName());
			// FIXME: search goole not position
			resIntent.putExtra("position", getIntent().getIntExtra("position", -1));
			resIntent.putExtra("latitude", latitude);
			resIntent.putExtra("longitude", longitude);
			switch (rgNotifyWhen.getCheckedRadioButtonId()) {
			case R.id.rbProximityEntering:
				resIntent.putExtra("isProximityEntering", true);
				break;
			case R.id.rbProximityExiting:
				resIntent.putExtra("isProximityEntering", false);
				break;
			}
			resIntent.putExtra("radius", Float.parseFloat(tvRadius.getText().toString()));
			setResult(RESULT_OK, resIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Auto complete async task to geocode addresses.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	private class AddressesAutoCompleteTask extends AsyncTask<String, Void, List<Address>> {

		private static final String TAG = "AddressesAutoCompleteTask";

		/**
		 * Max results per autocomplete.
		 */
		private static final int MAX_RESULTS = 3;

		@Override
		protected List<Address> doInBackground(String... addresses) {
			Log.d(TAG, "doInBackground(String... addresses)");

			String toAutoCompleteAddressStr = addresses[0];
			try {
				return new Geocoder(LocationProximityTriggerEditorActivity.this, LOCALE).getFromLocationName(toAutoCompleteAddressStr,
						MAX_RESULTS);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Address> autoCompleteAddressesSuggestions) {
			Log.d(TAG, "onPostExecute(List<Address> autoCompleteAddressesSuggestions)");

			autoCompleteAdapter.clear();
			if (autoCompleteAddressesSuggestions == null) {
				return;
			}
			for (Address address : autoCompleteAddressesSuggestions) {
				String addressLine = (address.getMaxAddressLineIndex() > 0) ? address.getAddressLine(0) : "";
				String addressLocality = (null != address.getLocality()) ? address.getLocality() : "";
				String addressCountry = (null != address.getCountryName()) ? address.getCountryName() : "";
				String addressText = String.format("%s, %s, %s", addressLine, addressLocality, addressCountry);
				Log.d(TAG, "Address: " + addressText);
				autoCompleteAdapter.add(addressText);
			}
			autoCompleteAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * Reverse geocoding an latitude longitude to readable address.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	private class ReverseGeocoding extends AsyncTask<Double, Void, List<Address>> {

		private static final String TAG = "ReverseGeocoding";

		private static final int MAX_RESULTS = 1;

		@Override
		protected List<Address> doInBackground(Double... params) {
			Log.d(TAG, "doInBackground(Double params)");

			double latitude = params[0];
			double longitude = params[1];

			try {
				return new Geocoder(LocationProximityTriggerEditorActivity.this, LOCALE).getFromLocation(latitude, longitude, MAX_RESULTS);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {
			Log.d(TAG, "onPostExecute(String address)");

			if (addresses != null && !addresses.isEmpty()) {
				Address address = addresses.get(0);
				String addressLine = (address.getMaxAddressLineIndex() > 0) ? address.getAddressLine(0) : "";
				String addressLocality = (null != address.getLocality()) ? address.getLocality() : "";
				String addressCountry = (null != address.getCountryName()) ? address.getCountryName() : "";
				String addressText = String.format("%s, %s, %s", addressLine, addressLocality, addressCountry);
				Log.d(TAG, "Reverse Geocoding: " + addressText);
				actvLocation.setText(addressText);
			}
		}

	}

	/**
	 * Geocoding address to latitude and longitude.
	 * 
	 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
	 * 
	 */
	private class Geocoding extends AsyncTask<String, Void, List<Address>> {

		private static final String TAG = "ReverseGeocoding";

		private static final int MAX_RESULTS = 1;

		@Override
		protected List<Address> doInBackground(String... params) {
			Log.d(TAG, "doInBackground(String... params)");

			String address = params[0];

			try {
				return new Geocoder(LocationProximityTriggerEditorActivity.this, LOCALE).getFromLocationName(address, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {
			Log.d(TAG, "onPostExecute(String address)");

			if (addresses != null && !addresses.isEmpty()) {
				Address address = addresses.get(0);
				latitude = address.getLatitude();
				longitude = address.getLongitude();
				Log.d(TAG, "Geocoding: lat: " + latitude + " lng: " + longitude);
			} else {
				Toast.makeText(LocationProximityTriggerEditorActivity.this, "Connect to the Internet", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
