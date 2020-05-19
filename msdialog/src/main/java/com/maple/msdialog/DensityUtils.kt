package com.maple.msdialog

import android.content.Context
import android.util.TypedValue

/**
 * @author maple
 * @time 2018/3/10
 */
object DensityUtils {

    @JvmStatic
    fun Float.dp2px(context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun Int.px2dp(context: Context): Float {
        return this / context.resources.displayMetrics.density
    }

    @JvmStatic
    fun Float.sp2px(context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun Int.px2sp(context: Context): Float {
        return this / context.resources.displayMetrics.scaledDensity
    }

}
