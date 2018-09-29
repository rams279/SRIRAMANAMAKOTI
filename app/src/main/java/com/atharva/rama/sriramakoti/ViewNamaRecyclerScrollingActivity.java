package com.atharva.rama.sriramakoti;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class ViewNamaRecyclerScrollingActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mRecyclerAdapter;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_view_nama_recycler);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Log.i (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> onCreate");

		ActionBar lActionBar = getSupportActionBar ();
		if(lActionBar != null) {lActionBar.setDisplayShowHomeEnabled (true);}

		mRecyclerView = (RecyclerView) findViewById (R.id.viewNamaRecyclerView);
		mRecyclerView.setHasFixedSize (true);
		mRecyclerView.setLayoutManager ( new StaggeredGridLayoutManager(7, 1));

		try {
			mRecyclerAdapter = new SriramaRecyclerViewAdapter (this);
			mRecyclerView.setAdapter (mRecyclerAdapter);
		} catch (Exception e) {
			Log.e (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> Failed ...", e);
			e.printStackTrace ();
		}


	}

	@Override
	public boolean onCreateOptionsMenu (Menu pMenu) {
		Log.i (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> onCreateOptionMenu");
		MenuInflater lMenuInflater = getMenuInflater ();
		lMenuInflater.inflate (R.menu.activity_nama_view_action_menu, pMenu);
		return super.onCreateOptionsMenu (pMenu);

	}

}
