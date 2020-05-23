package com.maple.msdialog

import android.graphics.Color

class SheetItem {
    var showName: String? = null
    var showColor = Color.parseColor("#333333")
    var isSelected = false
    var itemClickListener: OnSheetItemClickListener? = null

    constructor() {}
    constructor(showName: String?) {
        this.showName = showName
    }

    constructor(showName: String?, showColor: Int) {
        this.showName = showName
        this.showColor = showColor
    }

    constructor(name: String?, itemClickListener: OnSheetItemClickListener?) {
        showName = name
        this.itemClickListener = itemClickListener
    }

    constructor(name: String?, color: Int, itemClickListener: OnSheetItemClickListener?) {
        showName = name
        showColor = color
        this.itemClickListener = itemClickListener
    }
}