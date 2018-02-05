package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 警告框式Dialog [ 标题 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 17/3/21
 */
public class AlertDialog extends BaseDialog {
    private TextView txt_title;
    private TextView txt_msg;
    private Button leftButton;
    private Button rightButton;
    private ImageView img_line;

    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showRightBtn = false;
    private boolean showLeftBtn = false;


    public AlertDialog(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_alert_dialog, null);

        // get custom Dialog layout
        txt_title = rootView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = rootView.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        leftButton = rootView.findViewById(R.id.bt_left);
        leftButton.setVisibility(View.GONE);
        rightButton = rootView.findViewById(R.id.bt_right);
        rightButton.setVisibility(View.GONE);
        img_line = rootView.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // set Dialog style
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(rootView);

        setScaleWidth(0.85);
    }

    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setScaleWidth(double scWidth) {
        return (AlertDialog) super.setScaleWidth(scWidth);
    }

    public AlertDialog setTitle(String title) {
        int color = mContext.getResources().getColor(R.color.actionsheet_red);
        return setTitle(title, color, 18, true);
    }

    public AlertDialog setTitle(String title, int color, int size, boolean isBold) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        if (color != -1)
            txt_title.setTextColor(color);
        if (size > 0)
            txt_title.setTextSize(size);
        if (isBold)
            txt_title.setTypeface(txt_title.getTypeface(), Typeface.BOLD);
        return this;
    }

    public AlertDialog setMsg(String msg) {
        int color = mContext.getResources().getColor(R.color.actionsheet_red);
        return setMsg(msg, color, 16, false);
    }

    public AlertDialog setMsg(String msg, int color, int size, boolean isBold) {
        showMsg = true;
        txt_msg.setVisibility(View.VISIBLE);
        txt_msg.setText(msg);
        if (color != -1)
            txt_msg.setTextColor(color);
        if (size > 0)
            txt_msg.setTextSize(size);
        if (isBold)
            txt_msg.setTypeface(txt_msg.getTypeface(), Typeface.BOLD);
        return this;
    }

    public AlertDialog setRightButton(String text, final OnClickListener listener) {
        int color = mContext.getResources().getColor(R.color.actionsheet_blue);
        return setRightButton(text, color, 16, false, listener);
    }

    public AlertDialog setRightButton(String text, int color, int size, boolean isBold, final OnClickListener listener) {
        showRightBtn = true;
        if (TextUtils.isEmpty(text)) {
            rightButton.setText("OK");
        } else {
            rightButton.setText(text);
        }
        if (color != -1)
            rightButton.setTextColor(color);
        if (size > 0)
            rightButton.setTextSize(size);
        if (isBold)
            rightButton.setTypeface(rightButton.getTypeface(), Typeface.BOLD);
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setLeftButton(String text, final OnClickListener listener) {
        int color = mContext.getResources().getColor(R.color.actionsheet_blue);
        return setLeftButton(text, color, 16, false, listener);
    }

    public AlertDialog setLeftButton(String text, int color, int size, boolean isBold, final OnClickListener listener) {
        showLeftBtn = true;
        if (TextUtils.isEmpty(text)) {
            leftButton.setText("Cancel");
        } else {
            leftButton.setText(text);
        }
        if (color != -1)
            leftButton.setTextColor(color);
        if (size > 0)
            leftButton.setTextSize(size);
        if (isBold)
            leftButton.setTypeface(leftButton.getTypeface(), Typeface.BOLD);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("Alert");
            txt_title.setVisibility(View.VISIBLE);
        }
        // one button
        if (!showRightBtn && !showLeftBtn) {
            rightButton.setText("OK");
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setBackgroundResource(R.drawable.alertdialog_single_selector);
            rightButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showRightBtn && !showLeftBtn) {
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showRightBtn && showLeftBtn) {
            leftButton.setVisibility(View.VISIBLE);
            leftButton.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
        // two button
        if (showRightBtn && showLeftBtn) {
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setBackgroundResource(R.drawable.alertdialog_right_selector);
            leftButton.setVisibility(View.VISIBLE);
            leftButton.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

}
