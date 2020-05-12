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
import com.maple.msdialog.databinding.DialogActionSheetListBinding

/**
 * 页签List Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author : shaoshuai27
 * @date ：2020/5/6
 */
class ActionSheetListDialog(context: Context) : BaseDialog(context) {
    private val binding: DialogActionSheetListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_action_sheet_list, null, false)
    private var showTitle = false
    private val adapter by lazy { ActionSheetAdapter(mContext) }
    private var sheetItemList: MutableList<SheetItem>? = null

    companion object {
        const val ACTION_SHEET_ITEM_HEIGHT = 50
    }

    init {
        rootView = binding.root
        // set Dialog min width
        rootView?.minimumWidth = screenInfo().x
        binding.tvTitle.visibility = View.GONE
        binding.tvCancel.visibility = View.GONE
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
            title: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 16f,
            isBold: Boolean = false
    ): ActionSheetListDialog {
        showTitle = true
        binding.tvTitle.apply {
            visibility = View.VISIBLE
            text = title
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        adapter.showTitle(showTitle)
        return this
    }

    fun setCancelText(
            cancelText: CharSequence?,
            color: Int = ContextCompat.getColor(mContext, R.color.def_title_color),
            spSize: Float = 18f,
            isBold: Boolean = false
    ): ActionSheetListDialog {
        binding.tvCancel.apply {
            visibility = View.VISIBLE
            text = cancelText
            setTextColor(color)
            textSize = spSize
            setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
        }
        return this
    }

    fun addSheetItems(
            items: MutableList<SheetItem>,
            itemClickListener: SheetItem.OnSheetItemClickListener?
    ) {
        sheetItemList = items
        binding.lvData.adapter = adapter
        adapter.refresh(sheetItemList)
        binding.lvData.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            adapter.setSelectedIndex(position)
            itemClickListener?.onClick(item)
            dialog.dismiss()
        }
    }

    fun setSelectedIndex(index: Int) {
        adapter.setSelectedIndex(index)
    }

    /**
     * set items layout
     */
    private fun setSheetLayout() {
        if (sheetItemList == null || sheetItemList!!.size <= 0) {
            return
        }
        val size = sheetItemList!!.size
        // 添加条目过多的时候控制高度
        val screenHeight = screenInfo().y
        if (size > screenHeight / dp2px(ACTION_SHEET_ITEM_HEIGHT * 2.toFloat())) {
            val params = binding.lvData.layoutParams as LinearLayout.LayoutParams
            params.height = screenHeight / 2
            binding.lvData.layoutParams = params
        }
    }

    fun show() {
        setSheetLayout()
        dialog.show()
    }

}