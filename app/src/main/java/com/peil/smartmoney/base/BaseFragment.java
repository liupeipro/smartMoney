package com.peil.smartmoney.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.peil.smartmoney.calculator.CalculatorFragment;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment {
    protected OnBackToFirstListener _mBackToFirstListener;
    protected LocalBroadcastManager mLocalBroadcast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnBackToFirstListener");
        }
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof CalculatorFragment) {    // 如果是 第一个Fragment 则退出app
                _mActivity.finish();
            } else {                                     // 如果不是,则回到第一个Fragment
                _mBackToFirstListener.onBackToFirstFragment();
            }
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    public void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(_mActivity.getApplicationContext());
        }

        mLocalBroadcast.registerReceiver(receiver, filter);
    }

    public void sendLocalBroadcast(Intent intent) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(_mActivity.getApplicationContext());
        }

        mLocalBroadcast.sendBroadcast(intent);
    }

    public void unRegistLocalReceiver(BroadcastReceiver receiver) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(_mActivity.getApplicationContext());
        }

        mLocalBroadcast.unregisterReceiver(receiver);
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}


