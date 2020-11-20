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
        var _sheetId: String,
        var _sheetName: String,
        var isSelected: Boolean = false// 是否为选中状态
) : Serializable {
    var showColor: Int = Color.parseColor("#333333")
    var itemClickListener: OnSheetItemClickListener? = null

    constructor(name: String) : this(name, name, false)
    constructor(
            name: String, color: Int = Color.parseColor("#333333"),
            clickListener: OnSheetItemClickListener? = null
    ) : this(name, name, false) {
        this._sheetId = name
        this._sheetName = name
        this.isSelected = false
        this.showColor = color
        this.itemClickListener = clickListener
    }

    open fun getShowName() = _sheetName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SheetItem
        if (_sheetId != other._sheetId) return false
        if (_sheetName != other._sheetName) return false
        if (getShowName() != other.getShowName()) return false
        return true
    }

    override fun hashCode(): Int {
        var result = _sheetId.hashCode()
        result = 31 * result + _sheetName.hashCode()
        result = 31 * result + getShowName().hashCode()
        return result
    }

}