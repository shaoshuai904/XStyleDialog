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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.maple.msdialog.databinding.MsDialogAlertBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.setScaleWidth
import java.io.Serializable

/**
 * 警告框式Dialog [ 标题 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/21
 */
class AlertDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : Dialog(mContext, R.style.AlertDialogStyle) {
    private val binding: MsDialogAlertBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.ms_dialog_alert, null, false)
    private var showTitle = false
    private var showMsg = false
    private var showRightBtn = false
    private var showLeftBtn = false

    constructor(mContext: Context) : this(mContext, Config(mContext))

    init {
        // get custom Dialog layout
        binding.apply {
            tvTitle.visibility = View.GONE
            tvMsg.visibility = View.GONE
            vLine.visibility = View.VISIBLE
            btLeft.visibility = View.GONE
            btRight.visibility = View.GONE
            vBtnLine.visibility = View.GONE
        }

        // set Dialog style
        setContentView(binding.root)
    }

    fun setScaleWidth(scWidth: Double): AlertDialog {
        config.scaleWidth = scWidth
        setScaleWidth(binding.root, scWidth)
        return this
    }

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getMessageView() = binding.tvMsg
    fun getLeftBtnView() = binding.btLeft
    fun getRightBtnView() = binding.btRight
    fun getLineView() = binding.vLine
    fun getBtnLineView() = binding.vBtnLine

    // 点击外围是否可取消
    fun setDialogCancelable(cancelable: Boolean = true): AlertDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun setDialogTitle(title: CharSequence?): AlertDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = config.titleColor,// 字体颜色
            spSize: Float = config.titleTextSizeSp,// 字体大小
            isBold: Boolean = false// 是否加粗
    ): AlertDialog {
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

    fun setHtmlMessage(message: String?): AlertDialog {
        return setMessage(convertHtmlText(message))
    }

    // 将html类文本转换成普通文本
    fun convertHtmlText(htmlText: String?): Spanned {
        val source = htmlText ?: config.defNullText
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    fun setMessage(message: CharSequence?) = setMessage(message, isBold = false)

    fun setMessage(
            message: CharSequence?,
            color: Int = config.messageColor,// 字体颜色
            spSize: Float = config.messageTextSizeSp, // 字体大小
            isBold: Boolean = false, // 是否加粗
            gravity: Int = Gravity.CENTER// 偏左，居中，偏右
    ): AlertDialog {
        showMsg = true
        binding.tvMsg.apply {
            text = message ?: config.defNullText
            setTextColor(color)
            this.gravity = gravity
            textSize = spSize
            setPadding(config.messagePaddingLeft, config.messagePaddingTop, config.messagePaddingRight, config.messagePaddingBottom)
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setBottomViewHeightDp(heightDp: Float) = setBottomViewHeight(heightDp.dp2px(mContext))

    // 设置底部按钮高度
    fun setBottomViewHeight(heightPixels: Int = config.bottomViewHeight): AlertDialog {
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
            color: Int = config.leftBtnColor,
            spSize: Float = config.leftBtnTextSizeSp,
            isBold: Boolean = false
    ): AlertDialog {
        showLeftBtn = true
        binding.btLeft.apply {
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
            color: Int = config.rightBtnColor,
            spSize: Float = config.rightBtnTextSizeSp,
            isBold: Boolean = false
    ): AlertDialog {
        showRightBtn = true
        binding.btRight.apply {
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
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            tvMsg.visibility = if (showMsg) View.VISIBLE else View.GONE
            // zero button
            if (!showRightBtn && !showLeftBtn) {
                vLine.visibility = View.GONE
                llBottom.visibility = View.GONE
            }
            // one button
            if (showRightBtn && !showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.background = config.singleBtnBg
            }
            if (!showRightBtn && showLeftBtn) {
                btLeft.visibility = View.VISIBLE
                btLeft.background = config.singleBtnBg
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                btLeft.visibility = View.VISIBLE
                btLeft.background = config.leftBtnBg
                vBtnLine.visibility = View.VISIBLE
                btRight.visibility = View.VISIBLE
                btRight.background = config.rightBtnBg
            }
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
        var defNullText: String = "null" // 默认空文本

        // title
        var titleTextSizeSp: Float = 18f // 字体大小
        var titleColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色
        var titlePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var titlePaddingTop: Int = 22f.dp2px(context) // 上边距
        var titlePaddingRight: Int = 15f.dp2px(context) // 右边距
        var titlePaddingBottom: Int = 0f.dp2px(context) // 下边距

        // message
        var messageTextSizeSp: Float = 14f // 字体大小
        var messageColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color) // 字体颜色
        var messagePaddingLeft: Int = 15f.dp2px(context) // 左边距
        var messagePaddingTop: Int = 22f.dp2px(context) // 上边距
        var messagePaddingRight: Int = 15f.dp2px(context) // 右边距
        var messagePaddingBottom: Int = 22f.dp2px(context) // 下边距

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