/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectBar.java
 *
 */
package com.anjuke.uicomponent.select;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.anjuke.uicomponent.R;
import com.anjuke.uicomponent.select.SelectGroupWrapper.OnGroupItemClickListener;
import com.anjuke.uicomponent.select.SelectItemAdpater.OnItemClickListener;
import com.anjuke.uicomponent.select.SelectTab.OnCheckedListener;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-22
 */
public class SelectBar extends LinearLayout implements OnCheckedListener {
    private int dividerColor = 0x1A000000;
    private int dividerPadding = 0;
    private int dividerHeight = 1;

    private SelectWindow selectWindow;
    private Paint dividerPaint;
    private List<SelectTab> items = new ArrayList<SelectTab>();
    private int arrowWith;
    private int indicatorColor;

    public SelectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerHeight);
        dividerPaint.setColor(dividerColor);
        setPadding(0, 0, 0, dividerHeight);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.ui_icon_arrow_choose, options);
        arrowWith = options.outWidth;
    }
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }
    public void addTabSingleList(String title, List<String> obejcts, OnItemClickListener itemClickListener) {
        createTab(title, wrapSingleList(obejcts, itemClickListener));
    }

    public void addTabDoubleList(String title, List<String> groups, List<List<String>> items,
            OnGroupItemClickListener groupItemClickListener) {
        createTab(title, wrapDoubleList(groups, items, groupItemClickListener));
    }

    private void createTab(String title, View popView) {
        SelectTab item = new SelectTab(getContext(), popView, this,indicatorColor);
        item.setText(title);
        addView(item.getItemView(), new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
        items.add(item);
    }

    private View wrapDoubleList(List<String> groups, List<List<String>> items,
            OnGroupItemClickListener groupItemClickListener) {
        SelectGroupWrapper wrap = new SelectGroupWrapper(getContext(), groups, items, indicatorColor);
        wrap.setGroupItemClickListener(groupItemClickListener);
        return wrap.getRoot();
    }

    private ListView wrapSingleList(List<String> obejcts, OnItemClickListener itemClickListener) {
        ListView contentLV = (ListView) View.inflate(getContext(), R.layout.ui_listview_select_one, null);
        contentLV.setBackgroundResource(R.drawable.ui_select_bg);
        SelectItemAdpater adapter = new SelectItemAdpater(getContext(), R.layout.ui_listitem_checked, obejcts);
        adapter.setListView(contentLV);
        adapter.setItemClickListener(itemClickListener);
        contentLV.setAdapter(adapter);
        return contentLV;
    }

    private void showPopup(SelectTab selectedTab) {
        if (selectWindow == null) {
            selectWindow = new SelectWindow(getContext());
        }
        if(selectWindow.isShowing()){
            selectWindow.hide();
        }
        View itemView = selectedTab.getItemView();
        selectWindow.setContentView(selectedTab.getPopView(), itemView.getRight() - itemView.getWidth() / 2 - arrowWith
                / 2 - selectWindow.getPaddingLeft());
        selectWindow.show(this);
    }

    @Override
    public FrameLayout getChildAt(int index) {
        return (FrameLayout) super.getChildAt(index);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || getChildCount() == 0) {
            return;
        }
        final int height = getHeight();
        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < getChildCount() - 1; i++) {
            View tab = getChildAt(i);
            tab.setPadding(0, 0, dividerHeight, 0);
            canvas.drawLine(tab.getRight() - dividerHeight, dividerPadding, tab.getRight() - dividerHeight, height
                    - dividerPadding, dividerPaint);
        }
        canvas.drawLine(100, dividerPadding, 100, height - dividerPadding, dividerPaint);
        canvas.drawLine(0, height - dividerHeight, getWidth(), height - dividerHeight, dividerPaint);
    }

    public void hidePopup() {
        if (selectWindow != null & selectWindow.isShowing()) {
            selectWindow.hide();
        }
        for (SelectTab tab : items) {
            tab.setChecked(false);
        }
    }
    public boolean isShowingPopup(){
        return selectWindow!=null&&selectWindow.isShowing();
    }
    @Override
    public void onCheckedChanged(SelectTab selectedTab, boolean isChecked) {
        for (SelectTab tab : items) {
            if (tab != selectedTab) {
                tab.setChecked(false);
            } else {
                tab.setChecked(isChecked);
            }
        }
        if (isChecked) {
            showPopup(selectedTab);
        } else {
            selectWindow.hide();
        }
    }
}
