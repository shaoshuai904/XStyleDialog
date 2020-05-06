package com.maple.msdialog;

public class SheetItem {
    String name;
    int color;
    OnSheetItemClickListener itemClickListener;

    public SheetItem() {
    }

    public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
        this.name = name;
        this.color = color;
        this.itemClickListener = itemClickListener;
    }

    public interface OnSheetItemClickListener {
        void onClick(int index);
    }

}
