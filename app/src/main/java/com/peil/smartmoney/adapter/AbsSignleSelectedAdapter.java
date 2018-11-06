package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoperun01 on 2017/3/11.
 */
public abstract class AbsSignleSelectedAdapter<T> extends BaseAdapter {
    protected ArrayList<T> mData = new ArrayList<T>();

    // 管理选中的状态，选中后的ID会add到里面，移除了就删除掉
    private Integer mSelectedPosition = -1;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public AbsSignleSelectedAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
    }

    public void add(T t) {
        if (t != null) {
            mData.add(t);
        }
    }

    public void add(int index, T t) {
        if (t != null) {
            mData.add(index, t);
        }
    }

    /**
     * 设置源数据
     */
    public void addAll(List<T> d) {
        mData.clear();

        if (d != null) {
            mData.addAll(d);
        }

        notifyDataSetChanged();
    }

    public void setSelectedPosition(Integer index) {
        if (mSelectedPosition != index) {
            this.mSelectedPosition = index;
        }
    }

    public Integer getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public void resetSelectedPosition() {
        this.mSelectedPosition = -1;
    }

    /**
     * 清除源数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /* 绑定ViewHolder 对象里的数据 */
    protected abstract void onBindViewHolder(BaseViewHolder viewHolder, int position);

    /* 创建 ViewHolder */
    protected abstract BaseViewHolder onCreateViewHolder(View view, ViewGroup parent,
                                                         int viewType);

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

    /**
     * 该value在 list中是否存在
     *
     * @param position value
     */
    public boolean hasSelected(int position) {
        return mSelectedPosition == position;
    }

    public T getSelectedItem() {
        T result = null;
        if (mSelectedPosition != -1) {
            result = getItem(mSelectedPosition);
        }

        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(getLayoutResId(position), null);
            holder = onCreateViewHolder(convertView, parent, getItemViewType(position));
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        onBindViewHolder(holder, position);

        return convertView;
    }
}


