package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.maple.msdialog.DensityUtils.dp2px
import com.maple.msdialog.DialogUtil.screenInfo
import com.maple.msdialog.databinding.DialogActionSheetRecyclerBinding
import kotlin.math.min

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
class ActionSheetRecyclerDialog(private val mContext: Context) : Dialog(mContext, R.style.ActionSheetDialogStyle) {
    private val binding: DialogActionSheetRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet_recycler, null, false)
    private var onSingleSelectedItemClickListener: OnSingleSelectedItemClickListener? = null
    val rootView by lazy { binding.root }// 根view
    var maxHeight: Int? = null //最大view高度, 单位：px
    private val adapter by lazy {
        SingleSelectItemListAdapter(mContext, true).apply {
            onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    updateSelectItem(position)
                    onSingleSelectedItemClickListener?.onItemClick(getItem(position), position)
                    dismiss()
                }
            }
        }
    }

    init {
        // set Dialog min width
        binding.apply {
            root.minimumWidth = screenInfo().x
            rlTitleBar.visibility = View.GONE
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

    override fun setTitle(title: CharSequence?) {
        this.setTitle(title, isBold = false)
    }

    fun setTitle(
            title: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ) {
        binding.rlTitleBar.visibility = View.VISIBLE
        binding.tvTitle.apply {
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    fun addSheetItems(items: MutableList<SingleSelectItem>) {
        binding.rvData.addItemDecoration(DividerItemDecoration(10f.dp2px(context), 0.7f.dp2px(context)))
        binding.rvData.adapter = adapter
        adapter.refreshData(items)
    }

    fun addSheetItemClickListener(itemClickListener: OnSingleSelectedItemClickListener?) {
        onSingleSelectedItemClickListener = itemClickListener
    }

    fun setSelectedIndex(index: Int) {
        adapter.updateSelectItem(index)
    }

    fun setMaxScaleHeight(scHeight: Double) {
        val height = (screenInfo().y * scHeight).toInt()
        maxHeight = height
    }

    /**
     * set layout
     */
    private fun setSheetLayout() {
        val height = if (maxHeight != null) {
            rootView.measure(0, 0)
            min(maxHeight!!, rootView.measuredHeight)
        } else {
            LinearLayout.LayoutParams.WRAP_CONTENT
        }
        rootView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                height)
    }

    override fun show() {
        setSheetLayout()
        super.show()
    }

}