package com.anjuke.anjukelib.uicomponent.select;

import com.anjuke.uicomponent.R;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.app.Activity;
import android.content.Context;

/**
 * select包内部使用
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 * 
 */
class SelectWindow {
    private Context mContext;
    private PopupWindow mWindow;
    private LinearLayout mRootLayout;
    private View mContentView;
    private DisplayMetrics mDm;
    private ImageView mArrowIV;

    /**
     * Constructor.
     * 
     * @param context
     *            Context
     */
    public SelectWindow(Context context) {
        mContext = context;
        mDm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDm);
        mRootLayout = (LinearLayout) View.inflate(mContext, R.layout.ui_window_select, null);
        mArrowIV = (ImageView) mRootLayout.findViewById(R.id.ui_arrow_iv);
        mWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mWindow.setAnimationStyle(R.style.UIPopupWindowAnimation);
//        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mWindow.setFocusable(false);
        mWindow.setOutsideTouchable(true);
        mWindow.setContentView(mRootLayout);
    }

    public int dipToPx(int dip) {
        if (mDm == null) {
            return -1;
        }
        return (int) (dip * mDm.density + 0.5f);
    }

    public int getPaddingLeft() {
        return mRootLayout.getPaddingLeft();
    }

    /**
     * On dismiss
     */
    protected void onDismiss() {
    }

    public void show(View anchor) {
        mWindow.showAsDropDown(anchor, 0, 0);
    }

    public void hide() {
        mWindow.dismiss();
    }

    public boolean isShowing() {
        return mWindow.isShowing();
    }

    /**
     * Set content view.
     * 
     * @param root
     *            Root view
     */
    public void setContentView(View contentView, int leftMargin) {
        if (mContentView != null) {
            mRootLayout.removeView(mContentView);
        }
        mContentView = contentView;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mArrowIV.getLayoutParams();
        lp.leftMargin = leftMargin;
        mArrowIV.setLayoutParams(lp);
        mRootLayout.addView(mContentView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    // /**
    // * Set content view.
    // *
    // * @param layoutResID
    // * Resource id
    // */
    // public void setContentView(int layoutResID) {
    // LayoutInflater inflator = (LayoutInflater)
    // mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    // setContentView(inflator.inflate(layoutResID, null));
    // }

    /**
     * Set listener on window dismissed.
     * 
     * @param listener
     */
    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

}