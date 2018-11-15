package com.peil.smartmoney.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.peil.smartmoney.util.ResHelper;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomTextBarTab extends FrameLayout implements IBarTab {
    private int mTabPosition = -1;
    private TextView mTvTitle;
    private Context mContext;
    
    public BottomTextBarTab(Context context , CharSequence title) {
        this(context , null , title);
    }
    
    public BottomTextBarTab(Context context , AttributeSet attrs , CharSequence title) {
        this(context , attrs , 0 , title);
    }
    
    public BottomTextBarTab(Context context , AttributeSet attrs , int defStyleAttr ,
                            CharSequence title) {
        super(context , attrs , defStyleAttr);
        init(context , title);
    }
    
    private int dip2px(Context context , float dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , dp ,
                                              context.getResources().getDisplayMetrics());
    }
    
    private void init(Context context , CharSequence title) {
        mContext = context;
        
        TypedArray typedArray =
            context.obtainStyledAttributes(new int[] { R.attr.selectableItemBackgroundBorderless });
        Drawable drawable = typedArray.getDrawable(0);
        
        setBackgroundDrawable(drawable);
        typedArray.recycle();
        
        LinearLayout lLContainer = new LinearLayout(context);
        
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT);
        
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);
        mTvTitle = new TextView(context);
        mTvTitle.setText(title);
        
        LinearLayout.LayoutParams paramsTv =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,
                                          ViewGroup.LayoutParams.WRAP_CONTENT);
        
        paramsTv.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 4 ,
                                                            getResources().getDisplayMetrics());
        paramsTv.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 4 ,
                                                               getResources().getDisplayMetrics());
        paramsTv.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 10 ,
                                                             getResources().getDisplayMetrics());
        paramsTv.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 10 ,
                                                              getResources().getDisplayMetrics());
        mTvTitle.setTextSize(14);
        mTvTitle.setTextColor(ResHelper.getColor(context , R.color.tab_unselect));
        mTvTitle.setLayoutParams(paramsTv);
        lLContainer.addView(mTvTitle);
        addView(lLContainer);
    }
    
    @Override public void setSelected(boolean selected) {
        super.setSelected(selected);
        
        if (selected) {
            mTvTitle.setTextColor(ResHelper.getColor(mContext,  R.color.blue_normal));
        } else {
            mTvTitle.setTextColor(ResHelper.getColor(mContext, R.color.tab_unselect));
        }
    }
    
    @Override public void setTabLayoutParams(LinearLayout.LayoutParams params) {
        super.setLayoutParams(params);
    }
    
    @Override public void setTabOnClickListener(OnClickListener listener) {
        super.setOnClickListener(listener);
    }
    
    @Override public int getTabPosition() {
        return mTabPosition;
    }
    
    public void setTabPosition(int position) {
        mTabPosition = position;
        
        if (position == 0) {
            setSelected(true);
        }
    }
    
    @Override public void setTabSelected(boolean selected) {
        setSelected(selected);
    }
    
    @Override public View getTabView() {
        return this;
    }

    public void setTitleSize(float size){
        if(mTvTitle != null){
            mTvTitle.setTextSize(size);
        }
    }

    public void setTitleColor(int color){
        if(mTvTitle != null){
            mTvTitle.setTextColor(color);
        }
    }
}


