package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.maple.msdialog.databinding.MsDialogAlertEditBinding
import com.maple.msdialog.utils.DialogUtil.setScaleWidth

/**
 * 警告框式Edit Dialog [ 标题 + 输入框 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/23
 */
class AlertEditDialog(private val mContext: Context) : Dialog(mContext, R.style.AlertDialogStyle) {
    private val binding: MsDialogAlertEditBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.ms_dialog_alert_edit, null, false)
    val rootView by lazy { binding.root }
    private var showTitle = false
    private var showMsg = false
    private var showLeftBtn = false
    private var showRightBtn = false

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
        //dialog = Dialog(context, R.style.AlertDialogStyle)
        setContentView(binding.root)
        setScaleWidth(binding.root, 0.85)
    }

    fun setScaleWidth(scWidth: Double): AlertEditDialog {
        setScaleWidth(rootView, scWidth)
        return this
    }

    fun setDialogTitle(title: CharSequence?): AlertEditDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_title_color),
            spSize: Float = 18f,
            isBold: Boolean = false
    ): AlertEditDialog {
        showTitle = true
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setMessage(
            msg: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_message_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): AlertEditDialog {
        showMsg = true
        binding.tvMsg.apply {
            text = msg
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null
    ) = setLeftButton(text, listener, ContextCompat.getColor(mContext, R.color.ms_def_left_color), 16f, false)

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_left_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): AlertEditDialog {
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
            listener: OnEditTextCallListener? = null
    ) = setRightButton(text, listener, ContextCompat.getColor(mContext, R.color.ms_def_right_color), 16f, false)

    fun setRightButton(
            text: CharSequence?,
            listener: OnEditTextCallListener? = null,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_right_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): AlertEditDialog {
        showRightBtn = true
        binding.btRight.apply {
            this.text = text
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
            setOnClickListener {
                if (listener != null) {
                    val inputText = binding.etText.text.toString().trim()
                    listener.callBack(inputText)
                }
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
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
                btRight.setOnClickListener { dismiss() }
            }
            if (showRightBtn && !showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            if (!showRightBtn && showLeftBtn) {
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_right)
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_left)
                ivLine.visibility = View.VISIBLE
            }
        }
    }

    override fun show() {
        setLayout()
        super.show()
    }

}