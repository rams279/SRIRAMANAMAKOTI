package com.atharva.rama.sriramakoti;

import android.graphics.Bitmap;

/**
 * Created by Rama on 29-04-2017.
 */

public class ListItem {

	private Bitmap mSriramaBitMap;

	public ListItem (Bitmap pSriramaImage) {
		mSriramaBitMap = pSriramaImage;
	}


	public Bitmap getSriramaBitMap () {
		return mSriramaBitMap;
	}


}
