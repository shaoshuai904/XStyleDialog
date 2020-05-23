package com.maple.msdialog.utils

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

    // 设置Dialog高度：相对于屏幕高度比例
    fun Dialog.setScaleHeight(rootView: View, scHeight: Double) {
        rootView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (screenInfo().y * scHeight).toInt())
    }

    fun Dialog.screenInfo(): Point {
        val size = Point()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(size)
        return size
    }
}