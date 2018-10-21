package com.peil.smartmoney.cost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseBackFragment;

public class CostOutFragment extends BaseBackFragment {
    private void initView(View view) {}

    public static CostOutFragment newInstance() {
        Bundle          args     = new Bundle();
        CostOutFragment fragment = new CostOutFragment();

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost_out, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
