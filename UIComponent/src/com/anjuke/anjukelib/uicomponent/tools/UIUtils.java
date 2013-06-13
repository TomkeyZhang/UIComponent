/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * UIUtils.java
 *
 */
package com.anjuke.anjukelib.uicomponent.tools;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-6-3
 */
public class UIUtils {
    public static int dipToPx(DisplayMetrics dm, int dip) {
        return (int) (dip * dm.density + 0.5f);
    }
}
