package com.peil.smartmoney.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class AboutFragment extends BaseFragment {
    
    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    private QMUITopBarLayout bar_top;
    
    private QMUIGroupListView mGroupListView;
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container ,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_about , container , false);
        
        initView(view);
        
        return view;
    }
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("我的");
        
        mGroupListView = view.findViewById(R.id.groupListView);
        //      账户
        QMUICommonListItemView itemAccount =
            mGroupListView.createItemView(null , "功能介绍" , "" , QMUICommonListItemView.HORIZONTAL ,
                                          QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        
        //      创建时间
        QMUICommonListItemView itemCreateTime =
            mGroupListView.createItemView(null , "关于APP" , "" , QMUICommonListItemView.HORIZONTAL ,
                                          QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        
        QMUIGroupListView.newSection(_mActivity)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(itemAccount , new View.OnClickListener() {
                             @Override public void onClick(View v) {
                                 startActivity(new Intent(_mActivity , IntroduceActivity.class));
                             }
                         })
                         .addItemView(itemCreateTime , new View.OnClickListener() {
                             @Override public void onClick(View v) {
                                 startActivity(new Intent(_mActivity , AboutActivity.class));
                             }
                         })
                         .addTo(mGroupListView);
    }
}
