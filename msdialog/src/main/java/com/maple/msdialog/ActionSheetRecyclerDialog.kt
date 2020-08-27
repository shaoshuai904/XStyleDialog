package com.maple.msdialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.maple.msdialog.adapter.SingleSelectItemListAdapter
import com.maple.msdialog.databinding.DialogActionSheetRecyclerBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.screenInfo
import java.io.Serializable

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai
 * @date ：2020/5/6
 */
class ActionSheetRecyclerDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : BottomSheetDialog(mContext, R.style.ActionSheetDialogStyle) {
    private val binding: DialogActionSheetRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet_recycler, null, false)
    private var onSingleSelectedItemClickListener: OnSheetItemClickListener? = null
    private val adapter by lazy {
        SingleSelectItemListAdapter(mContext, config).apply {
            setOnItemClickListener { item, position ->
                updateSelectItem(position)
                onSingleSelectedItemClickListener?.onItemClick(item, position)
                dismiss()
            }
        }
    }

    constructor(mContext: Context) : this(mContext, Config(mContext))

    init {
        // set Dialog min width
        binding.apply {
            root.minimumWidth = mContext.screenInfo().x
            rlTitleBar.visibility = View.GONE
            ivClose.setImageDrawable(config.closeDraw)
            ivClose.setOnClickListener { dismiss() }
        }

        // create Dialog
        setContentView(binding.root)
        window?.apply {
            setGravity(Gravity.BOTTOM)
            attributes = attributes.apply {
                x = 0
                y = 0
            }
        }
    }

    fun getRootView() = binding.root // 根view
    fun getTitleBarView() = binding.rlTitleBar
    fun getTitleView() = binding.tvTitle
    fun getCloseView() = binding.ivClose
    fun getDataView() = binding.rvData

    fun setDialogTitle(title: CharSequence?): ActionSheetRecyclerDialog {
        return setTitle(title, isBold = false)
    }

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = config.titleColor,
            spSize: Float = config.titleTextSizeSp,
            isBold: Boolean = false
    ): ActionSheetRecyclerDialog {
        binding.rlTitleBar.visibility = View.VISIBLE
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    // 设置顶部条【关闭】按钮图标
    fun setCloseIcon(bg: Drawable? = config.closeDraw): ActionSheetRecyclerDialog {
        binding.ivClose.let {
            it.setImageDrawable(bg)
            it.visibility = View.VISIBLE
        }
        return this
    }

    // 设置顶部条【关闭】按钮是否显示
    fun setCloseVisibility(isShow: Boolean): ActionSheetRecyclerDialog {
        binding.ivClose.visibility = if (isShow) View.VISIBLE else View.GONE
        return this
    }

    // 添加动作页签集合
    fun addSheetItems(items: List<SheetItem>): ActionSheetRecyclerDialog {
        binding.rvData.addItemDecoration(DividerItemDecoration(
                config.dividerPaddingLeft, config.dividerHeight, config.dividerColor,
                LinearLayoutManager.VERTICAL, config.skipLastItems))
        binding.rvData.adapter = adapter
        adapter.refreshData(items)
        return this
    }

    // 添加条目点击监听
    fun addSheetItemClickListener(itemClickListener: OnSheetItemClickListener?): ActionSheetRecyclerDialog {
        onSingleSelectedItemClickListener = itemClickListener
        return this
    }

    // 设置当前选中条目
    fun setSelectedIndex(index: Int): ActionSheetRecyclerDialog {
        adapter.updateSelectItem(index)
        return this
    }

    // 是否显示item选中标记
    fun isShowItemMark(isShow: Boolean): ActionSheetRecyclerDialog {
        adapter.config.isShowMark = isShow
        adapter.notifyDataSetChanged()
        return this
    }

    // 设置最小高度百分比
    fun setMinScaleHeight(scHeight: Double): ActionSheetRecyclerDialog {
        val height = (mContext.screenInfo().y * scHeight).toInt()
        config.minHeight = height
        return this
    }

    // 设置最大高度百分比
    fun setMaxScaleHeight(scHeight: Double): ActionSheetRecyclerDialog {
        val height = (mContext.screenInfo().y * scHeight).toInt()
        config.maxHeight = height
        return this
    }

    // 设置底部padding值
    fun setBottomPadding(bottomPx: Float): ActionSheetRecyclerDialog {
        binding.rvData.setPadding(0, 0, 0, bottomPx.dp2px(mContext))
        return this
    }

    /**
     * set layout
     */
    private fun setSheetLayout() {
        getRootView().measure(0, 0)
        val curHeight = getRootView().measuredHeight // 当前实际高度
        val height = if (config.minHeight != null && curHeight < config.minHeight!!) {
            config.minHeight!!
        } else if (config.maxHeight != null && curHeight > config.maxHeight!!) {
            config.maxHeight!!
        } else {
            LinearLayout.LayoutParams.WRAP_CONTENT
        }

        getRootView().layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                height)
    }

    override fun show() {
        setSheetLayout()
        super.show()
    }


    /**
     * ActionSheetRecyclerDialog 的配置
     */
    open class Config(
            var context: Context
    ) : Serializable {
        var minHeight: Int? = null //最小view高度, 单位：px
        var maxHeight: Int? = null //最大view高度, 单位：px
        var isShowMark: Boolean = false // 是否显示 右侧对勾 √
        var selectMark: Drawable? = ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background)
        var closeDraw: Drawable? = ContextCompat.getDrawable(context, R.drawable.svg_ic_close)

        // title
        var titleTextSizeSp: Float = 16f // 字体大小
        var titleColor: Int = ContextCompat.getColor(context, R.color.def_title_color) // 字体颜色

        // item
        var itemTextSizeSp: Float = 14f // 字体大小
        var itemTextColor: Int = ContextCompat.getColor(context, R.color.def_left_color)
        var itemTextSelectedColor: Int = ContextCompat.getColor(context, R.color.def_right_color)
        var itemPaddingLeft: Int = 15f.dp2px(context)
        var itemPaddingTop: Int = 12f.dp2px(context)
        var itemPaddingRight: Int = 15f.dp2px(context)
        var itemPaddingBottom: Int = 12f.dp2px(context)

        // divider 分割线
        var dividerPaddingLeft: Int = 12f.dp2px(context) // 分割线距离左侧距离
        var dividerHeight: Int = 0.7f.dp2px(context) // 分割线高度
        var dividerColor: Drawable = ColorDrawable(Color.parseColor("#e6e9ee"))// 分割线
        var skipLastItems: Int = 0 // 跳过几个item不画分割线，默认每个item底部都有分割线
    }

}

