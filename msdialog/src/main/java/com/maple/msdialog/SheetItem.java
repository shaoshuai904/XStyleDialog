package com.maple.msdialog;

import android.graphics.Color;

public class SheetItem {
    public String showName;
    int ShowColor = Color.parseColor("#333333");
    boolean isSelected = false;
    OnSheetItemClickListener itemClickListener;

    public SheetItem() {
    }

    public SheetItem(String showName) {
        this.showName = showName;
    }

    public SheetItem(String showName, int showColor) {
        this.showName = showName;
        ShowColor = showColor;
    }

    public SheetItem(String name, OnSheetItemClickListener itemClickListener) {
        this.showName = name;
        this.itemClickListener = itemClickListener;
    }

    public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
        this.showName = name;
        this.ShowColor = color;
        this.itemClickListener = itemClickListener;
    }

    public interface OnSheetItemClickListener {
        void onClick(SheetItem item);
    }

}
