/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * LargeImageActivity.java
 *
 */
package com.anjuke.library.uicomponent.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.anjuke.library.uicomponent.photo.EndlessViewPager;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoItemClick;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.library.uicomponent.photo.photoview.PhotoPagerAdapter;
import com.anjuke.uicomponent.R;

/**
 * 
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-24
 */
public class LargePhotoActivity extends BundleActivity implements OnPhotoLoader, OnPageChangeListener, OnPhotoItemClick, OnItemSelectedListener, OnItemClickListener {
	public static final String EXTRA_POSTION = "position";
	protected EndlessViewPager mPager;
	protected Gallery mGallery;
	protected ImageButton mCallPhoneBtn;
	protected TextView mCountTV;
	protected List<String> mPhotos;
	private ImageView mDirectiveIV;
	private PhotoList[] mPhotoLists;
	private List<Integer> mBasePosition;
	private int mNextPosition;

	public static void start(Activity activity, Class<? extends LargePhotoActivity> clz, int position) {
		activity.startActivity(new Intent(activity, clz).putExtra(EXTRA_POSTION, position));
		activity.overridePendingTransition(R.anim.ui_in_zoom, R.anim.ui_none);
	}
 
	@Override
	protected void onCreate(Bundle savedState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedState);
		setContentView(R.layout.ui_activity_large_photo);
		mPager = (EndlessViewPager) findViewById(R.id.ui_photo_evp);
		mGallery = (Gallery) findViewById(R.id.ui_title_gallery);

		mCallPhoneBtn = (ImageButton) findViewById(R.id.ui_call_phone_btn);
		mCountTV = (TextView) findViewById(R.id.ui_count_tv);
		mDirectiveIV = (ImageView) findViewById(R.id.ui_directive_iv);
		mGallery.setOnItemSelectedListener(this);
		mGallery.setOnItemClickListener(this);
	}

	public void updateUI(PhotoList[] photoLists) {
		this.mPhotoLists = photoLists;
		this.mPhotos = new ArrayList<String>();
		TitleAdapter adapter = new TitleAdapter(this);
		int i = 0;
		mBasePosition = new ArrayList<Integer>();
		mBasePosition.add(i);
		for (PhotoList photoList : photoLists) {
			adapter.addTitle(photoList.getTitle());
			mPhotos.addAll(photoList.getPhotos());
			i = i + photoList.getPhotos().size();
			mBasePosition.add(i);
		}
		mGallery.setAdapter(adapter);
		updateUI();
	}

	public void updateUI(List<String> photos) {
		this.mPhotos = photos;
		mGallery.setVisibility(View.GONE);
		updateUI();
	}

	private void updateUI() {
		mPager.setAdapter(new PhotoPagerAdapter(mPhotos, LargePhotoActivity.this));
		mPager.setOnPageChangeListener(LargePhotoActivity.this);
		mNextPosition = getIntentExtras().getInt(EXTRA_POSTION, 0) % mPhotos.size();
		// Log.d("zqt", "nextPosition :" + nextPosition);
		onPageSelected(mNextPosition);
		mPager.setCurrentItem(mNextPosition, false);
	}
	protected void startDirective() {
		mDirectiveIV.setVisibility(View.VISIBLE);
		mDirectiveIV.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ui_directive));
		mDirectiveIV.setVisibility(View.GONE);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
		// Log.d("zqt", "onItemClick pager.getCurrentItem()%photos.size()+1:" + (pager.getCurrentItem() % photos.size() + 1));
		// Log.d("zqt", "onItemClick position:" + position);
		// Log.d("zqt", "onItemClick basePosition.get(position):" + basePosition.get(position));
		// Log.d("zqt", "onItemClick nextPosition:" + nextPosition);
		// onPageSelected(basePosition.get(position));
		if (mNextPosition > 0) {
			mPager.setCurrentItem(mNextPosition, false);
		} else {
			mPager.setCurrentItem(mBasePosition.get(position), false);
		}
		mNextPosition = -1;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		position = position % mPhotos.size();
		Log.d("zqt", "onPageSelected position:" + position);
		if (mPhotoLists == null || mPhotoLists.length == 0) {
			int length = mPhotos.size();
			mCountTV.setText((position + 1) + "/" + length);
		} else {
			int currPosition = position;
			for (int i = 0; i < mPhotoLists.length; i++) {
				List<String> currPhotos = mPhotoLists[i].getPhotos();
				if (currPhotos.contains(mPhotos.get(position))) {
					if (i < mGallery.getSelectedItemPosition()) {
						mNextPosition = mBasePosition.get(i + 1) - 1;
					} else if (i > mGallery.getSelectedItemPosition()) {
						mNextPosition = mBasePosition.get(i);
					}
					mGallery.setSelection(i);
					int length = currPhotos.size();
					mCountTV.setText((currPosition % length + 1) + "/" + length);
					break;
				} else {
					currPosition = currPosition - currPhotos.size();
				}

			}

		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void loadImage(ImageView photoIV, String url) {
		AQuery aQuery = new AQuery(photoIV);
		aQuery.image(url, true, false);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.ui_none, R.anim.ui_out_zoom);
	}

	@Override
	public void onItemClick(String url, int position) {
		onBackPressed();
	}

	public static class TitleAdapter extends BaseAdapter {
		private List<String> titles = new ArrayList<String>();
		private Context activity;

		public TitleAdapter(Activity activity) {
			this.activity = activity;
		}

		public void addTitle(String title) {
			titles.add(title);
		}

		@Override
		public int getCount() {
			return titles.size();
		}

		@Override
		public Object getItem(int position) {
			return titles.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView titleTV;
			if (convertView == null) {
				titleTV = (TextView) View.inflate(activity, R.layout.ui_item_photo_title, null);
			} else {
				titleTV = (TextView) convertView;
			}
			titleTV.setText(titles.get(position));
			return titleTV;
		}

	}

	public static class PhotoList {
		private String title;
		private List<String> photos;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<String> getPhotos() {
			return photos;
		}

		public void setPhotos(List<String> photos) {
			this.photos = photos;
		}

	}

}
