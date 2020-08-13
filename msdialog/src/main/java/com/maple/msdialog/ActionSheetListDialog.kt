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
import com.maple.msdialog.adapter.ActionSheetAdapter
import com.maple.msdialog.databinding.DialogActionSheetListBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.screenInfo
import java.io.Serializable

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
class ActionSheetListDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : Dialog(mContext, R.style.ActionSheetDialogStyle) {
    private val binding: DialogActionSheetListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet_list, null, false)
    private val adapter by lazy { ActionSheetAdapter(mContext) }
    private var sheetItemList: MutableList<SheetItem>? = null

    init {
        // set Dialog min width
        binding.root.minimumWidth = mContext.screenInfo().x
        binding.tvTitle.visibility = if (config.showTitle) View.VISIBLE else View.GONE
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvCancel.visibility = if (config.showCancel) {
            setCancelText()
            View.VISIBLE
        } else View.GONE

        // create Dialog
        // dialog = Dialog(context, R.style.ActionSheetDialogStyle)
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

    fun setDialogTitle(title: CharSequence?): ActionSheetListDialog {
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
    ): ActionSheetListDialog {
        config.showTitle = true
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        adapter.showTitle(config.showTitle)
        return this
    }

    fun setCancelText(
            cancelText: CharSequence? = config.cancelText,
            color: Int = config.cancelTextColor,
            spSize: Float = config.cancelTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetListDialog {
        config.showTitle = true
        binding.tvCancel.apply {
            text = cancelText
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun addSheetItems(
            items: MutableList<SheetItem>,
            itemClickListener: OnSheetItemClickListener?
    ): ActionSheetListDialog {
        sheetItemList = items
        binding.lvData.adapter = adapter
        adapter.refresh(sheetItemList)
        binding.lvData.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            adapter.setSelectedIndex(position)
            itemClickListener?.onItemClick(item, position)
            dismiss()
        }
        return this
    }

    fun setSelectedIndex(index: Int): ActionSheetListDialog {
        adapter.setSelectedIndex(index)
        return this
    }

    /**
     * set items layout
     */
    private fun setSheetLayout() {
        binding.tvTitle.visibility = if (config.showTitle) View.VISIBLE else View.GONE
        binding.tvCancel.visibility = if (config.showCancel) View.VISIBLE else View.GONE
        if (sheetItemList == null || sheetItemList!!.size <= 0) {
            return
        }
        val size = sheetItemList!!.size
        // 添加条目过多的时候控制高度
        val screenHeight = mContext.screenInfo().y
        if (size > screenHeight / (config.actionSheetItemHeight * 2)) {
            val params = binding.lvData.layoutParams as LinearLayout.LayoutParams
            params.height = screenHeight / 2
            binding.lvData.layoutParams = params
        }
    }

    override fun show() {
        setSheetLayout()
        super.show()
    }

    /**
     * ActionSheetListDialog 的配置
     */
    open class Config(
            var context: Context
    ) : Serializable {
        // title
        var showTitle: Boolean = false
        var titleTextSizeSp: Float = 16f // 字体大小
        var titleTextColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色

        // item
        var actionSheetItemHeight: Int = 50f.dp2px(context)
        var itemTextSizeSp: Float = 18f // 字体大小
        var itemTextColor: Int = ContextCompat.getColor(context, R.color.def_message_color)

        // divider 分割线
        var dividerHeight: Int = 0.4f.dp2px(context) // 分割线高度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#C9C9C9"))// 分割线

        // cancel
        var showCancel: Boolean = false
        var cancelText: String = "取消"
        var cancelTextSizeSp: Float = 18f // 字体大小
        var cancelTextColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色
    }
}