package com.atharva.rama.sriramakoti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Rama on 26-04-2016.
 */
public class SriramaUtility {

	private static final int mSRIRAMAKOTIValue = 10000000;
	private int mCounter;


	private String mDataDirectory;
	private Context mContext;
	private static SriramaUtility mSriramaUtility;
	private final String mAppName;
	private String[] mFilesList;
	//	private static final int mViewScreenBitmapWidth = 73;
//	private static final int mViewScreenBitmapHight = 45;
	private ArrayList<ListItem> mNamaList;


	private SriramaUtility (Context pContext) {
		mContext = pContext;
		mAppName = mContext.getResources ().getString (R.string.app_name);
		mDataDirectory = Environment.getExternalStorageDirectory () + "/" + mAppName + "/";
		Log.i (Constants.LOG_TAG, "SriramaUtility >> mDataDirectory -  " + mDataDirectory);

		makeApplicationDir (mDataDirectory);
		refreshFileList ();
		Log.i (Constants.LOG_TAG, "SriramaUtility >> mDataDirectory -  " + mDataDirectory);

		setCounter ();
	}

	public static SriramaUtility getInstance (Context pContext) {
		if (mSriramaUtility == null) {
			mSriramaUtility = new SriramaUtility (pContext);

		}
		return mSriramaUtility;
	}


	private boolean makeApplicationDir (String pDataDirectory) {
		File tempdir = new File (pDataDirectory);
//		ContextWrapper cw = new ContextWrapper (mContext.getApplicationContext ());
//		tempdir = cw.getDir (pDataDirectory, Context.);
		if (!tempdir.exists ()) {
			boolean lstatus = tempdir.mkdirs ();
			Log.i (Constants.LOG_TAG, "SriramaUtility >> Directory Creation Status : " + lstatus);
		}
		return (tempdir.isDirectory ());
	}


	public Bitmap getImageBitMap (String pFileName) {
		Bitmap bitmap = BitmapFactory.decodeFile (mDataDirectory + pFileName);
		//bitmap = Bitmap.createScaledBitmap (bitmap, mViewScreenBitmapWidth, mViewScreenBitmapHight, true);
		return bitmap;
	}

	private void refreshFileList () {
		File lDataDirectory = new File (mDataDirectory);
		mFilesList = lDataDirectory.list ();
	}

	public String getCount () {
		if (mCounter == 0) {
			setCounter ();
		}
		String lCount = String.valueOf (mCounter);
		StringBuilder lCountB = new StringBuilder (lCount);
		lCountB.append (" / ");
		lCountB.append (mSRIRAMAKOTIValue);
		return lCountB.toString ();
	}


	private void setCounter (int pCounter) {
		mCounter = pCounter;
	}

	private void setCounter () {
		Log.i (Constants.LOG_TAG, "SriramaUtility >> getCount - count from cahce " + mCounter);
		if (mCounter == 0) {
			int lCount = (mFilesList != null && mFilesList.length != 0) ? mFilesList.length : 0;
			Log.i (Constants.LOG_TAG, "SriramaUtility >> getCount - count from drive " + lCount);
			setCounter (lCount);
		}
	}

	public File getNewFile () {
		File lFileName = null;
		try {
			StringBuilder lFileNameSB = new StringBuilder (mDataDirectory);
			lFileNameSB.append (mAppName);
			lFileNameSB.append ("_");
			lFileNameSB.append (getCurrentTime ());
			lFileNameSB.append ("_");
			lFileNameSB.append (mSriramaUtility.mCounter);
			lFileNameSB.append (".png");
			lFileName = new File (lFileNameSB.toString ());
			Log.i (Constants.LOG_TAG, "SriramaUtility >> getNewFile>>lFileName : " + lFileName.getAbsolutePath ());
			mSriramaUtility.mCounter++;
		} catch (Exception e) {
			Log.e (Constants.LOG_TAG, e.getMessage ());

		}
		return lFileName;
	}

	private String getCurrentTime () {
		return (String.valueOf (System.currentTimeMillis ()));
	}

	public String[] getFilesList () {
		refreshFileList ();
		return mFilesList;
	}

	public String getDataDirectory () {
		return mDataDirectory;
	}

	private void loadImages () {

		mNamaList = new ArrayList<ListItem> ();
		mSriramaUtility = SriramaUtility.getInstance (mContext);
		String[] lStrings = mSriramaUtility.getFilesList ();
		Log.i (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> lStrings #" + lStrings.length);
		mDataDirectory = mSriramaUtility.getDataDirectory ();

		Log.i (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> initializing utility");
		ListItem lListItem;

		for (int i = 0; i < lStrings.length; i++) {

			Log.i (Constants.LOG_TAG, "ViewNamaRecyclerScrollingActivity >> loadImages >> Image # " + mDataDirectory + lStrings[i]);
			Bitmap bitmap = BitmapFactory.decodeFile (mDataDirectory + lStrings[i]);
			lListItem = new ListItem (bitmap);
			mNamaList.add (lListItem);
		}
	}

}
