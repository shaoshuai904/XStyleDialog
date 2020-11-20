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
import kotlin.math.max
import kotlin.math.min


/**
 * 单选 item适配器
 *
 * @author : shaoshuai
 * @date ：2020/1/7
 */
class SheetSingleSelectedAdapter(
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
