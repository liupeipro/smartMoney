package com.peil.smartmoney.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class BaseActivity extends Activity {
    protected LocalBroadcastManager mLocalBroadcast;

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


//~ Formatted by Jindent --- http://www.jindent.com
