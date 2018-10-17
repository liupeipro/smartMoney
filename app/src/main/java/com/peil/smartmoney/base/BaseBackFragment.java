package com.peil.smartmoney.base;

import android.content.Context;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseBackFragment extends SupportFragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();
        return true;
    }

}