package com.peil.smartmoney.base;

import android.app.Application;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;

public class MoneyApplication extends Application {

    public MoneyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG).install();
    }
}
