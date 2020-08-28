package com.maple.iosdialog.custom

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.maple.iosdialog.R
import com.maple.msdialog.AlertDialog
import com.maple.msdialog.utils.DensityUtils.dp2px
import kotlinx.android.synthetic.main.custom_alert_dialog.*

/**
 * 深度自定义AlertDialog
 *
 * @author : shaoshuai27
 * @date ：2020/8/27
 */
class CustomAlertDialogActivity : Activity() {
    var mTitle: String? = null
    var mMsg: String? = null
    var mLeftBtn: String? = null
    var mRightBtn: String? = null
    var mGravity: Int = Gravity.CENTER // 消息显示位置
    var isCancelable: Boolean = false // 点击其他区域消失
    var isHtmlMsg: Boolean = true // 富文本消息


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
    }

    fun showDialog(view: View?) {
        val config = AlertDialog.Config(this).apply {
            rightBtnColor = Color.RED
            messagePaddingBottom = 40f.dp2px(context)
        }
        AlertDialog(this, config).apply {
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(isCancelable)
            setScaleWidth(0.75) // 设置宽度，占屏幕宽度百分比
            if (!mTitle.isNullOrEmpty())
                setDialogTitle(mTitle)
            if (isHtmlMsg) {
                // 方式1：简单居中设置
                // setHtmlMessage(mMsg)
                // 方式2：借助setMessage深度自定义
                setMessage(convertHtmlText(mMsg), gravity = mGravity)
            } else {
                setMessage(mMsg, gravity = mGravity)
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