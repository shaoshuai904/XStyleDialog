package com.maple.msdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
        sLayout_content = (ScrollView) rootView.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) rootView.findViewById(R.id.lLayout_content);
        txt_title = (TextView) rootView.findViewById(R.id.txt_title);
        txt_cancel = (TextView) rootView.findViewById(R.id.txt_cancel);
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

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

//    public ActionSheetDialog addSheetItem(String strItem) {
//        return addSheetItem(strItem, SheetItemColor.Blue, null);
//    }
//
//    public ActionSheetDialog addSheetItem(String strItem, OnSheetItemClickListener listener) {
//        return addSheetItem(strItem, SheetItemColor.Blue, listener);
//    }

    /**
     * @param strItem  item text
     * @param color    item text color，default:Blue
     * @param listener item click listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null)
            sheetItemList = new ArrayList<>();
        if (color == null)
            color = SheetItemColor.Blue;
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    /** set items layout */
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
            if (sheetItem.color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue.colorString));
            } else {
                textView.setTextColor(Color.parseColor(sheetItem.color.colorString));
            }

            // set item height
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(ACTION_SHEET_ITEM_HEIGHT)));

            // add click listener
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sheetItem.itemClickListener != null)
                        sheetItem.itemClickListener.onClick(index);
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

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        String colorString;

        SheetItemColor(String colorString) {
            this.colorString = colorString;
        }

    }
}
