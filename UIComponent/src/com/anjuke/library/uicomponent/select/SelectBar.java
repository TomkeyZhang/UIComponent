/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectBar.java
 *
 */
package com.anjuke.library.uicomponent.select;

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

import com.anjuke.library.uicomponent.select.SelectTab.OnCheckedListener;
import com.anjuke.library.uicomponent.select.listener.OnGroupItemClickListener;
import com.anjuke.library.uicomponent.select.listener.OnItemClickListener;
import com.anjuke.uicomponent.R;

/**
 * 筛选选择条，可作为自定义View控件加入到xml布局文件中
 * 
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-22
 */
public class SelectBar extends LinearLayout implements OnCheckedListener {
    private int mDividerColor = 0x1A000000;
    private int mDividerPadding = 0;
    private int mDividerHeight = 1;

    private SelectWindow mSelectWindow;
    private Paint mDividerPaint;
    private List<SelectTab> mItems = new ArrayList<SelectTab>();
    private int mArrowWith;
    private int mIndicatorColor;

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
        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(mDividerHeight);
        mDividerPaint.setColor(mDividerColor);
        setPadding(0, 0, 0, mDividerHeight);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.ui_icon_arrow_choose, options);
        mArrowWith = options.outWidth;
    }

    /**
     * 设置选中下划线的颜色
     * 
     * @param indicatorColor
     */
    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    /**
     * 添加单个筛选列表
     * 
     * @param title
     * @param items
     *            列表项显示文本
     * @param itemClickListener
     */
    public void addTabSingleList(String title, List<String> items, OnItemClickListener itemClickListener) {
        createTab(title, wrapSingleList(items, itemClickListener));
    }

    /**
     * 添加两个筛选列表
     * 
     * @param title
     * @param groups
     *            列表组显示文本
     * @param items
     *            列表组下各个列表项显示文本
     * @param groupItemClickListener
     */
    public void addTabDoubleList(String title, List<String> groups, List<List<String>> items,
            OnGroupItemClickListener groupItemClickListener) {
        createTab(title, wrapDoubleList(groups, items, groupItemClickListener));
    }

    private void createTab(String title, View popView) {
        SelectTab item = new SelectTab(getContext(), popView, this, mIndicatorColor);
        item.setText(title);
        addView(item.getItemView(), new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
        mItems.add(item);
    }

    private View wrapDoubleList(List<String> groups, List<List<String>> items,
            OnGroupItemClickListener groupItemClickListener) {
        SelectGroupWrapper wrap = new SelectGroupWrapper(getContext(), groups, items, mIndicatorColor);
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
        if (mSelectWindow == null) {
            mSelectWindow = new SelectWindow(getContext());
        }
        if (mSelectWindow.isShowing()) {
            mSelectWindow.hide();
        }
        View itemView = selectedTab.getItemView();
        mSelectWindow.setContentView(selectedTab.getPopView(), itemView.getRight() - itemView.getWidth() / 2
                - mArrowWith / 2 - mSelectWindow.getPaddingLeft());
        mSelectWindow.show(this);
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
        mDividerPaint.setColor(mDividerColor);
        for (int i = 0; i < getChildCount() - 1; i++) {
            View tab = getChildAt(i);
            tab.setPadding(0, 0, mDividerHeight, 0);
            canvas.drawLine(tab.getRight() - mDividerHeight, mDividerPadding, tab.getRight() - mDividerHeight, height
                    - mDividerPadding, mDividerPaint);
        }
        canvas.drawLine(100, mDividerPadding, 100, height - mDividerPadding, mDividerPaint);
        canvas.drawLine(0, height - mDividerHeight, getWidth(), height - mDividerHeight, mDividerPaint);
    }

    public void hidePopup() {
        if (mSelectWindow != null & mSelectWindow.isShowing()) {
            mSelectWindow.hide();
        }
        for (SelectTab tab : mItems) {
            tab.setChecked(false);
        }
    }

    public boolean isShowingPopup() {
        return mSelectWindow != null && mSelectWindow.isShowing();
    }

    @Override
    public void onCheckedChanged(SelectTab selectedTab, boolean isChecked) {
        for (SelectTab tab : mItems) {
            if (tab != selectedTab) {
                tab.setChecked(false);
            } else {
                tab.setChecked(isChecked);
            }
        }
        if (isChecked) {
            showPopup(selectedTab);
        } else {
            mSelectWindow.hide();
        }
    }
}
