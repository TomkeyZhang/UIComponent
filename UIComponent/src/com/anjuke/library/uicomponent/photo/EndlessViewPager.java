package com.anjuke.library.uicomponent.photo;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoItemClick;
import com.anjuke.library.uicomponent.photo.listener.OnPhotoLoader;
import com.anjuke.uicomponent.R;

public class EndlessViewPager extends ViewPager implements OnGestureListener {
	public static final int LOOPS_COUNT = 1000;
	private GestureDetector mGestureDetector;
	private OnPhotoItemClick mItemClick;
	private List<String> mList;

	public EndlessViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EndlessViewPager(Context context) {
		super(context);
		init();
	}

	private void init() {
		mGestureDetector = new GestureDetector(getContext(), this);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if (mItemClick != null&&mList!=null) {
			int position = getCurrentItem() % mList.size();
			mItemClick.onItemClick(mList.get(position), position);
		}
		return false;
	}

	public void setData(FragmentManager fm, List<String> list, OnPhotoLoader loader, OnPhotoItemClick itemClick) {
		setData(fm, list, loader, itemClick,getChildCount() * LOOPS_COUNT / 2 + 1, R.layout.ui_item_photo);
	}

	public void setData(FragmentManager fm, List<String> list, OnPhotoLoader loader, OnPhotoItemClick itemClick,int item, int itemResId) {
		this.mList = list;
		this.mItemClick = itemClick;
		setAdapter(new EndlessFragmentPagerAdapter(fm, list, loader, itemResId));
		setCurrentItem(item, false);
	}
	public void setItemClick(OnPhotoItemClick itemClick) {
		this.mItemClick=itemClick;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

}
