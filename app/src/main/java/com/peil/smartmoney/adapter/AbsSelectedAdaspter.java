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

public abstract class AbsSelectedAdaspter<T> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<T> mData = new ArrayList<T>();
    //管理选中的状态，选中后的ID会add到里面，移除了就删除掉
    private ArrayList<Integer> mSelectedArray = new ArrayList<Integer>();

    public AbsSelectedAdaspter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(getLayoutResId(position), null);

            holder = onCreateViewHolder(convertView, parent,
                    getItemViewType(position));

            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        onBindViewHolder(holder, position);
        return convertView;
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

    public void add(T t) {
        if (t != null) {
            mData.add(t);
        }
    }

    public void add(int index,T t) {
        if (t != null) {
            mData.add(index,t);
        }
    }

    /**
     * 清除源数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 设置选中标志
     *
     * @param position
     */
    public void setSelectedState(int position) {
        // 如果没有该值，则添加
        if (!hasSelected(position)) {
            mSelectedArray.add(position);
        }
    }

    /**
     * 移除选中标志
     *
     * @param positon
     */
    public void removeSelectedState(int positon) {
        // 如果有该值，则删除
        if (hasSelected(positon)) {
            //remove的是value。需要转换成Object类型来做删除
            mSelectedArray.remove(Integer.valueOf(positon));
        }
    }

    /**
     * 该value在 list中是否存在
     *
     * @param position value
     * @return
     */
    public boolean hasSelected(int position) {
        return mSelectedArray.contains(position);
    }

    /**
     * 清除存储选中id的list
     */
    public void clearSelectedArray() {
        mSelectedArray.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置选中的ID并更新
     *
     * @param selectedArray
     */
    public void addAllSelectedArray(ArrayList<Integer> selectedArray) {
        mSelectedArray.clear();
        if (selectedArray != null) {
            mSelectedArray.addAll(selectedArray);
        }
        notifyDataSetChanged();
    }

    /**
     * 克隆选中的list
     *
     * @param cloneList 克隆后保存的对象
     */
    public void cloneSelectedArray(ArrayList<Integer> cloneList) {
        if (cloneList != null) {
            cloneList.clear();
            for (Integer i : mSelectedArray) {
                cloneList.add(i);
            }
        }
    }

    /* LAYOUT ID */
    protected abstract int getLayoutResId(int position);

    /* 创建 ViewHolder */
    protected abstract BaseViewHolder onCreateViewHolder(View view,
                                                           ViewGroup parent, int viewType);

    /* 绑定ViewHolder 对象里的数据 */
    protected abstract void onBindViewHolder(BaseViewHolder viewHolder,
                                             int position);


}
