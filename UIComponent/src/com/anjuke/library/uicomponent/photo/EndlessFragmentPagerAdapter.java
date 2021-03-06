/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * EndlessViewPager2.java
 *
 */
package com.anjuke.library.uicomponent.photo;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-3
 */
class EndlessFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private List<String> mPhotos;
	private OnPhotoLoader mLoader;
	private int mItemResId;

	public EndlessFragmentPagerAdapter(FragmentManager manager, List<String> photos, OnPhotoLoader loader,int itemResId) {
		super(manager);
		this.mPhotos = photos;
		this.mLoader = loader;
		this.mItemResId=itemResId;
	}

	public void setLoader(OnPhotoLoader loader) {
		this.mLoader = loader;
	}

	@Override
	public Fragment getItem(int position) {
		if (mPhotos != null && mPhotos.size() > 0) {
			position = position % mPhotos.size(); // use modulo for infinite cycling
			return PhotoFragment.newInstance(mPhotos.get(position), mLoader,mItemResId);
		} else {
			return PhotoFragment.newInstance("", mLoader,mItemResId);
		}
	}

	@Override
	public int getCount() {
		if (mPhotos != null && mPhotos.size() > 0) {
			return mPhotos.size() * EndlessViewPager.LOOPS_COUNT; // simulate infinite by big number of products
		} else {
			return 1;
		}
	}

	public static class PhotoFragment extends Fragment {

		public static PhotoFragment newInstance(String photo, OnPhotoLoader loader,int itemResId) {
			PhotoFragment fragment = new PhotoFragment();
			Bundle args = new Bundle();
			args.putString("photo", photo);
			args.putSerializable("loader", loader);
			args.putInt("itemResId", itemResId);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return View.inflate(getActivity(),getArguments().getInt("itemResId"), null);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			OnPhotoLoader loader = (OnPhotoLoader) getArguments().getSerializable("loader");
			if (loader != null)
				loader.loadImage((ImageView) view.findViewById(R.id.ui_photo_iv), getArguments().getString("photo"));
		}

	}
}
