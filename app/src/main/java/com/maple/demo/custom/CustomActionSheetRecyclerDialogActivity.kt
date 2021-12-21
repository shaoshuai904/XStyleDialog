package com.maple.demo.custom

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.maple.demo.databinding.CustomActionSheetRecyclerDialogBinding
import com.maple.msdialog.ActionSheetRecyclerDialog
import com.maple.msdialog.ActionSheetRecyclerSingleSelectedDialog
import com.maple.msdialog.SheetItem
import com.maple.msdialog.utils.DensityUtils.dp2px
import java.util.*

/**
 * 深度自定义 ActionSheetRecyclerDialog
 *
 * @author : shaoshuai
 * @date ：2020/8/29
 */
class CustomActionSheetRecyclerDialogActivity : Activity() {
    private lateinit var binding: CustomActionSheetRecyclerDialogBinding
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
        binding = CustomActionSheetRecyclerDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        setDefaultValue()
    }

    // 设置默认值
    private fun setDefaultValue() {
        with(binding) {
            sbScaleHeightMin.progress = 7 // 最小高度
            sbScaleHeightMax.progress = 12 // 最大高度
            etTitle.setText("退出当前账号")
            sbTitleSize.progress = 4 // 标题文本大小
            sbTitleHeight.progress = 5 // 标题高度

            sbItemCount.progress = 3 // item个数
            sbItemTextSize.progress = 2 // item字体大小
            sbItemPaddingTb.progress = 6 // item上下间距

            sbDividerHeight.progress = 2 // 分割线高度
            sbButtonPadding.progress = 2 // Dialog底部padding
        }
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
            selectMark =
                ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background)
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
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mTitle = s?.toString()?.trim()
            }
        })
        binding.sbScaleHeightMin.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mScaleHeightMin = (progress * 5.0) / 100.0
                binding.tvScaleHeightMin.text = "最小高度百分比：${mScaleHeightMin * 100}%"
            }
        })
        binding.sbScaleHeightMax.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mScaleHeightMax = (progress * 5.0) / 100.0
                binding.tvScaleHeightMax.text = "最大高度百分比：${mScaleHeightMax * 100}%"
            }
        })
        binding.sbTitleSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2 sp
                mTitleSpSize = 10 + progress * 2f
                binding.tvTitleSize.text = "标题大小: ${mTitleSpSize}sp"
            }
        })
        binding.sbTitleHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mTitleHeight = 30 + progress * 4f
                binding.tvTitleHeight.text = "标题高度: ${mTitleHeight}dp"
            }
        })
        binding.sbItemCount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mItemCount = progress
                binding.tvItemCount.text = "Item个数: ${mItemCount}个"
            }
        })
        binding.sbItemTextSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2sp
                mItemTextSpSize = 10 + progress * 2f
                binding.tvItemTextSize.text = "Item字体大小${mItemTextSpSize}sp"
            }
        })
        binding.sbItemPaddingTb.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mItemPaddingTopBottom = progress * 2f
                binding.tvItemPaddingTb.text = "Item上下间距${mItemPaddingTopBottom}dp"
            }
        })

        binding.sbDividerHeight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mLineHeight = progress
                binding.tvDividerHeight.text = "分割线高度：${mLineHeight}px"
            }
        })
        binding.sbButtonPadding.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mDialogPaddingBottom = progress * 3f
                binding.tvButtonPadding.text = "Dialog底部间距：${mDialogPaddingBottom}dp"
            }
        })
        binding.swCancelable.setOnCheckedChangeListener { _, isChecked ->
            isCancelable = isChecked
        }
        binding.swShowClose.setOnCheckedChangeListener { _, isChecked ->
            isShowClose = isChecked
        }
        binding.swShowMark.setOnCheckedChangeListener { _, isChecked ->
            isShowItemMark = isChecked
        }
        binding.swLastItemLine.setOnCheckedChangeListener { _, isChecked ->
            isShowLastItemLine = isChecked
        }
    }

}