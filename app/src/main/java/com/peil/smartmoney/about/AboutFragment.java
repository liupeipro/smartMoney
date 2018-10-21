package com.peil.smartmoney.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class AboutFragment extends BaseFragment {
    private QMUITopBarLayout bar_top;
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("我的");
    }
    
    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_about, container, false);
        
        initView(view);
        
        return view;
    }
    
    @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
