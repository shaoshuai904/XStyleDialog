package com.maple.iosdialog

import com.maple.msdialog.SheetItem

class User(
        id: String,
        name: String,
        var age: Int
) : SheetItem(id, name) {

    override fun getShowName(): String {
        return "${id}号 $name"
    }
}