package com.maple.iosdialog.custom

import android.content.Context
import androidx.core.content.ContextCompat
import com.maple.iosdialog.R
import com.maple.msdialog.ActionSheetDialog
import com.maple.msdialog.ActionSheetRecyclerDialog
import com.maple.msdialog.AlertDialog

/**
 * 自定义样式Dialog配置
 * https://github.com/shaoshuai904/iOS_Style_Dialog
 *
 * @author : shaoshuai27
 * @date ：2020/8/12
 */
object MsDialogConfigs {

    /**
     * 普通dialog
     */
    fun getAlertDialogConfig(mContext: Context) = AlertDialog.Config(mContext).apply {
        messageTextSizeSp = 17f
//        messageColor = ContextCompat.getColor(context, R.color.colorThinBlack)
//        leftBtnColor = ContextCompat.getColor(context, R.color.colorThinGrey)
//        rightBtnColor = ContextCompat.getColor(context, R.color.colorPrimaryBlue)
    }

    /**
     * 页签样式dialog
     */
    fun getActionSheetDialogConfig(mContext: Context) = ActionSheetDialog.Config(mContext).apply {
        showCancel = true
//        itemTextColor = ContextCompat.getColor(context, R.color.colorPrimaryBlue)
//        cancelTextColor = ContextCompat.getColor(context, R.color.colorPrimaryBlue)
    }

    /**
     * 底部抽屉样式dialog
     */
    fun getActionSheetRecyclerDialogConfig(mContext: Context) = ActionSheetRecyclerDialog.Config(mContext).apply {
        titleTextSizeSp = 18f
        closeDraw = ContextCompat.getDrawable(context, R.drawable.ms_svg_ic_close)
        isShowMark = true
        selectMark = ContextCompat.getDrawable(context, android.R.drawable.ic_delete)
        itemTextColor = ContextCompat.getColor(context, R.color.colorPrimary)
        itemTextSelectedColor = ContextCompat.getColor(context, R.color.colorAccent)
    }

}
