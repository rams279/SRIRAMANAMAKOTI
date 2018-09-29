package com.atharva.rama.sriramakoti;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class SriRamaJayamWelcomeActivity extends AppCompatActivity {

	//MediaPlayer mMediaPlayer;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_sri_rama_jayam_welcome);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Log.i (Constants.LOG_TAG, "onCreate");

	}

	public void Action (View v) {
		Log.i (Constants.LOG_TAG, "inSide Action");
		Intent sriramaCapture = new Intent (SriRamaJayamWelcomeActivity.this, CaptureSriRama.class);
		startActivity (sriramaCapture);
	}

}
