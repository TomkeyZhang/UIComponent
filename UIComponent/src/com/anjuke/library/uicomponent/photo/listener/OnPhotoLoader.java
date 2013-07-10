/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * IPhotoLoader.java
 *
 */
package com.anjuke.library.uicomponent.photo.listener;

import java.io.Serializable;

import android.widget.ImageView;

/**
 *@author qitongzhang (qitongzhang@anjuke.com)
 *@date 2013-6-6
 */
public interface OnPhotoLoader extends Serializable{
	void loadImage(ImageView photoIV,String url);
}
