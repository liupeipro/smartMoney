package com.peil.smartmoney.cost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;


public class CostFragment extends BaseFragment {

    public static CostFragment newInstance() {

        Bundle args = new Bundle();
        CostFragment fragment = new CostFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost, container, false);
        initView();
        return view;
    }

    private void initView() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }

}
