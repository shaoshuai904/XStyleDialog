package com.maple.msdialog

import android.content.Context
import android.support.v4.content.ContextCompat
import com.maple.msdialog.utils.DensityUtils.dp2px
import java.io.Serializable

/**
 * Dialog 的配置
 *
 * @author : shaoshuai27
 * @date ：2020/7/24
 */
open class DialogConfig(
        var context: Context
) : Serializable {
    var scaleWidth: Double = 0.75 // 宽度占屏幕宽百分比

    // title
    var titleTextSizeSp: Float = 18f // 字体大小
    var titleColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色
    var titlePaddingLeft: Int = 15f.dp2px(context)
    var titlePaddingTop: Int = 22f.dp2px(context)
    var titlePaddingRight: Int = 15f.dp2px(context)
    var titlePaddingBottom: Int = 0f.dp2px(context)

    // message
    var messageTextSizeSp: Float = 14f // 字体大小
    var messageColor: Int = ContextCompat.getColor(context, R.color.def_message_color)
    var messagePaddingLeft: Int = 15f.dp2px(context)
    var messagePaddingTop: Int = 22f.dp2px(context)
    var messagePaddingRight: Int = 15f.dp2px(context)
    var messagePaddingBottom: Int = 22f.dp2px(context)

    // button
    var bottomViewHeightDp: Float = 48f // 底部按钮高度

    // left button
    var leftBtnTextSizeSp: Float = 18f // 字体大小
    var leftBtnColor: Int = ContextCompat.getColor(context, R.color.def_left_color)

    // right button
    var rightBtnTextSizeSp: Float = 18f // 字体大小
    var rightBtnColor: Int = ContextCompat.getColor(context, R.color.def_right_color)

}