package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsBaseDialogAlertBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.setScaleWidth
import java.io.Serializable

/**
 * 基础样式Dialog [ 自定义View + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/21
 */
open class AlertBaseDialog(
    private val mContext: Context,
    private val config: Config = Config(mContext),
    @StyleRes protected val themeResId: Int = R.style.AlertDialogStyle
) : Dialog(mContext, themeResId) {
    private val binding: MsBaseDialogAlertBinding = MsBaseDialogAlertBinding.inflate(
        LayoutInflater.from(mContext), null, false
    )
    protected var showLeftBtn = false
    protected var showRightBtn = false

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.AlertDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.AlertDialogStyle)

    fun getRootView() = binding.root
    fun getContentView() = binding.llContent
    fun getLineView() = binding.vLine
    fun getBottomBtnView() = binding.llBottom // 底部bottom区域
    fun getLeftBtnView() = binding.btLeft // 左侧按钮
    fun getRightBtnView() = binding.btRight // 右侧按钮
    fun getBtnLineView() = binding.vBtnLine // 左右按钮分割线

    /**
     * 设置Dialog宽度：相对于屏幕宽度比例
     */
    fun setScaleWidth(scWidth: Double): AlertBaseDialog {
        config.scaleWidth = scWidth
        return this
    }

    /**
     * 点击外围是否可取消
     */
    fun setDialogCancelable(cancelable: Boolean = true): AlertBaseDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    /**
     * 设置自定义内容View
     * 底部是默认的 取消、确认 按钮
     */
    fun setDialogContext(view: View, index: Int = -1): AlertBaseDialog {
        getContentView().addView(view, index)
        return this
    }

    /**
     * 设置底部按钮高度，单位: dp
     */
    fun setBottomViewHeightDp(heightDp: Float) = setBottomViewHeight(heightDp.dp2px(mContext))

    /**
     * 设置底部按钮高度，单位：px
     */
    fun setBottomViewHeight(heightPixels: Int = config.bottomViewHeight): AlertBaseDialog {
        with(getBottomBtnView()) {
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
    ): AlertBaseDialog {
        showLeftBtn = true
        getLeftBtnView().apply {
            this.text = text ?: config.defNullText
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
    ): AlertBaseDialog {
        showRightBtn = true
        getRightBtnView().apply {
            this.text = text ?: config.defNullText
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

    protected open fun setLayout() {
        setContentView(getRootView())
        setScaleWidth(getRootView(), config.scaleWidth)
        getRootView().background = config.dialogBg
        // 底部按钮
        setBottomViewHeight(config.bottomViewHeight)
        with(getLineView()) {
            background = config.dividerColor
            layoutParams = layoutParams.apply { height = config.dividerWidth }
        }
        // zero button
        if (!showRightBtn && !showLeftBtn) {
            getLineView().visibility = View.GONE
            getBottomBtnView().visibility = View.GONE
        } else {
            getLineView().visibility = View.VISIBLE
            getBottomBtnView().visibility = View.VISIBLE
        }
        // one button
        if (showRightBtn && !showLeftBtn) {
            getLeftBtnView().visibility = View.GONE
            getBtnLineView().visibility = View.GONE
            getRightBtnView().visibility = View.VISIBLE
            getRightBtnView().background = config.singleBtnBg
        }
        if (!showRightBtn && showLeftBtn) {
            getLeftBtnView().visibility = View.VISIBLE
            getLeftBtnView().background = config.singleBtnBg
            getBtnLineView().visibility = View.GONE
            getRightBtnView().visibility = View.GONE
        }
        // two button
        if (showRightBtn && showLeftBtn) {
            getLeftBtnView().visibility = View.VISIBLE
            getLeftBtnView().background = config.leftBtnBg
            with(getBtnLineView()) {
                visibility = View.VISIBLE
                background = config.dividerColor
                layoutParams = layoutParams.apply { width = config.dividerWidth }
            }
            getRightBtnView().visibility = View.VISIBLE
            getRightBtnView().background = config.rightBtnBg
        }
    }

    override fun show() {
        setLayout()
        super.show()
    }

    /**
     * AlertDialog 的配置
     */
    open class Config(
        var context: Context
    ) : Serializable {
        var scaleWidth: Double = 0.75 // 宽度占屏幕宽百分比
        var defNullText: CharSequence = "null" // 默认空文本
        var dialogBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_shape_alert_dialog_bg) // 整个Dialog的背景

        // button
        var bottomViewHeight: Int = 48f.dp2px(context) // 底部按钮高度
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