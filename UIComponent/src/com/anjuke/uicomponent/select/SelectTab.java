/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectItem.java
 *
 */
package com.anjuke.uicomponent.select;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-22
 */
public class SelectTab implements OnCheckedChangeListener {
    private CompoundButton contentTB;
    private ImageView arrowIV;
    private View lineView;
    private Context context;
    private View itemView;
    private View popView;
    private OnCheckedListener listener;
    private int lineColor ;

    public SelectTab(Context context, View popView, OnCheckedListener listener,int lineColor) {
        this.context = context;
        this.listener = listener;
        this.popView = popView;
        this.lineColor=lineColor;
        init();
    }

    private void init() {
        itemView = View.inflate(context, R.layout.ui_tab_select, null);
        contentTB = (CompoundButton) itemView.findViewById(R.id.ui_content_tb);
        arrowIV = (ImageView) itemView.findViewById(R.id.ui_arrow_view);
        lineView = itemView.findViewById(R.id.ui_line_view);
        contentTB.setOnCheckedChangeListener(this);
    }

    public View getItemView() {
        return itemView;
    }

    public View getPopView() {
        return popView;
    }

    public void setText(String text) {
        contentTB.setText(text);
    }

    public void setChecked(boolean checked) {
        contentTB.setOnCheckedChangeListener(null);
        contentTB.setChecked(checked);
        contentTB.setOnCheckedChangeListener(this);
        if (checked) {
            arrowIV.setImageResource(R.drawable.ui_icon_arrow_screen_up);
            lineView.setBackgroundColor(lineColor);
        } else {
            arrowIV.setImageResource(R.drawable.ui_icon_arrow_screen_down);
            lineView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setContentBackgroundSelector(int selector) {
        contentTB.setBackgroundResource(selector);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public static interface OnCheckedListener {
        void onCheckedChanged(SelectTab selectedTab, boolean isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setChecked(isChecked);
        listener.onCheckedChanged(this, isChecked);
    }
}
