/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * ProgressActivity.java
 *
 */
package com.anjuke.demo.uicomponent.activity;

import com.anjuke.library.uicomponent.activity.BundleActivity;
import com.anjuke.library.uicomponent.progress.ProgressWrapper;
import com.anjuke.uicomponent.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-5-29
 */
public class ProgressActivity extends BundleActivity implements OnClickListener {
    private Handler handler = new Handler();
    private ProgressWrapper wrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        wrapper = new ProgressWrapper(this);
        switch (getIntentExtras().getInt("position", 0)) {
        case 0:
            wrapper.showProgress();
            findViewById(R.id.ui_empty_view).setOnClickListener(this);
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    wrapper.progressToEmpty();
                }
            }, 3000);
            break;
        case 1:
            wrapper.showProgress();
            findViewById(R.id.ui_content_view).setOnClickListener(this);
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    wrapper.progressToContent();
                }
            }, 3000);
            break;
        case 2:
            wrapper.showProgress();
            findViewById(R.id.ui_error_view).setOnClickListener(this);
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    wrapper.progressToError();
                }
            }, 3000);
            break;
        case 3:
            wrapper.showEmpty();
            findViewById(R.id.ui_empty_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapper.emptyToProgress();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrapper.progressToContent();
                        }
                    }, 3000);
                }
            });
            break;
        case 4:
            wrapper.showError();
            findViewById(R.id.ui_error_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapper.errorToProgress();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrapper.progressToContent();
                        }
                    }, 3000);
                }
            });
            break;
        case 5:
            wrapper.showError();
            findViewById(R.id.ui_error_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapper.errorToProgress();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrapper.progressToEmpty();
                        }
                    }, 3000);
                }
            });
            break;
            //"EmptyToContent", "ErrorToContent", "ErrorToEmpty"
        default:
            break;
        }
        
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ui_empty_view:
            wrapper.emptyToProgress();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    wrapper.progressToEmpty();
                }
            }, 3000);
            break;
        case R.id.ui_content_view:
            wrapper.contentToProgress();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    wrapper.progressToContent();
                }
            }, 3000);
            break;
        case R.id.ui_error_view:
            wrapper.errorToProgress();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    wrapper.progressToError();
                }
            }, 3000);
            break;

        default:
            break;
        }
    }
}
