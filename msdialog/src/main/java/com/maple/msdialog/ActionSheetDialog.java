package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 页签式Dialog [ 标题 + 页签条目 + 取消按钮 ]
 *
 * @author maple
 * @time 17/3/21
 */
public class ActionSheetDialog extends BaseDialog {
    public static final int ACTION_SHEET_ITEM_HEIGHT = 45;

    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;

    public ActionSheetDialog(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_action_sheet_dialog, null);
        // set Dialog min width
        rootView.setMinimumWidth(getScreenWidth());
        // get custom Dialog layout
        sLayout_content = rootView.findViewById(R.id.sLayout_content);
        lLayout_content = rootView.findViewById(R.id.lLayout_content);
        txt_title = rootView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_cancel = rootView.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // create Dialog
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(rootView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setTitle(title, color, 16, false);
    }

    public ActionSheetDialog setTitle(String title, int color, int size, boolean isBold) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
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

    public ActionSheetDialog setCancelText(String cancelText) {
        int color = ContextCompat.getColor(mContext, R.color.def_title_color);
        return setCancelText(cancelText, color, 18, false);
    }

    public ActionSheetDialog setCancelText(String cancelText, int color, int size, boolean isBold) {
        txt_cancel.setText(cancelText);
        if (color != -1) {
            txt_cancel.setTextColor(color);
        }
        if (size > 0) {
            txt_cancel.setTextSize(size);
        }
        if (isBold) {
            txt_cancel.setTypeface(txt_cancel.getTypeface(), Typeface.BOLD);
        }
        return this;
    }

    public ActionSheetDialog addSheetItem(String strItem, OnSheetItemClickListener listener) {
        int color = ContextCompat.getColor(mContext, R.color.def_message_color);
        return addSheetItem(strItem, color, listener);
    }

    public ActionSheetDialog addSheetItem(String strItem, int color, OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    /**
     * set items layout
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }
        int size = sheetItemList.size();
        // 添加条目过多的时候控制高度
        if (size > getScreenHeight() / dp2px(ACTION_SHEET_ITEM_HEIGHT * 2)) {
            LayoutParams params = (LayoutParams) sLayout_content.getLayoutParams();
            params.height = getScreenHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }
        // loop add item
        for (int i = 1; i <= size; i++) {
            final int index = i;
            final SheetItem sheetItem = sheetItemList.get(i - 1);

            TextView textView = new TextView(mContext);
            textView.setText(sheetItem.name);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            // set item background
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            // set item text color
            if (sheetItem.color != -1) {
                textView.setTextColor(sheetItem.color);
            }

            // set item height
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(ACTION_SHEET_ITEM_HEIGHT)));

            // add click listener
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sheetItem.itemClickListener != null) {
                        sheetItem.itemClickListener.onClick(index);
                    }
                    dialog.dismiss();
                }
            });

            lLayout_content.addView(textView);
        }
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    // ----------------------------------------------------------------------------------------------------

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        int color;
        OnSheetItemClickListener itemClickListener;

        public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

}
