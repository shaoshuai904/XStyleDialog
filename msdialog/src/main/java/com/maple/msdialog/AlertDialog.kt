package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.maple.msdialog.databinding.DialogAlertBinding

/**
 * 警告框式Dialog [ 标题 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 2017/3/21
 */
class AlertDialog(context: Context) : BaseDialog(context) {
    private val binding: DialogAlertBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_alert, null, false)

    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false


    init {
        rootView = binding.root

        // get custom Dialog layout
        binding.apply {
            tvTitle.visibility = View.GONE
            tvMsg.visibility = View.GONE
            btLeft.visibility = View.GONE
            btRight.visibility = View.GONE
            ivLine.visibility = View.GONE
        }

        // set Dialog style
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog.setContentView(binding.root)
        setScaleWidth(0.85)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),// 字体颜色
            spSize: Float = 17f,// 字体大小
            isBold: Boolean = false// 是否加粗
    ): AlertDialog {
        showTitle = true
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setHtmlMessage(message: String?): AlertDialog {
        return setMessage(Html.fromHtml(message))
    }

    fun setMessage(
            message: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_message_color),// 字体颜色
            spSize: Float = 14f, // 字体大小
            isBold: Boolean = false, // 是否加粗
            gravity: Int = Gravity.CENTER// 偏左，居中，偏右
    ): AlertDialog {
        showMsg = true
        binding.tvMsg.apply {
            text = message
            setTextColor(color)
            this.gravity = gravity
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setLeftButton(
            text: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_left_color),
            spSize: Float = 17f,
            isBold: Boolean = false,
            listener: View.OnClickListener? = null
    ): AlertDialog {
        showLeftBtn = true
        binding.btLeft.apply {
            this.text = text
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
            setOnClickListener { v ->
                listener?.onClick(v)
                dialog.dismiss()
            }
        }
        return this
    }

    fun setRightButton(
            text: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_right_color),
            spSize: Float = 17f,
            isBold: Boolean = false,
            listener: View.OnClickListener? = null
    ): AlertDialog {
        showRightBtn = true
        binding.btRight.apply {
            this.text = text
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
            setOnClickListener { v ->
                listener?.onClick(v)
                dialog.dismiss()
            }
        }
        return this
    }

    private fun setLayout() {
        binding.apply {
            if (!showTitle && !showMsg) {
                tvTitle.text = ""
                tvTitle.visibility = View.VISIBLE
            }
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            tvMsg.visibility = if (showMsg) View.VISIBLE else View.GONE
            // one button
            if (!showRightBtn && !showLeftBtn) {
                btRight.text = "确定"
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.sel_alert_dialog_single)
                btRight.setOnClickListener { dialog.dismiss() }
            }
            if (showRightBtn && !showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.sel_alert_dialog_single)
            }
            if (!showRightBtn && showLeftBtn) {
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.sel_alert_dialog_single)
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.sel_alert_dialog_right)
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.sel_alert_dialog_left)
                ivLine.visibility = View.VISIBLE
            }
        }
    }

    fun show() {
        setLayout()
        dialog.show()
    }
}