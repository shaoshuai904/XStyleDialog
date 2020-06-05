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
import com.maple.msdialog.utils.DialogUtil.setScaleWidth

/**
 * 警告框式Dialog [ 标题 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 2017/3/21
 */
class AlertDialog(private val mContext: Context) : Dialog(mContext, R.style.AlertDialogStyle) {
    private val binding: DialogAlertBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.dialog_alert, null, false)
    val rootView by lazy { binding.root }
    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false

    init {
        // get custom Dialog layout
        binding.apply {
            tvTitle.visibility = View.GONE
            tvMsg.visibility = View.GONE
            btLeft.visibility = View.GONE
            btRight.visibility = View.GONE
            ivLine.visibility = View.GONE
        }

        // set Dialog style
        setContentView(rootView)
        setScaleWidth(rootView, 0.85)
    }

    fun setScaleWidth(scWidth: Double): AlertDialog {
        setScaleWidth(rootView, scWidth)
        return this
    }

    fun setDialogTitle(title: CharSequence?): AlertDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
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

    fun setTitleMinHeight(minPixels: Int) {
        binding.tvTitle.minHeight = minPixels
    }

    fun setHtmlMessage(message: String?): AlertDialog {
        return setMessage(Html.fromHtml(message))
    }

    fun setMessage(message: CharSequence?) = setMessage(message, isBold = false)

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

    fun setMessageMinHeight(minPixels: Int) {
        binding.tvMsg.minHeight = minPixels
    }

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null
    ) = setLeftButton(text, listener, ContextCompat.getColor(mContext, R.color.def_left_color), 17f, false)

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            color: Int = ContextCompat.getColor(mContext, R.color.def_left_color),
            spSize: Float = 17f,
            isBold: Boolean = false
    ): AlertDialog {
        showLeftBtn = true
        binding.btLeft.apply {
            this.text = text
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
            setOnClickListener { v ->
                listener?.onClick(v)
                dismiss()
            }
        }
        return this
    }

    fun setRightButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null
    ) = setRightButton(text, listener, ContextCompat.getColor(mContext, R.color.def_right_color), 17f, false)

    fun setRightButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            color: Int = ContextCompat.getColor(mContext, R.color.def_right_color),
            spSize: Float = 17f,
            isBold: Boolean = false
    ): AlertDialog {
        showRightBtn = true
        binding.btRight.apply {
            this.text = text
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
            setOnClickListener { v ->
                listener?.onClick(v)
                dismiss()
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
                btRight.setOnClickListener { dismiss() }
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

    override fun show() {
        setLayout()
        super.show()
    }

}