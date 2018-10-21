package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 多种子类型 布局
 */
public abstract class AbsMultiTypeAdapter<T> extends BaseAdapter {
    protected Context        mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<T>   mData;

    public AbsMultiTypeAdapter(Context c) {
        mContext  = c;
        mInflater = LayoutInflater.from(mContext);
        mData     = new ArrayList<T>();
    }

    public void add(T t) {
        if (t != null) {
            mData.add(t);
        }
    }

    /**
     * 设置源数据
     *
     * @param d
     */
    public void addAll(List<T> d) {
        mData.clear();

        if (d != null) {
            mData.addAll(d);
        }

        notifyDataSetChanged();
    }

    /**
     * 清除源数据
     */
    @Deprecated
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /* 绑定ViewHolder 对象里的数据 */
    protected abstract void onBindViewHolder(BaseViewHolder viewHolder, int position);

    /* 创建 ViewHolder */
    protected abstract BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType);

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* LAYOUT ID */
    protected abstract int getLayoutResId(int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(getLayoutResId(position), null);
            holder      = onCreateViewHolder(convertView, parent, getItemViewType(position));
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        onBindViewHolder(holder, position);

        return convertView;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
