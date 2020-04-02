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
        binding.tvTitle.visibility = View.GONE
        binding.tvMsg.visibility = View.GONE
        binding.btLeft.visibility = View.GONE
        binding.btRight.visibility = View.GONE
        binding.ivLine.visibility = View.GONE

        // set Dialog style
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog.setContentView(binding.root)
        setScaleWidth(0.85)
    }


    fun setTitle(title: String?): AlertEditDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setTitle(title, color, 18, true)
    }

    fun setTitle(title: String?, color: Int, size: Int, isBold: Boolean): AlertEditDialog {
        showTitle = true
        binding.tvTitle.text = title
        if (color != -1) {
            binding.tvTitle.setTextColor(color)
        }
        if (size > 0) {
            binding.tvTitle.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvTitle.setTypeface(binding.tvTitle.typeface, Typeface.BOLD)
        }
        return this
    }

    fun setMessage(msg: String?): AlertEditDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setMessage(msg, color, 16, false)
    }

    fun setMessage(msg: String?, color: Int, size: Int, isBold: Boolean): AlertEditDialog {
        showMsg = true
        binding.tvMsg.text = msg
        if (color != -1) {
            binding.tvMsg.setTextColor(color)
        }
        if (size > 0) {
            binding.tvMsg.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvMsg.setTypeface(binding.tvMsg.typeface, Typeface.BOLD)
        }
        return this
    }

    fun setRightButton(text: String?, listener: EditTextCallListener?): AlertEditDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setRightButton(text, color, 16, false, listener)
    }

    fun setRightButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: EditTextCallListener?): AlertEditDialog {
        showRightBtn = true
        binding.btRight.text = text
        if (color != -1) {
            binding.btRight.setTextColor(color)
        }
        if (size > 0) {
            binding.btRight.textSize = size.toFloat()
        }
        if (isBold) {
            binding.btRight.setTypeface(binding.btRight.typeface, Typeface.BOLD)
        }
        binding.btRight.setOnClickListener {
            if (listener != null) {
                val inputText = binding.etText.text.toString().trim()
                listener.callBack(inputText)
            }
            dialog!!.dismiss()
        }
        return this
    }

    fun setLeftButton(text: String?, listener: View.OnClickListener?): AlertEditDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setLeftButton(text, color, 16, false, listener)
    }

    fun setLeftButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: View.OnClickListener?): AlertEditDialog {
        showLeftBtn = true
        binding.btLeft.text = text
        if (color != -1) {
            binding.btLeft.setTextColor(color)
        }
        if (size > 0) {
            binding.btLeft.textSize = size.toFloat()
        }
        if (isBold) {
            binding.btLeft.setTypeface(binding.btLeft.typeface, Typeface.BOLD)
        }
        binding.btLeft.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        }
        return this
    }

    interface EditTextCallListener {
        fun callBack(str: String?)
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
        if (!showRightBtn && !showLeftBtn) {
            binding.btRight.setText(R.string.ok)
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
            binding.btRight.setOnClickListener { dialog!!.dismiss() }
        }
        if (showRightBtn && showLeftBtn) {
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_right_selector)
            binding.btLeft.visibility = View.VISIBLE
            binding.btLeft.setBackgroundResource(R.drawable.alertdialog_left_selector)
            binding.ivLine.visibility = View.VISIBLE
        }
        if (showRightBtn && !showLeftBtn) {
            binding.btRight.visibility = View.VISIBLE
            binding.btRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
        if (!showRightBtn && showLeftBtn) {
            binding.btLeft.visibility = View.VISIBLE
            binding.btLeft.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
    }

    fun show() {
        setLayout()
        dialog!!.show()
    }


}