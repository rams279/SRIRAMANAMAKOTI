package com.atharva.rama.sriramakoti;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Rama on 29-04-2017.
 */

public class SriramaRecyclerViewAdapter extends RecyclerView.Adapter<SriramaRecyclerViewAdapter.ViewHolder> {


	//private List<ListItem> mListItems;
	private Context mContext;
	private SriramaUtility mSriramaUtility;
	private Bitmap mplaceHolderBitmap;


	public static class AsyncDrawable extends BitmapDrawable {


		final WeakReference<BitmapWorkerTask> taskReference;

		public AsyncDrawable (Resources R, Bitmap pBitmap, BitmapWorkerTask pBitmapWorkerTask) {
			super (R, pBitmap);
			taskReference = new WeakReference (pBitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask () {
			return taskReference.get ();
		}
	}


	public SriramaRecyclerViewAdapter (Context pContext) {
		//mListItems = pListItem;
		mContext = pContext;
		mSriramaUtility = SriramaUtility.getInstance (pContext);
	}

	@Override
	public SriramaRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
		View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.newsriramalist, parent, false);
		return new ViewHolder (view);

	}

	@Override
	public void onBindViewHolder (SriramaRecyclerViewAdapter.ViewHolder holder, int position) {
//		ListItem lListItem = mListItems.get (position);
//		holder.imImageView.setImageBitmap (lListItem.getSriramaBitMap ());
		File lImageFile = new File (mSriramaUtility.getDataDirectory () + mSriramaUtility.getFilesList ()[position]);
		//BitmapWorkerTask lBitmapWorkerTask = new BitmapWorkerTask (holder.imImageView);
		//lBitmapWorkerTask.execute (lImageFile);
		if (checkBitmapWorkerTask (lImageFile, holder.imImageView)) {
			BitmapWorkerTask lBitmapWorkerTask = new BitmapWorkerTask (holder.imImageView);
			AsyncDrawable lAsyncDrawable = new AsyncDrawable (holder.imImageView.getResources (), mplaceHolderBitmap, lBitmapWorkerTask);
			holder.imImageView.setImageDrawable (lAsyncDrawable);
			lBitmapWorkerTask.execute (lImageFile);
		}

	}

	@Override
	public int getItemCount () {
		return mSriramaUtility.getFilesList ().length;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public AppCompatImageView imImageView;

		public ViewHolder (View itemView) {
			super (itemView);

			imImageView = (AppCompatImageView) itemView.findViewById (R.id.recyclerImageView);
			imImageView.setLayoutParams (new LinearLayout.LayoutParams (200, 100));
			imImageView.setMaxWidth (R.dimen.image_w);
			imImageView.setMaxHeight (R.dimen.image_h);
			//imImageView.setLayoutParams (new GridView.LayoutParams (200, 100));
			imImageView.setScaleType (ImageView.ScaleType.FIT_CENTER);
			//imImageView.setPadding (IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING);
		}
	}

	public static BitmapWorkerTask getBitmapWorkerTask (ImageView pImageView) {
		Drawable lDrawable = pImageView.getDrawable ();
		if (lDrawable instanceof AsyncDrawable) {
			AsyncDrawable lAsyncDrawable = (AsyncDrawable) lDrawable;
			return lAsyncDrawable.getBitmapWorkerTask ();
		}
		return null;
	}

	public static boolean checkBitmapWorkerTask (File pFile, ImageView pImageView) {

		BitmapWorkerTask lBitmapWorkerTask = getBitmapWorkerTask (pImageView);
		if (lBitmapWorkerTask != null) {
			final File lworkerFile = lBitmapWorkerTask.getImageFile ();
			if (lworkerFile != pFile) {
				lBitmapWorkerTask.cancel (true);

			} else {
				// Bitmap worker file is the same as image view - do nothing
				return false;
			}

		}
		return true;
	}
}