package com.maple.iosdialog

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import com.maple.msdialog.*
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.msdialog.utils.DialogUtil.setScaleWidth
import java.util.*

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
        val config = AlertDialog.Config(this).apply {
            rightBtnColor = Color.RED
            messagePaddingBottom = 40f.dp2px(context)
        }
        AlertDialog(this, config)
                .setDialogTitle("退出当前账号")
                .setHtmlMessage("再连续登陆<font color=\"#ff0000\">15</font>天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                .setLeftButton("取消")
                .setRightButton("确认退出", View.OnClickListener { showToast("exit") })
                .show()
    }

    fun adTwo(view: View?) {
        AlertDialog(this).apply {
            setCancelable(true)
            setScaleWidth(0.75) // 设置宽度，占屏幕宽度百分比
            // getMessageView().setPadding(15f.dp2px(context), 24f.dp2px(context), 15f.dp2px(context), 24f.dp2px(context))
            setMessage("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
            // setBottomViewHeightDp(48f)
            setRightButton("确定", View.OnClickListener { showToast("OK") })
        }.show()
    }

    fun adThree(view: View?) {
        AlertDialog(this)
                .setTitle("确认删除：XXXX？", isBold = false)
                .setMessage("1.必须确保空间下不存在任何文件、文件夹或图片样式，否则无法删除;\n2.存储空间删除后不可恢复且可能会影响正在使用该空间的其他用户。",
                        spSize = 14f, gravity = Gravity.START)
                .setRightButton("确定", View.OnClickListener { showToast("OK") })
                .show()
    }

    // -------------------------------- Action Sheet Dialog ----------------------------------------
    private val onItemClickListener = OnSheetItemClickListener { item, position ->
        showToast("item ${item.getShowName()}")
    }

    fun asMessage(view: View?) {
        ActionSheetDialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
            addSheetItem("清空消息列表", OnSheetItemClickListener { item, position ->
                showToast("clear msg list")
            })
            // setCancelText("取 消")
        }.show()
    }

    fun asImage(view: View?) {
        ActionSheetDialog(this).apply {
//            setCancelable(false)
//            setCanceledOnTouchOutside(false)
            addSheetItem("发送给好友", Color.parseColor(DEF_BLUE), onItemClickListener)
            addSheetItem("转载到空间相册", Color.parseColor(DEF_BLUE), onItemClickListener)
//            addSheetItem("上传到群相册")
//            addSheetItem("保存到手机")
//            addSheetItem("收藏")
//            addSheetItem("查看聊天图片")
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
            addSheetItem(SheetItem("条目九").apply { itemClickListener = onItemClickListener })
            addSheetItem(SheetItem("条目十", Color.CYAN, onItemClickListener))
        }.show()
    }

    // -------------------------------- Action Sheet Recycler List Dialog ----------------------------------------

    var ar1: ActionSheetRecyclerDialog? = null
    fun asrList(view: View?) {
        if (ar1 == null) {
            val items = arrayListOf(
                    User("001", "张三", 23),
                    User("002", "李四", 8),
                    User("004", "王五", 11),
                    User("007", "赵六六", 123)
            )
            ar1 = ActionSheetRecyclerDialog(this).apply {
                setTitle("选择条目")
                setCloseVisibility(false)
                setBottomPadding(12f)// 默认底部留白：20dp
                addSheetItems(items)
                addSheetItemClickListener(OnSheetItemClickListener { item, position ->
                    if (item is User) {
                        showToast("${item.name}  年龄:${item.age}岁")
                    } else {
                        showToast("$position   ${item.getShowName()}")
                    }
                })
                isShowItemMark(false)// 不显示item 选中对勾
                setSelectedIndex(1)// 选中第二个
            }
        }
        ar1?.show()
    }

    var ar2: ActionSheetRecyclerDialog? = null
    fun asrBigDataList(view: View?) {
        if (ar2 == null) {
            val items = getSingleSelectItemTestData(20)
            ar2 = ActionSheetRecyclerDialog(this, ActionSheetRecyclerDialog.Config(this).apply {
                titleTextSizeSp = 18f
                closeDraw = ContextCompat.getDrawable(context, android.R.drawable.ic_delete)
                isShowMark = true
                selectMark = ContextCompat.getDrawable(context, android.R.drawable.ic_media_next)
                itemTextSelectedColor = Color.RED
            }).apply {
                setTitle("选择条目")
                addSheetItems(items)
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                setMaxScaleHeight(0.65)
                addSheetItemClickListener(OnSheetItemClickListener { item, position ->
                    showToast("$position   ${item.getShowName()}")
                })
            }
        }
        ar2?.show()
    }

    // -------------------------------- Action Sheet List Dialog ----------------------------------------
    var asl1: ActionSheetListDialog? = null
    fun aslList(view: View?) {
        if (asl1 == null) {
            asl1 = ActionSheetListDialog(this).apply {
                setCancelText("取消")
                addSheetItems(getSingleSelectItemTestData(2), OnSheetItemClickListener { item, position ->
                    showToast(item.getShowName())
                })
            }
        }
        asl1?.show()
    }

    var asl2: ActionSheetListDialog? = null
    fun aslListNoCancel(view: View?) {
        if (asl2 == null) {
            asl2 = ActionSheetListDialog(this).apply {
                setTitle("标题")
                addSheetItems(getSingleSelectItemTestData(10), OnSheetItemClickListener { item, position ->
                    showToast(item.getShowName())
                })
                setSelectedIndex(3)
            }
        }
        asl2?.show()
    }

    var asl3: ActionSheetListDialog? = null
    fun aslListTitle(view: View?) {
        if (asl3 == null) {
            asl3 = ActionSheetListDialog(this).apply {
                setTitle("标题")
                setCancelText("取消")
                addSheetItems(getSingleSelectItemTestData(20), OnSheetItemClickListener { item, position ->
                    showToast(item.getShowName())
                })
            }
        }
        asl3?.show()
    }

    // --------------------------------- Alert Edit Dialog -----------------------------------------
    fun aeOne(view: View?) {
        AlertEditDialog(this).apply {
            setTitle("姓名")
            setMessage("请输入您的真实姓名。")
            setLeftButton("取消")
            setRightButton("确定", OnEditTextCallListener {
                showToast(it)
            })
        }.show()
    }

    fun aeTwo(view: View?) {
        AlertEditDialog(this).apply {
            setMessage("给自己起一个好听的名字吧")
            setRightButton("确定", OnEditTextCallListener {
                showToast(it)
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
            setRightButton("OK", View.OnClickListener { showToast(defValue) })
        }.show()
    }

    fun npTwo(view: View?) {
        val numbers = arrayOf("北京", "上海", "天津", "杭州", "苏州", "深圳")
        defValue = numbers[index]
        AlertNumberPickerDialog(this).apply {
            setScaleWidth(rootView, 0.8)
            setCancelable(false)
            setTitle("选择城市")
            setNumberValues(numbers, index, OnValueChangeListener { picker: NumberPicker?, oldVal: Int, newVal: Int -> defValue = numbers[newVal] })
            setNumberValueSuffix("市")
            setLeftButton("取消")
            setRightButton("确定", View.OnClickListener { showToast(defValue) })
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

    // 获取测试数据
    private fun getSingleSelectItemTestData(count: Int): ArrayList<SheetItem> {
        val testData = arrayListOf<SheetItem>()
        for (index in 1..count) {
            val mColor = -0x1000000 or Random().nextInt(0xffffff)
            testData.add(SheetItem("single select item $index", mColor))
        }
        return testData
    }
}