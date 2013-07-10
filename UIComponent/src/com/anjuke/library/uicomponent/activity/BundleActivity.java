/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * BundleActivity.java
 *
 */
package com.anjuke.library.uicomponent.activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 可自动保存Intent中Bundle信息的Activity基类，子类只需调用getIntentExtras()来获取上一个Activity传来的参数即可 <br>
 * 用于解决Activity重新启动后，原有的Intent中的参数丢失问题
 * 
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-31
 */
public class BundleActivity extends FragmentActivity {
	private Bundle mSavedState;

	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		if (savedState == null) {
			this.mSavedState = getIntent().getExtras();
		} else {
			this.mSavedState = savedState;
		}
	};

	/**
	 * 获取Intent传过来的参数，用于替代方法getIntent().getExtras() <br>
	 * 用于解决Activity重新启动后，原有的Intent中的参数丢失问题，已做容错处理，可以放心调用
	 * 
	 * @return
	 */
	public Bundle getIntentExtras() {
		if (mSavedState == null)
			mSavedState = new Bundle();
		return mSavedState;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mSavedState != null)
			outState.putAll(mSavedState);
	}
}
