/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * LargePhotoActivity.java
 *
 */
package com.anjuke.uicomponent.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.anjuke.anjukelib.uicomponent.photo.EndlessViewPager;
import com.anjuke.anjukelib.uicomponent.photo.listener.IPhotoItemClick;
import com.anjuke.anjukelib.uicomponent.photo.listener.IPhotoLoader;
import com.anjuke.anjukelib.uicomponent.photo.photoview.EndlessPagerAdapter;
import com.anjuke.anjukelib.uicomponent.photo.photoview.PhotoView;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-13
 */
public class LargePhotoActivity extends SherlockFragmentActivity implements IPhotoLoader, OnPageChangeListener {
	private static final long serialVersionUID = -1411139388959660618L;
	private EndlessViewPager mViewPager;
	private AQuery aq;
	private List<String> photos;

	public static Intent getLaunchIntent(Activity activity, int position) {
		return new Intent(activity, LargePhotoActivity.class).putExtra("position", position);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new EndlessViewPager(this);
		setContentView(mViewPager);
		// mViewPager.setAdapter(new SamplePagerAdapter());
		aq = new AQuery(this);
		
		aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971502", JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				Log.d("zqt", object.toString());
				photos = JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class);
				mViewPager.setAdapter(new EndlessPagerAdapter(photos, LargePhotoActivity.this));
				mViewPager.setOnPageChangeListener(LargePhotoActivity.this);
				onPageSelected(0);
			}
		});

	}

	@Override
	public void loadImage(ImageView photoIV, String url) {
		AQuery aQuery = new AQuery(photoIV);
		aQuery.image(url, true, false);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		int length = photos.size();
		setTitle((position % length+1) + "/" + length);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
