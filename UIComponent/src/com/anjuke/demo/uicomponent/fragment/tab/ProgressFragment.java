/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * ProgressFragment.java
 *
 */
package com.anjuke.demo.uicomponent.fragment.tab;

import java.util.ArrayList;
import java.util.Map;

import com.anjuke.demo.uicomponent.activity.ProgressActivity;
import com.anjuke.library.uicomponent.progress.ProgressWrapper;
import com.anjuke.uicomponent.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-29
 */
public class ProgressFragment extends ListFragment {
    private ProgressWrapper wrapper;
    private Handler handler = new Handler();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wrapper = new ProgressWrapper((ViewGroup) view);
        wrapper.showProgress();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                wrapper.progressToContent();
            }
        }, 3000);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] {
                "ProgressToEmpty", "ProgressToContent", "ProgressToError", "EmptyToContent", "ErrorToContent", "ErrorToEmpty"});
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(getActivity(), ProgressActivity.class).putExtra("position", position));
    }
}
