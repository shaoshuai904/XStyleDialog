package com.maple.msdialog

import android.graphics.Color
import java.io.Serializable

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
open class SingleSelectItem(
        private var id: String,
        private var name: String,
        var isSelect: Boolean = false// 是否为选中状态
) : Serializable {
    var showColor = Color.parseColor("#333333")

    constructor(name: String) : this(name, name, false)

    open fun getShowName() = name

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