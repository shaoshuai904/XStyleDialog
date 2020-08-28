package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
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
            btLeft.visibility = View.GONE
            btRight.visibility = View.GONE
            ivLine.visibility = View.GONE
        }

        // set Dialog style
        setContentView(binding.root)
    }

    fun setScaleWidth(scWidth: Double): AlertDialog {
        setScaleWidth(binding.root, scWidth)
        return this
    }

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getMessageView() = binding.tvMsg
    fun getLeftBtnView() = binding.btLeft
    fun getRightBtnView() = binding.btRight

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

    fun setBottomViewHeightDp(heightDp: Float = config.bottomViewHeightDp) = setBottomViewHeight(dp2px(mContext, heightDp))

    fun setBottomViewHeight(heightPixels: Int): AlertDialog {
        with(binding.llBottom) {
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
        setBottomViewHeightDp()

        binding.apply {
            if (!showTitle && !showMsg) {
                tvTitle.text = config.defNullText
                tvTitle.visibility = View.VISIBLE
            }
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            tvMsg.visibility = if (showMsg) View.VISIBLE else View.GONE
            // one button
            if (!showRightBtn && !showLeftBtn) {
                btRight.text = config.defNullText
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
                btRight.setOnClickListener { dismiss() }
            }
            if (showRightBtn && !showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            if (!showRightBtn && showLeftBtn) {
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_single)
            }
            // two button
            if (showRightBtn && showLeftBtn) {
                btRight.visibility = View.VISIBLE
                btRight.setBackgroundResource(R.drawable.ms_sel_alert_dialog_right)
                btLeft.visibility = View.VISIBLE
                btLeft.setBackgroundResource(R.drawable.ms_sel_alert_dialog_left)
                ivLine.visibility = View.VISIBLE
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
        var titlePaddingLeft: Int = 15f.dp2px(context)
        var titlePaddingTop: Int = 22f.dp2px(context)
        var titlePaddingRight: Int = 15f.dp2px(context)
        var titlePaddingBottom: Int = 0f.dp2px(context)

        // message
        var messageTextSizeSp: Float = 14f // 字体大小
        var messageColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color)
        var messagePaddingLeft: Int = 15f.dp2px(context)
        var messagePaddingTop: Int = 22f.dp2px(context)
        var messagePaddingRight: Int = 15f.dp2px(context)
        var messagePaddingBottom: Int = 22f.dp2px(context)

        // button
        var bottomViewHeightDp: Float = 48f // 底部按钮高度

        // left button
        var leftBtnTextSizeSp: Float = 18f // 字体大小
        var leftBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_left_color)

        // right button
        var rightBtnTextSizeSp: Float = 18f // 字体大小
        var rightBtnColor: Int = ContextCompat.getColor(context, R.color.ms_def_right_color)

    }
}