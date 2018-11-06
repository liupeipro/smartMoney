package com.peil.smartmoney.base;

import android.content.Context;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseBackFragment extends SupportFragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}


