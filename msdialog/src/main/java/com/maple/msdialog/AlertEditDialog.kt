package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogAlertEditBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.setScaleWidth
import java.io.Serializable

/**
 * 警告框式Edit Dialog [ 标题 + 消息文本 + 输入框 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/23
 */
class AlertEditDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext),
        @StyleRes private val themeResId: Int = R.style.AlertDialogStyle
) : Dialog(mContext, themeResId) {
    private val binding: MsDialogAlertEditBinding = MsDialogAlertEditBinding.inflate(
            LayoutInflater.from(mContext), null, false)
    private var showTitle = false
    private var showMsg = false
    private var showLeftBtn = false
    private var showRightBtn = false

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.AlertDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.AlertDialogStyle)

//    init {
//        setContentView(getRootView())
//    }

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getMessageView() = binding.tvMsg
    fun getLineView() = binding.vLine
    fun getBottomBtnView() = binding.llBottom // 底部bottom区域
    fun getLeftBtnView() = binding.btLeft // 左侧按钮
    fun getRightBtnView() = binding.btRight // 右侧按钮
    fun getBtnLineView() = binding.vBtnLine // 左右按钮分割线

    fun setScaleWidth(scWidth: Double): AlertEditDialog {
        config.scaleWidth = scWidth
        return this
    }

    fun setDialogCancelable(cancelable: Boolean = true): AlertEditDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun setDialogTitle(title: CharSequence?): AlertEditDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            @ColorInt color: Int = config.titleColor,// 字体颜色
            spSize: Float = config.titleTextSizeSp,// 字体大小
            isBold: Boolean = false// 是否加粗
    ): AlertEditDialog {
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

    fun setHtmlMessage(message: String?): AlertEditDialog {
        return setMessage(convertHtmlText(message))
    }

    /**
     * 将html类文本转换成普通文本
     */
    fun convertHtmlText(htmlText: String?): Spanned {
        val source = htmlText ?: (config.defNullText) as String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    fun setMessage(message: CharSequence?) = setMessage(message, isBold = false)

    fun setMessage(
            message: CharSequence?,
            @ColorInt color: Int = config.messageColor,// 字体颜色
            spSize: Float = config.messageTextSizeSp, // 字体大小
            isBold: Boolean = false, // 是否加粗
            gravity: Int = Gravity.CENTER// 偏左，居中，偏右
    ): AlertEditDialog {
        showMsg = true
        getMessageView().apply {
            text = message ?: config.defNullText
            setTextColor(color)
            this.gravity = gravity
            textSize = spSize
            setPadding(config.messagePaddingLeft, config.messagePaddingTop, config.messagePaddingRight, config.messagePaddingBottom)
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    /**
     * 设置底部按钮高度，单位: dp
     */
    fun setBottomViewHeightDp(heightDp: Float) = setBottomViewHeight(heightDp.dp2px(mContext))

    /**
     * 设置底部按钮高度，单位：px
     */
    fun setBottomViewHeight(heightPixels: Int = config.bottomViewHeight): AlertEditDialog {
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
    ): AlertEditDialog {
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
            listener: OnEditTextCallListener? = null
    ) = setRightButton(text, listener, config.rightBtnColor, config.rightBtnTextSizeSp, false)

    fun setRightButton(
            text: CharSequence?,
            listener: OnEditTextCallListener? = null,
            @ColorInt color: Int = config.rightBtnColor,
            spSize: Float = config.rightBtnTextSizeSp,
            isBold: Boolean = false
    ): AlertEditDialog {
        showRightBtn = true
        binding.btRight.apply {
            this.text = text ?: config.defNullText
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
        setContentView(getRootView())
        setScaleWidth(getRootView(), config.scaleWidth)
        getRootView().background = config.dialogBg
        // title & msg
        getTitleView().visibility = if (showTitle) View.VISIBLE else View.GONE
        getMessageView().visibility = if (showMsg) View.VISIBLE else View.GONE
        if (!showTitle && !showMsg) {
            getTitleView().text = config.defNullText
            getTitleView().visibility = View.VISIBLE
        }
        with(getLineView()) {
            background = config.dividerColor
            layoutParams = layoutParams.apply { height = config.dividerWidth }
        }
        setBottomViewHeight(config.bottomViewHeight)
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
     * AlertEditDialog 的配置
     */
    class Config(
            var context: Context
    ) : Serializable {
        var scaleWidth: Double = 0.75 // 宽度占屏幕宽百分比
        var defNullText: CharSequence = "null" // 默认空文本

        // title
        var titleTextSizeSp: Float = 18f // 字体大小
        @ColorInt var titleColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色
        var titlePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var titlePaddingTop: Int = 15f.dp2px(context) // 上边距
        var titlePaddingRight: Int = 15f.dp2px(context) // 右边距
        var titlePaddingBottom: Int = 0f.dp2px(context) // 下边距

        // message
        var messageTextSizeSp: Float = 16f // 字体大小
        @ColorInt var messageColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color) // 字体颜色
        var messagePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var messagePaddingTop: Int = 15f.dp2px(context) // 上边距
        var messagePaddingRight: Int = 15f.dp2px(context) // 右边距
        var messagePaddingBottom: Int = 5f.dp2px(context) // 下边距

        // button
        var bottomViewHeight: Int = 48f.dp2px(context) // 底部按钮高度
        var dialogBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_shape_alert_dialog_bg) // 整个Dialog的背景
        var singleBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_single) // 单按钮背景

        // left button
        var leftBtnTextSizeSp: Float = 16f // 字体大小
        var leftBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_left_color) // 字体颜色
        var leftBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_left) // 左侧按钮背景

        // right button
        var rightBtnTextSizeSp: Float = 16f // 字体大小
        var rightBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_right_color) // 字体颜色
        var rightBtnBg: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_sel_alert_dialog_right) // 右侧按钮背景

        // divider 分割线
        var dividerWidth: Int = 1 //0.4f.dp2px(context) // 分割线宽度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#C9C9C9"))// 分割线
    }

}