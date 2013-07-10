/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * OnItemClickListener.java
 *
 */
package com.anjuke.library.uicomponent.select.listener;

import android.widget.ListView;

/**
 * 筛选列表item点击监听器
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-5-30
 */
public interface OnItemClickListener {
    void OnItemClick(ListView lv, String item, int position);
}
