/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * TextAdpater.java
 *
 */
package com.anjuke.anjukelib.uicomponent.select;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anjuke.anjukelib.uicomponent.select.listener.OnItemClickListener;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-27
 */
class SelectItemAdpater extends ArrayAdapter<String> implements OnClickListener {
    private ListView mListView;
    private OnItemClickListener mItemClickListener;
    private int mSelectedPos = 0;
    private boolean mIsGroup;
    private int mLineColor;

    public SelectItemAdpater(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, android.R.id.text1, objects);
    }

    public void setListView(ListView listView) {
        this.mListView = listView;
    }

    public void setGroupLineColor(int lineColor) {
        this.mIsGroup = true;
        this.mLineColor = lineColor;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setOnClickListener(this);
        view.findViewById(R.id.ui_indicator_view).setVisibility(
                position == mSelectedPos ? View.VISIBLE : View.INVISIBLE);
        if (mIsGroup) {
            view.findViewById(R.id.ui_indicator_view).setBackgroundColor(mLineColor);
            view.findViewById(android.R.id.text1).setBackgroundResource(
                    position == mSelectedPos ? R.color.ui_bg_select_right : 0);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (mListView != null) {
            mSelectedPos = mListView.getPositionForView(v);
            notifyDataSetChanged();
            if (mItemClickListener != null)
                mItemClickListener.OnItemClick(mListView, getItem(mSelectedPos), mSelectedPos);
        }

    }

}
