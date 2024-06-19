package com.maple.msdialog

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogNumberPickerBinding
import com.maple.msdialog.utils.DensityUtils.dp2px

/**
 * 警告框式数字选择器Dialog [ 标题 + 数字选择 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2018/12/6
 */
class AlertNumberPickerDialog(
    private val mContext: Context,
    private val config: Config = Config(mContext),
    @StyleRes themeResId: Int = R.style.AlertDialogStyle
) : AlertBaseDialog(mContext, config, themeResId) {
    private val binding: MsDialogNumberPickerBinding = MsDialogNumberPickerBinding.inflate(
        LayoutInflater.from(mContext), null, false
    )
    private var showTitle = false
    private var showMsg = false

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.AlertDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.AlertDialogStyle)

    fun getTitleView() = binding.tvTitle
    fun getSuffixView() = binding.tvSuffix

    fun setDialogTitle(title: CharSequence?): AlertNumberPickerDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
        title: CharSequence?,
        @ColorInt color: Int = config.titleColor,// 字体颜色
        spSize: Float = config.titleTextSizeSp,// 字体大小
        isBold: Boolean = false
    ): AlertNumberPickerDialog {
        showTitle = true
        getTitleView().apply {
            text = title ?: config.defNullText
            setTextColor(color)
            textSize = spSize
            setPadding(config.titlePaddingLeft, config.titlePaddingTop, config.titlePaddingRight, config.titlePaddingBottom)
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
        @ColorInt color: Int = config.messageColor,// 字体颜色
        spSize: Float = config.messageTextSizeSp // 字体大小
    ): AlertNumberPickerDialog {
        showMsg = true
        getSuffixView().apply {
            text = suffix ?: config.defNullText
            setTextColor(color)
            textSize = spSize
            visibility = View.VISIBLE
        }
        return this
    }

    override fun setLayout() {
        super.setLayout()
        // title & msg
        setDialogContext(binding.root)
        getTitleView().visibility = if (showTitle) View.VISIBLE else View.GONE
        if (!showTitle && !showMsg) {
            getTitleView().text = config.defNullText
            getTitleView().visibility = View.VISIBLE
        }
    }

    /**
     * AlertNumberPickerDialog 的配置
     */
    open class Config(
        context: Context
    ) : AlertBaseDialog.Config(context) {
        // title
        var titleTextSizeSp: Float = 18f // 字体大小
        @ColorInt var titleColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色
        var titlePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var titlePaddingTop: Int = 22f.dp2px(context) // 上边距
        var titlePaddingRight: Int = 15f.dp2px(context) // 右边距
        var titlePaddingBottom: Int = 0f.dp2px(context) // 下边距

        // message
        var messageTextSizeSp: Float = 16f // 字体大小
        @ColorInt var messageColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color) // 字体颜色
    }

}