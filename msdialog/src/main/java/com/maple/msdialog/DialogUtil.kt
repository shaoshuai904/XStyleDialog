package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout

object DialogUtil {

    // 设置Dialog宽度：相对于屏幕宽度比例
    fun Dialog.setScaleWidth(rootView: View, scWidth: Double) {
        rootView.layoutParams = FrameLayout.LayoutParams(
                (screenInfo().x * scWidth).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    //------------------------------------- utils --------------------------------------------------
    fun Dialog.screenInfo(): Point {
        val size = Point()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(size)
        return size
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun Dialog.dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}