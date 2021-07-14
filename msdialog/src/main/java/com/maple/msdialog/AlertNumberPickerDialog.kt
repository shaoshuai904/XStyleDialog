package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogNumberPickerBinding
import com.maple.msdialog.utils.DialogUtil.setScaleWidth

/**
 * 警告框式数字选择器Dialog [ 标题 + 数字选择 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2018/12/6
 */
class AlertNumberPickerDialog(private val mContext: Context) : Dialog(mContext, R.style.AlertDialogStyle) {
    private val binding: MsDialogNumberPickerBinding = MsDialogNumberPickerBinding.inflate(
            LayoutInflater.from(mContext), null, false)
    val rootView by lazy { binding.root }
    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false

    init {
        // get custom Dialog layout
        binding.apply {
            tvTitle.visibility = View.GONE
            tvSuffix.visibility = View.GONE
            tvLeft.visibility = View.GONE
            tvRight.visibility = View.GONE
            ivLine.visibility = View.GONE
        }

        // set Dialog style
        setContentView(rootView)
        setScaleWidth(rootView, 0.8)
    }

    fun setScaleWidth(scWidth: Double): AlertNumberPickerDialog {
        setScaleWidth(rootView, scWidth)
        return this
    }

    fun setDialogTitle(title: CharSequence?): AlertNumberPickerDialog {
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
            suffix: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_title_color),
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
    ): AlertNumberPickerDialog {
        showLeftBtn = true
        binding.tvLeft.apply {
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
    ) = setRightButton(text, listener, ContextCompat.getColor(mContext, R.color.ms_def_right_color), 16f, false)

    fun setRightButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            color: Int = ContextCompat.getColor(mContext, R.color.ms_def_right_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): AlertNumberPickerDialog {
        showRightBtn = true
        binding.tvRight.apply {
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
            if (showTitle) {
                tvTitle.visibility = View.VISIBLE
            }
            // one button
            if (!showRightBtn && !showLeftBtn) {
                tvRight.text = "确定"
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
                tvRight.setOnClickListener { dismiss() }
            }
            if (showRightBtn && !showLeftBtn) {
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            if (!showRightBtn && showLeftBtn) {
                tvLeft.visibility = View.VISIBLE
                tvLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                tvRight.visibility = View.VISIBLE
                tvRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_right)
                tvLeft.visibility = View.VISIBLE
                tvLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_left)
                ivLine.visibility = View.VISIBLE
            }
        }
    }

    override fun show() {
        setLayout()
        super.show()
    }

}