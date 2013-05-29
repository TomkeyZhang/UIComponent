/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectGroupWrap.java
 *
 */
package com.anjuke.uicomponent.select;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.anjuke.uicomponent.R;
import com.anjuke.uicomponent.select.SelectItemAdpater.OnItemClickListener;

/**
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-5-28
 */
public class SelectGroupWrapper implements OnItemClickListener{
    private SelectItemAdpater mItemAdapter;
    private SelectItemAdpater mGroupAdapter;
    private ListView mItemListView;
    private ListView mGroupListView;
    private List<List<String>> mItems;
    private View mRoot;
    private OnGroupItemClickListener groupItemClickListener;
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
        this.groupItemClickListener = groupItemClickListener;
        mItemAdapter.setItemClickListener(groupItemClickListener);
    }
    @Override
    public void OnItemClick(ListView lv,String item, int position) {
        mItemAdapter = new SelectItemAdpater(mContext, R.layout.ui_listitem_checked, mItems.get(position));
        mItemAdapter.setListView(mItemListView);
        mItemAdapter.setItemClickListener(groupItemClickListener);
        mItemListView.setAdapter(mItemAdapter);
        if(groupItemClickListener!=null)
            groupItemClickListener.OnGroupItemClick(lv, item, position);
    }
    public static interface OnGroupItemClickListener extends OnItemClickListener{
        public void OnGroupItemClick(ListView lv,String item, int position);
    }
}
