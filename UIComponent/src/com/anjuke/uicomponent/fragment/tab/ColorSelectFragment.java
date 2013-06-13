/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * RegionFragment.java
 *
 */
package com.anjuke.uicomponent.fragment.tab;

import com.anjuke.uicomponent.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-22
 */
public class ColorSelectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color, null);
    }
    
}
