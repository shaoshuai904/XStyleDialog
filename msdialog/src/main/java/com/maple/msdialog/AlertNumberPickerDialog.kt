package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
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
        binding.apply {
            tvTitle.visibility = View.GONE
            tvSuffix.visibility = View.GONE
            tvLeft.visibility = View.GONE
            tvRight.visibility = View.GONE
            ivLine.visibility = View.GONE
        }

        // set Dialog style
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog.setContentView(binding.root)
        setScaleWidth(0.8)
    }

    fun setTitle(
            title: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 18f,
            isBold: Boolean = false
    ): AlertNumberPickerDialog {
        showTitle = true
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setNumberValues(displayedValues: Array<String>, defaultValue: Int, onValueChangedListener: OnValueChangeListener?): AlertNumberPickerDialog {
        showMsg = true
        binding.npNumber.apply {
            this.displayedValues = displayedValues
            minValue = 0
            maxValue = displayedValues.size - 1
            // 设置为对当前值不可编辑
            descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
            // default value
            value = defaultValue
            setOnValueChangedListener(onValueChangedListener)
        }
        return this
    }

    // 设置value的后缀
    fun setNumberValueSuffix(
            suffix: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f
    ): AlertNumberPickerDialog {
        showMsg = true
        binding.tvSuffix.apply {
            text = suffix
            setTextColor(color)
            textSize = spSize
            visibility = View.VISIBLE
        }
        return this
    }

    fun setRightButton(
            text: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false,
            listener: View.OnClickListener? = null
    ): AlertNumberPickerDialog {
        showRightBtn = true
        binding.tvRight.apply {
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

    fun setLeftButton(
            text: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false,
            listener: View.OnClickListener? = null
    ): AlertNumberPickerDialog {
        showLeftBtn = true
        binding.tvLeft.apply {
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
                tvTitle.setText(R.string.alert)
                tvTitle.visibility = View.VISIBLE
            }
            if (showTitle) {
                tvTitle.visibility = View.VISIBLE
            }
            // one button
            if (!showRightBtn && !showLeftBtn) {
                tvRight.setText(R.string.ok)
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.sel_alert_dialog_single)
                tvRight.setOnClickListener { dialog.dismiss() }
            }
            if (showRightBtn && !showLeftBtn) {
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.sel_alert_dialog_single)
            }
            if (!showRightBtn && showLeftBtn) {
                tvLeft.visibility = View.VISIBLE
                tvLeft.setBackgroundResource(R.drawable.sel_alert_dialog_single)
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.sel_alert_dialog_right)
                tvLeft.visibility = View.VISIBLE
                tvLeft.setBackgroundResource(R.drawable.sel_alert_dialog_left)
                ivLine.visibility = View.VISIBLE
            }
        }
    }

    fun show() {
        setLayout()
        dialog.show()
    }

}