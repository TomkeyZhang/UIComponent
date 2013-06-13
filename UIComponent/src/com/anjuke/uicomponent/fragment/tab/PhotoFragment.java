/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * PhotoFragment.java
 *
 */
package com.anjuke.uicomponent.fragment.tab;

import com.anjuke.uicomponent.activity.PhotoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-31
 */
public class PhotoFragment extends ListFragment {
	ArrayAdapter<String> adapter;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] { "Gallery+Point", "Viewpager+Fragment+Indicator" });
		setListAdapter(adapter);
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		startActivity(new Intent(getActivity(), PhotoActivity.class).putExtra("type", position));
	}
}
