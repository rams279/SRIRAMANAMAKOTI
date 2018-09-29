package com.atharva.rama.sriramakoti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Rama on 29-04-2017.
 */

public class BitmapWorkerTask extends AsyncTask<File, Void, Bitmap> {
	WeakReference<ImageView> mImageViewWeakReference;


	private  File mImageFile;
	public BitmapWorkerTask (ImageView pImageView) {
		mImageViewWeakReference = new WeakReference<ImageView> (pImageView);
	}

	@Override
	protected Bitmap doInBackground (File... params) {
		//Log.i (Constants.LOG_TAG, "BitmapWorkerTask >> doInBackground -  " + params[0].getAbsolutePath ());
		//return BitmapFactory.decodeFile (params[0].getAbsolutePath ());
		mImageFile = params[0];
		return decodeBitmapFromFile (params[0]);
	}

	@Override
	protected void onPostExecute (Bitmap pBitmap) {
//		if (pBitmap != null && mImageViewWeakReference != null) {
//			ImageView lImageView = mImageViewWeakReference.get ();
//			if (lImageView != null) {
//				lImageView.setImageBitmap (pBitmap);
//			}
//		}
		if(isCancelled ())
		{
			pBitmap = null;
		}
		if(pBitmap != null && mImageViewWeakReference != null)
		{
			ImageView lImageView = mImageViewWeakReference.get ();
			BitmapWorkerTask lBitmapWorkerTask = SriramaRecyclerViewAdapter.getBitmapWorkerTask (lImageView);
			if(this ==lBitmapWorkerTask && lImageView != null ){
				lImageView.setImageBitmap (pBitmap);
			}
		}
	}

	private int calculateInSampleSize (BitmapFactory.Options pBitmapOptions) {
		final int photoImageWidth = pBitmapOptions.outWidth;
		final int photoImageHeight = pBitmapOptions.outHeight;
		int scalefator = 1;
		if (photoImageHeight > R.dimen.image_h || photoImageWidth > R.dimen.image_w) {
			final int halfPhotoImageWidth = photoImageWidth / 2;
			final int halfPhotImageHeight = photoImageHeight / 2;
			while (halfPhotImageHeight / scalefator > R.dimen.image_h ||
					halfPhotoImageWidth / scalefator > R.dimen.image_w) {
				scalefator *= 2;

			}
		}
		return scalefator;
	}

	private Bitmap decodeBitmapFromFile (File pFile) {
		BitmapFactory.Options lBitmapOptions = new BitmapFactory.Options ();
		lBitmapOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile (pFile.getAbsolutePath (), lBitmapOptions);
		lBitmapOptions.inSampleSize = calculateInSampleSize (lBitmapOptions);
		lBitmapOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile (pFile.getAbsolutePath (), lBitmapOptions);
	}

	public File getImageFile () {
		return mImageFile;
	}

}
