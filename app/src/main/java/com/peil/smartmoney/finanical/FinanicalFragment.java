package com.peil.smartmoney.finanical;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class FinanicalFragment extends BaseFragment {
    private QMUITopBarLayout bar_top;
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("理财");
        bar_top.addRightTextButton("股票", R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                
                       //              startActivity(new Intent(_mActivity.getApplicationContext(), CostAddActivity.class));
                   }
               });
    }
    
    public static FinanicalFragment newInstance() {
        Bundle args = new Bundle();
        FinanicalFragment fragment = new FinanicalFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_finanical, container, false);
        
        initView(view);
        
        return view;
    }
    
    @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
