package com.maple.iosdialog.custom

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maple.iosdialog.R
import com.maple.msdialog.*
import com.maple.msdialog.adapter.SheetMultipleSelectedAdapter
import com.maple.msdialog.databinding.MsLayoutSelectConfirmBinding
import com.maple.pagestate.PageStateManager
import java.util.*

/**
 * 多选页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai
 * @date ：2020/11/20
 */
class CustomMultipleSelectedDialog(
        private val mContext: Context,
        private val config: Config = Config(mContext)
) : ActionSheetRecyclerDialog(mContext, config) {
    private lateinit var confirmView: MsLayoutSelectConfirmBinding
    private lateinit var pageStateManager: PageStateManager
    private var onSheetItemClickListener: OnSheetItemClickListener? = null
    private var onSheetItemSelectConfirmListener: OnSheetItemSelectConfirmListener? = null
    private val adapter by lazy {
        SheetMultipleSelectedAdapter(mContext, config).apply {
            setOnItemClickListener { item, position ->
                val newItem = item.apply { isSelected = !isSelected }
                updateItemStatus(newItem)// 选中状态取反
                confirmView.cbSelectAll.isChecked = !isSelectAll()// 判断是否已经全选
                val size: Int = getCurrentSelectedItemList()?.size ?: 0
                confirmView.tvConfirm.text = if (size > 0) "确认(${size})" else "确认"
                onSheetItemClickListener?.onItemClick(newItem, position)
            }
        }
    }

    init {
        setTitle("多选条目")
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setDataBottomPadding(0f)
        setMinScaleHeight(0.7) // 设置最小高度
        setMaxScaleHeight(0.7) // 设置最大高度

        // 设置底部确认view
        confirmView = getSelectConfirmBinding()
        getFooterRoot().let {
            if (it.childCount > 0) it.removeAllViews()
            it.addView(confirmView.root)
        }

        // 数据列表配置
        getDataView().let {
            pageStateManager = PageStateManager(it).apply {
                emptyView?.setOnClickListener { loadData() }
                retryView?.setOnClickListener { loadData() }
            }

            it.adapter = adapter
            // 添加item分割线
            it.addItemDecoration(DividerItemDecoration(
                    config.dividerPaddingLeft, config.dividerHeight, config.dividerColor,
                    LinearLayoutManager.VERTICAL, config.skipLastItems))
            // 添加滑动监听
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!it.canScrollVertically(1)) {
                        loadData()
                        // Toast.makeText(mContext, "滑动到底部了", Toast.LENGTH_SHORT).show()
                    }
                    if (!it.canScrollVertically(-1)) {
                        // Toast.makeText(mContext, "到顶了", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        loadData()
    }

    fun loadData() {
        pageStateManager.showLoading()
        object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (Math.random() > 0.6) {
                    addSheetItems(getSheetItemTestData(20))
                    pageStateManager.showContent()
                } else {
                    pageStateManager.showEmpty()
                }
            }
        }.start()
    }

    // 获取测试数据
    private fun getSheetItemTestData(count: Int): ArrayList<SheetItem> {
        val startIndex = adapter.data.size
        val testData = arrayListOf<SheetItem>()
        for (index in 1..count) {
            val mColor = -0x1000000 or Random().nextInt(0xffffff)
            testData.add(SheetItem("custom sheet view ${startIndex + index}", mColor))
        }
        return testData
    }

    // 添加数据集合
    fun addSheetItems(items: List<SheetItem>): CustomMultipleSelectedDialog {
        val allData = arrayListOf<SheetItem>()
        allData.addAll(adapter.data)
        allData.addAll(items)
        // UI线程执行
        getDataView().post {
            adapter.refreshData(allData)
            // setSheetLayout()
        }
        return this
    }

    // 获取当前选中的item集合
    fun getCurrentSelectedItemList() = adapter.getCurrentSelectedItemList()

    // 添加条目点击监听
    fun addSheetItemClickListener(itemClickListener: OnSheetItemClickListener?): CustomMultipleSelectedDialog {
        onSheetItemClickListener = itemClickListener
        return this
    }

    // 添加条目选择确认监听
    fun addSheetItemSelectedConfirmListener(selectConfirmListener: OnSheetItemSelectConfirmListener?): CustomMultipleSelectedDialog {
        onSheetItemSelectConfirmListener = selectConfirmListener
        return this
    }

    // 是否显示item选中标记
    fun isShowItemMark(isShow: Boolean): CustomMultipleSelectedDialog {
        adapter.config.isShowMark = isShow
        adapter.notifyDataSetChanged()
        return this
    }

    // 底部确认选择View
    private fun getSelectConfirmBinding(): MsLayoutSelectConfirmBinding {
        val binding: MsLayoutSelectConfirmBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.ms_layout_select_confirm, null, false)
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

