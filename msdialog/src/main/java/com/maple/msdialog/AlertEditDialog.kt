package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.maple.msdialog.databinding.DialogAlertEditBinding

/**
 * 警告框式Edit Dialog [ 标题 + 输入框 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 2017/3/23
 */
class AlertEditDialog(context: Context) : BaseDialog(context) {
    private val binding: DialogAlertEditBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_alert_edit, null, false)
    private var showTitle = false
    private var showMsg = false
    private var showLeftBtn = false
    private var showRightBtn = false


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
            title: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
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
            msg: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
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
            text: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false,
            listener: View.OnClickListener? = null
    ): AlertEditDialog {
        showLeftBtn = true
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

    fun setRightButton(
            text: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false,
            listener: EditTextCallListener? = null
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
                dialog.dismiss()
            }
        }
        return this
    }

    interface EditTextCallListener {
        fun callBack(str: String?)
    }

    private fun setLayout() {
        binding.apply {
            if (!showTitle && !showMsg) {
                tvTitle.setText(R.string.alert)
                tvTitle.visibility = View.VISIBLE
            }
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            tvMsg.visibility = if (showMsg) View.VISIBLE else View.GONE
            // one button
            if (!showRightBtn && !showLeftBtn) {
                btRight.setText(R.string.ok)
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