package com.maple.iosdialog

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
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

    // -------------------------------- Action Sheet Dialog ----------------------------------------
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
        val onItemClickListener = SheetItem.OnSheetItemClickListener { }
        ActionSheetDialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            addSheetItem("发送给好友", Color.parseColor(DEF_BLUE), onItemClickListener)
            addSheetItem("转载到空间相册", Color.parseColor(DEF_BLUE), onItemClickListener)
            addSheetItem("上传到群相册", onItemClickListener)
            addSheetItem("保存到手机", onItemClickListener)
            addSheetItem("收藏", onItemClickListener)
            addSheetItem("查看聊天图片", onItemClickListener)
        }.show()
    }

    fun asList(view: View?) {
        val onItemClickListener = SheetItem.OnSheetItemClickListener { index -> showToast("item $index") }
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

    // ------------------------------------ Alert Dialog -------------------------------------------
    fun adOne(view: View?) {
        AlertDialog(this).apply {
            setCancelable(false)
            setTitle("退出当前账号")
            setMessage("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
            setLeftButton("取消", null)
            setRightButton("确认退出", View.OnClickListener { showToast("exit") })
        }.show()
    }

    fun adTwo(view: View?) {
        AlertDialog(this).apply {
            setCancelable(true)
            setScaleWidth(0.7) // 设置宽度，占屏幕宽度百分比
            setMessage("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
            setRightButton("确定", View.OnClickListener { showToast("OK") })
        }.show()
    }

    // --------------------------------- Alert Edit Dialog -----------------------------------------
    fun aeOne(view: View?) {
        AlertEditDialog(this).apply {
            setTitle("姓名")
            setMessage("请输入您的真实姓名。")
            setLeftButton("取消", null)
            setRightButton("确定", object : EditTextCallListener {
                override fun callBack(str: String?) {
                    showToast(str)
                }
            })
        }.show()
    }

    fun aeTwo(view: View?) {
        AlertEditDialog(this).apply {
            setMessage("给自己起一个好听的名字吧")
            setRightButton("确定", object : EditTextCallListener {
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
            setLeftButton("Cancel", null)
            setRightButton("OK", View.OnClickListener { showToast(defValue) })
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
            setLeftButton("取消", null)
            setRightButton("确定", View.OnClickListener { showToast(defValue) })
        }.show()
    }

    // ----------------------------------- other methods -------------------------------------------
    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DEF_BLUE = "#037BFF"
        const val DEF_RED = "#FD4A2E"
    }
}