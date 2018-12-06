package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


/**
 * 警告框式数字选择器Dialog [ 标题 + 数字选择 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 2018/12/6
 */
public class AlertNumberPickerDialog extends BaseDialog {
    private TextView txt_title;
    private NumberPicker np_number;
    private TextView tv_suffix;
    private TextView leftButton;
    private TextView rightButton;
    private ImageView img_line;

    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showRightBtn = false;
    private boolean showLeftBtn = false;


    public AlertNumberPickerDialog(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_number_picker_dialog, null);

        // get custom Dialog layout
        txt_title = rootView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        np_number = rootView.findViewById(R.id.np_number);
        tv_suffix = rootView.findViewById(R.id.tv_suffix);
        tv_suffix.setVisibility(View.GONE);
        leftButton = rootView.findViewById(R.id.tv_left);
        leftButton.setVisibility(View.GONE);
        rightButton = rootView.findViewById(R.id.tv_right);
        rightButton.setVisibility(View.GONE);
        img_line = rootView.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // set Dialog style
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(rootView);

        setScaleWidth(0.8);
    }

    public AlertNumberPickerDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    @Override
    public AlertNumberPickerDialog setScaleWidth(double scWidth) {
        return (AlertNumberPickerDialog) super.setScaleWidth(scWidth);
    }

    public AlertNumberPickerDialog setTitle(String title) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setTitle(title, color, 18, false);
    }

    public AlertNumberPickerDialog setTitle(String title, int color, int size, boolean isBold) {
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

    public AlertNumberPickerDialog setNumberValues(String[] displayedValues, int defaultValue, NumberPicker.OnValueChangeListener onValueChangedListener) {
        showMsg = true;
        np_number.setDisplayedValues(displayedValues);
        np_number.setMinValue(0);
        np_number.setMaxValue(displayedValues.length - 1);
        // 设置为对当前值不可编辑
        np_number.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        // default value
        np_number.setValue(defaultValue);
        np_number.setOnValueChangedListener(onValueChangedListener);
        return this;
    }

    public AlertNumberPickerDialog setNumberValueSuffix(String suffix) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setNumberValueSuffix(suffix, color, 16);
    }

    public AlertNumberPickerDialog setNumberValueSuffix(String suffix, int color, int size) {
        showMsg = true;
        tv_suffix.setText(suffix);
        if (color != -1) {
            tv_suffix.setTextColor(color);
        }
        if (size > 0) {
            tv_suffix.setTextSize(size);
        }
        tv_suffix.setVisibility(View.VISIBLE);
        return this;
    }

    public AlertNumberPickerDialog setRightButton(String text, final OnClickListener listener) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setRightButton(text, color, 16, false, listener);
    }

    public AlertNumberPickerDialog setRightButton(String text, int color, int size, boolean isBold, final OnClickListener listener) {
        showRightBtn = true;
        if (TextUtils.isEmpty(text)) {
            rightButton.setText(R.string.ok);
        } else {
            rightButton.setText(text);
        }
        if (color != -1) {
            rightButton.setTextColor(color);
        }
        if (size > 0) {
            rightButton.setTextSize(size);
        }
        if (isBold) {
            rightButton.setTypeface(rightButton.getTypeface(), Typeface.BOLD);
        }
        rightButton.setOnClickListener(new OnClickListener() {
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

    public AlertNumberPickerDialog setLeftButton(String text, final OnClickListener listener) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setLeftButton(text, color, 16, false, listener);
    }

    public AlertNumberPickerDialog setLeftButton(String text, int color, int size, boolean isBold, final OnClickListener listener) {
        showLeftBtn = true;
        if (TextUtils.isEmpty(text)) {
            leftButton.setText(R.string.cancel);
        } else {
            leftButton.setText(text);
        }
        if (color != -1) {
            leftButton.setTextColor(color);
        }
        if (size > 0) {
            leftButton.setTextSize(size);
        }
        if (isBold) {
            leftButton.setTypeface(leftButton.getTypeface(), Typeface.BOLD);
        }
        leftButton.setOnClickListener(new OnClickListener() {
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

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText(R.string.alert);
            txt_title.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }
        // one button
        if (!showRightBtn && !showLeftBtn) {
            rightButton.setText(R.string.ok);
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
