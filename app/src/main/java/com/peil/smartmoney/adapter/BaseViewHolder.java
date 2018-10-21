package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;

/**
 * Created by hoperun01 on 2017/3/21.
 */
public class BaseViewHolder {
    protected Context mContext;
    protected final View mItemView;
    protected final int mHolderType;
    
    public BaseViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        
        mItemView = itemView;
        mHolderType = -1;
        mContext = mItemView.getContext();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
