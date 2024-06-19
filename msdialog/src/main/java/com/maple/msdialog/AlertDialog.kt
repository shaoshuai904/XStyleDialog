package com.maple.msdialog

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogAlertBinding
import com.maple.msdialog.utils.DensityUtils.dp2px

/**
 * 警告框式Dialog [ 标题 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/21
 */
class AlertDialog(
    private val mContext: Context,
    private val config: Config = Config(mContext),
    @StyleRes themeResId: Int = R.style.AlertDialogStyle
) : AlertBaseDialog(mContext, config, themeResId) {
    private val binding: MsDialogAlertBinding = MsDialogAlertBinding.inflate(
        LayoutInflater.from(mContext), null, false
    )
    private var showTitle = false
    private var showMsg = false

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.AlertDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.AlertDialogStyle)

    fun getTitleView() = binding.tvTitle
    fun getMessageView() = binding.tvMsg

    fun setDialogTitle(title: CharSequence?): AlertDialog {
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
    ): AlertDialog {
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

    fun setHtmlMessage(message: String?): AlertDialog {
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
    ): AlertDialog {
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

    override fun setLayout() {
        super.setLayout()
        // title & msg
        setDialogContext(binding.root)
        getTitleView().visibility = if (showTitle) View.VISIBLE else View.GONE
        getMessageView().visibility = if (showMsg) View.VISIBLE else View.GONE
        if (!showTitle && !showMsg) {
            getTitleView().text = config.defNullText
            getTitleView().visibility = View.VISIBLE
        }
    }

    /**
     * AlertDialog 的配置
     */
    class Config(
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
        var messageTextSizeSp: Float = 14f // 字体大小
        @ColorInt var messageColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color) // 字体颜色
        var messagePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var messagePaddingTop: Int = 22f.dp2px(context) // 上边距
        var messagePaddingRight: Int = 15f.dp2px(context) // 右边距
        var messagePaddingBottom: Int = 22f.dp2px(context) // 下边距
    }

}