/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * EndlessPagerAdapter.java
 *
 */
package com.anjuke.anjukelib.uicomponent.photo.photoview;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.anjuke.anjukelib.uicomponent.photo.listener.IPhotoLoader;
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
public class EndlessPagerAdapter extends PagerAdapter {

	private List<String> photos = new ArrayList<String>();
	private IPhotoLoader loader;

	public EndlessPagerAdapter(List<String> photos, IPhotoLoader loader) {
		this.photos = photos;
		this.loader = loader;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(container.getContext(), R.layout.ui_item_photo_large, null);
		PhotoView photoView = (PhotoView) view.findViewById(R.id.ui_photo_iv);
		container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if (loader != null)
			loader.loadImage(photoView, photos.get(position % photos.size()));
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
