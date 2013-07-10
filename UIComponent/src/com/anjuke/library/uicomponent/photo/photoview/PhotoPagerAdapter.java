/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * EndlessPagerAdapter.java
 *
 */
package com.anjuke.library.uicomponent.photo.photoview;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.library.uicomponent.photo.photoview.PhotoViewAttacher.OnViewTapListener;
import com.anjuke.uicomponent.R;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-13
 */
public class PhotoPagerAdapter extends PagerAdapter {

	private List<String> mPhotos = new ArrayList<String>();
	private OnPhotoLoader mLoader;

	public PhotoPagerAdapter(List<String> photos, OnPhotoLoader loader) {
		this.mPhotos = photos;
		this.mLoader = loader;
	}
	@Override
	public int getCount() {
		return mPhotos.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(container.getContext(), R.layout.ui_item_photo_large, null);
		final PhotoView photoView = (PhotoView) view.findViewById(R.id.ui_photo_iv);
		photoView.setOnViewTapListener(new OnViewTapListener() {

			@Override
			public void onViewTap(View view, float x, float y) {
				((Activity) photoView.getContext()).finish();
			}
		});
		container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if (mLoader != null)
			mLoader.loadImage(photoView, mPhotos.get(position % mPhotos.size()));
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
