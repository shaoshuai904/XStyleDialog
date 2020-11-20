package com.maple.msdialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maple.msdialog.ActionSheetRecyclerDialog
import com.maple.msdialog.R
import com.maple.msdialog.SheetItem
import com.maple.msdialog.databinding.MsItemSingleStringBinding


/**
 * 多选 item适配器
 *
 * @author : shaoshuai
 * @date ：2020/11/20
 */
class SheetMultipleSelectedAdapter(
        private val mContext: Context,
        val config: ActionSheetRecyclerDialog.Config
) : BaseQuickAdapter<SheetItem, RecyclerView.ViewHolder>() {

    // 更新item选中状态
    fun updateItemStatus(item: SheetItem) {
        data.filter {
            it == item // equals 对比id 和 name
        }.forEach {
            it.isSelected = item.isSelected
        }
        notifyDataSetChanged()
    }

    // 更新item选中状态
    fun updateItemStatus(index: Int, isSelected: Boolean) {
        data[index].isSelected = isSelected
        notifyDataSetChanged()
    }

    // 获取当前选中的item集合
    fun getCurrentSelectedItemList(): List<SheetItem>? = data.filter { it.isSelected }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding: MsItemSingleStringBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.ms_item_single_string, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).bind(getItem(position))
    }

    inner class MyHolder(val binding: MsItemSingleStringBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SheetItem) {
            bindViewClickListener(this)
            binding.apply {
                root.background = config.itemBg
                root.setPadding(config.itemPaddingLeft, config.itemPaddingTop, config.itemPaddingRight, config.itemPaddingBottom)
                tvName.text = item.getShowName()
                tvName.textSize = config.itemTextSizeSp
                if (item.isSelected) {
                    tvName.setTextColor(config.itemTextSelectedColor)
                    ivMark.setImageDrawable(config.selectMark)
                    ivMark.visibility = if (config.isShowMark) View.VISIBLE else View.GONE
                } else {
                    tvName.setTextColor(config.itemTextColor)
                    ivMark.visibility = View.GONE
                }
            }
        }
    }

}
