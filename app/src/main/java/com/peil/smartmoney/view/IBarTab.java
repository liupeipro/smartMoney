package com.peil.smartmoney.view;

import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by YoKeyword on 16/6/3.
 */
public interface IBarTab {

    View getTabView();

    int getTabPosition();

    void setTabSelected(boolean selected);

    void setTabPosition(int position);

    void setTabOnClickListener(View.OnClickListener listener);

    void setTabLayoutParams(LinearLayout.LayoutParams params);

}
