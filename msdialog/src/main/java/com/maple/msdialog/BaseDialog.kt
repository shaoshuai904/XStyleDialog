package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * @author maple
 * @time 2017/4/6
 */
open class BaseDialog(var mContext: Context) {
    protected var dialog: Dialog = Dialog(mContext)
    protected var rootView: View? = null

    // 设置Dialog宽度：相对于屏幕宽度比例
    open fun setScaleWidth(scWidth: Double) {
        rootView?.layoutParams = FrameLayout.LayoutParams(
                (screenInfo().x * scWidth).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT)

    }

    fun setCancelable(cancel: Boolean) {
        dialog.setCancelable(cancel)
    }

    fun setCanceledOnTouchOutside(cancel: Boolean) {
        dialog.setCanceledOnTouchOutside(cancel)
    }

    //------------------------------------- utils --------------------------------------------------
    fun screenInfo(): Point {
        val size = Point()
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(size)
        return size
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(dpValue: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}