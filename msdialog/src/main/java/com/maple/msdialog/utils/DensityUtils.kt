package com.maple.msdialog.utils

import android.content.Context
import android.util.TypedValue

/**
 * @author maple
 * @time 2018/3/10
 */
object DensityUtils {

    @JvmStatic
    fun Float.dp2px(context: Context) = dp2px(context, this)

    @JvmStatic
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun Int.px2dp(context: Context) = px2dp(context, this)

    @JvmStatic
    fun px2dp(context: Context, pxVal: Int): Float {
        return pxVal / context.resources.displayMetrics.density
    }

    @JvmStatic
    fun Float.sp2px(context: Context) = sp2px(context, this)


    @JvmStatic
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun Int.px2sp(context: Context) = px2sp(context, this)

    @JvmStatic
    fun px2sp(context: Context, pxVal: Int): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }
}
