package com.maple.msdialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maple.msdialog.databinding.MsDialogActionSheetRecyclerBinding
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.screenInfo
import java.io.Serializable

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * 此类是abstract的原因是：
 * 没有实现RecyclerView的数据填充。请自行实现adapter并填充数据。
 *
 * 示例子类：
 * 单选  ActionSheetRecyclerSingleSelectedDialog
 * 多选  ActionSheetRecyclerMultipleSelectedDialog
 *
 * @author : shaoshuai
 * @date ：2020/5/6
 */
abstract class ActionSheetRecyclerDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : BottomSheetDialog(mContext, R.style.ActionSheetDialogStyle) {
    private val binding: MsDialogActionSheetRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.ms_dialog_action_sheet_recycler, null, false)

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
    fun getFooterRoot() = binding.llFooter

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
            text = title ?: "null"
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    // 设置底部按钮高度
    fun setTitleViewHeight(heightPixels: Int = config.titleViewHeight): ActionSheetRecyclerDialog {
        with(binding.rlTitleBar) {
            config.titleViewHeight = heightPixels
            layoutParams = layoutParams.apply { height = heightPixels }
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
        setTitleViewHeight()

        getRootView().measure(0, 0)
        val curHeight = getRootView().measuredHeight // 当前实际高度
        val height = if (config.minHeight != null && curHeight < config.minHeight!!) {
            config.minHeight!!
        } else if (config.maxHeight != null && curHeight > config.maxHeight!!) {
            config.maxHeight!!
        } else {
            LinearLayout.LayoutParams.WRAP_CONTENT
        }

        getRootView().layoutParams = getRootView().layoutParams.apply {
            this.height = height
        }
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

        // title
        var titleTextSizeSp: Float = 16f // 字体大小
        var titleColor: Int = ContextCompat.getColor(context, R.color.ms_def_title_color) // 字体颜色
        var titleViewHeight: Int = 48f.dp2px(context) // 标题栏高度
        var closeDraw: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_svg_ic_close)

        // item
        var itemBg: Drawable? = ColorDrawable(Color.WHITE) // item背景
        var itemTextSizeSp: Float = 14f // 字体大小
        var itemTextColor: Int = ContextCompat.getColor(context, R.color.ms_def_left_color)
        var itemTextSelectedColor: Int = ContextCompat.getColor(context, R.color.ms_def_right_color)
        var isShowMark: Boolean = false // 是否显示 右侧对勾 √
        var selectMark: Drawable? = ContextCompat.getDrawable(context, R.drawable.ms_svg_round_check)
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

