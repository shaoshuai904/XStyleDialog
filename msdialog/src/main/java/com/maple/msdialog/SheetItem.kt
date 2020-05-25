package com.maple.msdialog

import android.graphics.Color
import java.io.Serializable

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
open class SheetItem(
        var id: String,
        var name: String,
        var isSelected: Boolean = false// 是否为选中状态
) : Serializable {
    var showColor: Int = Color.parseColor("#333333")

    constructor(name: String) : this(name, name, false)
    constructor(name: String, color: Int) : this(name, name, false) {
        this.showColor = color
    }

    open fun getShowName() = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SheetItem
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