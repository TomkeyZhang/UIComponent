package com.anjuke.anjukelib.uicomponent.photo;

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

    private int pointCount_;

    private int activatePoint_;
    private Bitmap pointNormal_;
    private Bitmap pointActivate_;
    private int pointWH;
    private int pointMarge = 5;

    public PhotoPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        pointActivate_ = BitmapFactory.decodeResource(this.getResources(), R.drawable.ui_point_selected);
        pointNormal_ = BitmapFactory.decodeResource(this.getResources(), R.drawable.ui_point_default);
        pointWH = pointActivate_.getHeight();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        int w = this.getWidth();
        int h = this.getHeight();
        int pointx = (w >> 1);
        int pointy = ((h - pointWH) >> 1);
        int temp = (pointCount_ >> 1);
        if (pointCount_ % 2 == 1) {
            pointx -= temp * (pointWH + pointMarge) + (pointWH >> 1);
        } else {
            pointx -= temp * (pointWH + pointMarge) - (pointMarge >> 1);
        }
        for (int i = 0; i < pointCount_; i++) {
            if (activatePoint_ == i) {
                canvas.drawBitmap(pointActivate_, pointx + i * (pointWH + pointMarge), pointy, paint);
            } else {
                canvas.drawBitmap(pointNormal_, pointx + i * (pointWH + pointMarge), pointy, paint);
            }
        }
    }

    public void setActivatePoint(int activatePoint) {
        this.activatePoint_ = (pointCount_ + activatePoint) % pointCount_;
        this.postInvalidate();
    }

    public void setPointCount(int pointCount) {
        this.pointCount_ = pointCount;
    }

    /**
     * 回收
     */
    public void recycle() {
        pointNormal_ = null;
        pointActivate_ = null;
    }
}
