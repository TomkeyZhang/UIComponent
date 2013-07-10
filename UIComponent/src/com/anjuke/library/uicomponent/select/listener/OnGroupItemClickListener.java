/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * OnGroupItemClickListener.java
 *
 */
package com.anjuke.library.uicomponent.select.listener;

import android.widget.ListView;

/**
 * 筛选列表group点击监听器
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-5-30
 */
public interface OnGroupItemClickListener extends OnItemClickListener{
    void OnGroupItemClick(ListView lv, String item, int position);
}
