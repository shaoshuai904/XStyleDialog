package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker.OnValueChangeListener
import com.maple.msdialog.databinding.DialogNumberPickerBinding

/**
 * 警告框式数字选择器Dialog [ 标题 + 数字选择 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 2018/12/6
 */
class AlertNumberPickerDialog(context: Context) : BaseDialog(context) {
    private val binding: DialogNumberPickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_number_picker, null, false)

    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false

    init {
        rootView = binding.root

        // get custom Dialog layout
        binding.tvTitle.visibility = View.GONE
        binding.tvSuffix.visibility = View.GONE
        binding.tvLeft.visibility = View.GONE
        binding.tvRight.visibility = View.GONE
        binding.ivLine.visibility = View.GONE

        // set Dialog style
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog.setContentView(binding.root)
        setScaleWidth(0.8)
    }

    fun setTitle(title: String?): AlertNumberPickerDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setTitle(title, color, 18, false)
    }

    fun setTitle(title: String?, color: Int, size: Int, isBold: Boolean): AlertNumberPickerDialog {
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

    fun setNumberValues(displayedValues: Array<String>, defaultValue: Int, onValueChangedListener: OnValueChangeListener?): AlertNumberPickerDialog {
        showMsg = true
        binding.npNumber.displayedValues = displayedValues
        binding.npNumber.minValue = 0
        binding.npNumber.maxValue = displayedValues.size - 1
        // 设置为对当前值不可编辑
        binding.npNumber.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
        // default value
        binding.npNumber.value = defaultValue
        binding.npNumber.setOnValueChangedListener(onValueChangedListener)
        return this
    }

    fun setNumberValueSuffix(suffix: String?): AlertNumberPickerDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setNumberValueSuffix(suffix, color, 16)
    }

    fun setNumberValueSuffix(suffix: String?, color: Int, size: Int): AlertNumberPickerDialog {
        showMsg = true
        binding.tvSuffix.text = suffix
        if (color != -1) {
            binding.tvSuffix.setTextColor(color)
        }
        if (size > 0) {
            binding.tvSuffix.textSize = size.toFloat()
        }
        binding.tvSuffix.visibility = View.VISIBLE
        return this
    }

    fun setRightButton(text: String?, listener: View.OnClickListener?): AlertNumberPickerDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setRightButton(text, color, 16, false, listener)
    }

    fun setRightButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: View.OnClickListener?): AlertNumberPickerDialog {
        showRightBtn = true
        if (TextUtils.isEmpty(text)) {
            binding.tvRight.setText(R.string.ok)
        } else {
            binding.tvRight.text = text
        }
        if (color != -1) {
            binding.tvRight.setTextColor(color)
        }
        if (size > 0) {
            binding.tvRight.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvRight.setTypeface(binding.tvRight.typeface, Typeface.BOLD)
        }
        binding.tvRight.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        }
        return this
    }

    fun setLeftButton(text: String?, listener: View.OnClickListener?): AlertNumberPickerDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setLeftButton(text, color, 16, false, listener)
    }

    fun setLeftButton(text: String?, color: Int, size: Int, isBold: Boolean, listener: View.OnClickListener?): AlertNumberPickerDialog {
        showLeftBtn = true
        if (TextUtils.isEmpty(text)) {
            binding.tvLeft.setText(R.string.cancel)
        } else {
            binding.tvLeft.text = text
        }
        if (color != -1) {
            binding.tvLeft.setTextColor(color)
        }
        if (size > 0) {
            binding.tvLeft.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvLeft.setTypeface(binding.tvLeft.typeface, Typeface.BOLD)
        }
        binding.tvLeft.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
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
        // one button
        if (!showRightBtn && !showLeftBtn) {
            binding.tvRight.setText(R.string.ok)
            binding.tvRight.visibility = View.VISIBLE
            binding.tvRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
            binding.tvRight.setOnClickListener { dialog!!.dismiss() }
        }
        if (showRightBtn && !showLeftBtn) {
            binding.tvRight.visibility = View.VISIBLE
            binding.tvRight.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
        if (!showRightBtn && showLeftBtn) {
            binding.tvLeft.visibility = View.VISIBLE
            binding.tvLeft.setBackgroundResource(R.drawable.alertdialog_single_selector)
        }
        // two button
        if (showRightBtn && showLeftBtn) {
            binding.tvRight.visibility = View.VISIBLE
            binding.tvRight.setBackgroundResource(R.drawable.alertdialog_right_selector)
            binding.tvLeft.visibility = View.VISIBLE
            binding.tvLeft.setBackgroundResource(R.drawable.alertdialog_left_selector)
            binding.ivLine.visibility = View.VISIBLE
        }
    }

    fun show() {
        setLayout()
        dialog?.show()
    }

}