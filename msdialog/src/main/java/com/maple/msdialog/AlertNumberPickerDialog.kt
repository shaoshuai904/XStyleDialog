package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogNumberPickerBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.setScaleWidth
import java.io.Serializable

/**
 * 警告框式数字选择器Dialog [ 标题 + 数字选择 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2018/12/6
 */
class AlertNumberPickerDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext),
        @StyleRes private val themeResId: Int = R.style.AlertDialogStyle
) : Dialog(mContext, themeResId) {
    private val binding: MsDialogNumberPickerBinding = MsDialogNumberPickerBinding.inflate(
            LayoutInflater.from(mContext), null, false)
    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.AlertDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.AlertDialogStyle)

    init {
        // get custom Dialog layout
        binding.apply {
            tvTitle.visibility = View.GONE
            tvSuffix.visibility = View.GONE
            vLine.visibility = View.VISIBLE
            tvLeft.visibility = View.GONE
            tvRight.visibility = View.GONE
            vBtnLine.visibility = View.GONE
        }

        // set Dialog style
        setContentView(binding.root)
    }

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getSuffixView() = binding.tvSuffix
    fun getLeftBtnView() = binding.tvLeft
    fun getRightBtnView() = binding.tvRight
    fun getLineView() = binding.vLine
    fun getBtnLineView() = binding.vBtnLine

    fun setScaleWidth(scWidth: Double): AlertNumberPickerDialog {
        config.scaleWidth = scWidth
        setScaleWidth(binding.root, scWidth)
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
            @ColorInt color: Int = config.titleColor,// 字体颜色
            spSize: Float = config.titleTextSizeSp,// 字体大小
            isBold: Boolean = false
    ): AlertNumberPickerDialog {
        showTitle = true
        binding.tvTitle.apply {
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
        binding.tvSuffix.apply {
            text = suffix ?: config.defNullText
            setTextColor(color)
            textSize = spSize
            visibility = View.VISIBLE
        }
        return this
    }

    fun setBottomViewHeightDp(heightDp: Float) = setBottomViewHeight(heightDp.dp2px(mContext))

    // 设置底部按钮高度
    fun setBottomViewHeight(heightPixels: Int = config.bottomViewHeight): AlertNumberPickerDialog {
        with(binding.llBottom) {
            config.bottomViewHeight = heightPixels
            layoutParams = layoutParams.apply { height = heightPixels }
        }
        return this
    }

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null
    ) = setLeftButton(text, listener, config.leftBtnColor, config.leftBtnTextSizeSp, false)

    fun setLeftButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            @ColorInt color: Int = config.leftBtnColor,
            spSize: Float = config.leftBtnTextSizeSp,
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
    ) = setRightButton(text, listener, config.rightBtnColor, config.rightBtnTextSizeSp, false)

    fun setRightButton(
            text: CharSequence?,
            listener: View.OnClickListener? = null,
            @ColorInt color: Int = config.rightBtnColor,
            spSize: Float = config.rightBtnTextSizeSp,
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
        setScaleWidth(binding.root, config.scaleWidth)
        setBottomViewHeight()

        binding.apply {
            llContent.background = config.dialogBg
            with(vLine) {
                background = config.dividerColor
                layoutParams = layoutParams.apply { height = config.dividerWidth }
            }
            with(vBtnLine) {
                background = config.dividerColor
                layoutParams = layoutParams.apply { width = config.dividerWidth }
            }
            if (!showTitle && !showMsg) {
                tvTitle.text = config.defNullText
                tvTitle.visibility = View.VISIBLE
            }
            if (showTitle) {
                tvTitle.visibility = View.VISIBLE
            }
            // tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            // tvMsg.visibility = if (showMsg) View.VISIBLE else View.GONE
            // zero button
            if (!showRightBtn && !showLeftBtn) {
                vLine.visibility = View.GONE
                llBottom.visibility = View.GONE
            }
            // one button
            if (showRightBtn && !showLeftBtn) {
                tvRight.visibility = View.VISIBLE
                tvRight.background = config.singleBtnBg
            }
            if (!showRightBtn && showLeftBtn) {
                tvLeft.visibility = View.VISIBLE
                tvLeft.background = config.singleBtnBg
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                tvLeft.visibility = View.VISIBLE
                tvLeft.background = config.leftBtnBg
                vBtnLine.visibility = View.VISIBLE
                tvRight.visibility = View.VISIBLE
                tvRight.background = config.rightBtnBg
            }
        }
    }

    override fun show() {
        setLayout()
        super.show()
    }

    /**
     * AlertNumberPickerDialog 的配置
     */
    open class Config(
            var context: Context
    ) : Serializable {
        var scaleWidth: Double = 0.75 // 宽度占屏幕宽百分比
        var defNullText: CharSequence = "null" // 默认空文本

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

        // button
        var bottomViewHeight: Int = 48f.dp2px(context) // 底部按钮高度
        var dialogBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_shape_alert_dialog_bg) // 整个Dialog的背景
        var singleBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_single) // 单按钮背景

        // left button
        var leftBtnTextSizeSp: Float = 18f // 字体大小
        var leftBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_left_color) // 字体颜色
        var leftBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_left) // 左侧按钮背景

        // right button
        var rightBtnTextSizeSp: Float = 18f // 字体大小
        var rightBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_right_color) // 字体颜色
        var rightBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_right) // 右侧按钮背景

        // divider 分割线
        var dividerWidth: Int = 1 //0.4f.dp2px(context) // 分割线宽度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#C9C9C9"))// 分割线

    }

}