package com.anjuke.library.uicomponent.photo;

import com.anjuke.uicomponent.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 与Gallery等组件配合使用，主要用小圆点来标识所选择的图片或页面
 * 
 * @author duhu (hudu@anjuke.com)
 * @date 2012-7-23
 * @modified by qitongzhang (qitongzhang@anjuke.com)
 **/
public class PhotoPoint extends View {

    private int mPointCount;

    private int mActivatePoint;
    private Bitmap mPointNormal;
    private Bitmap mPointActivate;
    private int mPointWH;
    private int mPointMarge = 5;

    public PhotoPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPointActivate = BitmapFactory.decodeResource(this.getResources(), R.drawable.ui_point_selected);
        mPointNormal = BitmapFactory.decodeResource(this.getResources(), R.drawable.ui_point_default);
        mPointWH = mPointActivate.getHeight();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        int w = this.getWidth();
        int h = this.getHeight();
        int pointx = (w >> 1);
        int pointy = ((h - mPointWH) >> 1);
        int temp = (mPointCount >> 1);
        if (mPointCount % 2 == 1) {
            pointx -= temp * (mPointWH + mPointMarge) + (mPointWH >> 1);
        } else {
            pointx -= temp * (mPointWH + mPointMarge) - (mPointMarge >> 1);
        }
        for (int i = 0; i < mPointCount; i++) {
            if (mActivatePoint == i) {
                canvas.drawBitmap(mPointActivate, pointx + i * (mPointWH + mPointMarge), pointy, paint);
            } else {
                canvas.drawBitmap(mPointNormal, pointx + i * (mPointWH + mPointMarge), pointy, paint);
            }
        }
    }

    public void setActivatePoint(int activatePoint) {
        this.mActivatePoint = (mPointCount + activatePoint) % mPointCount;
        this.postInvalidate();
    }

    public void setPointCount(int pointCount) {
        this.mPointCount = pointCount;
    }

    /**
     * 回收
     */
    public void recycle() {
        mPointNormal = null;
        mPointActivate = null;
    }
}
