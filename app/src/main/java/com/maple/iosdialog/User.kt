package com.maple.iosdialog

import com.maple.msdialog.SheetItem

class User(
        val id: String,
        val name: String,
        var age: Int
) : SheetItem(id, name) {

    override fun getShowName(): String {
        return "${id}号 $name"
    }
}