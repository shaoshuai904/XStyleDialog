package com.maple.msdialog

import android.graphics.Color
import java.io.Serializable

/**
 * Sheet Item Bean
 *
 * @author : shaoshuai
 * @date ：2020/5/6
 */
open class SheetItem(
        var sheetId: String,
        var sheetName: String,
        var isSelected: Boolean = false// 是否为选中状态
) : Serializable {
    var showColor: Int = Color.parseColor("#333333")
    var itemClickListener: OnSheetItemClickListener? = null

    constructor(name: String) : this(name, name, false)

    constructor(
            name: String,
            color: Int = Color.parseColor("#333333"),
            clickListener: OnSheetItemClickListener? = null
    ) : this(name, name, false) {
        this.sheetId = name
        this.sheetName = name
        this.isSelected = false
        this.showColor = color
        this.itemClickListener = clickListener
    }

    open fun getShowName() = sheetName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SheetItem
        if (sheetId != other.sheetId) return false
        if (sheetName != other.sheetName) return false
        if (getShowName() != other.getShowName()) return false
        return true
    }

    override fun hashCode(): Int {
        var result = sheetId.hashCode()
        result = 31 * result + sheetName.hashCode()
        result = 31 * result + getShowName().hashCode()
        return result
    }

}