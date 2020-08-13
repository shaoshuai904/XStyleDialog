package com.maple.msdialog.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * Dialog Utils
 *
 * @author : shaoshuai
 * @date ：2020/8/13
 */
object DialogUtil {

    // 设置Dialog宽度：相对于屏幕宽度比例
    fun Dialog.setScaleWidth(rootView: View, scWidth: Double) {
        rootView.layoutParams = FrameLayout.LayoutParams(
                (context.getScreenWidth() * scWidth).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    // 设置Dialog高度：相对于屏幕高度比例
    fun Dialog.setScaleHeight(rootView: View, scHeight: Double) {
        rootView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (context.getScreenHeight() * scHeight).toInt())
    }

    // 获取屏幕宽度
    @JvmStatic
    fun Context.getScreenWidth() = screenInfo().x

    // 获取屏幕高度
    @JvmStatic
    fun Context.getScreenHeight() = screenInfo().y

    // 获取屏幕信息
    @JvmStatic
    fun Context.screenInfo(): Point {
        val size = Point()
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(size)
        return size
    }

}