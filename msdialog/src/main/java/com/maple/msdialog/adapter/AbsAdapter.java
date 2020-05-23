package com.maple.msdialog.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Adapter
 *
 * @author maple
 * @time 2018/10/11
 */
public abstract class AbsAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    public AbsAdapter(Context context) {
        this(context, null);
    }

    public AbsAdapter(Context context, List<T> datas) {
        mContext = context;
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = datas;
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T t) {
        if (t == null)
            return;
        mDatas.add(t);
        this.notifyDataSetChanged();
    }

    public void remove(int index) {
        if (index < 0 || index >= mDatas.size())
            return;
        mDatas.remove(index);
        this.notifyDataSetChanged();
    }

    public void remove(T data) {
        if (data == null)
            return;
        mDatas.remove(data);
        this.notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        this.notifyDataSetChanged();
    }

    public void refresh(List<T> datas) {
        if (datas == null)
            mDatas = new ArrayList<>();
        else
            mDatas = datas;
        this.notifyDataSetChanged();
    }

    // 测试此列表是否包含指定的对象。
    public boolean contains(T t) {
        return mDatas.contains(t);
    }
}
