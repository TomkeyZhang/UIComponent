/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectGroupWrap.java
 *
 */
package com.anjuke.anjukelib.uicomponent.select;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.anjuke.anjukelib.uicomponent.select.listener.OnGroupItemClickListener;
import com.anjuke.anjukelib.uicomponent.select.listener.OnItemClickListener;
import com.anjuke.uicomponent.R;

/**
 * 内部使用
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-5-28
 */
class SelectGroupWrapper implements OnItemClickListener{
    private SelectItemAdpater mItemAdapter;
    private SelectItemAdpater mGroupAdapter;
    private ListView mItemListView;
    private ListView mGroupListView;
    private List<List<String>> mItems;
    private View mRoot;
    private OnGroupItemClickListener mGroupItemClickListener;
    private Context mContext;

    public SelectGroupWrapper(Context context,List<String> groups,List<List<String>> items,int lineColor) {
        this.mContext=context;
        this.mRoot = View.inflate(mContext, R.layout.ui_listview_select_two, null);
        this.mItems=items;
        this.mGroupListView = (ListView) mRoot.findViewById(R.id.ui_select_group_lv);
        this.mItemListView = (ListView) mRoot.findViewById(R.id.ui_select_lv);
        this.mGroupAdapter = new SelectItemAdpater(mContext, R.layout.ui_listgroup_checked, groups);
        this.mItemAdapter = new SelectItemAdpater(mContext, R.layout.ui_listitem_checked, mItems.get(0));

        mGroupAdapter.setGroupLineColor(lineColor);
        mItemAdapter.setListView(mItemListView);
        mGroupAdapter.setListView(mGroupListView);
        mGroupAdapter.setItemClickListener(this);
        mGroupListView.setAdapter(mGroupAdapter);
        mItemListView.setAdapter(mItemAdapter);
    }
    public View getRoot() {
        return mRoot;
    }
    public void setGroupItemClickListener(OnGroupItemClickListener groupItemClickListener) {
        this.mGroupItemClickListener = groupItemClickListener;
        mItemAdapter.setItemClickListener(groupItemClickListener);
    }
    @Override
    public void OnItemClick(ListView lv,String item, int position) {
        mItemAdapter = new SelectItemAdpater(mContext, R.layout.ui_listitem_checked, mItems.get(position));
        mItemAdapter.setListView(mItemListView);
        mItemAdapter.setItemClickListener(mGroupItemClickListener);
        mItemListView.setAdapter(mItemAdapter);
        if(mGroupItemClickListener!=null)
            mGroupItemClickListener.OnGroupItemClick(lv, item, position);
    }
    
}
