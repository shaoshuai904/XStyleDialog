package com.maple.msdialog.adapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.msdialog.R
import com.maple.msdialog.SheetItem
import com.maple.msdialog.databinding.MsItemSheetBinding

/**
 * Action sheet adapter
 *
 * @author : shaoshuai
 * @date ：2020/5/6
 */
class ActionSheetAdapter(mContext: Context) : AbsAdapter<SheetItem>(mContext) {
    var isShowTitle = false

    fun showTitle(showTitle: Boolean) {
        isShowTitle = showTitle
        notifyDataSetChanged()
    }

    fun setSelectedIndex(index: Int) {
        mDatas.forEachIndexed { i, item ->
            item.isSelected = (i == index)
        }
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var returnView: View? = convertView
        val holder: MyHolder
        if (returnView == null) {
            val binding: MsItemSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.ms_item_sheet, parent, false)
            holder = MyHolder(binding)
            returnView = binding.root
            returnView.tag = holder
        } else {
            holder = returnView.tag as MyHolder
        }
        holder.bind(getItem(position), position)
        return returnView
    }

    inner class MyHolder(val binding: MsItemSheetBinding) {

        fun bind(item: SheetItem, index: Int) {
            binding.apply {
                tvName.text = item.getShowName()
                tvName.setTextColor(item.showColor)
                // ivMark.isSelected = item.isSelected
                ivMark.visibility = if (item.isSelected) View.VISIBLE else View.GONE
                val bg = if (count == 1) {
                    if (isShowTitle) R.drawable.ms_sel_action_sheet_bottom else R.drawable.ms_sel_action_sheet_single
                } else {
                    if (isShowTitle) {
                        if (index < count - 1) R.drawable.ms_sel_action_sheet_middle else R.drawable.ms_sel_action_sheet_bottom
                    } else {
                        when {
                            index == 0 -> R.drawable.ms_sel_action_sheet_top
                            index < count - 1 -> R.drawable.ms_sel_action_sheet_middle
                            else -> R.drawable.ms_sel_action_sheet_bottom
                        }
                    }
                }
                root.setBackgroundResource(bg)
//                root.setOnClickListener {
//                    item.itemClickListener?.onClick(item)
//                }
            }
        }
    }
}