package com.maple.iosdialog

import com.maple.msdialog.SingleSelectItem

class User(
        val id: String,
        val name: String,
        var age: Int
) : SingleSelectItem(id, name) {

    override fun getShowName(): String {
        return "${id}号 $name"
    }
}