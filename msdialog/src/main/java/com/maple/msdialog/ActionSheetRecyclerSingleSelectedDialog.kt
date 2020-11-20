package com.maple.msdialog

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.maple.msdialog.adapter.SheetSingleSelectedAdapter

/**
 * 单选页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai
 * @date ：2020/5/6
 */
class ActionSheetRecyclerSingleSelectedDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : ActionSheetRecyclerDialog(mContext, config) {
    private var onSingleSelectedItemClickListener: OnSheetItemClickListener? = null
    private val adapter by lazy {
        SheetSingleSelectedAdapter(mContext, config).apply {
            setOnItemClickListener { item, position ->
                updateSelectItem(position)
                onSingleSelectedItemClickListener?.onItemClick(item, position)
                dismiss()
            }
        }
    }

    // 添加动作页签集合
    fun addSheetItems(items: List<SheetItem>): ActionSheetRecyclerSingleSelectedDialog {
        getDataView().addItemDecoration(DividerItemDecoration(
                config.dividerPaddingLeft, config.dividerHeight, config.dividerColor,
                LinearLayoutManager.VERTICAL, config.skipLastItems))
        getDataView().adapter = adapter
        adapter.refreshData(items)
        return this
    }

    // 添加条目点击监听
    fun addSheetItemClickListener(itemClickListener: OnSheetItemClickListener?): ActionSheetRecyclerSingleSelectedDialog {
        onSingleSelectedItemClickListener = itemClickListener
        return this
    }

    // 设置当前选中条目
    fun setSelectedIndex(index: Int): ActionSheetRecyclerSingleSelectedDialog {
        adapter.updateSelectItem(index)
        return this
    }

    // 是否显示item选中标记
    fun isShowItemMark(isShow: Boolean): ActionSheetRecyclerSingleSelectedDialog {
        adapter.config.isShowMark = isShow
        adapter.notifyDataSetChanged()
        return this
    }

}

