package com.ranlior.smartdroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ranlior.smartdroid.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

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

			@Override
			public void onAnimationStart(Animator arg0) {
				settingAnim.start();
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				
				storeButton.setVisibility(View.VISIBLE);
				storeButtonTran.start();
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});

		appButtonTran.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		findViewById(R.id.btnApp).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RuleActivity.class);
				startActivity(intent);
			}
		});

	}

}
