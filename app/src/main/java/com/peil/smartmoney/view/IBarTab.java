package com.peil.smartmoney.view;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by YoKeyword on 16/6/3.
 */
public interface IBarTab {
    void setTabLayoutParams(LinearLayout.LayoutParams params);
    
    void setTabOnClickListener(View.OnClickListener listener);
    
    int getTabPosition();
    
    void setTabPosition(int position);
    
    void setTabSelected(boolean selected);
    
    View getTabView();
}


