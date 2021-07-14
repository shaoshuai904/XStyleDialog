package com.maple.msdialog

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maple.msdialog.adapter.SheetMultipleSelectedAdapter
import com.maple.msdialog.databinding.MsLayoutSelectConfirmBinding

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
    private lateinit var confirmView: MsLayoutSelectConfirmBinding
    private var onSheetItemClickListener: OnSheetItemClickListener? = null
    private var onSheetItemSelectConfirmListener: OnSheetItemSelectConfirmListener? = null
    private val adapter by lazy {
        SheetMultipleSelectedAdapter(mContext, config).apply {
            setOnItemClickListener { item, position ->
                val newItem = item.apply { isSelected = !isSelected }
                updateItemStatus(newItem)// 选中状态取反
                confirmView.cbSelectAll.isChecked = !isSelectAll()// 判断是否已经全选
                onSheetItemClickListener?.onItemClick(newItem, position)
            }
        }
    }

    init {
        confirmView = getSelectConfirmBinding()
        getFooterRoot().let {
            if (it.childCount > 0) it.removeAllViews()
            it.addView(confirmView.root)
        }

        getDataView().let {
            // 添加item分割线
            it.addItemDecoration(DividerItemDecoration(
                    config.dividerPaddingLeft, config.dividerHeight, config.dividerColor,
                    LinearLayoutManager.VERTICAL, config.skipLastItems))
            // 添加滑动监听
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!it.canScrollVertically(1)) {
                        // Toast.makeText(mContext, "滑动到底部了", Toast.LENGTH_SHORT).show()
                    }
                    if (!it.canScrollVertically(-1)) {
                        // Toast.makeText(mContext, "到顶了", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    // 添加数据集合
    fun addSheetItems(items: List<SheetItem>): ActionSheetRecyclerMultipleSelectedDialog {
        getDataView().adapter = adapter
        adapter.refreshData(items)
        return this
    }

    // 获取当前选中的item集合
    fun getCurrentSelectedItemList() = adapter.getCurrentSelectedItemList()

    // 添加条目点击监听
    fun addSheetItemClickListener(itemClickListener: OnSheetItemClickListener?): ActionSheetRecyclerMultipleSelectedDialog {
        onSheetItemClickListener = itemClickListener
        return this
    }

    // 添加条目选择确认监听
    fun addSheetItemSelectedConfirmListener(selectConfirmListener: OnSheetItemSelectConfirmListener?): ActionSheetRecyclerMultipleSelectedDialog {
        onSheetItemSelectConfirmListener = selectConfirmListener
        return this
    }

    // 是否显示item选中标记
    fun isShowItemMark(isShow: Boolean): ActionSheetRecyclerMultipleSelectedDialog {
        adapter.config.isShowMark = isShow
        adapter.notifyDataSetChanged()
        return this
    }

    // 底部确认选择View
    private fun getSelectConfirmBinding(): MsLayoutSelectConfirmBinding {
        val binding: MsLayoutSelectConfirmBinding = MsLayoutSelectConfirmBinding.inflate(
            LayoutInflater.from(mContext), null, false)
        return binding.apply {
            cbSelectAll.setOnClickListener {
                // cbSelectAll.text = if (cbSelectAll.isChecked) "取消全选" else "全选"
                adapter.isSelectAll(cbSelectAll.isChecked)
            }
            tvConfirm.setOnClickListener {
                val currentSelectList = getCurrentSelectedItemList()
                onSheetItemSelectConfirmListener?.onSelectConfirm(currentSelectList)
                dismiss()
            }
        }
    }
}

