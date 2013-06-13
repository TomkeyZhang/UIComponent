/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * EndlessViewPager2.java
 *
 */
package com.anjuke.anjukelib.uicomponent.photo;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anjuke.anjukelib.uicomponent.photo.listener.IPhotoLoader;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-3
 */
class EndlessFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private List<String> photos;
	private IPhotoLoader loader;
	private int itemResId;

	public EndlessFragmentPagerAdapter(FragmentManager manager, List<String> photos, IPhotoLoader loader,int itemResId) {
		super(manager);
		this.photos = photos;
		this.loader = loader;
		this.itemResId=itemResId;
	}

	public void setLoader(IPhotoLoader loader) {
		this.loader = loader;
	}

	@Override
	public Fragment getItem(int position) {
		if (photos != null && photos.size() > 0) {
			position = position % photos.size(); // use modulo for infinite cycling
			return PhotoFragment.newInstance(photos.get(position), loader,itemResId);
		} else {
			return PhotoFragment.newInstance("", loader,itemResId);
		}
	}

	@Override
	public int getCount() {
		if (photos != null && photos.size() > 0) {
			return photos.size() * EndlessViewPager.LOOPS_COUNT; // simulate infinite by big number of products
		} else {
			return 1;
		}
	}

	public static class PhotoFragment extends Fragment {

		public static PhotoFragment newInstance(String photo, IPhotoLoader loader,int itemResId) {
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
			IPhotoLoader loader = (IPhotoLoader) getArguments().getSerializable("loader");
			if (loader != null)
				loader.loadImage((ImageView) view.findViewById(R.id.ui_photo_iv), getArguments().getString("photo"));
		}

	}
}
