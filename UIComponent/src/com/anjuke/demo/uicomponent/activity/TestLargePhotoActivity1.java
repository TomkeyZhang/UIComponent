/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * LargePhotoActivity.java
 *
 */
package com.anjuke.demo.uicomponent.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.anjuke.library.uicomponent.activity.LargePhotoActivity;
import com.anjuke.library.uicomponent.activity.LargePhotoActivity.TitleAdapter;
import com.anjuke.library.uicomponent.photo.EndlessViewPager;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.library.uicomponent.photo.photoview.PhotoPagerAdapter;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-13
 */
public class TestLargePhotoActivity1 extends LargePhotoActivity {
	private AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq=new AQuery(this);
		aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971502", JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				Log.d("zqt", url);
				final PhotoList[] photoLists = new PhotoList[3];
				photoLists[0] = new PhotoList();
				photoLists[0].setPhotos(JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class));
				photoLists[0].setTitle("室内图");
				aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971503", JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object, AjaxStatus status) {
						Log.d("zqt", url);
						photoLists[1] = new PhotoList();
						photoLists[1].setPhotos(JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class));
						photoLists[1].setTitle("样板间图");
						aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971505", JSONObject.class, new AjaxCallback<JSONObject>() {
							@Override
							public void callback(String url, JSONObject object, AjaxStatus status) {
								Log.d("zqt", url);
								photoLists[2] = new PhotoList();
								photoLists[2].setPhotos(JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class));
								photoLists[2].setTitle("规划图");
								updateUI(photoLists);
								startDirective();
							}
						});
					}
				});
			}
		});

	}

}
