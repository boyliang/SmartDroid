package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ranlior.smartdroid.R;

public class SplashScreen extends Activity {

	// used to know if the back button was pressed in the splash screen activity
	// and avoid opening the next activity
	private boolean mIsBackButtonPressed;
	private static final int SPLASH_DURATION = 5000; // 5 seconds

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_spalsh_screen);

		View view = findViewById(R.id.fullSplash);

		Animation fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		fadeInAnimation.setStartOffset(1000);
		fadeInAnimation.setDuration(2000);
		// Now Set your animation
		view.startAnimation(fadeInAnimation);

		Handler handler = new Handler();

		// run a thread after 5 seconds to start the home screen
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				// make sure we close the splash screen so the user won't come
				// back when it presses back key

				finish();

				if (!mIsBackButtonPressed) {
					// start the home screen if the back button wasn't pressed
					// already
					Intent intent = new Intent(SplashScreen.this, RuleActivity.class);
					SplashScreen.this.startActivity(intent);
				}

			}

		}, SPLASH_DURATION); // time in milliseconds (1 second = 1000
								// milliseconds) until the run() method will be
								// called

	}

	@Override
	public void onBackPressed() {

		// set the flag to true so the next activity won't start up
		mIsBackButtonPressed = true;
		super.onBackPressed();

	}
}