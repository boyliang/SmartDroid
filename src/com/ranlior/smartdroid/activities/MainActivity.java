package com.ranlior.smartdroid.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ranlior.smartdroid.R;

public class MainActivity extends SherlockActivity {
	
	/**
	 * Holds the logger's tag.
	 */
	private final static String TAG = "MainActivity";

	private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Logger
		Log.d(TAG, "onCreate(Bundle savedInstanceState)");

		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btnApp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RuleActivity.class);
				startActivity(intent);
			}
		});
	}

	// dneeded to determine the layout's width after the layout has been set
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		Log.d(TAG, "onWindowFocusChanged(hasFocus)");
		
		if(hasFocus) {
		
			// The animation time of each button to reach his place
			final int animationTime = 1500;
	
			// get the width of the activity layout
			LinearLayout layout = (LinearLayout) findViewById(R.id.llButtons);
			int width = layout.getWidth();
			Log.i("StartScreen", "onWindowFocusChanged width is=" + width);
	
			// settings icon animation setup
			final ImageView settingIcon = (ImageView) findViewById(R.id.ivSettings);
			final ObjectAnimator settingAnim = ObjectAnimator.ofFloat(settingIcon, "rotation", 0, 360);
			settingAnim.setDuration(animationTime);
	
			// setting store button animation
			final Button storeButton = (Button) findViewById(R.id.btnStore);
			storeButton.setVisibility(View.INVISIBLE);
			final ObjectAnimator storeButtonTran = ObjectAnimator.ofFloat(storeButton, "translationX", +width, 0).setDuration(animationTime);
			storeButtonTran.addListener(new AnimatorListener() {
	
				@Override
				public void onAnimationStart(Animator arg0) {
	
					// setting starts the settings icon rotation
					final ObjectAnimator settingAnim = ObjectAnimator.ofFloat(settingIcon, "rotation", 0, -360);
					settingAnim.setDuration(animationTime).start();
				}
	
				@Override
				public void onAnimationRepeat(Animator arg0) {
				}
	
				@Override
				public void onAnimationEnd(Animator arg0) {
					
				}
	
				@Override
				public void onAnimationCancel(Animator arg0) {
				}
			});
	
			// app button animation setup
			ObjectAnimator appButtonTran = ObjectAnimator.ofFloat(findViewById(R.id.btnApp), "translationX", -width, 0).setDuration(
					animationTime);
			appButtonTran.addListener(new AnimatorListener() {
	
				private MediaPlayer mp;
	
				@Override
				public void onAnimationStart(Animator arg0) {
					// starting the settings icon rotation animation
					 mp = MediaPlayer.create(getApplicationContext(), R.raw.cog_wheel);
	                 mp.setOnCompletionListener(new OnCompletionListener() {
	
	                     @Override
	                     public void onCompletion(MediaPlayer mp) {
	                         // TODO Auto-generated method stub
	                         mp.release();
	                     }
	
	                 });  
	                 
	                 mp.start();
					settingAnim.start();
				}
	
				@Override
				public void onAnimationRepeat(Animator arg0) {
				}
	
				@Override
				public void onAnimationEnd(Animator arg0) {
	
					// set the store button to visible and starting its animation
					storeButton.setVisibility(View.VISIBLE);
					storeButtonTran.start();
				}
	
				@Override
				public void onAnimationCancel(Animator arg0) {
				}
			});
	
			// starting the animation which eventually trigger all other animations
			appButtonTran.start();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}

}
