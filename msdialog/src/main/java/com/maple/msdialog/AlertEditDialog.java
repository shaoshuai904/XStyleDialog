package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
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
        txt_title = rootView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = rootView.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        et_text = rootView.findViewById(R.id.et_text);
        leftBtn = rootView.findViewById(R.id.btn_left);
        leftBtn.setVisibility(View.GONE);
        rightBtn = rootView.findViewById(R.id.btn_right);
        rightBtn.setVisibility(View.GONE);
        img_line = rootView.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // set Dialog style
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(rootView);

        setScaleWidth(0.85);
    }

    public AlertEditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    @Override
    public AlertEditDialog setScaleWidth(double scWidth) {
        return (AlertEditDialog) super.setScaleWidth(scWidth);
    }

    public AlertEditDialog setTitle(String title) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setTitle(title, color, 18, true);
    }

    public AlertEditDialog setTitle(String title, int color, int size, boolean isBold) {
        showTitle = true;
        txt_title.setText(title);
        if (color != -1) {
            txt_title.setTextColor(color);
        }
        if (size > 0) {
            txt_title.setTextSize(size);
        }
        if (isBold) {
            txt_title.setTypeface(txt_title.getTypeface(), Typeface.BOLD);
        }
        return this;
    }

    public AlertEditDialog setMessage(String msg) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setMessage(msg, color, 16, false);
    }

    public AlertEditDialog setMessage(String msg, int color, int size, boolean isBold) {
        showMsg = true;
        txt_msg.setText(msg);
        if (color != -1) {
            txt_msg.setTextColor(color);
        }
        if (size > 0) {
            txt_msg.setTextSize(size);
        }
        if (isBold) {
            txt_msg.setTypeface(txt_msg.getTypeface(), Typeface.BOLD);
        }
        return this;
    }

    public AlertEditDialog setRightButton(String text, final EditTextCallListener listener) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setRightButton(text, color, 16, false, listener);
    }

    public AlertEditDialog setRightButton(String text, int color, int size, boolean isBold, final EditTextCallListener listener) {
        showRightBtn = true;
        rightBtn.setText(text);
        if (color != -1) {
            rightBtn.setTextColor(color);
        }
        if (size > 0) {
            rightBtn.setTextSize(size);
        }
        if (isBold) {
            rightBtn.setTypeface(rightBtn.getTypeface(), Typeface.BOLD);
        }
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String inputText = et_text.getText().toString().trim();
                    listener.callBack(inputText);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertEditDialog setLeftButton(String text, final OnClickListener listener) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setLeftButton(text, color, 16, false, listener);
    }

    public AlertEditDialog setLeftButton(String text, int color, int size, boolean isBold, final OnClickListener listener) {
        showLeftBtn = true;
        leftBtn.setText(text);
        if (color != -1) {
            leftBtn.setTextColor(color);
        }
        if (size > 0) {
            leftBtn.setTextSize(size);
        }
        if (isBold) {
            leftBtn.setTypeface(leftBtn.getTypeface(), Typeface.BOLD);
        }
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public interface EditTextCallListener {
        void callBack(String str);
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText(R.string.alert);
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showRightBtn && !showLeftBtn) {
            rightBtn.setText(R.string.ok);
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
