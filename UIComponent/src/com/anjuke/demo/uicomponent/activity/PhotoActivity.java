/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * PhotoActivity.java
 *
 */
package com.anjuke.demo.uicomponent.activity;

import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.anjuke.library.uicomponent.activity.LargePhotoActivity;
import com.anjuke.library.uicomponent.photo.EndlessCircleIndicator;
import com.anjuke.library.uicomponent.photo.EndlessViewPager;
import com.anjuke.library.uicomponent.photo.PhotoGallery;
import com.anjuke.library.uicomponent.photo.PhotoPoint;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoItemClick;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-13
 */
public class PhotoActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		switch (getIntent().getIntExtra("type", 0)) {
		case 0:
			getSupportFragmentManager().beginTransaction().add(R.id.fragment, new PhotoGalleryFragment()).commit();
			break;
		case 1:
			getSupportFragmentManager().beginTransaction().add(R.id.fragment, new PhotoFragmentViewPagerFragment()).commit();
			break;

		default:
			break;
		}
	}
	public static class PhotoLoader implements OnPhotoLoader{

		@Override
		public void loadImage(ImageView photoIV, String url) {
			AQuery aQuery = new AQuery(photoIV);
			aQuery.image(url, true, false);
		}
		
	}
	public static class PhotoGalleryFragment extends Fragment {
		AQuery aq;
		PhotoGallery gallery;
		PhotoPoint point;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_photo_gallery, null);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			aq = new AQuery(view);
			gallery = (PhotoGallery) view.findViewById(R.id.ui_photo_gallery);
			point = (PhotoPoint) view.findViewById(R.id.ui_photo_point);
			
			gallery.setLoader(new PhotoLoader());
			gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					point.setActivatePoint(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971502", JSONObject.class, new AjaxCallback<JSONObject>() {
				@Override
				public void callback(String url, JSONObject object, AjaxStatus status) {
					Log.d("zqt", object.toString());
					final List<String> photos = JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class);
					gallery.setData(photos);
					gallery.setSelection(photos.size() * 5);
					point.setPointCount(photos.size());
					gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							LargePhotoActivity.start(getActivity(), TestLargePhotoActivity1.class, position%photos.size());
						}
					});
				}
			});
		}

	}

	public static class PhotoFragmentViewPagerFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_photo_viewpager, null);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			AQuery aq = new AQuery(view);
			final EndlessViewPager fixedViewPager = (EndlessViewPager) view.findViewById(R.id.ui_photo_fixed_viewpager);
			final EndlessCircleIndicator fixedIndicator = (EndlessCircleIndicator) view.findViewById(R.id.fixed_indicator);
			aq.ajax("http://api.anjuke.com/jinpu/1.0/shop/4971502", JSONObject.class, new AjaxCallback<JSONObject>() {
				@Override
				public void callback(String url, JSONObject object, AjaxStatus status) {
					Log.d("zqt", object.toString());
					final List<String> photos = JSON.parseArray(object.optJSONObject("detail").optString("photos"), String.class);
					fixedViewPager.setData(getActivity().getSupportFragmentManager(), photos, new PhotoLoader(), new OnPhotoItemClick() {

						@Override
						public void onItemClick(String url, int position) {
							LargePhotoActivity.start(getActivity(), TestLargePhotoActivity1.class, position%photos.size());
						}
					});
					fixedIndicator.setCount(photos.size());
					fixedIndicator.setViewPager(fixedViewPager);
				}
			});
		}

	}
}
