package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.maple.msdialog.databinding.DialogActionSheetBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.screenInfo
import java.io.Serializable
import java.util.*

/**
 * 页签式Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author maple
 * @time 2017/3/21
 */
class ActionSheetDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : Dialog(mContext, R.style.ActionSheetDialogStyle) {
    private val binding: DialogActionSheetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet, null, false)
    private var sheetItemList: MutableList<SheetItem>? = null
    var itemClickListener: OnSheetItemClickListener? = null

    init {
        // set Dialog min width
        binding.root.minimumWidth = mContext.screenInfo().x
        binding.tvTitle.visibility = View.GONE
        binding.tvCancel.setOnClickListener { dismiss() }

        // create Dialog
        setContentView(binding.root)
        window?.apply {
            setGravity(Gravity.LEFT or Gravity.BOTTOM)
            attributes = attributes.apply {
                x = 0
                y = 0
            }
        }
    }

    constructor(mContext: Context) : this(mContext, Config(mContext))

    fun getRootView() = binding.root
    fun getTitleView() = binding.tvTitle
    fun getCancelView() = binding.tvCancel

    fun setDialogTitle(title: CharSequence?): ActionSheetDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = config.titleTextColor,
            spSize: Float = config.titleTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetDialog {
        config.showTitle = true
        binding.tvTitle.apply {
            visibility = View.VISIBLE
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setCancelText(
            cancelText: String?,
            color: Int = config.cancelTextColor,
            spSize: Float = config.cancelTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetDialog {
        binding.tvCancel.apply {
            text = cancelText
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun addSheetItem(strItem: String): ActionSheetDialog {
        val color = config.itemTextColor
        return addSheetItem(strItem, color)
    }

    fun addSheetItem(strItem: String, color: Int): ActionSheetDialog {
        return addSheetItem(SheetItem(strItem, color))
    }

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
        if (sheetItemList == null || sheetItemList!!.size <= 0) {
            return
        }
        val size = sheetItemList!!.size
        // 添加条目过多的时候控制高度
        val screenHeight = mContext.screenInfo().y
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
            binding.llContent.addView(view)
            // set item background
            val bg = if (size == 1) {
                if (config.showTitle) R.drawable.sel_action_sheet_bottom else R.drawable.sel_action_sheet_single
            } else {
                if (config.showTitle) {
                    if (index < size - 1) R.drawable.sel_action_sheet_middle else R.drawable.sel_action_sheet_bottom
                } else {
                    when {
                        index == 0 -> R.drawable.sel_action_sheet_top
                        index < size - 1 -> R.drawable.sel_action_sheet_middle
                        else -> R.drawable.sel_action_sheet_bottom
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
                    itemClickListener?.onItemClick(sheetItem, index)
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
        // title
        var showTitle = false
        var titleTextSizeSp: Float = 16f // 字体大小
        var titleTextColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色

        // item
        var actionSheetItemHeight = 50f.dp2px(context)
        var itemTextSizeSp: Float = 18f // 字体大小
        var itemTextColor: Int = ContextCompat.getColor(context, R.color.def_message_color)

        // divider 分割线
        var dividerHeight: Int = 0.4f.dp2px(context) // 分割线高度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#C9C9C9"))// 分割线

        // cancel
        var cancelTextSizeSp: Float = 18f // 字体大小
        var cancelTextColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色
    }

}