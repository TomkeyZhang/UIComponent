/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * TextAdpater.java
 *
 */
package com.anjuke.uicomponent.select;

import java.util.List;

import com.anjuke.uicomponent.R;
import com.anjuke.uicomponent.R.color;
import com.anjuke.uicomponent.R.id;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-27
 */
public class SelectItemAdpater extends ArrayAdapter<String> implements OnClickListener {
    private ListView mListView;
    private OnItemClickListener mItemClickListener;
    private int selectedPos = 0;
    private boolean isGroup;
    private int lineColor;

    public SelectItemAdpater(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, android.R.id.text1, objects);
    }

    public void setListView(ListView listView) {
        this.mListView = listView;
    }

    public void setGroupLineColor(int lineColor) {
        this.isGroup = true;
        this.lineColor=lineColor;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setOnClickListener(this);
        view.findViewById(R.id.ui_indicator_view).setVisibility(position == selectedPos ? View.VISIBLE : View.INVISIBLE);
        if (isGroup) {
            view.findViewById(R.id.ui_indicator_view).setBackgroundColor(lineColor);
            view.findViewById(android.R.id.text1).setBackgroundResource(position == selectedPos ? R.color.ui_bg_select_right : 0);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (mListView != null) {
            selectedPos = mListView.getPositionForView(v);
            notifyDataSetChanged();
            if (mItemClickListener != null)
                mItemClickListener.OnItemClick(mListView,getItem(selectedPos), selectedPos);
        }

    }
    public static class Group{
        private String name;
        private List<String> items;
        
        public Group() {
        }
        public Group(String name, List<String> items) {
            this.name = name;
            this.items = items;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<String> getItems() {
            return items;
        }
        public void setItems(List<String> items) {
            this.items = items;
        }
        
    }
    public static interface OnItemClickListener {
        void OnItemClick(ListView lv,String item, int position);
    }
}
