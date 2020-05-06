package com.maple.msdialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.msdialog.databinding.ItemSheetBinding

/**
 * Action sheet adapter
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
class ActionSheetAdapter(mContext: Context) : AbsAdapter<SheetItem>(mContext) {
    var isShowTitle = false

    fun showTitle(showTitle: Boolean) {
        isShowTitle = showTitle
        notifyDataSetChanged()
    }
//    fun updatePinLoginName(pin: String, loginName: String) {
//        mDatas.filter {
//            it.getPin() == pin
//        }.forEach {
//            if (it.loginName != loginName) {
//                it.loginName = loginName
//                notifyDataSetChanged()
//            }
//        }
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var returnView: View? = convertView
        val holder: MyHolder
        if (returnView == null) {
            val binding: ItemSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sheet, parent, false)
            holder = MyHolder(binding)
            returnView = binding.root
            returnView.tag = holder
        } else {
            holder = returnView.tag as MyHolder
        }
        holder.bind(getItem(position), position)
        return returnView
    }

    inner class MyHolder(val binding: ItemSheetBinding) {

        fun bind(item: SheetItem, index: Int) {
            binding.apply {
                tvName.text = item.showName
                tvName.setTextColor(item.ShowColor)
                ivMark.isSelected = item.isSelected

                val bg = if (count == 1) {
                    if (isShowTitle) R.drawable.sel_action_sheet_bottom else R.drawable.sel_action_sheet_single
                } else {
                    if (isShowTitle) {
                        if (index < count - 1) R.drawable.sel_action_sheet_middle else R.drawable.sel_action_sheet_bottom
                    } else {
                        when {
                            index == 0 -> R.drawable.sel_action_sheet_top
                            index < count - 1 -> R.drawable.sel_action_sheet_middle
                            else -> R.drawable.sel_action_sheet_bottom
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