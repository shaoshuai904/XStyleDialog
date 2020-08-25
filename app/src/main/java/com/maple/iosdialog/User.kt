package com.maple.iosdialog

import com.maple.msdialog.SheetItem

class User(
        id: String = "",
        var username: String = "",
        var age: Int
) : SheetItem(id, username) {

    override fun getShowName(): String {
        return "${id}号 $name"
    }

    override fun toString(): String {
        return "id:$id    name:$name   isSelected:$isSelected   username:$username   age:$age"
    }
}