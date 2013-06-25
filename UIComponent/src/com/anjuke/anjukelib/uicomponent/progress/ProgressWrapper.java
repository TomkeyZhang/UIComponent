/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * ProgressWraper.java
 *
 */
package com.anjuke.anjukelib.uicomponent.progress;

import com.anjuke.uicomponent.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 单个界面UI状态切换包装类
 * 
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-29
 */
public class ProgressWrapper {
    private ViewGroup mRootView;
    private View mProgressView;
    private View mEmptyView;
    private View mContentView;
    private View mErrorView;
    private Animation mInAnimation;
    private Animation mOutAnimation;

    /**
     * activity 的content view中需要有四个view <br>
     * R.id.ui_progress_view <br>
     * R.id.ui_empty_view <br>
     * R.id.ui_content_view <br>
     * R.id.ui_error_view
     * 
     * @param activity
     */
    public ProgressWrapper(Activity activity) {
        mProgressView = activity.findViewById(R.id.ui_progress_view);
        mEmptyView = activity.findViewById(R.id.ui_empty_view);
        mContentView = activity.findViewById(R.id.ui_content_view);
        mErrorView = activity.findViewById(R.id.ui_error_view);
        if (mProgressView == null)
            throw new IllegalArgumentException("你的activity需要一个id为 R.id.ui_progress_view的View");
        if (mEmptyView == null)
            throw new IllegalArgumentException("你的activity需要一个id为 R.id.ui_empty_view的View");
        if (mContentView == null)
            throw new IllegalArgumentException("你的activity需要一个id为 R.id.ui_content_view的View");
        if (mErrorView == null)
            throw new IllegalArgumentException("你的activity需要一个id为 R.id.ui_error_view的View");
        mRootView = (ViewGroup) mContentView.getParent();
        mInAnimation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
        mOutAnimation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
    }

    /**
     * rootView中需要有四个子view <br>
     * R.id.ui_progress_view <br>
     * R.id.ui_empty_view <br>
     * R.id.ui_content_view <br>
     * R.id.ui_error_view
     * 
     * @param rootView
     */
    public ProgressWrapper(ViewGroup rootView) {
        mProgressView = rootView.findViewById(R.id.ui_progress_view);
        mEmptyView = rootView.findViewById(R.id.ui_empty_view);
        mContentView = rootView.findViewById(R.id.ui_content_view);
        mErrorView = rootView.findViewById(R.id.ui_error_view);
        if (mProgressView == null)
            throw new IllegalArgumentException("你的rootView需要一个id为 R.id.ui_progress_view的子View");
        if (mEmptyView == null)
            throw new IllegalArgumentException("你的rootView需要一个id为 R.id.ui_empty_view的子View");
        if (mContentView == null)
            throw new IllegalArgumentException("你的rootView需要一个id为 R.id.ui_content_view的子View");
        if (mErrorView == null)
            throw new IllegalArgumentException("你的rootView需要一个id为 R.id.ui_error_view的子View");
        mRootView = (ViewGroup) rootView;
        mInAnimation = AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_in);
        mOutAnimation = AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_out);
    }

    /**
     * 重设ProgressView
     * 
     * @param progressView
     */
    public void setProgressView(View progressView) {
        if (progressView != null) {
            mRootView.removeView(mProgressView);
            mRootView.addView(progressView);
            this.mProgressView = progressView;
        }
    }

    /**
     * 重设EmptyView
     * 
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (emptyView != null) {
            mRootView.removeView(mEmptyView);
            mRootView.addView(emptyView);
            this.mEmptyView = emptyView;
        }

    }

    /**
     * 重设ContentView
     * 
     * @param contentView
     */
    public void setContentView(View contentView) {
        if (contentView != null) {
            mRootView.removeView(mContentView);
            mRootView.addView(contentView);
            this.mContentView = contentView;
        }

    }

    /**
     * 重设ErrorView
     * 
     * @param errorView
     */
    public void setErrorView(View errorView) {
        if (errorView != null) {
            mRootView.removeView(mErrorView);
            mRootView.addView(errorView);
            this.mErrorView = errorView;
        }
    }
    /**
     * 将UI状态从Progress切换为Empty
     */
    public void progressToEmpty() {
        if (isVisible(mProgressView) && isGone(mEmptyView)) {
            mProgressView.startAnimation(mOutAnimation);
            mEmptyView.startAnimation(mInAnimation);
            gone(mProgressView);
            visible(mEmptyView);
        }
    }
    /**
     * 将UI状态从Progress切换为Content
     */
    public void progressToContent() {
        if (isVisible(mProgressView) && isGone(mContentView)) {
            mProgressView.startAnimation(mOutAnimation);
            mContentView.startAnimation(mInAnimation);
            gone(mProgressView);
            visible(mContentView);
        }
    }
    /**
     * 将UI状态从Progress切换为Error
     */
    public void progressToError() {
        if (isVisible(mProgressView) && isGone(mErrorView)) {
            mProgressView.startAnimation(mOutAnimation);
            mErrorView.startAnimation(mInAnimation);
            gone(mProgressView);
            visible(mErrorView);
        }
    }
    /**
     * 将UI状态从Empty切换为Progress
     */
    public void emptyToProgress() {
        if (isVisible(mEmptyView) && isGone(mProgressView)) {
            mEmptyView.startAnimation(mOutAnimation);
            mProgressView.startAnimation(mInAnimation);
            gone(mEmptyView);
            visible(mProgressView);
        }
    }
    /**
     * 将UI状态从content切换为Progress
     */
    public void contentToProgress() {
        if (isVisible(mContentView) && isGone(mProgressView)) {
            mContentView.startAnimation(mOutAnimation);
            mProgressView.startAnimation(mInAnimation);
            gone(mContentView);
            visible(mProgressView);
        }
    }
    /**
     * 将UI状态从Error切换为Progress
     */
    public void errorToProgress() {
        if (isVisible(mErrorView) && isGone(mProgressView)) {
            mErrorView.startAnimation(mOutAnimation);
            mProgressView.startAnimation(mInAnimation);
            gone(mErrorView);
            visible(mProgressView);
        }
    }
    /**
     * 显示ProgressView，隐藏其它
     */
    public void showProgress() {
        visible(mProgressView);
        gone(mEmptyView);
        gone(mContentView);
        gone(mErrorView);
    }
    /**
     * 显示EmptyView，隐藏其它
     */
    public void showEmpty() {
        gone(mProgressView);
        visible(mEmptyView);
        gone(mContentView);
        gone(mErrorView);
    }
    /**
     * 显示ContentView，隐藏其它
     */
    public void showContent() {
        gone(mProgressView);
        gone(mEmptyView);
        visible(mContentView);
        gone(mErrorView);
    }
    /**
     * 显示ErrorView，隐藏其它
     */
    public void showError() {
        gone(mProgressView);
        gone(mEmptyView);
        gone(mContentView);
        visible(mErrorView);
    }

    private boolean isVisible(View v) {
        return v.getVisibility() == View.VISIBLE;
    }

    private boolean isGone(View v) {
        return v.getVisibility() == View.GONE;
    }

    private void visible(View v) {
        v.setVisibility(View.VISIBLE);
    }

    private void gone(View v) {
        v.setVisibility(View.GONE);
    }
}
