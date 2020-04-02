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

    init {
        rootView = binding.root
        // set Dialog min width
        rootView?.minimumWidth = screenInfo().x
        binding.tvTitle.visibility = View.GONE
        binding.tvCancel.setOnClickListener { dialog.dismiss() }

        // create Dialog
        dialog  = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(binding.root)
        dialog.window?.apply {
            setGravity(Gravity.LEFT or Gravity.BOTTOM)
            attributes = attributes.apply {
                x = 0
                y = 0
            }
        }
    }

    fun setTitle(title: String?): ActionSheetDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setTitle(title, color, 16, false)
    }

    fun setTitle(title: String?, color: Int, size: Int, isBold: Boolean): ActionSheetDialog {
        showTitle = true
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvTitle.text = title
        if (color != -1) {
            binding.tvTitle.setTextColor(color)
        }
        if (size > 0) {
            binding.tvTitle.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvTitle.setTypeface(binding.tvTitle.typeface, Typeface.BOLD)
        }
        return this
    }

    fun setCancelText(cancelText: String?): ActionSheetDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_title_color)
        return setCancelText(cancelText, color, 18, false)
    }

    fun setCancelText(cancelText: String?, color: Int, size: Int, isBold: Boolean): ActionSheetDialog {
        binding.tvCancel.text = cancelText
        if (color != -1) {
            binding.tvCancel.setTextColor(color)
        }
        if (size > 0) {
            binding.tvCancel.textSize = size.toFloat()
        }
        if (isBold) {
            binding.tvCancel.setTypeface(binding.tvCancel.typeface, Typeface.BOLD)
        }
        return this
    }

    fun addSheetItem(strItem: String?, listener: OnSheetItemClickListener?): ActionSheetDialog {
        val color = ContextCompat.getColor(mContext, R.color.def_message_color)
        return addSheetItem(strItem, color, listener)
    }

    fun addSheetItem(strItem: String?, color: Int, listener: OnSheetItemClickListener?): ActionSheetDialog {
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
        for (i in 1..size) {
            val sheetItem = sheetItemList!![i - 1]
            val textView = TextView(mContext)
            textView.text = sheetItem.name
            textView.textSize = 18f
            textView.gravity = Gravity.CENTER

            // set item background
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector)
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector)
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector)
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector)
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector)
                    }
                }
            }

            // set item text color
            if (sheetItem.color != -1) {
                textView.setTextColor(sheetItem.color)
            }

            // set item height
            textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(ACTION_SHEET_ITEM_HEIGHT.toFloat()))

            // add click listener
            textView.setOnClickListener {
                if (sheetItem.itemClickListener != null) {
                    sheetItem.itemClickListener?.onClick(i)
                }
                dialog?.dismiss()
            }
            binding.llContent.addView(textView)
        }
    }

    fun show() {
        setSheetItems()
        dialog?.show()
    }

    // ----------------------------------------------------------------------------------------------------
    interface OnSheetItemClickListener {
        fun onClick(index: Int)
    }

    inner class SheetItem(
            var name: String?,
            var color: Int,
            var itemClickListener: OnSheetItemClickListener?
    )

    companion object {
        const val ACTION_SHEET_ITEM_HEIGHT = 45
    }
}