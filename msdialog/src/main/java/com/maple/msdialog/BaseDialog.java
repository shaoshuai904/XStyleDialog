package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * @author maple
 * @time 17/4/6
 */
public class BaseDialog {
    public Context mContext;
    public Dialog dialog;
    public View rootView;

    public BaseDialog(Context context) {
        mContext = context;
    }

    // 设置Dialog宽度：相对于屏幕宽度比例
    public BaseDialog setScaleWidth(double scWidth) {
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                (int) (getScreenWidth() * scWidth),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    //------------------------------------- utils --------------------------------------------------

    public int getScreenWidth() {
        return getScreenInfo().x;
    }

    public int getScreenHeight() {
        return getScreenInfo().y;
    }

    public Point getScreenInfo() {
        Point size = new Point();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        return size;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
