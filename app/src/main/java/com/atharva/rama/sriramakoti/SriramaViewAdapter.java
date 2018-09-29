package com.atharva.rama.sriramakoti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static com.atharva.rama.sriramakoti.Constants.*;

/**
 * Created by Rama on 27-04-2016.
 */
public class SriramaViewAdapter extends BaseAdapter {

	private Context mCotext;
	private String mFileStrings[];
	private SriramaUtility mSriramaUtility;
	private String mDataDirectory;

	public SriramaViewAdapter (Context pContext) {
		mCotext = pContext;
		mSriramaUtility = SriramaUtility.getInstance (pContext);
		mFileStrings = mSriramaUtility.getFilesList ();
		mDataDirectory = mSriramaUtility.getDataDirectory ();
	}

	@Override
	public int getCount () {
		return mFileStrings.length;
	}

	@Override
	public Object getItem (int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId (int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView (int pImageID, View pImage, ViewGroup pImageGroup) {
		ImageView lImgeView;
		if (pImage == null) {
			lImgeView = new ImageView (mCotext);
			lImgeView.setLayoutParams (new GridView.LayoutParams (200, 100));
			lImgeView.setScaleType (ImageView.ScaleType.CENTER_INSIDE);
			lImgeView.setPadding (IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING);

		} else {
			lImgeView = (ImageView) pImage;
		}
		Bitmap bitmap = BitmapFactory.decodeFile ( mDataDirectory+ mFileStrings[pImageID]);
		lImgeView.setImageBitmap (bitmap);

		return lImgeView;
	}

	public void setFileStrings (String[] pFileStrings) {
		mFileStrings = pFileStrings;
	}
}
