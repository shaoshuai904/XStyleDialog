package com.maple.msdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.maple.msdialog.databinding.DialogActionSheetBinding
import java.util.*

/**
 * 页签式Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author maple
 * @time 2017/3/21
 */
class ActionSheetDialog(context: Context) : BaseDialog(context) {
    private val binding: DialogActionSheetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet, null, false)
    private var showTitle = false
    private var sheetItemList: MutableList<SheetItem>? = null

    companion object {
        const val ACTION_SHEET_ITEM_HEIGHT = 50
    }

    init {
        rootView = binding.root
        // set Dialog min width
        rootView?.minimumWidth = screenInfo().x
        binding.tvTitle.visibility = View.GONE
        binding.tvCancel.setOnClickListener { dialog.dismiss() }

        // create Dialog
        dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(binding.root)
        dialog.window?.apply {
            setGravity(Gravity.LEFT or Gravity.BOTTOM)
            attributes = attributes.apply {
                x = 0
                y = 0
            }
        }
    }

    fun setTitle(
            title: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): ActionSheetDialog {
        showTitle = true
        binding.tvTitle.apply {
            visibility = View.VISIBLE
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun setCancelText(
            cancelText: String?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 18f,
            isBold: Boolean = false
    ): ActionSheetDialog {
        binding.tvCancel.apply {
            text = cancelText
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun addSheetItem(strItem: String?, listener: SheetItem.OnSheetItemClickListener?): ActionSheetDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_message_color)
        return addSheetItem(strItem, color, listener)
    }

    fun addSheetItem(strItem: String?, color: Int, listener: SheetItem.OnSheetItemClickListener?): ActionSheetDialog {
        if (sheetItemList == null) {
            sheetItemList = ArrayList()
        }
        sheetItemList?.add(SheetItem(strItem, color, listener))
        return this
    }

    /**
     * set items layout
     */
    private fun setSheetItems() {
        if (sheetItemList == null || sheetItemList!!.size <= 0) {
            return
        }
        val size = sheetItemList!!.size
        // 添加条目过多的时候控制高度
        val screenHeight = screenInfo().y
        if (size > screenHeight / dp2px(ACTION_SHEET_ITEM_HEIGHT * 2.toFloat())) {
            val params = binding.slContent.layoutParams as LinearLayout.LayoutParams
            params.height = screenHeight / 2
            binding.slContent.layoutParams = params
        }
        // loop add item
        sheetItemList?.forEachIndexed { index, sheetItem ->
            val view = View(mContext).apply {
                setBackgroundResource(R.color.act_line)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(0.3f))
            }
            binding.llContent.addView(view)
            // set item background
            val bg = if (size == 1) {
                if (showTitle) R.drawable.sel_action_sheet_bottom else R.drawable.sel_action_sheet_single
            } else {
                if (showTitle) {
                    if (index < size - 1) R.drawable.sel_action_sheet_middle else R.drawable.sel_action_sheet_bottom
                } else {
                    when {
                        index == 0 -> R.drawable.sel_action_sheet_top
                        index < size - 1 -> R.drawable.sel_action_sheet_middle
                        else -> R.drawable.sel_action_sheet_bottom
                    }
                }
            }
            val textView = TextView(mContext).apply {
                text = sheetItem.showName
                textSize = 18f
                gravity = Gravity.CENTER
                setBackgroundResource(bg)
                setTextColor(sheetItem.ShowColor)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(ACTION_SHEET_ITEM_HEIGHT.toFloat()))
                // add click listener
                setOnClickListener {
                    sheetItem.itemClickListener?.onClick(sheetItem)
                    dialog.dismiss()
                }
            }
            binding.llContent.addView(textView)
        }
    }

    fun show() {
        setSheetItems()
        dialog.show()
    }

}