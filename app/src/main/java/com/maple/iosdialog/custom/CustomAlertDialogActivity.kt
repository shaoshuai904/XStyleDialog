package com.maple.iosdialog.custom

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.maple.iosdialog.R
import com.maple.msdialog.AlertDialog
import com.maple.msdialog.utils.DensityUtils.dp2px
import kotlinx.android.synthetic.main.custom_alert_dialog.*

/**
 * 深度自定义 AlertDialog
 *
 * @author : shaoshuai27
 * @date ：2020/8/27
 */
class CustomAlertDialogActivity : Activity() {
    var mScaleWidth: Double = 0.7
    var isCancelable: Boolean = false // 点击其他区域消失

    // 标题
    var mTitle: String? = null
    var mTitleSpSize: Float = 18f
    var mTitlePaddingTop: Float = 22f

    // 消息
    var mMsg: String? = null
    var mMsgSpSize: Float = 14f
    var isHtmlMsg: Boolean = true // 富文本消息
    var mGravity: Int = Gravity.CENTER // 消息显示位置
    var mMessagePaddingBottom: Float = 22f

    // 按钮
    var mLeftBtn: String? = null
    var mRightBtn: String? = null
    var mBottomHeight: Float = 48f // 按钮高度
    var mBtnTextSizeSp: Float = 18f // 字体大小


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_alert_dialog)

        initListener()
        setDefaultValue()
    }

    // 设置默认值
    private fun setDefaultValue() {
        et_title.setText("退出当前账号")
        et_message.setText("再连续登陆<font color=\"#ff0000\">15435</font>天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
        et_left_btn.setText("取消")
        et_right_btn.setText("确认退出")
        sb_scale_width.progress = 5 // 默认宽度：0.75
        sb_title_size.progress = 4 // 标题文本大小
        sb_title_padding_top.progress = 3 // 标题上间距
        sb_msg_size.progress = 4 // 消息文本大小
        sb_msg_padding_bottom.progress = 3 // 消息下间距
        sb_button_height.progress = 6 // 按钮高度
        sb_button_text_size.progress = 4 // 按钮字体大小
    }

    fun showDialog(view: View?) {
        // config统一配置 或 具体方法设置
        val config = AlertDialog.Config(this).apply {
            titleTextSizeSp = mTitleSpSize
            titlePaddingTop = mTitlePaddingTop.dp2px(context)
            messageTextSizeSp = mMsgSpSize
            messagePaddingBottom = mMessagePaddingBottom.dp2px(context)

            bottomViewHeight = mBottomHeight.dp2px(context)
            leftBtnTextSizeSp = mBtnTextSizeSp
            rightBtnTextSizeSp = mBtnTextSizeSp
            rightBtnColor = Color.RED
        }
        AlertDialog(this, config).apply {
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(isCancelable)
            setScaleWidth(mScaleWidth) // 设置宽度，占屏幕宽度百分比
            if (!mTitle.isNullOrEmpty())
                setTitle(mTitle, spSize = mTitleSpSize)
            if (isHtmlMsg) {
                // 方式1：简单居中设置
                // setHtmlMessage(mMsg)
                // 方式2：借助setMessage深度自定义
                setMessage(convertHtmlText(mMsg), spSize = mMsgSpSize, gravity = mGravity)
            } else {
                // 可以在config中统一配置。
                setMessage(mMsg, spSize = mMsgSpSize, gravity = mGravity)
            }
            // setBottomViewHeightDp(48f)
            if (!mLeftBtn.isNullOrEmpty())
                setLeftButton(mLeftBtn)
            if (!mRightBtn.isNullOrEmpty()) {
                setRightButton(mRightBtn) {
                    showToast("点击了右侧按钮")
                }
            }
        }.show()
    }


    // ----------------------------------- other methods -------------------------------------------
    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
        et_message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mMsg = s?.toString()?.trim()
            }
        })
        et_left_btn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mLeftBtn = s?.toString()?.trim()
            }
        })
        et_right_btn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mRightBtn = s?.toString()?.trim()
            }
        })
        sb_scale_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小50，一格 +5%
                mScaleWidth = (50 + progress * 5.0) / 100.0
                tv_scale_width.text = "宽度百分比：${mScaleWidth * 100}%"
            }
        })
        sb_title_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2 sp
                mTitleSpSize = 10 + progress * 2f
                tv_title_size.text = "标题大小${mTitleSpSize}sp"
            }
        })
        sb_msg_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +1 sp
                mMsgSpSize = 10 + progress * 1f
                tv_msg_size.text = "消息大小${mMsgSpSize}sp"
            }
        })
        sb_title_padding_top.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +4dp
                mTitlePaddingTop = 10 + progress * 4f
                tv_title_padding_top.text = "标题上间距${mTitlePaddingTop}dp"
            }
        })
        sb_msg_padding_bottom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +4dp
                mMessagePaddingBottom = 10 + progress * 4f
                tv_msg_padding_bottom.text = "消息下间距${mMessagePaddingBottom}dp"
            }
        })
        sb_button_height.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小30，一格 +3 sp
                mBottomHeight = 30 + progress * 3f
                tv_button_height.text = "底部按钮高度${mBottomHeight}dp"
            }
        })
        sb_button_text_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 最小10，一格 +2 sp
                mBtnTextSizeSp = 10 + progress * 2f
                tv_button_text_size.text = "按钮字体大小${mBtnTextSizeSp}sp"
            }
        })
        rg_msg_style.setOnCheckedChangeListener { group, checkedId ->
            mGravity = when (checkedId) {
                R.id.rb_msg_left -> Gravity.START
                R.id.rb_msg_center -> Gravity.CENTER
                R.id.rb_msg_right -> Gravity.END
                else -> Gravity.CENTER
            }
        }
        sw_html_msg.setOnCheckedChangeListener { _, isChecked ->
            isHtmlMsg = isChecked
        }
        sw_cancelable.setOnCheckedChangeListener { _, isChecked ->
            isCancelable = isChecked
        }
    }

}