package com.maple.msdialog.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.msdialog.ActionSheetRecyclerDialog
import com.maple.msdialog.R
import com.maple.msdialog.SheetItem
import com.maple.msdialog.databinding.ItemSingleStringBinding
import kotlin.math.max
import kotlin.math.min


/**
 * 单选 item适配器
 *
 * @author : shaoshuai27
 * @date ：2020/1/7
 */
class SingleSelectItemListAdapter(
        private val mContext: Context,
        val config: ActionSheetRecyclerDialog.Config
) : BaseQuickAdapter<SheetItem, RecyclerView.ViewHolder>() {

    fun updateSelectItem(index: Int) {
        // 合规化index值，大于0 且 小于 size
        val number = min(max(index, 0), data.size - 1)
        data.forEachIndexed { i, item ->
            item.isSelected = (number == i)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding: ItemSingleStringBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.item_single_string, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).bind(getItem(position))
    }

    inner class MyHolder(val binding: ItemSingleStringBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SheetItem) {
            bindViewClickListener(this)
            binding.apply {
                root.setPadding(config.itemPaddingLeft, config.itemPaddingTop, config.itemPaddingRight, config.itemPaddingBottom)
                tvName.text = item.getShowName()
                tvName.textSize = config.itemTextSizeSp
                if (item.isSelected) {
                    tvName.setTextColor(config.itemTextSelectedColor)
                    ivMark.background = config.selectMark
                    ivMark.visibility = if (config.isShowMark) View.VISIBLE else View.GONE
                } else {
                    tvName.setTextColor(config.itemTextColor)
                    ivMark.visibility = View.GONE
                }
            }
        }
    }

}
