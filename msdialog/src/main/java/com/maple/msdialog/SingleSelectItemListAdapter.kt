package com.maple.msdialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.msdialog.databinding.ItemSingleStringBinding
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min


/**
 * 单选 item适配器
 *
 * @author : shaoshuai27
 * @date ：2020/1/7
 */
class SingleSelectItemListAdapter(
        val mContext: Context,
        var isShowMark: Boolean = false // 是否显示 右侧对勾 √
) : BaseQuickAdapter<SingleSelectItem, RecyclerView.ViewHolder>() {

    fun updateSelectItem(index: Int) {
        // 合规化index值，大于0 且 小于 size
        val number = min(max(index, 0), data().size - 1)
        data().forEachIndexed { i, item ->
            item.isSelect = (number == i)
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

        fun bind(item: SingleSelectItem) {
            bindViewClickListener(this)
            binding.apply {
                tvName.text = item.name
                if (item.isSelect) {
                    tvName.setTextColor(ContextCompat.getColor(mContext, R.color.def_right_color))
                    ivMark.setImageResource(android.R.drawable.checkbox_on_background)
                    ivMark.visibility = if (isShowMark) View.VISIBLE else View.GONE
                } else {
                    tvName.setTextColor(ContextCompat.getColor(mContext, R.color.def_left_color))
                    ivMark.visibility = View.GONE
                }
            }
        }
    }

}

class SingleSelectItem(
        var id: String,
        var name: String,
        var isSelect: Boolean = false// 是否为选中状态
) : Serializable {

    constructor(name: String) : this(name, name, false)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SingleSelectItem
        if (id != other.id) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}