/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectItem.java
 *
 */
package com.anjuke.anjukelib.uicomponent.select;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.anjuke.uicomponent.R;

/**
 * 每个筛选选项
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-22
 */
public class SelectTab implements OnCheckedChangeListener {
    private CompoundButton mContentTB;
    private ImageView mArrowIV;
    private View mLineView;
    private Context mContext;
    private View mItemView;
    private View mPopupView;
    private OnCheckedListener mCheckedListener;
    private int mLineColor ;

    public SelectTab(Context context, View popView, OnCheckedListener listener,int lineColor) {
        this.mContext = context;
        this.mCheckedListener = listener;
        this.mPopupView = popView;
        this.mLineColor=lineColor;
        init();
    }

    private void init() {
        mItemView = View.inflate(mContext, R.layout.ui_tab_select, null);
        mContentTB = (CompoundButton) mItemView.findViewById(R.id.ui_content_tb);
        mArrowIV = (ImageView) mItemView.findViewById(R.id.ui_arrow_view);
        mLineView = mItemView.findViewById(R.id.ui_line_view);
        mContentTB.setOnCheckedChangeListener(this);
    }

    public View getItemView() {
        return mItemView;
    }

    public View getPopView() {
        return mPopupView;
    }

    public void setText(String text) {
        mContentTB.setText(text);
    }

    public void setChecked(boolean checked) {
        mContentTB.setOnCheckedChangeListener(null);
        mContentTB.setChecked(checked);
        mContentTB.setOnCheckedChangeListener(this);
        if (checked) {
            mArrowIV.setImageResource(R.drawable.ui_icon_arrow_screen_up);
            mLineView.setBackgroundColor(mLineColor);
        } else {
            mArrowIV.setImageResource(R.drawable.ui_icon_arrow_screen_down);
            mLineView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setContentBackgroundSelector(int selector) {
        mContentTB.setBackgroundResource(selector);
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }

    public static interface OnCheckedListener {
        void onCheckedChanged(SelectTab selectedTab, boolean isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setChecked(isChecked);
        mCheckedListener.onCheckedChanged(this, isChecked);
    }
}
