package com.maple.msdialog

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.maple.msdialog.adapter.SheetMultipleSelectedAdapter

/**
 * 多选页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai
 * @date ：2020/11/20
 */
class ActionSheetRecyclerMultipleSelectedDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : ActionSheetRecyclerDialog(mContext, config) {
    private var onSingleSelectedItemClickListener: OnSheetItemClickListener? = null
    private val adapter by lazy {
        SheetMultipleSelectedAdapter(mContext, config).apply {
            setOnItemClickListener { item, position ->
                // updateItemStatus(position, !item.isSelected)
                val newItem = item.apply { isSelected = !isSelected } // 选中状态取反
                updateItemStatus(newItem)
                onSingleSelectedItemClickListener?.onItemClick(newItem, position)
                // dismiss()
            }
        }
    }

    // 添加动作页签集合
    fun addSheetItems(items: List<SheetItem>): ActionSheetRecyclerMultipleSelectedDialog {
        getDataView().addItemDecoration(DividerItemDecoration(
                config.dividerPaddingLeft, config.dividerHeight, config.dividerColor,
                LinearLayoutManager.VERTICAL, config.skipLastItems))
        getDataView().adapter = adapter
        adapter.refreshData(items)
        return this
    }

    // 获取当前选中的item集合
    fun getCurrentSelectedItemList() = adapter.getCurrentSelectedItemList()

    // 添加条目点击监听
    fun addSheetItemClickListener(itemClickListener: OnSheetItemClickListener?): ActionSheetRecyclerMultipleSelectedDialog {
        onSingleSelectedItemClickListener = itemClickListener
        return this
    }

//    // 设置当前选中条目
//    fun setSelectedIndex(index: Int): ActionSheetRecyclerMultipleSelectedDialog {
//        adapter.updateSelectItem(index)
//        return this
//    }

    // 是否显示item选中标记
    fun isShowItemMark(isShow: Boolean): ActionSheetRecyclerMultipleSelectedDialog {
        adapter.config.isShowMark = isShow
        adapter.notifyDataSetChanged()
        return this
    }

}

