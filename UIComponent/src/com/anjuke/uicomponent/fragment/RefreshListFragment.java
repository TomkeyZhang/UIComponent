/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * RefreshListFragment.java
 *
 */
package com.anjuke.uicomponent.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.anjuke.anjukelib.uicomponent.list.RefreshLoadMoreListView;
import com.anjuke.anjukelib.uicomponent.list.RefreshLoadMoreListView.OnRefreshListener;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-28
 */
public class RefreshListFragment extends ListFragment implements OnRefreshListener {
    RefreshLoadMoreListView mListView;
    SimpleAdapter adapter;
    Handler handler=new Handler();
    List<Map<String, String>> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refresh_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView=(RefreshLoadMoreListView)getListView();
        data = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(getActivity(), data, R.layout.ui_listitem_house, new String[] {},
                new int[] {});
        setListAdapter(adapter);
        updateData(false);
        mListView.setOnRefreshListener(this);
    }
    private void updateData(boolean append) {
        if(!append)
            data.clear();
        data.add(new HashMap<String, String>());
        data.add(new HashMap<String, String>());
        data.add(new HashMap<String, String>());
        data.add(new HashMap<String, String>());
        data.add(new HashMap<String, String>());
        data.add(new HashMap<String, String>());
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData(false);
                page=0;
                mListView.onRefreshComplete();
            }
        }, 3000);
    }
    int page=0;
    @Override
    public void onMore() {
        page++;
        if(page==3){
            mListView.setHasMore(false);
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData(true);
            }
        }, 3000);
    }
}
