package com.atharva.rama.sriramakoti;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ViewNamaActivity extends AppCompatActivity {

	GridView mGView;
	SriramaViewAdapter mSriramaViewAdapter;
	SriramaUtility mSriramaUtility;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_view_nama);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mSriramaUtility = SriramaUtility.getInstance (getApplicationContext ());
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onCreate");
		ActionBar lActionBar = getSupportActionBar ();
		lActionBar.setDisplayShowHomeEnabled (true);
		mSriramaViewAdapter = new SriramaViewAdapter (getApplicationContext ());
		prepareGridView ();
	}

	private void prepareGridView () {
		mGView = (GridView) findViewById (R.id.viewNamaGridView);
		mGView.setAdapter (mSriramaViewAdapter);
		mGView.setOnItemClickListener (new AdapterView.OnItemClickListener () {

			@Override
			public void onItemClick (AdapterView<?> arg0, View arg1, int arg2,
									 long arg3) {
				Toast.makeText (getBaseContext (), R.string.SriRama + arg2, Toast.LENGTH_LONG).show ();
			}

		});
	}

	@Override
	protected void onResume () {
		super.onResume ();
		mSriramaViewAdapter.setFileStrings(mSriramaUtility.getFilesList ());
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onResume preparing view");

	}

	@Override
	protected void onDestroy () {
		super.onDestroy ();
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onDestory");
	}

	@Override
	public boolean onCreateOptionsMenu (Menu pMenu) {
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onCreateOptionMenu");
		MenuInflater lMenuInflater = getMenuInflater ();
		lMenuInflater.inflate (R.menu.activity_nama_view_action_menu, pMenu);
		return super.onCreateOptionsMenu (pMenu);

	}


	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onOptionsItemSelected and id " + item.getItemId ());

		switch (item.getItemId ()) {
			default:
				return super.onOptionsItemSelected (item);
		}

	}

}
