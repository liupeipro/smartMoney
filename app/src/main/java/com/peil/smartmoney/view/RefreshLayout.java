package com.peil.smartmoney.view;

import android.content.Context;
import android.util.AttributeSet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

public class RefreshLayout extends QMUIPullRefreshLayout {
    public RefreshLayout(Context context) {
        super(context);
    }
    
    public RefreshLayout(Context context , AttributeSet attrs) {
        super(context , attrs);
    }
    
    public RefreshLayout(Context context , AttributeSet attrs , int defStyleAttr) {
        super(context , attrs , defStyleAttr);
    }
    
    public void doRefresh() {
        onRefresh();
    }
}


