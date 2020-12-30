package com.maple.iosdialog.custom

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.maple.iosdialog.R
import com.maple.msdialog.ActionSheetRecyclerDialog
import com.maple.msdialog.ActionSheetRecyclerSingleSelectedDialog
import com.maple.msdialog.SheetItem
import com.maple.msdialog.utils.DensityUtils.dp2px
import kotlinx.android.synthetic.main.custom_action_sheet_recycler_dialog.*
import java.util.*

/**
 * 深度自定义 ActionSheetRecyclerDialog
 *
 * @author : shaoshuai
 * @date ：2020/8/29
 */
class CustomActionSheetRecyclerDialogActivity : Activity() {
    var mScaleHeightMin: Double = 0.25
    var mScaleHeightMax: Double = 0.6
    var isCancelable: Boolean = true // 点击其他区域消失
    var isShowClose: Boolean = true // 关闭按钮
    var isShowItemMark: Boolean = true // item选中标志
    var isShowLastItemLine: Boolean = true// 最后一个item的分割线
    var mDialogPaddingBottom: Float = 12f

    // 标题
    var mTitle: String? = null
    var mTitleSpSize: Float = 18f
    var mTitleHeight: Float = 48f

    // item
    var mItemCount: Int = 3
    var mItemTextSpSize: Float = 14f
    var mItemPaddingTopBottom: Float = 12f
    var mLineHeight: Int = 1// 分割线


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_action_sheet_recycler_dialog)

        initListener()
        setDefaultValue()
    }

    // 设置默认值
    private fun setDefaultValue() {
        sb_scale_height_min.progress = 7 // 最小高度
        sb_scale_height_max.progress = 12 // 最大高度
        et_title.setText("退出当前账号")
        sb_title_size.progress = 4 // 标题文本大小
        sb_title_height.progress = 5 // 标题高度

        sb_item_count.progress = 3 // item个数
        sb_item_text_size.progress = 2 // item字体大小
        sb_item_padding_tb.progress = 6 // item上下间距

        sb_divider_height.progress = 2 // 分割线高度
        sb_button_padding.progress = 2 // Dialog底部padding
    }

    var currentSelectedIndex = 0 // 当前选中条目
    fun showDialog(view: View?) {
        val items = getSingleSelectItemTestData(mItemCount)
        if (currentSelectedIndex < items.size) {
            items[currentSelectedIndex].isSelected = true
        }
        // config统一配置 或 具体方法设置
        val config = ActionSheetRecyclerDialog.Config(this).apply {
            titleTextSizeSp = mTitleSpSize
            titleViewHeight = mTitleHeight.dp2px(context)
            closeDraw = ContextCompat.getDrawable(context, android.R.drawable.ic_delete)
            itemTextSizeSp = mItemTextSpSize // 字体大小
            itemTextSelectedColor = Color.RED
            itemPaddingTop = mItemPaddingTopBottom.dp2px(context)
            itemPaddingBottom = mItemPaddingTopBottom.dp2px(context)
            isShowMark = isShowItemMark
            selectMark = ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background)
            skipLastItems = if (isShowLastItemLine) 0 else 1
            dividerHeight = mLineHeight  // 分割线高度
        }
        ActionSheetRecyclerSingleSelectedDialog(this, config).apply {
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(isCancelable)
            // setScaleWidth(getRootView(), 0.8)
            setMinScaleHeight(mScaleHeightMin)
            setMaxScaleHeight(mScaleHeightMax)
            setDataBottomPadding(mDialogPaddingBottom)

            setTitle(mTitle)
            setCloseVisibility(isShowClose)

            addSheetItems(items)
            addSheetItemClickListener { item, position ->
                currentSelectedIndex = position
                showToast("点击了：$position   ${item.getShowName()}")
            }
        }.show()
    }

    // ----------------------------------- other methods -------------------------------------------

    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // 获取测试数据
    private fun getSingleSelectItemTestData(count: Int): ArrayList<SheetItem> {
        val testData = arrayListOf<SheetItem>()
        for (index in 1..count) {
            val mColor = -0x1000000 or Random().nextInt(0xffffff)
            testData.add(SheetItem("Custom Sheet View $index", mColor))
        }
        return testData
    }

    // 各种变化监听
    private fun initListener() {
        et_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mTitle = s?.toString()?.trim()
            }
        })
        sb_scale_height_min.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mScaleHeightMin = (progress * 5.0) / 100.0
                tv_scale_height_min.text = "最小高度百分比：${mScaleHeightMin * 100}%"
            }
        })
        sb_scale_height_max.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mScaleHeightMax = (progress * 5.0) / 100.0
                tv_scale_height_max.text = "最大高度百分比：${mScaleHeightMax * 100}%"
            }
        })
        sb_title_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2 sp
                mTitleSpSize = 10 + progress * 2f
                tv_title_size.text = "标题大小: ${mTitleSpSize}sp"
            }
        })
        sb_title_height.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mTitleHeight = 30 + progress * 4f
                tv_title_height.text = "标题高度: ${mTitleHeight}dp"
            }
        })
        sb_item_count.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mItemCount = progress
                tv_item_count.text = "Item个数: ${mItemCount}个"
            }
        })
        sb_item_text_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2sp
                mItemTextSpSize = 10 + progress * 2f
                tv_item_text_size.text = "Item字体大小${mItemTextSpSize}sp"
            }
        })
        sb_item_padding_tb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mItemPaddingTopBottom = progress * 2f
                tv_item_padding_tb.text = "Item上下间距${mItemPaddingTopBottom}dp"
            }
        })

        sb_divider_height.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mLineHeight = progress
                tv_divider_height.text = "分割线高度：${mLineHeight}px"
            }
        })
        sb_button_padding.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mDialogPaddingBottom = progress * 3f
                tv_button_padding.text = "Dialog底部间距：${mDialogPaddingBottom}dp"
            }
        })
        sw_cancelable.setOnCheckedChangeListener { _, isChecked ->
            isCancelable = isChecked
        }
        sw_show_close.setOnCheckedChangeListener { _, isChecked ->
            isShowClose = isChecked
        }
        sw_show_mark.setOnCheckedChangeListener { _, isChecked ->
            isShowItemMark = isChecked
        }
        sw_last_item_line.setOnCheckedChangeListener { _, isChecked ->
            isShowLastItemLine = isChecked
        }
    }

}