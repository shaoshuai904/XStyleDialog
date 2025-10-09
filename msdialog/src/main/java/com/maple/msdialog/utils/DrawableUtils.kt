package com.maple.msdialog.utils

import android.graphics.drawable.GradientDrawable

object DrawableUtils {

    /**
     * 获取单状态 Drawable
     */
    fun createGradientDrawable(mainColor: Int, topRadius: Float): GradientDrawable {
        return GradientDrawable().apply {
            setColor(mainColor)
            cornerRadius = 0f
            cornerRadii = floatArrayOf(
                topRadius, topRadius,
                topRadius, topRadius,
                0f, 0f,
                0f, 0f
            )
        }
    }
}

