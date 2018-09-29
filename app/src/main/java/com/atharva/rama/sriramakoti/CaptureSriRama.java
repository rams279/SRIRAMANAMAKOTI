package com.atharva.rama.sriramakoti;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class CaptureSriRama extends AppCompatActivity {
	private LinearLayout mWriteSriRamaLinearView1;
	private LinearLayout mWriteSriRamaLinearView2;
	android.support.v7.app.ActionBar lActionBar;
	private SriRama mSlate1;
	private static SriramaUtility mSriramaUtility;
	public static String msDataDirectory;
	private File mdirectory;
	public String current = null;
	private boolean mWritePermission = false;
	private boolean mReadPermission = false;
	private SriRama mSlate2;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		this.requestWindowFeature (Window.FEATURE_NO_TITLE);
		setContentView (R.layout.activity_capture_sri_rama);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSriramaUtility = SriramaUtility.getInstance (getApplicationContext ());

		mWriteSriRamaLinearView1 = (LinearLayout) findViewById (R.id.linearLayout1);
		mWriteSriRamaLinearView2 = (LinearLayout) findViewById (R.id.linearLayout2);

		initializeSriRamaSlates ();

		lActionBar = getSupportActionBar ();
		lActionBar.setDisplayShowHomeEnabled (true);

	}

	private void initializeSriRamaSlates () {
		mSlate1 = new SriRama (this, null);
		Log.i (Constants.LOG_TAG, "CaptureSriRama >> initializeSriRamaSlates1 ");
		mWriteSriRamaLinearView1.addView (mSlate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mSlate2 = new SriRama (this, null);
		Log.i (Constants.LOG_TAG, "CaptureSriRama >> initializeSriRamaSlates1 ");
		mWriteSriRamaLinearView2.addView (mSlate2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		if (!mReadPermission || !mWritePermission) {
			seekPermission ();
		}
	}

	@Override
	public boolean onCreateOptionsMenu (Menu pMenu) {
		Log.i (Constants.LOG_TAG, "capture >> onCreateOptionMenu");
		MenuInflater lMenuInflater = getMenuInflater ();
		lMenuInflater.inflate (R.menu.activity_capture_srirama_action_menu, pMenu);
		return super.onCreateOptionsMenu (pMenu);

	}

	@Override
	public boolean onOptionsItemSelected (MenuItem pMenuItem) {
		Log.i (Constants.LOG_TAG, "ViewNamaActivity >> onOptionsItemSelected and id " + pMenuItem.getItemId ());

		switch (pMenuItem.getItemId ()) {
			case R.id.capture_srirama_action_menu_clear: {
				onClear ();
				Toast.makeText (getApplicationContext (), "Cleared", Toast.LENGTH_SHORT).show ();
				return true;
			}
			case R.id.capture_srirama_action_menu_save: {
				onSave ();
				return true;
			}
			case R.id.capture_srirama_action_menu_view: {
				onView ();
				return true;
			}
			case R.id.capture_srirama_action_menu_view2: {
				onView2 ();
				return true;
			}
			default:
				return super.onOptionsItemSelected (pMenuItem);

		}

	}

	public void onClear () {
		//Log.i ("log_tag", "CaptureSriRama >> onClear");
		mSlate1.clear ();
		mSlate2.clear ();
	}

	public void onSave () {
		Log.i ("log_tag", "CaptureSriRama >> onSave");

		Log.i (Constants.LOG_TAG, "CaptureSriRama>prepareDirectory() completed ");
		if (mSlate1.isSaveFlag ()) {
			mSlate1.setDrawingCacheEnabled (true);
			if (mSlate1.save (mSlate1)) {
				Toast.makeText (getApplicationContext (), "Saved - 1", Toast.LENGTH_SHORT).show ();
				setCountText ();
			} else {
				Toast.makeText (getApplicationContext (), "Save Permission not granted", Toast.LENGTH_LONG).show ();
			}
		} else {
			Toast.makeText (getApplicationContext (), "Write SriRama-1 and touch Save", Toast.LENGTH_SHORT).show ();
		}
		if (mSlate2.isSaveFlag ()) {
			mSlate2.setDrawingCacheEnabled (true);
			if (mSlate2.save (mSlate2)) {
				Toast.makeText (getApplicationContext (), "Saved - 2", Toast.LENGTH_SHORT).show ();
				setCountText ();
			} else {
				Toast.makeText (getApplicationContext (), "Save Permission not granted", Toast.LENGTH_LONG).show ();
			}
		} else {
			Toast.makeText (getApplicationContext (), "Write SriRama-2 and touch Save", Toast.LENGTH_SHORT).show ();
		}
		onClear ();
	}

	public void onView () {
		Log.v ("log_tag", "CaptureSriRama >> onView ");
		Intent viewSrirama = new Intent (CaptureSriRama.this, ViewNamaActivity.class);
		startActivity (viewSrirama);

	}

	public void onView2 () {
		Log.v ("log_tag", "CaptureSriRama >> onView2 ");
		Intent viewSrirama2 = new Intent (CaptureSriRama.this, ViewNamaRecyclerScrollingActivity.class);
		startActivity (viewSrirama2);

	}
	@Override
	protected void onDestroy () {
		super.onDestroy ();
		Log.i (Constants.LOG_TAG, "onDestory");
	}

	@Override
	protected void onStart () {
		super.onStart ();
		setCountText ();
		Log.i (Constants.LOG_TAG, "onStart");
	}


	private void setCountText () {
		TextView lTView = (TextView) findViewById (R.id.countView);
		assert lTView != null;
		lTView.setText (mSriramaUtility.getCount ());
		lTView.setTextColor (Color.WHITE);
		lTView.setTextSize (15f);
	}


	@Override
	public void onStop () {
		super.onStop ();
		Log.w (Constants.LOG_TAG, "onStop");
	}

	public class SriRama extends View {
		private static final float STROKE_WIDTH = 10f;
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private Paint paint = new Paint ();
		private Path path = new Path ();
		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF ();
		private Bitmap mBitmap;

		private boolean mSaveFlag = false;

		public SriRama (Context context, AttributeSet attrs) {
			super (context, attrs);
			Log.i (Constants.LOG_TAG, "SriRama>constructor .. ");
			paint.setAntiAlias (true);
			paint.setColor (Color.BLUE);
			paint.setStyle (Paint.Style.STROKE);
			paint.setStrokeJoin (Paint.Join.ROUND);
			paint.setStrokeWidth (STROKE_WIDTH);
			setMinimumWidth (100);
			setMinimumHeight (200);
			setPadding (5, 10, 5, 10);
			setBackgroundColor (getResources ().getColor (R.color.colorWhiteB));
		}

		public void setSaveFlag (boolean pSaveFlag) {
			mSaveFlag = pSaveFlag;
		}

		public boolean isSaveFlag () {
			return mSaveFlag;
		}

		public boolean save (View v) {
			setSaveFlag (false);
			if (!mWritePermission) {
				Log.v (Constants.LOG_TAG, "User didn't accept the write permissions to external folder: " + mWritePermission);
				return false;
			}
			if (mBitmap == null) {
				mBitmap = Bitmap.createBitmap (v.getWidth (), v.getHeight (), Bitmap.Config.RGB_565);
			}
			Canvas canvas = new Canvas (mBitmap);
			setBackgroundColor (getResources ().getColor (R.color.colorWhiteB));
			try {
				File lCurrentFile = mSriramaUtility.getNewFile ();
				FileOutputStream mFileOutStream = new FileOutputStream (lCurrentFile);
				v.draw (canvas);
				mBitmap.compress (Bitmap.CompressFormat.PNG, 100, mFileOutStream);
				mFileOutStream.flush ();
				mFileOutStream.close ();
				String url = MediaStore.Images.Media.insertImage (getContentResolver (), mBitmap, "title", null);
				Log.v (Constants.LOG_TAG, "url: " + url);

			} catch (Exception e) {
				Log.v (Constants.LOG_TAG, e.toString ());
				return false;
			}
			return true;
		}

		public void clear () {
			path.reset ();
			invalidate ();
			setSaveFlag (false);
		}

		@Override
		protected void onDraw (Canvas canvas) {
			canvas.drawPath (path, paint);
		}

		@Override
		public boolean onTouchEvent (MotionEvent event) {
			setSaveFlag (true);
			float eventX = event.getX ();
			float eventY = event.getY ();
			switch (event.getAction ()) {
				case MotionEvent.ACTION_DOWN:
					path.moveTo (eventX, eventY);
					lastTouchX = eventX;
					lastTouchY = eventY;
					return true;

				case MotionEvent.ACTION_MOVE:

				case MotionEvent.ACTION_UP:

					resetDirtyRect (eventX, eventY);
					int historySize = event.getHistorySize ();
					for (int i = 0; i < historySize; i++) {
						float historicalX = event.getHistoricalX (i);
						float historicalY = event.getHistoricalY (i);
						expandDirtyRect (historicalX, historicalY);
						path.lineTo (historicalX, historicalY);
					}
					path.lineTo (eventX, eventY);
					break;

				default:
					Log.i (Constants.LOG_TAG, "Ignored touch event: " + event.toString ());
					return false;
			}

			invalidate ((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}


		private void expandDirtyRect (float historicalX, float historicalY) {
			if (historicalX < dirtyRect.left) {
				dirtyRect.left = historicalX;
			} else if (historicalX > dirtyRect.right) {
				dirtyRect.right = historicalX;
			}

			if (historicalY < dirtyRect.top) {
				dirtyRect.top = historicalY;
			} else if (historicalY > dirtyRect.bottom) {
				dirtyRect.bottom = historicalY;
			}
		}

		private void resetDirtyRect (float eventX, float eventY) {
			dirtyRect.left = Math.min (lastTouchX, eventX);
			dirtyRect.right = Math.max (lastTouchX, eventX);
			dirtyRect.top = Math.min (lastTouchY, eventY);
			dirtyRect.bottom = Math.max (lastTouchY, eventY);
		}
	}


	@Override
	public void onRequestPermissionsResult (int requestCode,
											String permissions[], int[] grantResults) {
		Log.i (Constants.LOG_TAG, "onRequestPermissionsResult " + requestCode);
		switch (requestCode) {
			case Constants.MY_PERMISSIONS_REQUEST_WRITE_ExTERNAL_STORAGE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.i (Constants.LOG_TAG, "Permission Granted");
					mWritePermission = true;
				} else {
					Log.e (Constants.LOG_TAG, "Permission Dined");
					mWritePermission = false;
				}
				break;
			}
			case Constants.MY_PERMISSIONS_REQUEST_READ_ExTERNAL_STORAGE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.i (Constants.LOG_TAG, "Permission Granted");
					mReadPermission = true;
				} else {
					Log.e (Constants.LOG_TAG, "Permission Dined");
					mReadPermission = false;
				}
				break;
			}

		}
	}

	public void seekPermission () {
		Log.i (Constants.LOG_TAG, "seekPermission >> ");
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission (getApplicationContext (),
				Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {

			// No explanation needed, we can request the permission.
			Log.i (Constants.LOG_TAG, "seekPermission >> Asking for Permission...");
			ActivityCompat.requestPermissions (CaptureSriRama.this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					Constants.MY_PERMISSIONS_REQUEST_WRITE_ExTERNAL_STORAGE);

			// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
			// app-defined int constant. The callback method gets the
			// result of the request.

		} else {
			mWritePermission = true;
		}
		if (ContextCompat.checkSelfPermission (getApplicationContext (),
				Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {

			// No explanation needed, we can request the permission.
			Log.i (Constants.LOG_TAG, "seekPermission >> Asking for Permission...");
			ActivityCompat.requestPermissions (CaptureSriRama.this,
					new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
					Constants.MY_PERMISSIONS_REQUEST_READ_ExTERNAL_STORAGE);

			// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
			// app-defined int constant. The callback method gets the
			// result of the request.

		} else {
			mReadPermission = true;
		}

	}

}
