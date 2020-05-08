package com.maple.iosdialog

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import com.maple.msdialog.*
import com.maple.msdialog.AlertEditDialog.EditTextCallListener

/**
 * Custom Dialog Demo
 *
 * @author maple
 * @time 2017/3/28
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // ------------------------------------ Alert Dialog -------------------------------------------
    fun adOne(view: View?) {
        AlertDialog(this).apply {
            setCancelable(false)
            setTitle("退出当前账号")
            setMessage("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
            setLeftButton("取消")
            setRightButton("确认退出", listener = View.OnClickListener { showToast("exit") })
        }.show()
    }

    fun adTwo(view: View?) {
        AlertDialog(this).apply {
            setCancelable(true)
            setScaleWidth(0.7) // 设置宽度，占屏幕宽度百分比
            setMessage("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
            setRightButton("确定", listener = View.OnClickListener { showToast("OK") })
        }.show()
    }

    fun adThree(view: View?) {
        AlertDialog(this).apply {
            setTitle("确认删除：XXXX？")
            setMessage("1.必须确保空间下不存在任何文件、文件夹或图片样式，否则无法删除;\n2.存储空间删除后不可恢复且可能会影响正在使用该空间的其他用户。",
                    spSize = 14f, gravity = Gravity.START)
            setRightButton("确定", listener = View.OnClickListener { showToast("OK") })
        }.show()
    }

    // -------------------------------- Action Sheet Dialog ----------------------------------------
    private val onItemClickListener = SheetItem.OnSheetItemClickListener { item ->
        showToast("item ${item.showName}")
    }

    fun asMessage(view: View?) {
        ActionSheetDialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
            addSheetItem("清空消息列表", Color.parseColor(DEF_RED), SheetItem.OnSheetItemClickListener { showToast("clear msg list") })
            setCancelText("取 消")
        }.show()
    }

    fun asImage(view: View?) {
        ActionSheetDialog(this).apply {
//            setCancelable(false)
//            setCanceledOnTouchOutside(false)
            addSheetItem("发送给好友", Color.parseColor(DEF_BLUE), onItemClickListener)
            addSheetItem("转载到空间相册", Color.parseColor(DEF_BLUE), onItemClickListener)
//            addSheetItem("上传到群相册", onItemClickListener)
//            addSheetItem("保存到手机", onItemClickListener)
//            addSheetItem("收藏", onItemClickListener)
//            addSheetItem("查看聊天图片", onItemClickListener)
            setCancelText("取消", Color.parseColor(DEF_BLUE))
        }.show()
    }

    fun asList(view: View?) {
        ActionSheetDialog(this).apply {
            setTitle("请选择操作")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            addSheetItem("条目一", Color.parseColor(DEF_RED), onItemClickListener)
            addSheetItem("条目二", onItemClickListener)
            addSheetItem("条目三", Color.BLUE, onItemClickListener)
            addSheetItem("条目四", Color.CYAN, onItemClickListener)
            addSheetItem("条目五", onItemClickListener)
            addSheetItem("条目六", onItemClickListener)
            addSheetItem("条目七", onItemClickListener)
            addSheetItem("条目八", onItemClickListener)
            addSheetItem("条目九", onItemClickListener)
            addSheetItem("条目十", onItemClickListener)
        }.show()
    }

    // -------------------------------- Action Sheet List Dialog ----------------------------------------
    fun aslList(view: View?) {
        val data = arrayListOf(
                SheetItem("变更配置", Color.parseColor("#4762FE")),
                SheetItem("续费", Color.BLUE)
        )
        ActionSheetListDialog(this).apply {
            setCancelText("取消")
            addSheetItems(data, SheetItem.OnSheetItemClickListener { item ->
                showToast(item.showName)
            })
        }.show()
    }

    fun aslListNoCancel(view: View?) {
        val data = arrayListOf(
                SheetItem("list item 0", Color.RED),
                SheetItem("list item 1", Color.BLUE),
                SheetItem("list item 2", Color.YELLOW),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 3"),
                SheetItem("list item 4")
        )
        ActionSheetListDialog(this).apply {
            setTitle("标题")
            addSheetItems(data, SheetItem.OnSheetItemClickListener { item ->
                showToast(item.showName)
            })
        }.show()
    }

    fun aslListTitle(view: View?) {
        val data = arrayListOf(
                SheetItem("list item 0", Color.RED),
                SheetItem("list item 1", Color.BLUE),
                SheetItem("list item 2", Color.GREEN),
                SheetItem("list item 3", Color.YELLOW),
                SheetItem("list item 4"),
                SheetItem("list item 5"),
                SheetItem("list item 6"),
                SheetItem("list item 6"),
                SheetItem("list item 6"),
                SheetItem("list item 6"),
                SheetItem("list item 7")
        )
        ActionSheetListDialog(this).apply {
            setTitle("标题")
            setCancelText("取消")
            addSheetItems(data, SheetItem.OnSheetItemClickListener { item ->
                showToast(item.showName)
            })
        }.show()
    }

    // --------------------------------- Alert Edit Dialog -----------------------------------------
    fun aeOne(view: View?) {
        AlertEditDialog(this).apply {
            setTitle("姓名")
            setMessage("请输入您的真实姓名。")
            setLeftButton("取消")
            setRightButton("确定", listener = object : EditTextCallListener {
                override fun callBack(str: String?) {
                    showToast(str)
                }
            })
        }.show()
    }

    fun aeTwo(view: View?) {
        AlertEditDialog(this).apply {
            setMessage("给自己起一个好听的名字吧")
            setRightButton("确定", listener = object : EditTextCallListener {
                override fun callBack(str: String?) {
                    if (!TextUtils.isEmpty(str)) {
                        showToast(str)
                    }
                }
            })
        }.show()
    }

    // --------------------------------- Number Picker Dialog -----------------------------------------

    var index = 0
    var defValue: String? = null
    fun npOne(view: View?) {
        val numbers = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        defValue = numbers[index]
        AlertNumberPickerDialog(this).apply {
            setTitle("Number")
            setNumberValues(numbers, index, OnValueChangeListener { picker: NumberPicker?, oldVal: Int, newVal: Int -> defValue = numbers[newVal] })
            setLeftButton("Cancel")
            setRightButton("OK", listener = View.OnClickListener { showToast(defValue) })
        }.show()
    }

    fun npTwo(view: View?) {
        val numbers = arrayOf("北京", "上海", "天津", "杭州", "苏州", "深圳")
        defValue = numbers[index]
        AlertNumberPickerDialog(this).apply {
            setScaleWidth(0.8)
            setCancelable(false)
            setTitle("选择城市")
            setNumberValues(numbers, index, OnValueChangeListener { picker: NumberPicker?, oldVal: Int, newVal: Int -> defValue = numbers[newVal] })
            setNumberValueSuffix("市")
            setLeftButton("取消")
            setRightButton("确定", listener = View.OnClickListener { showToast(defValue) })
        }.show()
    }

    // ----------------------------------- other methods -------------------------------------------
    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DEF_BLUE = "#4762FE"
        const val DEF_RED = "#FD4A2E"
    }
}