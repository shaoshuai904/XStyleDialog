package com.maple.msdialog.utils

import android.app.Dialog
import android.content.Context
import android.view.View

/**
 * Dialog Utils
 *
 * @author : shaoshuai
 * @date ：2020/8/13
 */
object DialogUtil {

    /**
     * 设置Dialog宽度：相对于屏幕宽度比例
     */
    fun Dialog.setScaleWidth(rootView: View, scWidth: Double) {
        val newWidth = (context.getScreenWidth() * scWidth).toInt()
        rootView.layoutParams = rootView.layoutParams.apply {
            width = newWidth
        }
    }

    /**
     * 设置Dialog高度：相对于屏幕高度比例
     */
    fun Dialog.setScaleHeight(rootView: View, scHeight: Double) {
        val newHeight = (context.getScreenHeight() * scHeight).toInt()
        rootView.layoutParams = rootView.layoutParams.apply {
            height = newHeight
        }
    }

    // 获取屏幕宽度
    @JvmStatic
    fun Context.getScreenWidth() = DisplayUtil.getAppDisplayWidth(this)

    // 获取屏幕高度
    @JvmStatic
    fun Context.getScreenHeight() = DisplayUtil.getAppDisplayHeight(this)

}