package com.peil.smartmoney.cost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


public class CostFragment extends BaseFragment {

    public static CostFragment newInstance() {
        Bundle args = new Bundle();
        CostFragment fragment = new CostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    QMUIRoundButton btn_add;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_mActivity.getApplicationContext(), CostAddActivity.class));
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }

}
