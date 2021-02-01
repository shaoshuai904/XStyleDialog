package com.maple.xdialog

import com.maple.msdialog.SheetItem

/**
 * SheetItem只实现了必要的属性字段，
 * 很多时候 SheetItem 内容不满足需求，此时可以 通过继承 丰富完善它
 *
 * @author : shaoshuai
 * @date ：2020/11/20
 */
class User(
        var id: String = "",
        var name: String = "",
        var sex: String = "",// 性别
        var age: Int // 年龄
) : SheetItem(id, name) {

    override fun getShowName(): String {
        return "${id}号 $name"
    }

    override fun toString(): String {
        return "id:$sheetId    name:$sheetName   isSelected:$isSelected   username:$name  sex:$sex  age:$age"
    }
}