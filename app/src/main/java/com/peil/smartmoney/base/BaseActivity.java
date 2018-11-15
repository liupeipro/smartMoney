package com.peil.smartmoney.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

public abstract class BaseActivity extends Activity {
    protected LocalBroadcastManager mLocalBroadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        initData();
        initController();
    }

    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initController();


    public void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(getApplicationContext());
        }

        mLocalBroadcast.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(getApplicationContext());
        }

        mLocalBroadcast.sendBroadcast(intent);
    }

    public void unRegistLocalReceiver(BroadcastReceiver receiver) {
        if (null == mLocalBroadcast) {
            mLocalBroadcast = LocalBroadcastManager.getInstance(getApplicationContext());
        }

        mLocalBroadcast.unregisterReceiver(receiver);
    }


}


