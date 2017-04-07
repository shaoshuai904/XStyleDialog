package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 警告框式Edit Dialog [ 标题 + 输入框 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 17/3/23
 */
public class AlertEditDialog extends BaseDialog {
    private TextView txt_title;
    private TextView txt_msg;
    private EditText et_text;
    private Button leftBtn;
    private Button rightBtn;
    private ImageView img_line;

    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showLeftBtn = false;
    private boolean showRightBtn = false;

    public AlertEditDialog(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_alert_edit_dialog, null);

        // get custom Dialog layout
        txt_title = (TextView) rootView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) rootView.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        et_text = (EditText) rootView.findViewById(R.id.et_text);
        leftBtn = (Button) rootView.findViewById(R.id.btn_left);
        leftBtn.setVisibility(View.GONE);
        rightBtn = (Button) rootView.findViewById(R.id.btn_right);
        rightBtn.setVisibility(View.GONE);
        img_line = (ImageView) rootView.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // set Dialog style
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(rootView);

        setScaleWidth(0.85);
    }

    public AlertEditDialog setScaleWidth(double scWidth) {
        return (AlertEditDialog) super.setScaleWidth(scWidth);
    }

    public AlertEditDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            txt_title.setText("Alert");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertEditDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("body text");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertEditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertEditDialog setRightButton(String text, final OnClickListener listener) {
        showRightBtn = true;
        if (TextUtils.isEmpty(text)) {
            rightBtn.setText("OK");
        } else {
            rightBtn.setText(text);
        }
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertEditDialog setLeftButton(String text, final OnClickListener listener) {
        showLeftBtn = true;
        if (TextUtils.isEmpty(text)) {
            leftBtn.setText("Cancel");
        } else {
            leftBtn.setText(text);
        }
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public interface EditTextCallListener {
        void callBack(String str);
    }

    public AlertEditDialog setEditCallListener(final EditTextCallListener callListener) {
        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callListener.callBack(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return this;
    }


    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("Alert");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showRightBtn && !showLeftBtn) {
            rightBtn.setText("OK");
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setBackgroundResource(R.drawable.alertdialog_single_selector);
            rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showRightBtn && showLeftBtn) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setBackgroundResource(R.drawable.alertdialog_right_selector);
            leftBtn.setVisibility(View.VISIBLE);
            leftBtn.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showRightBtn && !showLeftBtn) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showRightBtn && showLeftBtn) {
            leftBtn.setVisibility(View.VISIBLE);
            leftBtn.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

}
