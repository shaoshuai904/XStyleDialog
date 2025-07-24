package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.maple.msdialog.databinding.MsDialogActionSheetBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.getScreenHeight
import com.maple.msdialog.utils.DialogUtil.getScreenWidth
import java.io.Serializable

/**
 * 页签式Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author shaoshuai
 * @time 2017/3/21
 */
class ActionSheetDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext),
        @StyleRes private val themeResId: Int = R.style.ActionSheetDialogStyle
) : Dialog(mContext, themeResId) {
    private val binding: MsDialogActionSheetBinding = MsDialogActionSheetBinding.inflate(
        LayoutInflater.from(context), null, false)
    private var sheetItemList: MutableList<SheetItem>? = null
    // var itemClickListener: OnSheetItemClickListener? = null

    // 方便java调用
    constructor(mContext: Context) : this(mContext, Config(mContext), R.style.ActionSheetDialogStyle)
    constructor(mContext: Context, config: Config) : this(mContext, config, R.style.ActionSheetDialogStyle)

    init {
        // set Dialog min width
        binding.apply {
            root.minimumWidth = mContext.getScreenWidth()
            tvCancel.setOnClickListener { dismiss() }
        }
        // create Dialog
        setContentView(binding.root)
        window?.apply {
            setGravity(Gravity.START or Gravity.BOTTOM)
            attributes = attributes.apply {
                x = 0
                y = 0
            }
        }
    }

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getCancelView() = binding.tvCancel

    fun setDialogTitle(title: CharSequence?) = setTitle(title, isBold = false)

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            @ColorInt color: Int = config.titleTextColor,
            spSize: Float = config.titleTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetDialog {
        config.showTitle = true
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setCancelText(cancelText: CharSequence?) = setCancelText(cancelText, isBold = false)

    fun setCancelText(
            cancelText: CharSequence? = config.cancelText,
            @ColorInt color: Int = config.cancelTextColor,
            spSize: Float = config.cancelTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetDialog {
        config.showCancel = true
        binding.tvCancel.apply {
            text = cancelText
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setCancelViewHeightDp(heightDp: Float) = setCancelViewHeight(heightDp.dp2px(mContext))

    // 设置底部按钮高度
    fun setCancelViewHeight(heightPixels: Int = config.cancelViewHeight): ActionSheetDialog {
        with(binding.tvCancel) {
            config.cancelViewHeight = heightPixels
            layoutParams = layoutParams.apply { height = heightPixels }
        }
        return this
    }

    fun addSheetItem(strItem: String) =
            addSheetItem(strItem, config.itemTextColor, null)

    fun addSheetItem(strItem: String, clickListener: OnSheetItemClickListener?) =
            addSheetItem(SheetItem(strItem, config.itemTextColor, clickListener))

    fun addSheetItem(strItem: String, @ColorInt color: Int = config.itemTextColor, clickListener: OnSheetItemClickListener?) =
            addSheetItem(SheetItem(strItem, color, clickListener))

    fun addSheetItem(item: SheetItem): ActionSheetDialog {
        if (sheetItemList == null) {
            sheetItemList = ArrayList()
        }
        sheetItemList?.add(item)
        return this
    }

    /**
     * set items layout
     */
    private fun initLayout() {
        // binding.root.minimumWidth = mContext.screenInfo().x
        binding.root.setPadding(config.paddingLeft, config.paddingTop, config.paddingRight, config.paddingBottom)
        binding.tvTitle.visibility = if (config.showTitle) View.VISIBLE else View.GONE
        binding.tvCancel.visibility = if (config.showCancel) {
            setCancelText()
            View.VISIBLE
        } else View.GONE
        if (sheetItemList == null || sheetItemList!!.size <= 0) {
            return
        }
        val size = sheetItemList!!.size
        // 添加条目过多的时候控制高度
        val screenHeight = mContext.getScreenHeight()
        if (size > screenHeight / (config.actionSheetItemHeight * 2)) {
            val params = binding.slContent.layoutParams as LinearLayout.LayoutParams
            params.height = screenHeight / 2
            binding.slContent.layoutParams = params
        }
        // loop add item
        sheetItemList?.forEachIndexed { index, sheetItem ->
            val view = View(mContext).apply {
                background = config.dividerColor
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, config.dividerHeight)
            }
            // set item background
            val bg = if (size == 1) {
                if (config.showTitle) {
                    binding.llContent.addView(view)
                    config.sheetBottom
                } else config.sheetSingle
            } else {
                if (config.showTitle) {
                    binding.llContent.addView(view)
                    if (index < size - 1) config.sheetMiddle else config.sheetBottom
                } else {
                    if (index != 0) {
                        binding.llContent.addView(view)
                    }
                    when {
                        index == 0 -> config.sheetTop
                        index < size - 1 -> config.sheetMiddle
                        else -> config.sheetBottom
                    }
                }
            }
            val textView = TextView(mContext).apply {
                text = sheetItem.getShowName()
                textSize = config.itemTextSizeSp
                gravity = Gravity.CENTER
                setBackgroundResource(bg)
                setTextColor(sheetItem.showColor)
                layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        config.actionSheetItemHeight
                )
                // add click listener
                setOnClickListener {
                    sheetItem.itemClickListener?.onItemClick(sheetItem, index)
                    // itemClickListener?.onItemClick(sheetItem, index)
                    dismiss()
                }
            }
            binding.llContent.addView(textView)
        }
    }

    override fun show() {
        initLayout()
        super.show()
    }

    /**
     * ActionSheetDialog 的配置
     */
    open class Config(
            var context: Context
    ) : Serializable {
        // root
        var paddingLeft: Int = 10f.dp2px(context)
        var paddingTop: Int = 10f.dp2px(context)
        var paddingRight: Int = 10f.dp2px(context)
        var paddingBottom: Int = 10f.dp2px(context)

        // title
        var showTitle: Boolean = false
        var titleTextSizeSp: Float = 16f // 字体大小
        @ColorInt var titleTextColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色

        // item
        var actionSheetItemHeight: Int = 50f.dp2px(context)
        var itemTextSizeSp: Float = 18f // 字体大小
        var itemTextColor: Int = ContextCompat.getColor(context, R.color.ms_def_message_color)
        @DrawableRes var sheetSingle: Int = R.drawable.ms_sel_action_sheet_single
        @DrawableRes var sheetTop: Int = R.drawable.ms_sel_action_sheet_top
        @DrawableRes var sheetMiddle: Int = R.drawable.ms_sel_action_sheet_middle
        @DrawableRes var sheetBottom: Int = R.drawable.ms_sel_action_sheet_bottom

        // divider 分割线
        var dividerHeight: Int = 1 // 0.4f.dp2px(context) // 分割线高度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#C9C9C9"))// 分割线

        // cancel
        var showCancel: Boolean = false
        var cancelText: CharSequence = "取消"
        var cancelTextSizeSp: Float = 18f // 字体大小
        @ColorInt var cancelTextColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色
        var cancelViewHeight: Int = 48f.dp2px(context) // 底部按钮高度
    }

}