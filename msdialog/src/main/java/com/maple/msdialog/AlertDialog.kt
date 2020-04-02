package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.TextUtils
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

    fun setTitle(title: String?): AlertDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setTitle(title, color, 18, true)
    }

    fun setTitle(title: String?, color: Int, size: Int, isBold: Boolean): AlertDialog {
        showTitle = true
        binding.tvTitle.apply {
            text = title
            if (color != -1) setTextColor(color)
            if (size > 0) textSize = size.toFloat()
            if (isBold) setTypeface(typeface, Typeface.BOLD)
        }
        return this
    }

    fun setMessage(message: String?): AlertDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_message_color)
        return setMessage(message, color, 16, false)
    }

    fun setMessage(message: String?, color: Int, size: Int, isBold: Boolean): AlertDialog {
        showMsg = true
        binding.tvMsg.apply {
            text = message
            if (color != -1) setTextColor(color)
            if (size > 0) textSize = size.toFloat()
            if (isBold) setTypeface(typeface, Typeface.BOLD)
        }
        return this
    }

    fun setRightButton(text: String?, listener: View.OnClickListener?): AlertDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setRightButton(text, color, 16, false, listener)
    }

    fun setRightButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: View.OnClickListener?): AlertDialog {
        showRightBtn = true
        binding.btRight.apply {
            if (TextUtils.isEmpty(text)) {
                setText(R.string.ok)
            } else {
                this.text = text
            }
            if (color != -1) setTextColor(color)
            if (size > 0) textSize = size.toFloat()
            if (isBold) setTypeface(typeface, Typeface.BOLD)
            setOnClickListener { v ->
                listener?.onClick(v)
                dialog?.dismiss()
            }
        }
        return this
    }

    fun setLeftButton(text: String?, listener: View.OnClickListener?): AlertDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setLeftButton(text, color, 16, false, listener)
    }

    fun setLeftButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: View.OnClickListener?): AlertDialog {
        showLeftBtn = true
        binding.btLeft.apply {
            if (TextUtils.isEmpty(text)) {
                setText(R.string.cancel)
            } else {
                this.text = text
            }
            if (color != -1) setTextColor(color)
            if (size > 0) textSize = size.toFloat()
            if (isBold) setTypeface(typeface, Typeface.BOLD)
            setOnClickListener { v ->
                listener?.onClick(v)
                dialog?.dismiss()
            }
        }
        return this
    }

    private fun setLayout() {

        if (!showTitle && !showMsg) {
            binding.tvTitle.setText(R.string.alert)
            binding.tvTitle.visibility = View.VISIBLE
        }
        if (showTitle) {
            binding.tvTitle.visibility = View.VISIBLE
        }
        if (showMsg) {
            binding.tvMsg.visibility = View.VISIBLE
        }
        // one button
        if (!showRightBtn && !showLeftBtn) {
            binding.btRight.setText(R.string.ok)
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
            binding.btRight.setOnClickListener { dialog!!.dismiss() }
        }
        if (showRightBtn && !showLeftBtn) {
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
        if (!showRightBtn && showLeftBtn) {
            binding.btLeft.visibility = View.VISIBLE
            binding.btLeft.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
        // two button
        if (showRightBtn && showLeftBtn) {
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_right_selector)
            binding.btLeft.visibility = View.VISIBLE
            binding.btLeft.setBackgroundResource(R.drawable.alertdialog_left_selector)
            binding.ivLine.visibility = View.VISIBLE
        }
    }

    fun show() {
        setLayout()
        dialog?.show()
    }


}