package com.peil.smartmoney.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseFragment;


public class CalculatorFragment extends BaseFragment {

    public static CalculatorFragment newInstance() {

        Bundle args = new Bundle();
        CalculatorFragment fragment = new CalculatorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calculator, container, false);
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
